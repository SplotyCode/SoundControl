package team.gutterteam123.soundcontrol.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Button {
    private static JButton button = new JButton();
    private static int start = Toolkit.getDefaultToolkit().getScreenSize().width/2 + 120;

    public static final int INPUTBUTTON = 1;
    public static final int CHANNELBUTTON = 2;
    public static final int OUTPUTBUTTON = 3;
    public static final int CONNECTIONBUTTON = 4;

    public static JButton getButton(int i){

        switch (i){
            case 1:{
                button.setText("add as Input");
                button.setPreferredSize(new Dimension(120,42));
                button.setLocation(start, 40);
                break;

            }
            case 2:{
                button.setText("add as Channel");
                button.setPreferredSize(new Dimension(120,42));
                button.setLocation(start + 140,40);
                break;

            }
            case 3:{
                button.setText("add as Output");
                button.setPreferredSize(new Dimension(120,42));
                button.setLocation(start + 280,40);
                break;
            }
            case 4:{
                button.setText("add Connection");
                button.setPreferredSize(new Dimension(120,42));
                button.setLocation(start - 120, 30);
            }

        }

    return button;
    }

    public void addActionListener(ActionListener al) {
        button.addActionListener(al);
    }
}
