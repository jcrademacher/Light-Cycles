package jrad.main;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.*;
import java.awt.event.*;

import jrad.util.*;
import jrad.main.singleplayer.*;
import jrad.main.multiplayer.*;

/**
 * Created by jackrademacher on 10/5/16.
 */
public class Main extends MouseAdapter implements ActionListener {

    JFrame mainframe = new JFrame("Light Cycles");
    Background screen = new Background();

    JButton single = new JButton("Singleplayer");
    JButton multi = new JButton("Multiplayer");
    JButton quit = new JButton("Quit");

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                Music.load("assets/endofline.wav");
                Music.start();
                new Main(); // so as to not reference from static context
            }
        });
    }

    // more technical stuff
    public Main() {
        screen.setLayout(new BoxLayout(screen, BoxLayout.Y_AXIS));

        mainframe.setResizable(true);
        mainframe.setSize(500,500);
        mainframe.setLocation(500,100);
        mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // adds passed button with specified properties in method
        addButton(single);
        addButton(multi);
        addButton(quit);

        screen.add(Box.createVerticalGlue());
        mainframe.add(screen);

        mainframe.setVisible(true);
    }

    private void addButton(JButton b) {
        b.addActionListener(this);
        b.addMouseListener(this);
        b.setFont(new Font("Futura", Font.PLAIN, 30));
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setForeground(Color.WHITE);
        b.setAlignmentX(Component.CENTER_ALIGNMENT);

        screen.add(Box.createVerticalGlue());
        screen.add(b);
    }

    // makes button light up
    public void mouseEntered(MouseEvent e) {
        Object src = e.getSource();

        if(src.equals(single))
            single.setForeground(new Color(129,210,224));
        else if(src.equals(multi))
            multi.setForeground(new Color(129,210,224));
        else if(src.equals(quit))
            quit.setForeground(new Color(129,210,224));
    }

    // turns button white again
    public void mouseExited(MouseEvent e) {
        Object src = e.getSource();

        if(src.equals(single))
            single.setForeground(Color.WHITE);
        else if(src.equals(multi))
            multi.setForeground(Color.WHITE);
        else if(src.equals(quit))
            quit.setForeground(Color.WHITE);
    }

    // detects ActionEvents
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        mainframe.setVisible(false);

        if(src.equals(single))
            new SinglePlayerMain();

        else if(src.equals(multi))
            new MultiPlayerMain();

        mainframe.dispose();
    }
}

// class exclusively for drawing background
class Background extends JPanel {
    public void paintComponent(Graphics g) {
        g.setColor(new Color(18,18,18));
        g.fillRect(0,0,2000,2000);
    }
}
