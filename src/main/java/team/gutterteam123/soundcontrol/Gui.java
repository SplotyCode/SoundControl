package team.gutterteam123.soundcontrol;


import team.gutterteam123.soundcontrol.sound.Channel;
import team.gutterteam123.soundcontrol.sound.device.VirtualInput;
import team.gutterteam123.soundcontrol.sound.device.VirtualOutput;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Gui extends Frame {
    Point[] points = new Point[2];
    Rectangle[] rectanglesInput = new Rectangle[1048];
    Rectangle[] rectanglesChannel = new Rectangle[1048];
    Rectangle[] rectanglesOutput = new Rectangle[1048];
    private volatile int screenX = 0;
    private volatile int screenY = 0;
    private volatile int boxX = 0;
    private volatile int boxY = 0;
    final Frame popupFrame = new Frame();
    Button addConnectionButton = new Button();
    Button addInputButton = new Button();
    Button addChannelButton = new Button();
    Button addOutputButton = new Button();
    Panel PopupPanel = new Panel();
    TextField textFieldInput = new TextField();
    TextField textFieldOutput = new TextField();
    TextField textFieldChannel = new TextField();
    Button submitButton = new Button();
    Panel panel = new Panel();
    Frame frame = new Frame();
    JSlider slider = new JSlider();
    int inputNumberThatContains;
    int channelNumberThatContains;
    int outputNumberThatContains;
    int notNedded;
    int nedded;
    boolean Input;
    Map<VirtualInput, Channel> virtualInputChannelMap = new HashMap<>();
    Map<VirtualOutput, Channel> virtualOutputChannelMap = new HashMap<>();



    public void buildGui(int width, int height){
        popupFrame.setLayout(new BoxLayout(popupFrame,BoxLayout.Y_AXIS));
        Font font = new Font("MyFont",Font.BOLD,36);
        textFieldInput.setFont(font);
        textFieldChannel.setFont(font);
        PopupPanel.add(textFieldInput);
        submitButton.setLabel("Submit");
        addInputButton.setLabel("Add as Input");
        addChannelButton.setLabel("Add as Channel");
        addOutputButton.setLabel("Add as Output");
        addConnectionButton.setLabel("Add Connection");
        Dimension dimension = new Dimension();
        dimension.height = 36;
        dimension.width = 124*2;
        popupFrame.setLayout(new BoxLayout(popupFrame, BoxLayout.LINE_AXIS));
        PopupPanel.add(addInputButton);
        PopupPanel.add(addChannelButton);
        PopupPanel.add(addOutputButton);
        panel.add(addConnectionButton);
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
        panel.add(movableInputBox(new Dimension(100,100),"Input #1"));
        slider.addChangeListener(changeEvent ->

                {

        }

        );
        addInputButton.addActionListener(e -> {
            Input input = new Input();

            input.name = textFieldInput.getText();
            JButton box = movableInputBox(new Dimension(120,100), input.name);
            panel.add(box);
            input.rectangle = box.getBounds();
            frame.repaint();
        });
        addConnectionButton.addActionListener(e -> {

        });
        popupFrame.setSize(700,500);
        popupFrame.setVisible(true);
        frame.setTitle("Manage Inputs and Outputs");
        panel.setVisible(true);
        frame.add(panel);
        frame.setVisible(true);
    }
    public JButton movableInputBox(Dimension d, String text){
        JButton button = new JButton();
        button.setText(text);
        button.setEnabled(false);
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
                button.setLocation(boxX + deltaX,boxY + deltaY);
            }

            @Override
            public void mouseMoved(MouseEvent mouseEvent) {

            }
        });
    return  button;
    }
    /*public void drawPoint(Input[] checkingInputs, Channel[] checkingChannels, Output[] checkingOutputs){
        panel.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked (MouseEvent mouseEvent){
                if (points.length < 2) {
                    points[points.length] = MouseInfo.getPointerInfo().getLocation();
                } else {
                    panel.removeMouseListener(this);
                }
                    for(int e = 0; e < points.length ; e++){
                        for(int i = 0; i< checkingInputs.length ; i++) {
                            if (checkingInputs[i].contains(points[e])) {
                                inputNumberThatContains = i;
                                notNedded = e;
                                Input = true;
                                break;
                            }
                        }

                            }
                    if(Input = false){
                        for(int e = 0; e < points.length; e++){
                            for(int i = 0; i< checkingOutputs.length; i++){
                                if(checkingOutputs[i].contains(points[e])){
                                    outputNumberThatContains = i;
                                }
                            }
                        }


                    }
                    Input = false;

                    if(notNedded == 1){
                        nedded = 0;
                    }
                    for(int r = 0; r < checkingChannels.length; r++){
                        if(checkingChannels[r].contains(points[nedded])){
                            channelNumberThatContains = r;
                        }

                    }

                        }




                @Override
                public void mousePressed (MouseEvent mouseEvent){

            }

                @Override
                public void mouseReleased (MouseEvent mouseEvent){

            }

                @Override
                public void mouseEntered (MouseEvent mouseEvent){

            }

                @Override
                public void mouseExited (MouseEvent mouseEvent){

            }

        });




    } */

}
