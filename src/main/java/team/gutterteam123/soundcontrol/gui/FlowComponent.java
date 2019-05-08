package team.gutterteam123.soundcontrol.gui;

import team.gutterteam123.soundcontrol.settings.FlowableSetting;
import team.gutterteam123.soundcontrol.sound.Channel;
import team.gutterteam123.soundcontrol.sound.Controller;
import team.gutterteam123.soundcontrol.sound.device.Flowable;
import team.gutterteam123.soundcontrol.sound.device.VirtualDevice;
import team.gutterteam123.soundcontrol.sound.device.VirtualInput;
import team.gutterteam123.soundcontrol.sound.device.VirtualOutput;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class FlowComponent extends JComponent {

    private Flowable drag;
    private Flowable point1;

    public FlowComponent() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent event) {
                drag = null;
            }

            @Override
            public void mousePressed(MouseEvent event) {
                drag = getFlowabl(event.getPoint());
                if (drag == null) return;
                int con = drag.getConnections() + 1;
                int pointY = drag.getPosition().y + drag.getPosition().height / con * con / 2  - 4;
                Rectangle p1 = new Rectangle(drag.getPosition().x - 4, pointY, 8, 8);
                Rectangle p2 = new Rectangle(drag.getPosition().x + drag.getPosition().width - 4, pointY, 8, 8);

                if ((p1.contains(event.getPoint()) && !(drag instanceof VirtualInput)) ||
                        (p2.contains(event.getPoint()) && !(drag instanceof VirtualOutput))) {
                    if (point1 == null) {
                        point1 = drag;
                    } else {
                        System.out.println(point1 + " " + drag);
                        Channel channel = null;
                        VirtualDevice target = null;
                        if (point1 instanceof Channel) {
                            channel = (Channel) point1;
                            if (!(drag instanceof Channel)) {
                                target = (VirtualDevice) drag;
                            }
                        } else if (drag instanceof Channel) {
                            channel = (Channel) drag;
                            target = (VirtualDevice) point1;
                        }
                        System.out.println(channel + " " + target);
                        if (channel != null && target != null) {
                            if (target instanceof VirtualInput) {
                                channel.getInputNames().add(target.name());
                            } else {
                                channel.getOutputNames().add(target.name());
                            }
                            Controller.getInstance().update(channel);
                            Controller.getInstance().hardReload(true);
                        }
                        point1 = null;
                    }
                    drag = null;
                } else if (event.getClickCount() == 2 && drag != null) {
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

    private int getYScaling(Rectangle position, int max, int current) {
        int scale = position.height / max;
        return position.y + (scale * current - scale / 2);
    }

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
            int right = channel.getOutputs().size() + 1;
            Map<VirtualInput, Integer> counters = new HashMap<>();
            for (int i = 1 ; i < left + 1; i++) {
                int pointY = getYScaling(channel.getPosition(), left, i)  - 4;
                g.fillOval(channel.getPosition().x - 4, pointY,8, 8);
                if (channel.getInputs().size() >= i) {
                    VirtualInput input = channel.getInputs().get(i - 1);
                    Integer rawInt = counters.get(input);
                    int i2 = rawInt == null ? 1 : rawInt;
                    int inY = getYScaling(input.getPosition(), input.getConnections(), i2) - 4;
                    g.fillOval(input.getPosition().x + input.getPosition().width - 4, inY,8, 8);
                    g.drawLine(input.getPosition().x + input.getPosition().width, inY + 4, channel.getPosition().x, pointY + 4);
                    if (counters.containsKey(input)) {
                        counters.put(input, counters.get(input) + 1);
                    } else {
                        counters.put(input, 1);
                    }
                }
            }
            Map<VirtualOutput, Integer> outCount = new HashMap<>();
            for (int i = 1 ; i < right + 1; i++) {
                int pointY = getYScaling(channel.getPosition(), right, i) - 4;
                g.fillOval(channel.getPosition().x + channel.getPosition().width- 4, pointY,8, 8);
                if (channel.getOutputs().size() >= i) {
                    VirtualOutput output = channel.getOutputs().get(i - 1);
                    Integer rawInt = outCount.get(output);
                    int i2 = rawInt == null ? 1 : rawInt;
                    int inY = getYScaling(output.getPosition(), output.getConnections(), i2) - 4;
                    g.fillOval(output.getPosition().x - 4, inY,8, 8);
                    g.drawLine(output.getPosition().x, inY + 4, channel.getPosition().x + channel.getPosition().width, pointY + 4);
                    if (outCount.containsKey(output)) {
                        outCount.put(output, outCount.get(output) + 1);
                    } else {
                        outCount.put(output, 1);
                    }
                }
            }
        }
        g.setColor(ACCENT);
        for (Flowable flowable : Controller.getInstance().getAllFlowables().values()) {
            if (!(flowable instanceof Channel) && flowable.getConnections() == 0) {
                Rectangle rec = flowable.getPosition();
                if (flowable instanceof VirtualInput) {
                    g.fillOval(rec.x + rec.width - 4, rec.y + rec.height / 2 - 4,8, 8);
                } else {
                    g.fillOval(rec.x  - 4, rec.y + rec.height / 2 - 4,8, 8);
                }
            }
        }
    }

    public Flowable getFlowabl(Point point) {
        for (Flowable flowable : Controller.getInstance().getAllFlowables().values()) {
            if (GuiUtil.grow(flowable.getPosition(), 4).contains(point)) {
                return flowable;
            }
        }
        return null;
    }

}
