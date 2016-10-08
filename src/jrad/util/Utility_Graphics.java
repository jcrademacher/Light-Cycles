package jrad.util;

import java.awt.*;

/**
 * Created by jackrademacher on 10/6/16.
 */
public class Utility_Graphics
{

    // why is this in a method
    // its three lines of code
    // well 2 actually the return doesnt count
    public static int random(int min, int max)
    {
        int range = max - min + 1;
        int randomNumber = (int)(Math.random() * range) + min;
        return randomNumber;
    }

    // method to draw header, passes strings for assignment, date, and name. Also passes color, so you can
    // customize your header to contrast the background (I actually made this and used it)
    public static void header(Graphics g, String assignment, String name, String current_date, Color currentColor) {
        g.setColor(currentColor);
        g.drawRect(10,10,250,50);
        g.drawString(name,15,25);
        g.drawString(assignment,15,40);
        g.drawString(current_date,15,55);

    }

    // muthig I didnt use any of these
    public static void setBackground(Graphics g, Color c,int x,int y)
    {
        g.setColor(c);
        g.fillRect(0,0,x,y);
    }

    // i dont think i have to comment them
    public static void setRandomColor(Graphics g)
    {
        int red   = random(0,255);
        int green = random(0,255);
        int blue  = random(0,255);
        g.setColor(new Color(red, green, blue));
    }

    public static void wait(int milliseconds) {
        try {
            Thread.sleep(milliseconds); // wait
        }
        catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}

