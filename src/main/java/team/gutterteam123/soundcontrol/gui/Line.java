package team.gutterteam123.soundcontrol.gui;

import javax.swing.*;
import java.awt.*;

public class Line extends JComponent {
    Point point1;
    Point point2;
    public Line(Point from, Point to){
        point1 = from;
        point2 = to;
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawLine(point1.x,point1.y,point2.x,point2.y);

    }
}
