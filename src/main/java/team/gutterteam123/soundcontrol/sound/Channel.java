package team.gutterteam123.soundcontrol.sound;

import io.github.splotycode.mosaik.domparsing.annotation.FileSystem;
import io.github.splotycode.mosaik.domparsing.annotation.parsing.SerialisedEntryParser;
import io.github.splotycode.mosaik.runtime.LinkBase;
import io.github.splotycode.mosaik.runtime.Links;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import team.gutterteam123.soundcontrol.sound.device.VirtualInput;
import team.gutterteam123.soundcontrol.sound.device.VirtualOutput;

import java.util.ArrayList;

@Getter
@NoArgsConstructor
public class Channel {

    //public static transient final FileSystem<Channel> FILE_SYSTEM = LinkBase.getInstance().getLink(Links.PARSING_FILEPROVIDER).provide("channel", new SerialisedEntryParser());

    private transient ArrayList<VirtualInput> inputs = new ArrayList<>();
    private transient ArrayList<VirtualOutput> outputs = new ArrayList<>();

    private ArrayList<String> inputNames = new ArrayList<>();
    private ArrayList<String> outputNames = new ArrayList<>();

    private String name;

    public Channel(String name) {
        this.name = name;
    }

    @Setter private float volume = 1;

}
