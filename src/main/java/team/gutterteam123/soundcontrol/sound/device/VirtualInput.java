package team.gutterteam123.soundcontrol.sound.device;

import io.github.splotycode.mosaik.domparsing.annotation.FileSystem;
import io.github.splotycode.mosaik.domparsing.annotation.parsing.SerialisedEntryParser;
import io.github.splotycode.mosaik.runtime.LinkBase;
import io.github.splotycode.mosaik.runtime.Links;
import lombok.Getter;
import team.gutterteam123.soundcontrol.sound.Controller;

public class VirtualInput extends VirtualDevice {

    public static final FileSystem<VirtualInput> FILE_SYSTEM = LinkBase.getInstance().getLink(Links.PARSING_FILEPROVIDER).provide("inputs", new SerialisedEntryParser());

    @Getter private final byte[] buffer = new byte[Controller.BUFFER_SIZE];
    @Getter private final short[] shortBuffer = new short[Controller.BUFFER_SIZE / 2];

    public void updateBuffer() {

    }

}
