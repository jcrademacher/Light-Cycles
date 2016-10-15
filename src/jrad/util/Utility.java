package jrad.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by jackrademacher on 10/6/16.
 */
public class Utility {

    private static boolean musicOn;
    private static int frameInterval;
    private static int aiDifficulty;

    private static File dir;
    private static File settingsFile;

    public static final int AI_EASY = 0;
    public static final int AI_MEDIUM = 1;
    public static final int AI_HARD = 2;

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

    public static void initFiles() {
        dir = new File("/Applications/Light Cycles");
        settingsFile = new File("/Applications/Light Cycles/settings.dat");

        // creates files if they do not exist, does nothing if they exist
        try {
            if(!dir.exists()) {
                if(dir.mkdirs())
                    System.out.println("Created directories");
                else
                    System.out.println("Failed to create directories");
            }
            else {
                System.out.println("Directories already exist");
            }

            if(!settingsFile.exists()) {
                if(settingsFile.createNewFile())
                    System.out.println("Successfully created new settings.dat file");
                else
                    System.out.println("settings.dat creation failed");
            }
            else {
                System.out.println("settings.dat already exists");
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void importSettings() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(settingsFile));
            musicOn = Boolean.parseBoolean(reader.readLine());
            frameInterval = Integer.parseInt(reader.readLine());
            aiDifficulty = Integer.parseInt(reader.readLine());

            System.out.println("Successfully imported settings");
        }
        catch(Exception e) {
            System.out.println("Settings could not be imported");

            e.printStackTrace();
        }
    }

    public static void writeSettings() {
        try {
            PrintWriter out = new PrintWriter(settingsFile);
            out.println(musicOn);
            out.println(frameInterval);
            out.println(aiDifficulty);
            out.flush();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static int getAiDifficulty() {
        return aiDifficulty;
    }

    public static int getFrameInterval() {
        return frameInterval;
    }

    public static boolean isMusicOn() {
        return musicOn;
    }

    public static void setAiDifficulty(int difficulty) {
        if(difficulty < 0 || difficulty > 2)
            throw new IllegalArgumentException();
        else
            aiDifficulty = difficulty;
    }

    public static void setFrameInterval(int interval) {
        if(interval < 5 || interval > 40)
            throw new IllegalArgumentException();
        else
            frameInterval = interval;
    }

    public static void setMusicOn(boolean b) {
        musicOn = b;
    }
}
