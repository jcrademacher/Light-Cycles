package jrad.util;

import java.io.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * Created by jackrademacher on 10/6/16.
 */
public class Music {
    private static Clip music;
    private static boolean musicOn = true;

    public static void load(String file) {

        // playing sound file, gets audio input stream, starts reading
        try {
            AudioInputStream audioInputStream =
                    AudioSystem.getAudioInputStream(new File(file).getAbsoluteFile());
            music = AudioSystem.getClip();
            music.open(audioInputStream);

        } catch(Exception ex) {
            System.out.println("Error with loading sound.");
            ex.printStackTrace();
        }
    }

    public static void start() {
        if(musicOn) {
            music.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public static void stop() {
        music.stop();
    }

    public static void setOn(boolean on) {
        musicOn = on;
    }

    public static boolean isOn() {
        return musicOn;
    }
}
