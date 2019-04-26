package team.gutterteam123.soundcontrol.sound.device;

import io.github.splotycode.mosaik.domparsing.annotation.FileSystem;
import io.github.splotycode.mosaik.domparsing.annotation.parsing.SerialisedEntryParser;
import io.github.splotycode.mosaik.runtime.LinkBase;
import io.github.splotycode.mosaik.runtime.Links;
import lombok.Getter;
import lombok.Setter;
import team.gutterteam123.soundcontrol.sound.Channel;
import team.gutterteam123.soundcontrol.sound.Controller;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import java.util.ArrayList;
import java.util.List;

@Getter
public class VirtualOutput extends VirtualDevice<SourceDataLine> {

    public static transient final FileSystem<VirtualOutput> FILE_SYSTEM = LinkBase.getInstance().getLink(Links.PARSING_FILEPROVIDER).provide("output", new SerialisedEntryParser());

    private transient final byte[] buffer = new byte[Controller.BUFFER_SIZE];

    private transient ArrayList<Channel> channels = new ArrayList<>();

    @Setter private float volume;


    public void flushBuffer() {
        line.write(buffer, 0, buffer.length);
    }

    @Override
    public boolean openLine() {
        if (!super.openLine()) return false;
        try {
            line = (SourceDataLine) mixer.getLine(Controller.OUTPUT_INFO);
            line.open(Controller.FORMAT);
            line.start();
        } catch (LineUnavailableException e) {
            return false;
        }
        return true;
    }
}
