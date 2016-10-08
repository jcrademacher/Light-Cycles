package jrad.graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;

/**
 * Created by jackrademacher on 10/5/16.
 */
public class PreGraphicsPanel extends JPanel {

    private BufferedImage image;

    private int numPlayers;

    // default constructor to init class
    public PreGraphicsPanel(int numPlayers) {
        this.numPlayers = numPlayers;

        // for displaying background picture
        try {
            image = ImageIO.read(new File("assets/tron.png"));
        }
        catch(IOException e) {
            System.out.println("Could not find file");
        }
    }

    public void setNumPlayers(int num) {
        numPlayers = num;
    }

    // draws background image
    public void paintComponent(Graphics g) {
        g.drawImage(image,0,0,null);

        g.setColor(new Color(87,120,127));
        if(numPlayers == 2) {
            g.drawLine(385,100,405,100);
            g.drawLine(385,101,405,101);
            g.drawLine(385,102,405,102);

            g.drawLine(595,100,615,100);
            g.drawLine(595,101,615,101);
            g.drawLine(595,102,615,102);
        }
        else if(numPlayers == 3) {
            g.drawLine(380,160,400,160);
            g.drawLine(380,161,400,161);
            g.drawLine(380,162,400,162);

            g.drawLine(600,160,620,160);
            g.drawLine(600,161,620,161);
            g.drawLine(600,162,620,162);
        }
        else if(numPlayers == 4) {
            g.drawLine(385,220,405,220);
            g.drawLine(385,221,405,221);
            g.drawLine(385,222,405,222);

            g.drawLine(595,220,615,220);
            g.drawLine(595,221,615,221);
            g.drawLine(595,222,615,222);
        }
        else {}
    }
}
