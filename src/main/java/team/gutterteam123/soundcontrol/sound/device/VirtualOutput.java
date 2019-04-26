package team.gutterteam123.soundcontrol.sound.device;

import io.github.splotycode.mosaik.domparsing.annotation.FileSystem;
import io.github.splotycode.mosaik.domparsing.annotation.parsing.SerialisedEntryParser;
import io.github.splotycode.mosaik.runtime.LinkBase;
import io.github.splotycode.mosaik.runtime.Links;
import lombok.Getter;
import team.gutterteam123.soundcontrol.sound.Channel;
import team.gutterteam123.soundcontrol.sound.Controller;

import java.util.List;

public class VirtualOutput extends VirtualDevice {

    public static final FileSystem<VirtualOutput> FILE_SYSTEM = LinkBase.getInstance().getLink(Links.PARSING_FILEPROVIDER).provide("output", new SerialisedEntryParser());

    @Getter private final byte[] buffer = new byte[Controller.BUFFER_SIZE];

    @Getter private float volume;

    public List<Channel> getChannels() {
        return null;
    }

    public void flushBuffer() {

    }

    @Override
    public String name() {
        return null;
    }
}
