package team.gutterteam123.soundcontrol.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Line extends JComponent {
    Point mouseLocation;
    Point pointLocation;
    public Line(){
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                super.mousePressed(mouseEvent);
                addMouseMotionListener(new MouseMotionListener() {
                    @Override
                    public void mouseDragged(MouseEvent mouseEvent) {
                        mouseLocation = mouseEvent.getPoint();
                        pointLocation.setLocation(mouseLocation);
                        setLocation(pointLocation);

                    }

                    @Override
                    public void mouseMoved(MouseEvent mouseEvent) {

                    }
                });
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                super.mouseReleased(mouseEvent);
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                super.mouseEntered(mouseEvent);
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                super.mouseExited(mouseEvent);
            }

            @Override
            public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
                super.mouseWheelMoved(mouseWheelEvent);
            }

            @Override
            public void mouseDragged(MouseEvent mouseEvent) {
                super.mouseDragged(mouseEvent);
            }

            @Override
            public void mouseMoved(MouseEvent mouseEvent) {
                super.mouseMoved(mouseEvent);
            }
        });
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);


    }
}
