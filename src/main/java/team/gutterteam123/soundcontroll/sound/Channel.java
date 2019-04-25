package team.gutterteam123.soundcontroll.sound;

import team.gutterteam123.soundcontroll.sound.device.VirtualInput;
import team.gutterteam123.soundcontroll.sound.device.VirtualOutput;

import java.util.ArrayList;

public class Channel {

    private ArrayList<VirtualInput> inputs = new ArrayList<>();
    private ArrayList<VirtualOutput> outputs = new ArrayList<>();

    private double volume;

}
