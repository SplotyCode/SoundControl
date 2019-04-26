package team.gutterteam123.soundcontrol.sound;

import lombok.Getter;
import team.gutterteam123.soundcontrol.sound.device.VirtualInput;
import team.gutterteam123.soundcontrol.sound.device.VirtualOutput;

import java.util.ArrayList;

@Getter
public class Channel {

    private ArrayList<VirtualInput> inputs = new ArrayList<>();
    private ArrayList<VirtualOutput> outputs = new ArrayList<>();

    private double volume;

}
