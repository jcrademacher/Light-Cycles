package jrad.net;

import java.net.*;
import java.io.*;
import javax.swing.*;

/**
 * Created by jackrademacher on 10/5/16.
 */
public class PlayerSocket extends Thread {

    private BufferedReader in;
    private PrintWriter out;

    private InputStream input;
    private OutputStream output;

    private String clientName;
    private String hostName;

    private Socket skt;
    private ServerSocket srvr;

    private JLabel label;

    private int port;
    private boolean playerRequested, playerAccepted, playerReady, connecting;

    // only constructor
    public PlayerSocket(JLabel label, ServerSocket s, String n) {
        this.label = label;

        playerAccepted = false;
        playerRequested = false;
        playerReady = false;
        connecting = true;

        srvr = s;
        hostName = n;
    }

    public boolean ready() {
        return playerReady;
    }

    public boolean requested() {
        return playerRequested;
    }

    public boolean accepted() {
        return playerAccepted;
    }

    public synchronized void acceptPlayer() {
        playerAccepted = true;

        try {
            out.println(ClientRoom.ACCEPTED);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        label.setText(clientName + " has joined the game - Not Ready");
    }

    public synchronized void denyPlayer() {
        playerAccepted = false;
        playerRequested = false;

        try {
            out.println(ClientRoom.DENIED);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    // method invoked when thread is started
    public void run() {
        connect();
    }

    private void connect() {
        // loop to keep searching until thread itself is stopped
        while(connecting && !playerReady) {
            label.setText("Searching for players...");

            // accepts a connection from client
            try {
                System.out.println("Server trying to connect");

                skt = srvr.accept();

                playerRequested = true;

                // creates in/out streams
                out = new PrintWriter(output = skt.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(input = skt.getInputStream()));

                clientName = in.readLine();
                out.println(hostName);

                System.out.println("\nPlayer requesting access: " + clientName);
                label.setText(clientName + " is requesting access to your game");
            }

            // exception handling: DO NOT WANT BindException --> means socket is already in use
            catch(BindException ex) {
                connecting = false;
                ex.printStackTrace();
                System.out.println("This catch block should never happen");
            }
            catch(IOException ex) {
                connecting = false;
                System.out.println("Error with input/output streams");
            }

            // tests if client disconnected
            if(connecting) {
                if(!isConnected()) {
                    System.out.println(clientName + " disconnected");
                    playerReady = false;
                    playerRequested = false;
                    playerAccepted = false;
                }
                else {
                    System.out.println(clientName + " is ready");
                    label.setText(clientName + " has joined the game - Ready");
                    playerReady = true;
                }
            }

            if(!isConnected()) {
                System.out.println(clientName + " disconnected");
                playerReady = false;
                playerRequested = false;
                playerAccepted = false;
            }
        }
    }

    // get methods for writers and readers
    public InputStream getInputStream() {
     /*try {
       in.close();
     }
     catch(Exception e) {e.printStackTrace();}*/

        return input;
    }

    public OutputStream getOutputStream() {
     /*try {
       out.close();
     }
     catch(Exception e) {e.printStackTrace();}*/

        return output;
    }

    public String getClientName() {
        return clientName;
    }

    public boolean isConnected() {
        try {
            if(in == null)
                return false;
            else if(in.readLine() == null)
                return false;
            else
                return true;
        }
        catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean ping(String msg) {
        if(out != null) {
            out.println(msg);
            return true;
        }
        else {
            return false;
        }
    }

    // properly closes sockets and server to end connection process and thread
    public void terminate() {
        playerRequested = false;
        playerAccepted = false;
        connecting = false;

        try {
            skt.close();
            in.close();
            srvr.close();
        }

        catch(Exception e) {}
    }
}
