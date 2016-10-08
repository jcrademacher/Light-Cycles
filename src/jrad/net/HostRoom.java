package jrad.net;

import javax.swing.*;
import java.awt.*;
import java.net.*;
import java.awt.event.*;
import jrad.main.multiplayer.*;
import jrad.main.singleplayer.*;
import jrad.player.*;

/**
 * Created by jackrademacher on 10/5/16.
 */
public class HostRoom extends JPanel implements MouseListener, ActionListener {

    private String ip;
    private String hostName;

    private JButton start = new JButton("Start Game");
    private JButton back = new JButton("Back");

    private JLabel[] playerLabels;
    private JButton[] acceptButtons;
    private JButton[] denyButtons;

    private JPanel gridpanel;
    private JPanel top, middle, bottom;

    private PlayerSocket p2, p3, p4;
    private ServerSocket s;

    private boolean hasUpdate;

    public HostRoom(String n) {
        playerLabels = new JLabel[3];
        acceptButtons = new JButton[3];
        denyButtons = new JButton[3];

        hostName = n;

        gridpanel = new JPanel();
        top = new JPanel();
        middle = new JPanel();
        bottom = new JPanel();

        hasUpdate = true;

        // gets computer's ip on network
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        }
        catch(Exception e) { System.out.println("Something happened");}

    }

    public void begin() {

        try {
            s = new ServerSocket(1235);

            // constructs new playersocket classes to handle socket concurrently
            p2 = new PlayerSocket(playerLabels[0], s, hostName);
            p3 = new PlayerSocket(playerLabels[1], s, hostName);
            p4 = new PlayerSocket(playerLabels[2], s, hostName);

            // begins process of looking for connections
            p2.start();
            p3.start();
            p4.start();
        }
        catch(Exception e) {
            System.out.println("Something happened constructing the server");
            e.printStackTrace();
        }
    }

    private void updateStatuses() {

        if(p2.requested() && !p2.accepted()) {
            top.add(acceptButtons[0]);
            top.add(denyButtons[0]);
        }
        else {
            top.remove(acceptButtons[0]);
            top.remove(denyButtons[0]);
        }

        if(p3.requested() && !p3.accepted()) {
            middle.add(acceptButtons[1]);
            middle.add(denyButtons[1]);
        }
        else {
            middle.remove(acceptButtons[1]);
            middle.remove(denyButtons[1]);
        }

        if(p4.requested() && !p4.accepted()) {
            bottom.add(acceptButtons[2]);
            bottom.add(denyButtons[2]);
        }
        else {
            bottom.remove(acceptButtons[2]);
            bottom.remove(denyButtons[2]);
        }

        repaint();
    }

    // initializes all of gui
    public void initGUI() {

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel ipLabel = new JLabel("Current IP Address (give this to a friend!): " + ip);
        JLabel players = new JLabel("Players Joined " + hostName + "'s game:");

        ipLabel.setForeground(Color.WHITE);
        ipLabel.setFont(new Font("Futura", Font.PLAIN, 20));
        ipLabel.setOpaque(false);
        ipLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        players.setForeground(Color.WHITE);
        players.setFont(new Font("Futura", Font.PLAIN, 20));
        players.setOpaque(false);
        players.setAlignmentX(Component.CENTER_ALIGNMENT);

        start.setForeground(Color.WHITE);
        start.setFont(new Font("Futura", Font.PLAIN, 40));
        start.setBorderPainted(false);
        start.setContentAreaFilled(true);
        start.setAlignmentX(Component.CENTER_ALIGNMENT);
        start.addMouseListener(this);
        start.addActionListener(this);

        back.setForeground(Color.WHITE);
        back.setFont(new Font("Futura", Font.PLAIN, 20));
        back.setBorderPainted(false);
        back.setContentAreaFilled(true);
        back.setAlignmentX(Component.CENTER_ALIGNMENT);
        back.addMouseListener(this);
        back.addActionListener(this);

        // stuff for panel in center
        gridpanel.setMaximumSize(new Dimension(685,700));
        gridpanel.setMinimumSize(new Dimension(685,700));
        gridpanel.setLayout(new GridLayout(3,1));
        gridpanel.setOpaque(false);

        top.setOpaque(false);
        middle.setOpaque(false);
        bottom.setOpaque(false);

        for(int x = 0; x < 3; x++) {
            playerLabels[x] = new JLabel("Searching for players...");
            playerLabels[x].setFont(new Font("Futura", Font.PLAIN, 20));
            playerLabels[x].setHorizontalAlignment(JLabel.CENTER);
            playerLabels[x].setForeground(Color.WHITE);

            acceptButtons[x] = new JButton("Accept");
            acceptButtons[x].setFont(new Font("Futura", Font.PLAIN, 20));
            acceptButtons[x].setBorderPainted(false);
            acceptButtons[x].setForeground(Color.WHITE);
            acceptButtons[x].addActionListener(this);
            acceptButtons[x].addMouseListener(this);

            denyButtons[x] = new JButton("Deny");
            denyButtons[x].setFont(new Font("Futura", Font.PLAIN, 20));
            denyButtons[x].setBorderPainted(false);
            denyButtons[x].setForeground(Color.WHITE);
            denyButtons[x].addActionListener(this);
            denyButtons[x].addMouseListener(this);
        }

        top.add(playerLabels[0]);
        middle.add(playerLabels[1]);
        bottom.add(playerLabels[2]);

        gridpanel.add(top);
        gridpanel.add(middle);
        gridpanel.add(bottom);

        this.add(Box.createVerticalStrut(25));
        this.add(ipLabel);
        this.add(Box.createVerticalStrut(50));
        this.add(players);
        this.add(Box.createVerticalStrut(50));
        this.add(gridpanel);
        this.add(Box.createVerticalStrut(60));
        this.add(start);
        this.add(back);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if(hasUpdate)
            updateStatuses();
        else
            hasUpdate = true;

        // draws background
        g.setColor(new Color(18,18,18));
        g.fillRect(0,0,2000,2000);

        g.setColor(new Color(129,210,224));
        g.drawRect(147,150,700,300);
    }

    // starts game by initializing all players and necessary objects
    private void startGame() {
        // converts true/false values of player.ready var to numerical 1/0 values
        int numPlayers = 1 + (p2.ready() ? 1:0) + (p3.ready() ? 1:0) + (p4.ready() ? 1:0);

        System.out.println("Beginning game with " + numPlayers + " players...");

        Player[] players = new Player[4];

        players[0] = new Player(new Color(255,0,0),50,700,1,hostName);
        players[1] = new Player(new Color(0,255,0),900,700,2,p2.getClientName());
        players[2] = new Player(new Color(0,0,255),900,50,3,p3.getClientName());
        players[3] = new Player(new Color(255,255,0),50,50,4,p4.getClientName());

        // sends roundtrip message to client that game is starting
        p2.ping("start");
        p3.ping("start");
        p4.ping("start");



        GameDriver multigamedriver = new GameDriver(numPlayers,"multiplayer",players,p2,p3,p4);
        multigamedriver.begin();
    }

    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if(src.equals(start)) {
            // tests if game is ready to begin or not
            // at least 1 player must have joined
            // and at least 1 player must have requested and be ready
            if((p2.requested() || p3.requested() || p4.requested()) &&
                    ((!p2.requested() || p2.ready()) && (!p3.requested() || p3.ready()) &&
                            (!p4.requested() || p4.ready()))) {

                JFrame frame = (JFrame)SwingUtilities.getWindowAncestor(this);
                frame.setVisible(false);
                frame.dispose();

                startGame();
            }
        }

        // back button terminates all threads and disposes of current frame
        else if(src.equals(back)) {
            JFrame frame = (JFrame)SwingUtilities.getWindowAncestor(this);
            frame.setVisible(false);
            frame.dispose();

            new MultiPlayerMain();

            p2.terminate();
            p3.terminate();
            p4.terminate();

            System.out.println("Successfully terminated all threads");

            try {
                s.close();
            }
            catch(Exception ex) {
                System.out.println("Server closing exception, do not want this");
            }
        }

        else if(src.equals(acceptButtons[0]))
            p2.acceptPlayer();
        else if(src.equals(acceptButtons[1]))
            p3.acceptPlayer();
        else if(src.equals(acceptButtons[2]))
            p4.acceptPlayer();

        else if(src.equals(denyButtons[0]))
            p2.denyPlayer();
        else if(src.equals(denyButtons[1]))
            p3.denyPlayer();
        else if(src.equals(denyButtons[2]))
            p4.denyPlayer();

        updateStatuses();
    }

    public void mouseEntered(MouseEvent e) {
        Object src = e.getSource();

        if(src.equals(start)) {
            // tests if game is ready to begin or not
            // at least 1 player must have joined
            // and at least 1 player must have requested and be ready
            if((p2.requested() || p3.requested() || p4.requested()) &&
                    ((!p2.requested() || p2.ready()) && (!p3.requested() || p3.ready()) &&
                            (!p4.requested() || p4.ready())))

                start.setForeground(new Color(129,210,224));
        }
        else if(src.equals(back))
            back.setForeground(new Color(129,210,224));

            // accept button handling
        else if(src.equals(acceptButtons[0]))
            acceptButtons[0].setForeground(new Color(129,210,224));
        else if(src.equals(acceptButtons[1]))
            acceptButtons[1].setForeground(new Color(129,210,224));
        else if(src.equals(acceptButtons[2]))
            acceptButtons[2].setForeground(new Color(129,210,224));

            // deny button handling
        else if(src.equals(denyButtons[0]))
            denyButtons[0].setForeground(new Color(129,210,224));
        else if(src.equals(denyButtons[1]))
            denyButtons[1].setForeground(new Color(129,210,224));
        else if(src.equals(denyButtons[2]))
            denyButtons[2].setForeground(new Color(129,210,224));
    }

    public void mouseExited(MouseEvent e) {
        Object src = e.getSource();

        if(src.equals(start))
            start.setForeground(Color.WHITE);
        else if(src.equals(back))
            back.setForeground(Color.WHITE);

            // accept button handling
        else if(src.equals(acceptButtons[0]))
            acceptButtons[0].setForeground(Color.WHITE);
        else if(src.equals(acceptButtons[1]))
            acceptButtons[1].setForeground(Color.WHITE);
        else if(src.equals(acceptButtons[2]))
            acceptButtons[2].setForeground(Color.WHITE);

        else if(src.equals(denyButtons[0]))
            denyButtons[0].setForeground(Color.WHITE);
        else if(src.equals(denyButtons[1]))
            denyButtons[1].setForeground(Color.WHITE);
        else if(src.equals(denyButtons[2]))
            denyButtons[2].setForeground(Color.WHITE);
    }

    public void mouseReleased(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
}
