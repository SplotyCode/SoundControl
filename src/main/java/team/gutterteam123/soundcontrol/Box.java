package team.gutterteam123.soundcontrol;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Box extends JComponent {
    private String text;
    private Color color;
    private Color textColor;
    public void setText(String s){
        text = s;
    }
    public void setColor(Color c){
        color = c;
    }
    public void setTextColor(Color c){
        textColor=c;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(color);
        g.fillRect(0,0,100,100);
        g.create(0,0,100,100);
        g.setColor(textColor);
        g.drawString(text,25,15);


    }
}
