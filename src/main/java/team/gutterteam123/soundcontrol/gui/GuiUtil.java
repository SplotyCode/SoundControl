package team.gutterteam123.soundcontrol.gui;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.swing.*;
import java.awt.*;

//TODO https://stackoverflow.com/a/19864657/8738512
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GuiUtil {

    public static Rectangle grow(Rectangle rectangle, int grow) {
        Rectangle rec = rectangle.getBounds();
        rec.grow(grow, grow);
        return rec;
    }

    public static int showInput(Window frame, String title, JComponent... components) {
        return JOptionPane.showConfirmDialog(frame, components, title, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    }

}
