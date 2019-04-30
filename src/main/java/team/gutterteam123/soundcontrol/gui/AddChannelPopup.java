package team.gutterteam123.soundcontrol.gui;

import io.github.splotycode.mosaik.util.StringUtil;
import io.github.splotycode.mosaik.util.collection.ArrayUtil;
import team.gutterteam123.soundcontrol.SoundControl;
import team.gutterteam123.soundcontrol.sound.Channel;
import team.gutterteam123.soundcontrol.sound.Controller;
import team.gutterteam123.soundcontrol.sound.device.Flowable;

import javax.swing.*;
import java.awt.*;

public class AddChannelPopup {

    protected JTextField name = new JTextField();
    protected String title;

    public AddChannelPopup() {
        this("Create Channel");
        show();
    }

    public AddChannelPopup(String title) {
        this.title = title;
    }

    public void show(JComponent... other) {
        name.setPreferredSize(new Dimension(100, 20));

        other = ArrayUtil.resize(other, other.length + 1);
        ArrayUtil.setLast(other, name);

        int result = GuiUtil.showInput(SoundControl.getInstance().getGui(), title, other);
        if (result == JOptionPane.YES_OPTION) {
            if (!StringUtil.isEmpty(name.getText()) && !Controller.getInstance().getAllFlowables().containsKey(name.getText())) {
                Flowable flowable = newFlowable();
                if (flowable == null) {
                    reopen();
                    return;
                }
                SoundControl.getInstance().getGui().getFlowComponent().positionFlowable(flowable);
                Controller.getInstance().update(flowable);

                Controller.getInstance().hardReload(true);
            } else {
                reopen();
            }
        }
    }

    protected Flowable newFlowable() {
        return new Channel(name.getText());
    }

    protected void reopen() {
        new AddChannelPopup();
    }



}
