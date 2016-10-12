package jrad.main.singleplayer;

import javax.swing.*;
import jrad.util.*;
import jrad.player.*;
import jrad.net.*;
import jrad.main.multiplayer.*;

/**
 * Created by jackrademacher on 10/5/16.
 */
public class GameDriver {

    private JFrame gameframe;
    private Game game;
    private JPanel mainPane;
    private int numPlayers;

    private Timer gameTimer;

    private JMenuBar menuBar = new JMenuBar();
    private JMenu lightCycles = new JMenu("Light Cycles");

    private String gameType;

    public boolean isRunning = true;

    // constructor
    public GameDriver(int numPlayers, String gameType, Player[] players,
                      PlayerSocket p2, PlayerSocket p3, PlayerSocket p4) {

        this.numPlayers = numPlayers;

        gameframe = new JFrame("Light Cycles");
        this.gameType = gameType;

        // test case for singleplayer vs. multiplayer games
        if(gameType.equals("singleplayer")) {
            game = new Game(numPlayers, "singleplayer", players);
            gameframe.setContentPane(game);
        }
        else {
            game = new MultiGame(numPlayers, players, p2, p3, p4);
            //splash = new MultiGameSplash("server");
            //gameframe.setContentPane(splash);
            gameframe.setContentPane(game);
        }

        // same as in Main, except derezzed song plays
        Music.load("assets/derezzed.wav");
        Music.start();

        menuBar.add(lightCycles);

        gameframe.setJMenuBar(menuBar);

        // formatting stuff
        gameframe.setSize(1150,824);
        gameframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameframe.setResizable(false);
        gameframe.setVisible(true);
        game.setOpaque(false);
    }

    // begins game, timer starts firing actionPerformed method at specified (millis) interval
    // inherently creates own thread as well
    public void begin() {
        Runnable run = new Runnable() {
            public void run() {
                while(isRunning) {
                    if(game.gameOver()) {
                        gameTimer.stop();
                        game.displayWinner();
                        isRunning = false;
                    }

                    if(game.playAgain()) {
                        game.reset();
                        isRunning = true;
                        gameTimer.start();
                    }
                }
                gameframe.dispose();
            }
        };

        Thread gameThread = new Thread(run);

        gameTimer = new Timer(25,game);
        gameTimer.addActionListener(game);

      /*
      if(gameType.equals("multiplayer")) {

         gameframe.invalidate();

         gameframe.setContentPane(game);
         gameframe.revalidate();
      }*/

        gameTimer.start();
        gameThread.start();
    }
}
