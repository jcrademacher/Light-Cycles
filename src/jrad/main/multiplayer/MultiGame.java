package jrad.main.multiplayer;

import java.io.*;
import jrad.main.singleplayer.*;
import jrad.player.*;
import jrad.net.*;

/**
 * Created by jackrademacher on 10/5/16.
 */
public class MultiGame extends Game {

    private ObjectInputStream p2reader, p3reader, p4reader;
    private ObjectOutputStream p2writer, p3writer, p4writer;

    public MultiGame(int numPlayers, Player[] players, PlayerSocket p2, PlayerSocket p3, PlayerSocket p4) {
        super(numPlayers,"multiplayer",players);

        try {
            p2reader = new ObjectInputStream(p2.getInputStream());
            p2writer = new ObjectOutputStream(p2.getOutputStream());

            p3reader = new ObjectInputStream(p3.getInputStream());
            p3writer = new ObjectOutputStream(p3.getOutputStream());

            p4reader = new ObjectInputStream(p4.getInputStream());
            p4writer = new ObjectOutputStream(p4.getOutputStream());
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        String test = new String("test");

    }
}

