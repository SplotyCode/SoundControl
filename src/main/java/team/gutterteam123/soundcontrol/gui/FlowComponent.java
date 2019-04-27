package team.gutterteam123.soundcontrol.gui;

import team.gutterteam123.soundcontrol.sound.Channel;
import team.gutterteam123.soundcontrol.sound.device.Flowable;
import team.gutterteam123.soundcontrol.sound.device.VirtualInput;
import team.gutterteam123.soundcontrol.sound.device.VirtualOutput;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class FlowComponent extends JComponent {

    private Flowable drag;

    public FlowComponent() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent event) {
                drag = null;
            }

            @Override
            public void mousePressed(MouseEvent event) {
                drag = getFlowabl(event.getPoint());
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent event) {
                if (drag != null) {
                    Point old = drag.getPosition().getLocation();
                    Point point = new Point(event.getX() - drag.getPosition().width / 2,
                                            event.getY() - drag.getPosition().height / 2);
                    drag.getPosition().setLocation(point);
                    if (!getBounds(null).contains(old)) {
                        point.setLocation(old);
                    }
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g = (Graphics2D) graphics;
        for (Flowable flowable : getElements()) {
            int x = flowable.getPosition().x;
            int y = flowable.getPosition().y;
            int w = flowable.getPosition().width;
            int h = flowable.getPosition().height;

            /* BOX */
            g.setColor(Color.PINK);
            g.fillRect(x, y, w, h);

            /* TEXT */
            g.setColor(Color.GRAY);
            g.drawString(flowable.name(),
                    x + (w - g.getFontMetrics().stringWidth(flowable.name())) / 2,
                    y + (h - g.getFontMetrics().getHeight()) / 2);

            /* POINTS */
            if (!(flowable instanceof VirtualOutput)) {
                int numRigth = 3; //TODO GET REAL VALUE (+1)
                g.drawOval(x + w, y + h / 2, 2, 2);
            }
            if (!(flowable instanceof VirtualInput)) {
                int numLeft = 3; //TODO GET REAL VALUE (+1)
                g.drawOval(x, y + h / 2, 2, 2);
            }
        }
    }

    public ArrayList<Flowable> getElements() {
        ArrayList<Flowable> box = new ArrayList<>();
        box.addAll(VirtualInput.FILE_SYSTEM.getEntries());
        box.addAll(VirtualOutput.FILE_SYSTEM.getEntries());
        box.addAll(Channel.FILE_SYSTEM.getEntries());
        return box;
    }

    public Flowable getFlowabl(Point point) {
        for (Flowable flowable : getElements()) {
            if (flowable.getPosition().contains(point)) {
                return flowable;
            }
        }
        return null;
    }

}
