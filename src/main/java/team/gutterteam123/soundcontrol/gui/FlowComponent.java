package team.gutterteam123.soundcontrol.gui;

import team.gutterteam123.soundcontrol.settings.FlowableSetting;
import team.gutterteam123.soundcontrol.sound.Channel;
import team.gutterteam123.soundcontrol.sound.Controller;
import team.gutterteam123.soundcontrol.sound.device.Flowable;
import team.gutterteam123.soundcontrol.sound.device.VirtualDevice;
import team.gutterteam123.soundcontrol.sound.device.VirtualInput;

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
                if (event.getClickCount() == 2 && drag != null) {
                    new FlowableSetting(drag);
                }
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

    private long currentID;

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        currentID++;
        Graphics2D g = (Graphics2D) graphics;

        g.setColor(BACKGROUND);
        g.fillRect(0, 0, getWidth(), getHeight());

        for (Flowable flowable : Controller.getInstance().getAllFlowables().values()) {
            if (flowable instanceof Channel) {
                Channel channel = (Channel) flowable;
                for (VirtualDevice device : channel.getInputs()) {
                    device.checkDrawState(currentID);
                }
                for (VirtualDevice device : channel.getOutputs()) {
                    device.checkDrawState(currentID);
                }
            }
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
        }
        for (Channel channel : Controller.getInstance().getActiveChannels()) {
            /* POINTS & LINES */
            g.setColor(ACCENT);

            int left = channel.getInputs().size() + 1;
            int rigth = channel.getOutputs().size() + 1;
            for (int i = 1 ; i < left + 1; i++) {
                int pointY = channel.getPosition().y + channel.getPosition().height / left * i / 2  - 4;
                g.fillOval(channel.getPosition().x - 4, pointY,8, 8);
                int i2 = 0;
                for (VirtualInput input : channel.getInputs()) {
                    int inY = input.getPosition().y + input.getPosition().height / input.getConnections() * i2 / 2  + 4;
                    g.fillOval(channel.getPosition().x + channel.getPosition().width - 4, pointY,8, 8);
                    g.drawLine(channel.getPosition().x + channel.getPosition().width - 4, inY, channel.getPosition().x - 4, pointY);
                    i2++;
                }
            }
            for (int i = 1 ; i < rigth + 1; i++) {
                int pointY = channel.getPosition().y + channel.getPosition().height / left * i / 2  - 4;
                g.fillOval(channel.getPosition().x + channel.getPosition().width - 4, pointY,8, 8);
                int i2 = 0;
                for (VirtualInput input : channel.getInputs()) {
                    int inY = input.getPosition().y + input.getPosition().height / input.getConnections() * i2 / 2  + 4;
                    g.fillOval(channel.getPosition().x - 4, pointY,8, 8);
                    g.drawLine(channel.getPosition().x - 4, inY, channel.getPosition().x - 4, pointY);
                    i2++;
                }
            }
        }
        g.setColor(ACCENT);
        for (Flowable flowable : Controller.getInstance().getAllFlowables().values()) {
            if (!(flowable instanceof Channel) && flowable.getConnections() == 0) {
                Rectangle rec = flowable.getPosition();
                if (flowable instanceof VirtualInput) {
                    g.fillOval(rec.x - 4, rec.y + rec.height / 2 - 4,8, 8);
                } else {
                    g.fillOval(rec.x + rec.width - 4, rec.y + rec.height / 2 - 4,8, 8);
                }
            }
        }
    }

    public Flowable getFlowabl(Point point) {
        for (Flowable flowable : Controller.getInstance().getAllFlowables().values()) {
            if (flowable.getPosition().contains(point)) {
                return flowable;
            }
        }
        return null;
    }

}
