package team.gutterteam123.soundcontrol.gui;


import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;

public class Gui extends Frame {
    String separator = System.getProperty("file.separator");
    Point[] points = new Point[2];
    Rectangle[] rectanglesInput = new Rectangle[1048];
    Rectangle[] rectanglesChannel = new Rectangle[1048];
    Rectangle[] rectanglesOutput = new Rectangle[1048];
    private int screenX = 0;
    private int screenY = 0;
    private int boxX = 0;
    private int boxY = 0;
    JFrame popupFrame = new JFrame();
    int deltaX;
    int deltaY;
    Box button = new Box();
    Button addConnectionButton = new Button();
    Button addInputButton = new Button();
    Button addChannelButton = new Button();
    Button addOutputButton = new Button();
    JPanel PopupPanel = new JPanel();
    TextField textFieldInput = new TextField();
    TextField textFieldOutput = new TextField();
    TextField textFieldChannel = new TextField();
    Button submitButton = new Button();
    JFrame frame = new JFrame();
    JSlider slider = new JSlider();
    int inputNumberThatContains;
    int channelNumberThatContains;
    int outputNumberThatContains;
    int notNeeded;
    int needed;
    boolean Input;




    public void buildGui(int width, int height){
        System.out.println(points.length);
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
        PopupPanel.add(addInputButton);
        PopupPanel.add(addChannelButton);
        PopupPanel.add(addOutputButton);
        textFieldChannel.setPreferredSize(dimension);
        textFieldInput.setPreferredSize(dimension);
        PopupPanel.setVisible(true);
        popupFrame.add(PopupPanel);

        frame.add(button);

        popupFrame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setJMenuBar(new SoundMenu());
        popupFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        File imageFile= new File("data" + separator +"Icon.jpg");
        try {
            Image image = ImageIO.read(imageFile);
            frame.setIconImage(image);
            popupFrame.setIconImage(image);
        } catch (IOException e) {
            System.err.println("Image file not found: " + e);
        }
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());

        slider.addChangeListener(changeEvent -> {
        });
        addInputButton.addActionListener(e -> {
            String inputText = textFieldInput.getText();
            frame.repaint();
        });
        addConnectionButton.addActionListener(e -> {

        });
        popupFrame.setSize(700,500);
        popupFrame.setVisible(true);
        frame.setTitle("Manage Inputs and Outputs");


        frame.setVisible(true);
    }

    public void drawLine(Rectangle[] checkingInputs, Rectangle[] checkingChannels, Rectangle[] checkingOutputs) {
        button.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                while (points.length < 2) {
                    points[points.length] = MouseInfo.getPointerInfo().getLocation();
                }

                button.removeMouseListener(this);
                for (int e = 0; e < points.length; e++) {
                    for (int i = 0; i < checkingInputs.length; i++) {
                        if (checkingInputs[i].contains(points[e])) {
                            inputNumberThatContains = i;
                            notNeeded = e;
                            Input = true;
                            break;
                        }
                    }

                }
                if (Input = false) {
                    for (int e = 0; e < points.length; e++) {
                        for (int i = 0; i < checkingOutputs.length; i++) {
                            if (checkingOutputs[i].contains(points[e])) {
                                outputNumberThatContains = i;
                            }
                        }
                    }


                }
                Input = false;

                if (notNeeded == 1) {
                    needed = 0;
                }
                for (int i = 0; i < checkingChannels.length; i++) {
                    if (checkingChannels[i].contains(points[needed])) {
                        channelNumberThatContains = i;
                        break;
                    }

                }

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
