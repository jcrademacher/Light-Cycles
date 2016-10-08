package jrad.main.singleplayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import jrad.player.Player;
import jrad.player.ComputerPlayer;

/**
 * Created by jackrademacher on 10/5/16.
 */
public class Game extends JPanel implements KeyListener, ActionListener{

    protected int numPlayers;

    protected Player p1;
    protected Player p2;
    protected Player p3;
    protected Player p4;

    private int restart;
    private boolean running;

    private Font currentFont;

    private final int MAX_BOOST_BAR_VALUE = 76;

    private int p1BoostValue;
    private int p2BoostValue;
    private int p3BoostValue;
    private int p4BoostValue;

    private int boostIncrement;

    private String gameType;

    // singleplayer constructor
    public Game(int numPlayers, String gameType, Player[] players) {
        setFocusable(true);
        addKeyListener(this);

        running = true;
        boostIncrement = 1;

        currentFont = new Font("Futura", Font.PLAIN, 18);

        // creates 4 player objects to be put on screen with specified colors, starting pos, and direction
        this.numPlayers = numPlayers;
        this.gameType = gameType;

        if(gameType.equals("singleplayer")) {

            p1 = new Player(new Color(255,0,0),50,700,1,"p1");
            p2 = new Player(new Color(0,255,0),900,700,2,"p2");

            // creates AI objects for players who arent human
            if(numPlayers == 2) {
                p3 = new ComputerPlayer(new Color(0,0,255),900,50,3,"p3");
                p4 = new ComputerPlayer(new Color(255,255,0),50,50,4,"p4");


                //((ComputerPlayer)p1).addPlayerList(p2,p3,p4);
                //((ComputerPlayer)p2).addPlayerList(p1,p3,p4);
                ((ComputerPlayer)p3).addPlayerList(p1,p2,p4);
                ((ComputerPlayer)p4).addPlayerList(p1,p2,p3);
            }
            else if(numPlayers == 3) {
                p3 = new Player(new Color(0,0,255),900,50,3,"p3");
                p4 = new ComputerPlayer(new Color(255,255,0),50,50,4,"p4");

                ((ComputerPlayer)p4).addPlayerList(p1,p2,p3);
            }
            else if(numPlayers == 4) {
                p3 = new Player(new Color(0,0,255),900,50,3,"p3");
                p4 = new Player(new Color(255,255,0),50,50,4,"p4");
            }

        }
        else {
            p1 = players[0];
            p2 = players[1];
            p3 = players[2];
            p4 = players[3];
        }

        p1BoostValue = 0;
        p2BoostValue = 0;
        p3BoostValue = 0;
        p4BoostValue = 0;

        p1.setOnScreen(true);
        p2.setOnScreen(true);
        p3.setOnScreen(true);
        p4.setOnScreen(true);
    }

    // tests for if one and only one player is left on the screen
    public void displayWinner() {
        String winner;
        if(p1.isOnScreen() && !p2.isOnScreen() && !p3.isOnScreen() && !p4.isOnScreen())
            winner = "Red";
        else if(!p1.isOnScreen() && p2.isOnScreen() && !p3.isOnScreen() && !p4.isOnScreen())
            winner = "Green";
        else if(!p1.isOnScreen() && !p2.isOnScreen() && p3.isOnScreen() && !p4.isOnScreen())
            winner = "Blue";
        else
            winner = "Yellow";

        restart = JOptionPane.showConfirmDialog(null,winner + " wins! Would you like to restart?",
                "Winner!",JOptionPane.YES_NO_OPTION);
    }

    public boolean playAgain() {
        if(restart == JOptionPane.YES_OPTION) {
            restart = JOptionPane.NO_OPTION;
            return true;
        }
        else {
            return false;
        }
    }

    // resets all player info, trails, directions and positions
    public void reset() {
        p1.getBike().getTrail().clear();
        p2.getBike().getTrail().clear();
        p3.getBike().getTrail().clear();
        p4.getBike().getTrail().clear();

        p1.getBike().setPositionX(50);
        p2.getBike().setPositionX(900);
        p3.getBike().setPositionX(900);
        p4.getBike().setPositionX(50);

        p1.getBike().setPositionY(700);
        p2.getBike().setPositionY(700);
        p3.getBike().setPositionY(50);
        p4.getBike().setPositionY(50);

        p1.getBike().setDirection(1);
        p2.getBike().setDirection(2);
        p3.getBike().setDirection(3);
        p4.getBike().setDirection(4);

        p1.setBoostLevel(0);
        p2.setBoostLevel(0);
        p3.setBoostLevel(0);
        p4.setBoostLevel(0);

        p1BoostValue = 0;
        p2BoostValue = 0;
        p3BoostValue = 0;
        p4BoostValue = 0;

        p1.setOnScreen(true);
        p2.setOnScreen(true);
        p3.setOnScreen(true);
        p4.setOnScreen(true);
    }

    // must be synchronized because multiple threads are accessing the data inside this method
    // tests if a single player is on screen alone
    public synchronized boolean gameOver() {
        if(!p2.isOnScreen() && !p3.isOnScreen() && !p4.isOnScreen())
            return true;
        else if(!p1.isOnScreen() && !p3.isOnScreen() && !p4.isOnScreen())
            return true;
        else if(!p1.isOnScreen() && !p2.isOnScreen() && !p4.isOnScreen())
            return true;
        else if(!p1.isOnScreen() && !p2.isOnScreen() && !p3.isOnScreen())
            return true;

        else return false;
    }

    // fired when key is pressed
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_SPACE)
            running = !running;

        if(running) {
            // player 1 handling, controlled by keypad
            if(!(p1 instanceof ComputerPlayer)) {
                if(code == KeyEvent.VK_LEFT)
                    p1.turnLeft();
                if(code == KeyEvent.VK_RIGHT)
                    p1.turnRight();
                if(code == KeyEvent.VK_UP) {
                    // statement determines whether to boost or not, will boost if player
                    // is already boosting or if boostValue is at max value AND if boostValue
                    // is not completely empty
                    if(p1BoostValue == MAX_BOOST_BAR_VALUE || p1.getBoostLevel() == 3)
                        p1.setBoostLevel(3);
                }
            }

            if(!(p2 instanceof ComputerPlayer)) {
                // same block as above shown below, except with players 2, 3, 4
                if(code == KeyEvent.VK_Q)
                    p2.turnLeft();
                if(code == KeyEvent.VK_E)
                    p2.turnRight();
                if(code == KeyEvent.VK_W) {
                    if(p2BoostValue == MAX_BOOST_BAR_VALUE || p2.getBoostLevel() == 3)
                        p2.setBoostLevel(3);
                }
            }

            if(!(p3 instanceof ComputerPlayer)) {
                if(code == KeyEvent.VK_F)
                    p3.turnLeft();
                if(code == KeyEvent.VK_H)
                    p3.turnRight();
                if(code == KeyEvent.VK_G) {
                    if(p3BoostValue == MAX_BOOST_BAR_VALUE || p3.getBoostLevel() == 3)
                        p3.setBoostLevel(3);
                }
            }
            if(!(p4 instanceof ComputerPlayer)) {
                if(code == KeyEvent.VK_M)
                    p4.turnLeft();
                if(code == KeyEvent.VK_PERIOD)
                    p4.turnRight();
                if(code == KeyEvent.VK_COMMA) {
                    if(p4BoostValue == MAX_BOOST_BAR_VALUE || p4.getBoostLevel() == 3)
                        p4.setBoostLevel(3);
                }
            }
        }
    }

    // key released event handler
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_UP)
            p1.setBoostLevel(0);
        if(code == KeyEvent.VK_W)
            p2.setBoostLevel(0);
        if(code == KeyEvent.VK_G)
            p3.setBoostLevel(0);
        if(code == KeyEvent.VK_COMMA)
            p4.setBoostLevel(0);
    }

    // actionPerformed method, fired by timer in GameDriver class
    public void actionPerformed(ActionEvent e) {
        if(running)
            repaint(); // calls for an update to gui
    }

    public void paintInfo(Graphics g) {
        g.setFont(currentFont);
        g.setColor(new Color(0,0,60)); // deep dark blue color
        g.fillRect(1000,0,150,800);

        g.setColor(Color.WHITE);
        g.drawString("Boost Bars",1030,40);

        // drawing labels
        g.drawString("Player 1 Boost",1015,100);
        g.drawString("Player 2 Boost",1015,250);
        g.drawString("Player 3 Boost",1015,400);
        g.drawString("Player 4 Boost",1015,550);

        paintBoostBars(g);

    }

    public void paintBoostBars(Graphics g) {

        // if bar isnt already full yet..
        // if boostIncrement == 0 returns true, then (player)BoostValue will be incremented
        // else if player is boosting, deplete bar
        // else then stop boosting
        if(p1BoostValue != MAX_BOOST_BAR_VALUE && p1.getBoostLevel() == 0)
            p1BoostValue = p1BoostValue + ((boostIncrement == 0) ? 1:0);
        else if(p1.getBoostLevel() == 3 && p1BoostValue > 0)
            p1BoostValue--;
        else
            p1.setBoostLevel(0);

        if(p2BoostValue != MAX_BOOST_BAR_VALUE && p2.getBoostLevel() == 0)
            p2BoostValue = p2BoostValue + ((boostIncrement == 0) ? 1:0);
        else if(p2.getBoostLevel() == 3 && p2BoostValue > 0)
            p2BoostValue--;
        else
            p2.setBoostLevel(0);

        if(p3BoostValue != MAX_BOOST_BAR_VALUE && p3.getBoostLevel() == 0)
            p3BoostValue = p3BoostValue + ((boostIncrement == 0) ? 1:0);
        else if(p3.getBoostLevel() == 3 && p3BoostValue > 0)
            p3BoostValue--;
        else
            p3.setBoostLevel(0);

        if(p4BoostValue != MAX_BOOST_BAR_VALUE && p4.getBoostLevel() == 0)
            p4BoostValue = p4BoostValue + ((boostIncrement == 0) ? 1:0);
        else if(p4.getBoostLevel() == 3 && p4BoostValue > 0)
            p4BoostValue--;
        else
            p4.setBoostLevel(0);

        // boostIncrement cycles from 0-4, then back to 0, makes it slower
        boostIncrement++;
        boostIncrement %= 4;

        if(p1.isOnScreen()) {
            // drawing p1s boost bar
            g.setColor(Color.RED);
            if(p1BoostValue == 76)
                g.drawRect(1030,140,85,25);
            g.fillRect(1035,145,p1BoostValue,16);
        }
        if(p2.isOnScreen()) {
            // drawing p2s boost bar
            g.setColor(Color.GREEN);
            if(p2BoostValue == 76)
                g.drawRect(1030,290,85,25);
            g.fillRect(1035,295,p2BoostValue,16);
        }
        if(p3.isOnScreen()) {
            // drawing p3s boost bar
            g.setColor(Color.BLUE);
            if(p3BoostValue == 76)
                g.drawRect(1030,440,85,25);
            g.fillRect(1035,445,p3BoostValue,16);
        }
        if(p4.isOnScreen()) {
            // drawing p4s boost bar
            g.setColor(Color.YELLOW);
            if(p4BoostValue == 76)
                g.drawRect(1030,590,85,25);
            g.fillRect(1035,595,p4BoostValue,16);
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        paintInfo(g);

        // draws grid
        g.setColor(new Color(179,0,179));
        g.fillRect(0,0,1000,1000);
        g.setColor(Color.BLACK);
        g.fillRect(10,10,980,760);

        g.setColor(new Color(50,50,50));
        for(int x = 10; x < 990; x += 10) {
            g.drawLine(x,10,x,768);
            g.drawLine(10,x,989,x);
        }

        if(p1.isOnScreen()) {
            p1.move(g);

            if(p1.hasHitEdge())
                p1.setOnScreen(false);
            else if(p1.hasCollidedWith(p2,p3,p4))
                p1.setOnScreen(false);
            else if(p1.hasCollidedWithSelf())
                p1.setOnScreen(false);
        }
        if(p2.isOnScreen()) {
            p2.move(g);

            if(p2.hasHitEdge())
                p2.setOnScreen(false);
            else if(p2.hasCollidedWith(p1,p3,p4))
                p2.setOnScreen(false);
            else if(p2.hasCollidedWithSelf())
                p2.setOnScreen(false);
        }
        if(p3.isOnScreen()) {
            p3.move(g);

            if(p3.hasHitEdge())
                p3.setOnScreen(false);
            else if(p3.hasCollidedWith(p1,p2,p4))
                p3.setOnScreen(false);
            else if(p3.hasCollidedWithSelf())
                p3.setOnScreen(false);
        }
        if(p4.isOnScreen()) {
            p4.move(g);

            if(p4.hasHitEdge())
                p4.setOnScreen(false);
            else if(p4.hasCollidedWith(p1,p2,p3))
                p4.setOnScreen(false);
            else if(p4.hasCollidedWithSelf())
                p4.setOnScreen(false);
        }
    }

    // methods needed in keylistener interface

    public void keyTyped(KeyEvent e) {}
}
