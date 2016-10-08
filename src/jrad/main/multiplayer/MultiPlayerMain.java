package jrad.main.multiplayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import jrad.graphics.*;
import jrad.util.*;
import jrad.net.*;
import jrad.main.*;

/**
 * Created by jackrademacher on 10/5/16.
 */
public class MultiPlayerMain extends MouseAdapter implements ActionListener, FocusListener {

    private JFrame multiframe = new JFrame("Multiplayer");
    private JPanel mainScreen = new JPanel();
    private PreGraphicsPanel graphics = new PreGraphicsPanel(1);

    private Font defaultFont = new Font("Futura", Font.PLAIN, 30);

    private JButton musicButton = new JButton("Music On");
    private JButton host = new JButton("I will host this game");
    private JButton client = new JButton("I want to join a friend's game");
    private JButton back = new JButton("Back to main");

    private JTextField nameField = new JTextField();
    private JLabel nameLabel = new JLabel("Enter Name:");

    public MultiPlayerMain() {

        multiframe.setSize(1000,625);
        multiframe.setResizable(false);
        multiframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        graphics.setLayout(new GridLayout(1,1));

        mainScreen.setLayout(new BoxLayout(mainScreen, BoxLayout.Y_AXIS));
        mainScreen.setOpaque(false);

        createAndShowGUI();

        multiframe.setVisible(true);
    }

    // techincal stuff, formatting, not much to explain
    private void createAndShowGUI() {
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(defaultFont);
        nameLabel.setOpaque(false);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        nameField.setMaximumSize(new Dimension(200,25));
        nameField.setFont(new Font("Futura", Font.PLAIN, 20));
        nameField.setHorizontalAlignment(JTextField.CENTER);
        nameField.addFocusListener(this);

        addButton(musicButton, 20);

        mainScreen.add(Box.createVerticalStrut(120));

        mainScreen.add(nameLabel);
        mainScreen.add(nameField);

        mainScreen.add(Box.createVerticalStrut(200));

        addButton(host, 30);
        addButton(client, 30);

        mainScreen.add(Box.createVerticalGlue());

        addButton(back, 20);

        graphics.add(mainScreen);
        multiframe.setContentPane(graphics);
    }

    // adds button with desired properties
    private void addButton(JButton b, int fontSize) {
        b.setFont(new Font("Futura", Font.PLAIN, fontSize));
        b.setForeground(Color.WHITE);
        b.setAlignmentX(Component.CENTER_ALIGNMENT);
        b.setBorderPainted(false);
        b.addActionListener(this);
        b.addMouseListener(this);

        mainScreen.add(b);
    }

    // changes color when hovering
    public void mouseEntered(MouseEvent e) {
        Object src = e.getSource();

        if(src.equals(host))
            host.setForeground(new Color(129,210,224));
        else if(src.equals(client))
            client.setForeground(new Color(129,210,224));
        else if(src.equals(back))
            back.setForeground(new Color(129,210,224));
        else if(src.equals(musicButton))
            musicButton.setForeground(new Color(129,210,224));
    }

    // same here
    public void mouseExited(MouseEvent e) {
        Object src = e.getSource();

        if(src.equals(host))
            host.setForeground(Color.WHITE);
        else if(src.equals(client))
            client.setForeground(Color.WHITE);
        else if(src.equals(back))
            back.setForeground(Color.WHITE);
        else if(src.equals(musicButton))
            musicButton.setForeground(Color.WHITE);
    }

    // changes label when text field focus is gained
    public void focusGained(FocusEvent e) {
        nameLabel.setText("Enter Name:");
    }

    public void focusLost(FocusEvent e) {
    }

    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        // tests if host button was clicked
        if(src.equals(host)) {
            if(nameField.getText().equals(""))
                nameLabel.setText("You need a name!");
            else {
                multiframe.invalidate();

                // initializes waiting room
                HostRoom hroom = new HostRoom(nameField.getText());
                hroom.initGUI();

                multiframe.setContentPane(hroom);
                multiframe.revalidate();

                hroom.begin();
            }
        }
        else if(src.equals(client)) {
            if(nameField.getText().equals(""))
                nameLabel.setText("You need a name!");
            else {
                multiframe.invalidate();

                // initializes waiting room
                ClientRoom croom = new ClientRoom(nameField.getText());
                croom.initGUI();

                multiframe.setContentPane(croom);
                multiframe.revalidate();
            }
        }
        else if(src.equals(back)) {
            multiframe.setVisible(false);
            new Main();
            multiframe.dispose();
        }
        else if(src.equals(musicButton)) {
            if(musicButton.getText().equals("Music On")) {
                Music.stop();
                musicButton.setText("Music Off");
            }
            else {
                Music.start();
                musicButton.setText("Music On");
            }
        }
    }
}
