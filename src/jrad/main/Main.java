package jrad.main;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import jrad.util.*;
import jrad.main.singleplayer.*;
import jrad.main.multiplayer.*;

/**
 * Created by jackrademacher on 10/5/16.
 */
public class Main extends MouseAdapter implements ActionListener {

    private JFrame mainframe = new JFrame("Light Cycles");
    private Background screen = new Background();

    private JButton single = new JButton("Singleplayer");
    private JButton multi = new JButton("Multiplayer");
    private JButton quit = new JButton("Quit");
    private JButton howToPlay = new JButton("How to Play");
    private JButton settings = new JButton("Settings");
    private JButton about = new JButton("About");

    private boolean musicOn;
    private int frameInterval;
    private int aiDifficulty;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                Utility.initFiles();
                Utility.importSettings();

                Music.load("assets/endofline.wav");
                if(Utility.isMusicOn()) {
                    Music.start();
                }
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
        screen.add(Box.createVerticalGlue());
        screen.add(makeButton(single, 30));

        screen.add(Box.createVerticalGlue());
        screen.add(makeButton(multi, 30));

        screen.add(Box.createVerticalGlue());
        screen.add(makeButton(quit, 30));

        JPanel lower = new JPanel();
        lower.setOpaque(false);
        lower.setMaximumSize(new Dimension(500,75));
        lower.add(makeButton(howToPlay, 15));
        lower.add(makeButton(settings, 15));
        lower.add(makeButton(about, 15));

        screen.add(Box.createVerticalGlue());
        screen.add(lower);

        mainframe.add(screen);
        mainframe.setVisible(true);
    }

    // creates desired button
    private JButton makeButton(JButton b, int fontSize) {
        b.addActionListener(this);
        b.addMouseListener(this);
        b.setFont(new Font("Futura", Font.PLAIN, fontSize));
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setForeground(Color.WHITE);
        b.setAlignmentX(Component.CENTER_ALIGNMENT);

        return b;
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
        else if(src.equals(howToPlay))
            howToPlay.setForeground(new Color(129,210,224));
        else if(src.equals(settings))
            settings.setForeground(new Color(129,210,224));
        else if(src.equals(about))
            about.setForeground(new Color(129,210,224));

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
        else if(src.equals(howToPlay))
            howToPlay.setForeground(Color.WHITE);
        else if(src.equals(settings))
            settings.setForeground(Color.WHITE);
        else if(src.equals(about))
            about.setForeground(Color.WHITE);

    }

    // detects ActionEvents
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if(src.equals(single))
            new SinglePlayerMain(mainframe);
        else if(src.equals(multi)) {
            new MultiPlayerMain();
            mainframe.dispose();
        }
        else if(src.equals(quit))
            mainframe.dispose();
        else if(src.equals(howToPlay)) {
            mainframe.invalidate();
            JScrollPane s = new JScrollPane(new InfoPanel(InfoPanel.HOWTOPLAY));
            s.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            mainframe.setContentPane(s);
            mainframe.revalidate();
        }
        else if(src.equals(settings)) {
            mainframe.invalidate();
            InfoPanel i = new InfoPanel(InfoPanel.SETTINGS);
            mainframe.setContentPane(i);
            mainframe.revalidate();
        }
        else if(src.equals(about)) {
            mainframe.invalidate();
            InfoPanel i = new InfoPanel(InfoPanel.ABOUT);
            mainframe.setContentPane(i);
            mainframe.revalidate();
        }
    }
}

// class exclusively for drawing background
class Background extends JPanel {
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(18,18,18));
        g.fillRect(0,0,2000,2000);
    }
}
