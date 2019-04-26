package team.gutterteam123.soundcontrol;

import javax.imageio.ImageIO;
import javax.sound.sampled.Line;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;

public class Gui extends Frame {
    private volatile int screenX = 0;
    private volatile int screenY = 0;
    private volatile int boxX = 0;
    private volatile int boxY = 0;
    SoundControl soundControl = new SoundControl();
    Frame frame = new Frame();
    JSlider slider = new JSlider();



    public void buildGui(int width, int height){

        frame.setSize(width, height);
        File imageFile= new File("data/Icon.jpg");
        try {
            Image image = ImageIO.read(imageFile);
            frame.setIconImage(image);
        } catch (IOException e) {
            System.err.println("Image file not found: " + e);
        }
        frame.add(moveableBox());
        slider.addChangeListener(changeEvent ->

                {

        }

        );
        frame.setTitle("Sound Control");
        frame.setVisible(true);
    }
    public JButton moveableBox(){
        JButton button = new JButton();
        button.setBorder(new LineBorder(Color.BLACK,4));
        button.setBackground(Color.gray);
        button.setBounds(0,0,100,100);
        button.setOpaque(false);
        button.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {

            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                screenX = mouseEvent.getXOnScreen();
                screenY = mouseEvent.getYOnScreen();
                boxX = button.getX();
                boxY = button.getY();
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {

            }
        });
        button.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent mouseEvent) {
                int deltaX = mouseEvent.getXOnScreen() - screenX;
                int deltaY = mouseEvent.getYOnScreen() - screenY;
                button.setLocation(boxX + deltaX,boxY+deltaY);
            }

            @Override
            public void mouseMoved(MouseEvent mouseEvent) {

            }
        });
    return  button;
    }
}
