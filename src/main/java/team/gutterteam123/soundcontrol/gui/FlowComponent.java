package team.gutterteam123.soundcontrol.gui;

import team.gutterteam123.soundcontrol.sound.Channel;
import team.gutterteam123.soundcontrol.sound.Controller;
import team.gutterteam123.soundcontrol.sound.device.Flowable;
import team.gutterteam123.soundcontrol.sound.device.VirtualInput;
import team.gutterteam123.soundcontrol.sound.device.VirtualOutput;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.concurrent.ThreadLocalRandom;

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
                System.out.println("set " + drag);
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
                    if (!validPos(drag)) {
                        drag.getPosition().setLocation(old);
                    } else {
                        Controller.getInstance().update(drag);
                        repaint();
                    }
                }
            }
        });
    }

    public void positionFlowable(Flowable flowable) {
        while (!validPos(flowable)) {
            System.out.println("new position");
            //max is -1
            flowable.getPosition().setLocation(ThreadLocalRandom.current().nextInt(10, getWidth() - 11),
                                               ThreadLocalRandom.current().nextInt(10, getHeight() - 11));
        }
    }

    private boolean validPos(Flowable flow) {
        Rectangle rectangle = flow.getPosition().getBounds();
        rectangle.grow(10, 10);
        if (!getBounds(null).contains(rectangle)) return false;
        for (Flowable flowable : Controller.getInstance().getAllFlowables().values()) {
            if (flowable.getPosition().intersects(rectangle) && flow != flowable) {
                return false;
            }
        }
        return true;
    }

    private static final Color FOREGROUND = Color.decode("#393939");
    private static final Color FONT = Color.decode("#b7b7b7");
    private static final Color ACCENT = Color.decode("#83e1e4");
    private static final Color BACKGROUND = Color.decode("#202020");

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g = (Graphics2D) graphics;

        g.setColor(BACKGROUND);
        g.fillRect(0, 0, getWidth(), getHeight());

        for (Flowable flowable : Controller.getInstance().getAllFlowables().values()) {
            int x = flowable.getPosition().x;
            int y = flowable.getPosition().y;
            int w = flowable.getPosition().width;
            int h = flowable.getPosition().height;

            while (x + w > getWidth() - 10) {
                x--;
                flowable.getPosition().setLocation(x, y);
            }

            while (y + h > getHeight() - 10) {
                y--;
                flowable.getPosition().setLocation(x, y);
            }

            /* BOX */
            g.setColor(FOREGROUND);
            g.fillRoundRect(x, y, w, h, 16, 16);

            /* TEXT */
            g.setColor(FONT);
            g.drawString(flowable.name(),
                    x + (w - g.getFontMetrics().stringWidth(flowable.name())) / 2,
                    y + ((h - g.getFontMetrics().getHeight()) / 2) + g.getFontMetrics().getAscent());

            g.setColor(ACCENT);

            /* POINTS */
            if (!(flowable instanceof VirtualOutput)) {
                int numRigth = 3; //TODO GET REAL VALUE (+1)
                g.fillOval(x + w - 4, y + h / 2 - 4, 8, 8);
            }
            if (!(flowable instanceof VirtualInput)) {
                int numLeft = 3; //TODO GET REAL VALUE (+1)
                g.fillOval(x - 4, y + h / 2 - 4, 8, 8);
            }
        }
        for (Channel channel : Controller.getInstance().getActiveChannels()) {

        }
    }

    public Flowable getFlowabl(Point point) {
        for (Flowable flowable : Controller.getInstance().getAllFlowables().values()) {
            System.out.println(flowable.getPosition() + " " + point);
            if (flowable.getPosition().contains(point)) {
                return flowable;
            }
        }
        return null;
    }

}
