package team.gutterteam123.soundcontrol.sound.device;

import javax.sound.sampled.DataLine;
import javax.sound.sampled.Mixer;
import java.io.Serializable;

public abstract class VirtualDevice implements Serializable {

    private String mixerName;
    private String name;
    private transient DataLine line;
    private transient Mixer mixer;

    public String name() {
        return name;
    }

    public String mixerName() {
        return mixerName;
    }

    public DataLine line() {
        return line;
    }

}
