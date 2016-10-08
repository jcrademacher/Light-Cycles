package jrad.net;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;

import jrad.main.multiplayer.MultiPlayerMain;

/**
 * Created by jackrademacher on 10/5/16.
 */
public class ClientRoom extends JPanel implements MouseListener, ActionListener {

    private JLabel ipLabel;
    private JLabel games;

    private JTextField ipField;

    private JButton search;
    private JButton ready;
    private JButton back;

    private String clientName, hostName, ip;

    private Socket client;

    private JPanel center;

    private PrintWriter out;
    private BufferedReader in;

    public static final String ACCEPTED = "accepted";
    public static final String DENIED = "denied";

    public ClientRoom(String n) {
        ipLabel = new JLabel("Enter IP Address here:");
        games = new JLabel("No games found");
        ipField = new JTextField();
        search = new JButton("Search");
        ready = new JButton("Ready Up");
        back = new JButton("Back");

        clientName = n;
        ip = "unknown";
        center = new JPanel();
    }

    // generic formatting stuff
    public void initGUI() {

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        center.setOpaque(false);

        ipField.setMaximumSize(new Dimension(200,25));
        ipField.setFont(new Font("Futura", Font.PLAIN, 20));
        ipField.setHorizontalAlignment(JTextField.CENTER);

        ipLabel.setFont(new Font("Futura", Font.PLAIN, 20));
        ipLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        ipLabel.setForeground(Color.WHITE);

        search.setFont(new Font("Futura", Font.PLAIN, 25));
        search.setAlignmentX(Component.CENTER_ALIGNMENT);
        search.setBorderPainted(false);
        search.setForeground(Color.WHITE);
        search.addMouseListener(this);
        search.addActionListener(this);

        ready.setFont(new Font("Futura", Font.PLAIN, 20));
        ready.setBorderPainted(false);
        ready.setForeground(Color.WHITE);
        ready.addMouseListener(this);
        ready.addActionListener(this);

        back.setFont(new Font("Futura", Font.PLAIN, 20));
        back.setBorderPainted(false);
        back.setForeground(Color.WHITE);
        back.addMouseListener(this);
        back.addActionListener(this);
        back.setAlignmentX(Component.CENTER_ALIGNMENT);

        games.setFont(new Font("Futura", Font.PLAIN, 20));
        games.setAlignmentX(Component.CENTER_ALIGNMENT);
        games.setForeground(Color.WHITE);

        center.add(games);

        // adding components
        this.add(Box.createVerticalStrut(50));
        this.add(ipLabel);
        this.add(ipField);
        this.add(search);
        this.add(Box.createVerticalStrut(120));
        this.add(center);
        this.add(Box.createVerticalGlue());
        this.add(back);
    }

    // searches and attempts to bind to user-inputted ip address
    private void searchGame() {
        center.remove(ready);
        repaint();

        ip = ipField.getText();
        if(ip.equals("")) ip = "unknown";

        games.setText("Searching for games...");

        try {
            // closes previous socket if its not null
            if(client != null)
                client.close();

            System.out.println("Trying to bind...");
            client = new Socket(ip, 1235);
            System.out.println("Bound!");

            // creates input/output streams with server
            out = new PrintWriter(client.getOutputStream(), true);
            in = new BufferedReader(new
                    InputStreamReader(client.getInputStream()));

            out.println(clientName);
            hostName = in.readLine();

            games.setText("Requesting access to " + hostName + "'s game");

            waitAcceptance();
        }
        catch(UnknownHostException e) {
            games.setText("Could not find IP Address");
        }
        catch(IOException e) {
            games.setText("IOException: Something went wrong");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    // waits on acceptance from server, concurrent
    private void waitAcceptance() {
        System.out.println("Waiting on server...");

        Runnable wait = new Runnable() {
            public void run() {
                try {
                    String acceptance = in.readLine();

                    System.out.println("Status: " + acceptance);

                    // sets text and creates ready up button
                    if(acceptance.equals(ClientRoom.this.ACCEPTED)) {
                        games.setText(hostName + " has accepted your game request");
                        center.add(ready);
                    }
                    else if(acceptance.equals(ClientRoom.this.DENIED)) {
                        games.setText(hostName + " has denied your request!");
                        client.close();
                    }
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
            }
        };

        // starts thread
        Thread waitThread = new Thread(wait);
        waitThread.start();
    }

    // waits on host to start game
    private void waitForGameStart() {

        Runnable waitStart = new Runnable() {
            public void run() {
                try {
                    String dummy;

                    if((dummy = in.readLine()) == null) {
                        System.out.println("Lost connection");
                        games.setText("Lost Connection");
                    }
                    else if(dummy.equals("start")) {
                        System.out.println("Beginning game...");
                        out.println("arbitrary");
                    }
                }
                catch(IOException e) {
                    System.out.println("Lost connection");
                    games.setText("Lost Connection");
                }
            }
        };

        Thread startThread = new Thread(waitStart);
        startThread.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // draws background
        g.setColor(new Color(18,18,18));
        g.fillRect(0,0,2000,2000);

        g.setColor(new Color(129,210,224));
        g.drawRect(147,150,700,300);
    }

    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        String curIp = ipField.getText();

        if(src.equals(search)) {
            // if ip is not empty and ip is a new string entered
            if(!curIp.equals("") && !curIp.equals(ip))
                if(client != null) {
                    try {
                        client.close();
                    }
                    catch(Exception ex) {}
                }
            searchGame();
        }
        // alerts host that client is ready
        else if(src.equals(ready)) {
            try {
                out.println();
            }
            catch(Exception ex) {
                ex.printStackTrace();
            }

            games.setText("Waiting for " + hostName);
            System.out.println("Waiting for game to begin...");

            center.remove(ready);

            revalidate();
            repaint();

            waitForGameStart();
        }
        else if(src.equals(back)) {
            JFrame frame = (JFrame)SwingUtilities.getWindowAncestor(this);
            frame.setVisible(false);
            frame.dispose();

            if(client != null) {
                try {
                    client.close();
                }
                catch(Exception ex) {
                    ex.printStackTrace();
                }
            }

            new MultiPlayerMain();
        }
    }

    public void mouseEntered(MouseEvent e) {
        Object src = e.getSource();

        if(src.equals(search))
            search.setForeground(new Color(129,210,224));
        else if(src.equals(ready))
            ready.setForeground(new Color(129,210,224));
        else if(src.equals(back))
            back.setForeground(new Color(129,210,224));
    }

    public void mouseExited(MouseEvent e) {
        Object src = e.getSource();

        if(src.equals(search))
            search.setForeground(Color.WHITE);
        else if(src.equals(ready))
            ready.setForeground(Color.WHITE);
        else if(src.equals(back))
            back.setForeground(Color.WHITE);
    }

    public void mouseReleased(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
}

