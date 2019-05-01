package team.gutterteam123.soundcontrol.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Line extends JComponent {
    Point mouseLocation;
    Point pointLocation;

    public Line() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                super.mousePressed(mouseEvent);
                addMouseMotionListener(new MouseAdapter() {
                    @Override
                    public void mouseMoved(MouseEvent mouseEvent) {
                        super.mouseDragged(mouseEvent);
                    }
                });
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

    }
}