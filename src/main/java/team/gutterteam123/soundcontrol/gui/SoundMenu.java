package team.gutterteam123.soundcontrol.gui;

import team.gutterteam123.soundcontrol.SoundControl;
import team.gutterteam123.soundcontrol.sound.Controller;
import team.gutterteam123.soundcontrol.sound.device.Flowable;

import javax.swing.*;

public class SoundMenu extends JMenuBar {

    public SoundMenu() {
        JMenu soundControl = new JMenu("Sound Control");
        JMenu newMenu = new JMenu("New");
        add("Input", newMenu, () -> new AddElementPopup(true));
        add("Output", newMenu, () -> new AddElementPopup(false));
        add("Channel", newMenu, AddChannelPopup::new);
        add("Re-Pos", soundControl, () -> {
            for (Flowable flowable : Controller.getInstance().getAllFlowables().values()) {
                SoundControl.getInstance().getGui().getFlowComponent().positionFlowable(flowable);
                SoundControl.getInstance().getGui().reloadFlow();
            }
        });
        add("Reload", soundControl, () -> Controller.getInstance().hardReload(true));
        add("Exit", soundControl, () -> System.exit(0));
        add(soundControl);
        add(newMenu);
    }

    private void add(String name, JMenu menu, Runnable runnable) {
        JMenuItem item = new JMenuItem(name);
        item.addActionListener(actionEvent -> runnable.run());
        menu.add(item);
    }

}
