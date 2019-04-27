package team.gutterteam123.soundcontrol.sound.device;

import io.github.splotycode.mosaik.domparsing.annotation.FileSystem;
import io.github.splotycode.mosaik.domparsing.annotation.parsing.SerialisedEntryParser;
import io.github.splotycode.mosaik.runtime.LinkBase;
import io.github.splotycode.mosaik.runtime.Links;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.gutterteam123.soundcontrol.sound.Controller;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class VirtualInput extends VirtualDevice<TargetDataLine> {

    public static final FileSystem<VirtualInput> FILE_SYSTEM = LinkBase.getInstance().getLink(Links.PARSING_FILEPROVIDER).provide("inputs", new SerialisedEntryParser());

    @Getter private final transient byte[] buffer = new byte[Controller.BUFFER_SIZE];
    @Getter private final transient short[] sampleBuffer = new short[Controller.SAMPLE_BUFFER_SIZE];
    @Getter private transient int lastReadSize;

    public VirtualInput(String name, String mixerName) {
        super(name, mixerName);
    }

    public void updateBuffer() {
        lastReadSize = line.read(buffer, 0, buffer.length);
    }

    public void generateSamples() {
        for (int i = 0; i < Controller.BUFFER_SIZE; i+=Controller.FRAME_SIZE) {
            // convert byte pair to short
            short buf1 = (short) ((buffer[i+1] & 0xff) << 8);
            short buf2 = (short) (buffer[i] & 0xff);

            sampleBuffer[i / Controller.FRAME_SIZE] = (short) (buf1 | buf2);
        }
    }

    @Override
    public boolean openLine() {
        if (!super.openLine()) return false;
        try {
            if (!mixer.isLineSupported(Controller.INPUT_INFO)) return false;
            line = (TargetDataLine) mixer.getLine(Controller.INPUT_INFO);
            line.open(Controller.FORMAT);
            line.start();
        } catch (LineUnavailableException e) {
            return false;
        }
        return line != null && line.isOpen();
    }
}
