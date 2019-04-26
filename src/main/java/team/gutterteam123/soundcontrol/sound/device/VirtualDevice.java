package team.gutterteam123.soundcontrol.sound.device;

import team.gutterteam123.soundcontrol.sound.Controller;

import javax.sound.sampled.DataLine;
import javax.sound.sampled.Mixer;
import java.io.Serializable;

public abstract class VirtualDevice<L extends DataLine> implements Serializable {

    protected String mixerName;
    protected String name;
    protected transient L line;
    protected transient Mixer mixer;

    public String name() {
        return name;
    }

    public String mixerName() {
        return mixerName;
    }

    public L line() {
        return line;
    }

    public boolean openLine() {
        mixer = Controller.getInstance().getMixerByName(mixerName);
        return mixer != null;
    }

    public void closeLine() {
        if (line != null) {
            line.stop();
            line.close();
        }
    }

}
