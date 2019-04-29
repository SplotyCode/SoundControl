package team.gutterteam123.soundcontrol.sound.device;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import team.gutterteam123.soundcontrol.sound.Controller;

import javax.sound.sampled.DataLine;
import javax.sound.sampled.Mixer;
import java.awt.*;
import java.io.Serializable;

@NoArgsConstructor
@EqualsAndHashCode
public abstract class VirtualDevice<L extends DataLine> implements Flowable {

    protected String mixerName;
    protected String name;
    protected transient L line;
    protected transient Mixer mixer;

    @Getter @Setter private Rectangle position = new Rectangle(-1, -1, 100, 100);

    public VirtualDevice(String name, String mixerName) {
        this.name = name;
        this.mixerName = mixerName;
    }

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
