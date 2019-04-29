package team.gutterteam123.soundcontrol.gui;


import lombok.Getter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;

public class Gui extends JFrame {

    String separator = System.getProperty("file.separator");

    Point[] points = new Point[2];
    Rectangle[] rectanglesInput = new Rectangle[1048];
    Rectangle[] rectanglesChannel = new Rectangle[1048];
    Rectangle[] rectanglesOutput = new Rectangle[1048];
    private int screenX = 0;
    private int screenY = 0;
    private int boxX = 0;
    private int boxY = 0;
    int deltaX;
    int deltaY;
    int inputNumberThatContains;
    int channelNumberThatContains;
    int outputNumberThatContains;
    int notNeeded;
    int needed;
    boolean Input;


    JFrame popupFrame = new JFrame();
    Box button = new Box();
    Button MainButton = new Button();
    JButton InputButton = new JButton();
    JButton ChannelButton = new JButton();
    JButton OutputButton = new JButton();
    JButton ConnectionButton = new JButton();
    Panel PopupPanel = new Panel();
    TextField textFieldInput = new TextField();
    TextField textFieldOutput = new TextField();
    TextField textFieldChannel = new TextField();
    Button submitButton = new Button();
    JSlider slider = new JSlider();
    Font font = new Font("MyFont",Font.BOLD,36);


    @Getter private FlowComponent flowComponent = new FlowComponent();


    public void reloadFlow() {
        flowComponent.repaint();
    }

    public void buildGui(int width, int height){
        System.out.println(points.length);
        Font font = new Font("MyFont",Font.BOLD,36);
        textFieldInput.setFont(font);
        textFieldChannel.setFont(font);
        PopupPanel.add(textFieldInput);
        Dimension dimension = new Dimension(36,248);

        InputButton = MainButton.getButton(Button.INPUTBUTTON);
        ChannelButton = MainButton.getButton(Button.CHANNELBUTTON);
        OutputButton = MainButton.getButton(Button.OUTPUTBUTTON);
        ConnectionButton = MainButton.getButton(Button.CONNECTIONBUTTON);

        PopupPanel.add(InputButton);
        PopupPanel.add(ChannelButton);
        PopupPanel.add(OutputButton);

        popupFrame.add(PopupPanel);
        popupFrame.pack();
        popupFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        textFieldChannel.setPreferredSize(dimension);
        textFieldInput.setPreferredSize(dimension);

        add(flowComponent);

        popupFrame.pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setJMenuBar(new SoundMenu());


        File imageFile= new File("data" + separator +"Icon.jpg");
        try {
            Image image = ImageIO.read(imageFile);
            setIconImage(image);
            popupFrame.setIconImage(image);
        } catch (IOException e) {
            System.err.println("Image file not found: " + e);
        }
        setSize(Toolkit.getDefaultToolkit().getScreenSize());

        slider.addChangeListener(changeEvent -> {
        });
        InputButton.addActionListener(e -> {
            String inputText = textFieldInput.getText();
            repaint();
        });

        ConnectionButton.addActionListener(actionEvent -> {

        });
        popupFrame.setSize(700,500);
        //popupFrame.setVisible(true);
        setTitle("Manage Inputs and Outputs");


        setVisible(true);
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
