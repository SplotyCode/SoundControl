package team.gutterteam123.soundcontrol.gui;

import team.gutterteam123.soundcontrol.SoundControl;
import team.gutterteam123.soundcontrol.sound.Controller;
import team.gutterteam123.soundcontrol.sound.device.Flowable;

import javax.swing.*;
import java.awt.*;

public class SoundMenu extends JMenuBar {

    public SoundMenu(Gui gui) {
        JMenu soundControl = new JMenu("Sound Control");
        JMenu reloadMenu = new JMenu("Reload");
        JMenu newMenu = new JMenu("New");
        add("Input", newMenu, () -> new AddElementPopup(true));
        add("Output", newMenu, () -> new AddElementPopup(false));
        add("Channel", newMenu, AddChannelPopup::new);

        add("All", reloadMenu, () -> Controller.getInstance().hardReload(true));
        add("Mixer", reloadMenu, () -> Controller.getInstance().updateMixers());

        add("Toggle View", soundControl, () -> {
            gui.setInFlow(!gui.isInFlow());
            gui.remove(!gui.isInFlow() ? gui.getFlowComponent() : gui.getOptionComponent());
            gui.add(gui.isInFlow() ? gui.getFlowComponent() : gui.getOptionComponent());
            gui.revalidate();
            gui.repaint();
        }, 'U');
        add("Re-Pos", soundControl, () -> {
            for (Flowable flowable : Controller.getInstance().getAllFlowables().values()) {
                SoundControl.getInstance().getGui().getFlowComponent().positionFlowable(flowable);
                SoundControl.getInstance().getGui().reloadFlow();
            }
        });
        add("Exit", soundControl, () -> System.exit(0));
        add(soundControl);
        add(reloadMenu);
        add(newMenu);
    }

    private void add(String name, JMenu menu, Runnable runnable) {
        JMenuItem item = new JMenuItem(name);
        item.addActionListener(actionEvent -> runnable.run());
        menu.add(item);
    }

    private void add(String name, JMenu menu, Runnable runnable, char ch) {
        JMenuItem item = new JMenuItem(name);
        item.setAccelerator(KeyStroke.getKeyStroke(ch, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        item.addActionListener(actionEvent -> runnable.run());
        menu.add(item);
    }

}
