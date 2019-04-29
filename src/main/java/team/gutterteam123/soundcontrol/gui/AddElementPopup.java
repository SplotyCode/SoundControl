package team.gutterteam123.soundcontrol.gui;

import team.gutterteam123.soundcontrol.sound.Controller;
import team.gutterteam123.soundcontrol.sound.device.Flowable;
import team.gutterteam123.soundcontrol.sound.device.VirtualInput;
import team.gutterteam123.soundcontrol.sound.device.VirtualOutput;

import javax.swing.*;

public class AddElementPopup extends AddChannelPopup {

    private JComboBox<String> mixerBox;
    private boolean input;

    public AddElementPopup(boolean input) {
        super("Create " + (input ? "Input" : "Output"));
        this.input = input;
        mixerBox = new JComboBox<>(input ? Controller.getInstance().getInputMixers().toArray(new String[] {}) :
                Controller.getInstance().getOutputMixers().toArray(new String[] {}));;
        show(mixerBox);
    }

    @Override
    protected Flowable newFlowable() {
        if (input) {
            return new VirtualInput(name.getText(), (String) mixerBox.getSelectedItem());
        }
        return new VirtualOutput(name.getText(), (String) mixerBox.getSelectedItem());
    }

    @Override
    protected void reopen() {
        new AddElementPopup(input);
    }
}
