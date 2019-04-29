package team.gutterteam123.soundcontrol.gui;

import javax.swing.*;
import java.awt.*;

public class Box extends JComponent {
    private Rectangle box;
    private String text;
    private Color color;
    private Color textColor;
    private int height;
    private  int width;
    private int x;
    private int y;
    public void setText(String s){
        text = s;
    }
    public void setColor(Color c){
        color = c;
    }
    public void setTextColor(Color c){
        textColor=c;
    }
    public Point getPosition(){
        return (new Point(box.x,box.y));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(color);
        FlowComponent flowComponent = new FlowComponent();
        flowComponent.paintComponent(g);
    }

    @Override
    public void repaint() {
        super.repaint();
    }
}
