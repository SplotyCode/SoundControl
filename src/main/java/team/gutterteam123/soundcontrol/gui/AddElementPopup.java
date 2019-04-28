package team.gutterteam123.soundcontrol.gui;

import io.github.splotycode.mosaik.util.StringUtil;
import team.gutterteam123.soundcontrol.sound.Controller;
import team.gutterteam123.soundcontrol.sound.device.VirtualInput;
import team.gutterteam123.soundcontrol.sound.device.VirtualOutput;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

public class AddElementPopup extends JFrame {

    private JComboBox<String> mixerBox;
    private JTextField name = new JTextField();
    private Panel panel = new Panel();
    private JButton button = new JButton("Save");

    public AddElementPopup(boolean input) {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mixerBox = new JComboBox<>(input ? Controller.getInstance().getInputMixers().toArray(new String[] {}) :
                                           Controller.getInstance().getOutputMixers().toArray(new String[] {}));
        name.setPreferredSize(new Dimension(100, 20));
        panel.add(name);
        panel.add(mixerBox);
        panel.add(button);
        add(panel);
        pack();
        setVisible(true);
        button.addActionListener(listener -> {
            if (!StringUtil.isEmpty(name.getText())) {
                if (input) {
                    VirtualInput.FILE_SYSTEM.putEntry(name.getText(), new VirtualInput(name.getText(), (String) mixerBox.getSelectedItem()));
                } else {
                    VirtualOutput.FILE_SYSTEM.putEntry(name.getText(), new VirtualOutput(name.getText(), (String) mixerBox.getSelectedItem()));
                }
                dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
                Controller.getInstance().hardReload();
            }
        });
    }


}
