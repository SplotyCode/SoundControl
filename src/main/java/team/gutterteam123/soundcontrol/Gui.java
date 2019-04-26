package team.gutterteam123.soundcontrol;


import team.gutterteam123.soundcontrol.sound.Channel;
import team.gutterteam123.soundcontrol.sound.device.VirtualInput;
import team.gutterteam123.soundcontrol.sound.device.VirtualOutput;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Gui extends Frame {
    Point[] points = new Point[2];
    private volatile int screenX = 0;
    private volatile int screenY = 0;
    private volatile int boxX = 0;
    private volatile int boxY = 0;
    final Frame popupFrame = new Frame();
    Panel PopupPanel = new Panel();
    TextField textFieldInput = new TextField();
    TextField textFieldOutput = new TextField();
    TextField textFieldChannel = new TextField();
    Button submitButton = new Button();
    SoundControl soundControl = new SoundControl();
    Panel panel = new Panel();
    Frame frame = new Frame();
    JSlider slider = new JSlider();
    Map<VirtualInput, Channel> virtualInputChannelMap = new HashMap<>();
    Map<VirtualOutput, Channel> virtualOutputChannelMap = new HashMap<>();



    public void buildGui(int width, int height){
        popupFrame.setLayout(new BoxLayout(popupFrame,BoxLayout.Y_AXIS));
        Font font = new Font("MyFont",Font.BOLD,36);
        textFieldInput.setFont(font);
        textFieldChannel.setFont(font);
        PopupPanel.add(textFieldInput);
        submitButton.setLabel("Submit");
        Dimension dimension = new Dimension();
        dimension.height = 36;
        dimension.width = 124*2;
        popupFrame.setLayout(new BoxLayout(popupFrame, BoxLayout.LINE_AXIS));
        PopupPanel.add(submitButton);
        textFieldChannel.setPreferredSize(dimension);
        textFieldInput.setPreferredSize(dimension);
        panel.setPreferredSize(new Dimension(width,height));
        PopupPanel.setVisible(true);

        popupFrame.add(PopupPanel);

        popupFrame.pack();

        File imageFile= new File("data/Icon.jpg");
        try {
            Image image = ImageIO.read(imageFile);
            frame.setIconImage(image);
        } catch (IOException e) {
            System.err.println("Image file not found: " + e);
        }
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        panel.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        panel.add(movableInputBox(40,40, new Dimension(100,100)));
        slider.addChangeListener(changeEvent ->

                {

        }

        );
        popupFrame.setSize(700,500);
        popupFrame.setVisible(true);
        frame.setTitle("Manage Inputs and Outputs");
        panel.setVisible(true);
        frame.add(panel);
        frame.setVisible(true);
    }
    public JButton movableInputBox(int startX, int startY, Dimension d){
        JButton button = new JButton();
        button.setLocation(startX, startY);
        button.setBackground(Color.ORANGE);
        button.setBorder(new LineBorder(Color.BLACK,4));
        button.setPreferredSize(d);
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
    } public void drawPoint(Point[] checkingInputs, Point[] checkingChannels, Point[] checkingOutputs){
        panel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {

            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {

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


    }

}
