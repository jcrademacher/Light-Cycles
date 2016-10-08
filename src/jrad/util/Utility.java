package jrad.util;

import java.util.*;

/**
 * Created by jackrademacher on 10/6/16.
 */
public class Utility {

    public static String print(String assignment) {
        Date date = new Date();
        String line = "";

        line += "-------------------------------\n";
        line += "A Program by Jack Rademacher\n";
        line += (assignment + '\n');
        line += (date.toString() + '\n');
        line += "-------------------------------\n";

        return line;
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
