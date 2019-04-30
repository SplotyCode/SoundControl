package team.gutterteam123.soundcontrol.sound;

import io.github.splotycode.mosaik.domparsing.annotation.FileSystem;
import io.github.splotycode.mosaik.domparsing.annotation.parsing.SerialisedEntryParser;
import io.github.splotycode.mosaik.runtime.LinkBase;
import io.github.splotycode.mosaik.runtime.Links;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import team.gutterteam123.soundcontrol.sound.device.Flowable;
import team.gutterteam123.soundcontrol.sound.device.VirtualInput;
import team.gutterteam123.soundcontrol.sound.device.VirtualOutput;

import java.awt.*;
import java.util.ArrayList;

@NoArgsConstructor
public class Channel extends Flowable {

    public static transient final FileSystem<Channel> FILE_SYSTEM = LinkBase.getInstance().getLink(Links.PARSING_FILEPROVIDER).provide("channel", new SerialisedEntryParser());

    @Getter private transient ArrayList<VirtualInput> inputs = new ArrayList<>();
    @Getter private transient ArrayList<VirtualOutput> outputs = new ArrayList<>();

    @Getter private ArrayList<String> inputNames = new ArrayList<>();
    @Getter private ArrayList<String> outputNames = new ArrayList<>();


    private String name;

    @Override
    public String name() {
        return name;
    }

    public Channel(String name) {
        this.name = name;
    }

    @Setter private float volume = 1;

}
