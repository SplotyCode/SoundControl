package team.gutterteam123.soundcontrol.settings;

import team.gutterteam123.soundcontrol.SoundControl;
import team.gutterteam123.soundcontrol.sound.device.Flowable;

import javax.swing.*;

public class FlowableSetting extends JTabbedPane {

    public FlowableSetting(Flowable flowable) {
        JOptionPane.showMessageDialog(SoundControl.getInstance().getGui(), this,"Settings for " + flowable.name(), JOptionPane.PLAIN_MESSAGE);
    }

}
