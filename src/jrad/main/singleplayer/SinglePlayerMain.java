package jrad.main.singleplayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import jrad.graphics.PreGraphicsPanel;
import jrad.util.*;
import jrad.main.Main;

/**
 * Created by jackrademacher on 10/5/16.
 */
public class SinglePlayerMain extends MouseAdapter implements ActionListener {

    private JFrame singleframe;
    private JPanel screen;
    private PreGraphicsPanel graphics;

    private JButton volume = new JButton("Music On");
    private JButton start = new JButton("Start Game");
    private JButton two = new JButton("Two Players");
    private JButton three = new JButton("Three Players");
    private JButton four = new JButton("Four Players");
    private JButton back = new JButton("Back to Main");

    private int numPlayers = 2;

    public SinglePlayerMain(JFrame previousFrame) {
        singleframe = previousFrame;
        screen = new JPanel();
        graphics = new PreGraphicsPanel(numPlayers);

        // layouts and settings
        graphics.setLayout(new GridLayout(1,1));

        screen.setLayout(new BoxLayout(screen, BoxLayout.Y_AXIS));
        screen.setOpaque(false);

        // sets up all UI elements
        createAndShowGUI();

        // adds to screen
        graphics.add(screen);
        singleframe.invalidate();
        singleframe.setContentPane(graphics);
        singleframe.setResizable(false);
        singleframe.setSize(1000,625);
        singleframe.setLocation(200,100);
        singleframe.revalidate();

    }

    public void createAndShowGUI() {
        initButtons();
    }

    // formatting stuff
    private void initButtons() {
        volume.addActionListener(this);
        start.addActionListener(this);
        two.addActionListener(this);
        three.addActionListener(this);
        four.addActionListener(this);
        back.addActionListener(this);

        volume.addMouseListener(this);
        start.addMouseListener(this);
        back.addMouseListener(this);

        volume.setFont(new Font("Futura", Font.PLAIN, 20));
        start.setFont(new Font("Futura", Font.BOLD, 40));
        two.setFont(new Font("Futura", Font.PLAIN, 30)); // bigger text indicates selected
        three.setFont(new Font("Futura", Font.PLAIN, 25));
        four.setFont(new Font("Futura", Font.PLAIN, 25));
        back.setFont(new Font("Futura", Font.PLAIN, 20));

        // makes it all look not like trash default java settings
        volume.setContentAreaFilled(false);
        start.setContentAreaFilled(false);
        two.setContentAreaFilled(false);
        three.setContentAreaFilled(false);
        four.setContentAreaFilled(false);
        back.setContentAreaFilled(false);

        volume.setBorderPainted(false);
        start.setBorderPainted(false);
        two.setBorderPainted(false);
        three.setBorderPainted(false);
        four.setBorderPainted(false);
        back.setBorderPainted(false);

        volume.setForeground(Color.WHITE);
        start.setForeground(Color.WHITE);
        two.setForeground(Color.WHITE);
        three.setForeground(Color.WHITE);
        four.setForeground(Color.WHITE);
        back.setForeground(Color.WHITE);

        volume.setAlignmentX(Component.CENTER_ALIGNMENT);
        start.setAlignmentX(Component.CENTER_ALIGNMENT);
        two.setAlignmentX(Component.CENTER_ALIGNMENT);
        three.setAlignmentX(Component.CENTER_ALIGNMENT);
        four.setAlignmentX(Component.CENTER_ALIGNMENT);
        back.setAlignmentX(Component.CENTER_ALIGNMENT);

        // rigid areas add space between components
        screen.add(volume);
        screen.add(Box.createRigidArea(new Dimension(0,40)));
        screen.add(two);
        screen.add(Box.createRigidArea(new Dimension(0,20)));
        screen.add(three);
        screen.add(Box.createRigidArea(new Dimension(0,20)));
        screen.add(four);
        screen.add(Box.createRigidArea(new Dimension(0,180)));
        screen.add(start);
        screen.add(back);
    }

    public void mouseEntered(MouseEvent e) {
        Object src = e.getSource();

        if(src.equals(volume))
            volume.setForeground(new Color(129,210,224));
        else if(src.equals(start))
            start.setForeground(new Color(129,210,224));
        else if(src.equals(back))
            back.setForeground(new Color(129,210,224));
    }

    public void mouseExited(MouseEvent e) {
        Object src = e.getSource();

        if(src.equals(volume))
            volume.setForeground(Color.WHITE);
        else if(src.equals(start))
            start.setForeground(Color.WHITE);
        else if(src.equals(back))
            back.setForeground(Color.WHITE);
    }

    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        // changes button state of volume
        if(src.equals(volume)) {
            if(Music.isOn()) {
                Music.setOn(false);
                Music.stop();
                volume.setText("Music Off");
            }
            else {
                Music.setOn(true);
                Music.start();
                volume.setText("Music On");
            }
        }

        if(src.equals(two)) {
            two.setFont(new Font("Futura", Font.PLAIN, 30));
            three.setFont(new Font("Futura", Font.PLAIN, 25));
            four.setFont(new Font("Futura", Font.PLAIN, 25));

            numPlayers = 2;
        }
        else if(src.equals(three)) {
            two.setFont(new Font("Futura", Font.PLAIN, 25));
            three.setFont(new Font("Futura", Font.PLAIN, 30));
            four.setFont(new Font("Futura", Font.PLAIN, 25));

            numPlayers = 3;
        }
        else if(src.equals(four)) {
            two.setFont(new Font("Futura", Font.PLAIN, 25));
            three.setFont(new Font("Futura", Font.PLAIN, 25));
            four.setFont(new Font("Futura", Font.PLAIN, 30));

            numPlayers = 4;
        }
        else if(src.equals(start)) {
            Music.stop();
            singleframe.setVisible(false);
            singleframe.dispose();

            // generic singleplayer game, last 4 nulls apply to a multiplayer game only
            GameDriver gamedriver = new GameDriver(numPlayers, "singleplayer",null,null,null,null);
            gamedriver.begin();
        }
        else if(src.equals(back)) {
            singleframe.setVisible(false);
            new Main();
            singleframe.dispose();
        }

        // sets vars and repaints background and required graphics by GPanel
        graphics.setNumPlayers(numPlayers);
        graphics.repaint();
    }
}
