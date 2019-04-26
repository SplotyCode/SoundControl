package team.gutterteam123.soundcontrol.sound.device;

import io.github.splotycode.mosaik.domparsing.annotation.FileSystem;
import io.github.splotycode.mosaik.domparsing.annotation.parsing.SerialisedEntryParser;
import io.github.splotycode.mosaik.runtime.LinkBase;
import io.github.splotycode.mosaik.runtime.Links;
import lombok.Getter;
import team.gutterteam123.soundcontrol.sound.Controller;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

public class VirtualInput extends VirtualDevice<TargetDataLine> {

    public static final FileSystem<VirtualInput> FILE_SYSTEM = LinkBase.getInstance().getLink(Links.PARSING_FILEPROVIDER).provide("inputs", new SerialisedEntryParser());

    @Getter private final transient byte[] buffer = new byte[Controller.BUFFER_SIZE];
    @Getter private final transient short[] shortBuffer = new short[Controller.BUFFER_SIZE / 2];
    @Getter private transient int lastReadSize;


    public void updateBuffer() {
        lastReadSize = line.read(buffer, 0, buffer.length);
    }

    @Override
    public boolean openLine() {
        if (!super.openLine()) return false;
        try {
            line = (TargetDataLine) mixer.getLine(Controller.INPUT_INFO);
            line.open(Controller.FORMAT);
            line.start();
        } catch (LineUnavailableException e) {
            return false;
        }
        return true;
    }
}
