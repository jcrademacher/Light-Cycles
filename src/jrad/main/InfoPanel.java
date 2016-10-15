package jrad.main;

import jrad.util.Music;
import jrad.util.Utility;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.net.URI;

/**
 * Created by jackrademacher on 10/8/16.
 */
public class InfoPanel extends JPanel implements ActionListener, MouseListener, ChangeListener {

    public final static String HOWTOPLAY = "htp";
    public final static String SETTINGS = "settings";
    public final static String ABOUT = "about";

    private String panelType;
    private JButton back = new JButton("Back");

    // components for about panel
    private JButton l = new JButton("View source here");

    // components for settings panel
    private JButton music = new JButton("Music On");
    private JSlider gameSpeed = new JSlider(SwingConstants.HORIZONTAL, 5, 40, 25);
    private JLabel speedValue = new JLabel(Integer.toString(gameSpeed.getValue()));
    private JComboBox<String> aiDifficulty = new JComboBox<String>();

    public InfoPanel(String panelType) {
        this.panelType = panelType;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        back.setFont(new Font("Futura", Font.PLAIN, 20));
        back.setBorderPainted(false);
        back.setForeground(Color.WHITE);
        back.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        back.addActionListener(this);
        back.addMouseListener(this);

        initGUI();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(18,18,18));
        g.fillRect(0,0,2000,2000);
    }

    private void initGUI() {
        if(panelType.equals(this.HOWTOPLAY))
            initHTP();
        else if(panelType.equals(this.SETTINGS))
            initSettings();
        // inits about panel with info
        else if(panelType.equals(this.ABOUT))
            initAbout();

        this.add(Box.createVerticalGlue());
        this.add(back);
    }

    private void initHTP() {

    }

    private void initSettings() {

        JPanel gameSpeedPane = new JPanel();
        JPanel aiPane = new JPanel();

        aiDifficulty.addItem("Worst");
        aiDifficulty.addItem("Good");
        aiDifficulty.addItem("Best");

        JLabel gameSpeedLabel = new JLabel("Frame interval:");
        JLabel speedWarning = new JLabel(
                "<html><center>WARNING: Game speed may become wildly inconsistent<br>if small frame intervals are used</center></html>");
        JLabel aiLabel = new JLabel("AI Skill:");

        formatComponent(music, 20);
        formatComponent(speedValue, 20);
        formatComponent(gameSpeedLabel, 20);
        formatComponent(speedWarning, 15);
        formatComponent(aiLabel, 20);

        gameSpeed.setValue(Utility.getFrameInterval());
        speedValue.setText(Integer.toString(Utility.getFrameInterval()));

        if(Utility.getAiDifficulty() == Utility.AI_EASY)
            aiDifficulty.setSelectedItem("Worst");
        else if(Utility.getAiDifficulty() == Utility.AI_MEDIUM)
            aiDifficulty.setSelectedItem("Good");
        else if(Utility.getAiDifficulty() == Utility.AI_HARD)
            aiDifficulty.setSelectedItem("Best");

        if(Utility.isMusicOn())
            music.setText("Music On");
        else
            music.setText("Music Off");

        gameSpeed.addChangeListener(this);

        gameSpeedPane.add(gameSpeedLabel);
        gameSpeedPane.add(gameSpeed);
        gameSpeedPane.add(speedValue);
        gameSpeedPane.add(speedWarning);
        gameSpeedPane.setOpaque(false);

        aiPane.add(aiLabel);
        aiPane.add(aiDifficulty);
        aiPane.setOpaque(false);

        this.add(Box.createVerticalStrut(80));
        this.add(music);
        this.add(Box.createVerticalGlue());
        this.add(gameSpeedPane);
        this.add(Box.createVerticalGlue());
        this.add(aiPane);
        this.add(Box.createVerticalGlue());
    }

    private void initAbout() {
        String msg1 = "A recreation of the arcade game \"Tron\"";
        String msg2 = "Created by Jack Rademacher - April 2016";

        JLabel title = new JLabel("Light Cycles");
        formatComponent(title, 40);

        JLabel sub1 = new JLabel(msg1);
        formatComponent(sub1, 20);

        JLabel sub2 = new JLabel(msg2);
        formatComponent(sub2, 20);

        l.setBorderPainted(false);
        l.addActionListener(this);
        l.setCursor(new Cursor(Cursor.HAND_CURSOR));
        formatComponent(l, 20);

        this.add(Box.createVerticalGlue());
        this.add(title);
        this.add(Box.createVerticalGlue());
        this.add(sub1);
        this.add(sub2);
        this.add(Box.createVerticalGlue());
        this.add(l);
    }

    private void formatComponent(JComponent c, int fontSize) {
        c.setFont(new Font("Futura", Font.PLAIN, fontSize));
        c.setForeground(Color.WHITE);
        c.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        if(c instanceof JButton) {
            ((JButton) c).setBorderPainted(false);
            ((JButton) c).addActionListener(this);
            c.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if(src.equals(back)) {
            JFrame frame = (JFrame)SwingUtilities.getWindowAncestor(this);

            Utility.setMusicOn(music.getText().equals("Music On"));
            Utility.setFrameInterval(gameSpeed.getValue());

            if(aiDifficulty.getSelectedItem().equals("Worst"))
                Utility.setAiDifficulty(Utility.AI_EASY);
            else if(aiDifficulty.getSelectedItem().equals("Good"))
                Utility.setAiDifficulty(Utility.AI_MEDIUM);
            else if(aiDifficulty.getSelectedItem().equals("Best"))
                Utility.setAiDifficulty(Utility.AI_HARD);

            Utility.writeSettings();

            frame.setVisible(false);
            new Main();
            frame.dispose();
        }
        else if(src.equals(l)) {
            // opens browser to GitHub source code link
            try {
                Desktop.getDesktop().browse(new URI("https://github.com/jcrademacher/Light-Cycles?files=1"));
            }
            catch(Exception ex) {
                ex.printStackTrace();
            }
        }
        else if(src.equals(music)) {
            if(music.getText().equals("Music On")) {
                Music.stop();
                music.setText("Music Off");
            }
            else if(music.getText().equals("Music Off")) {
                Music.start();
                music.setText("Music On");
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        back.setForeground(new Color(129,210,224));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        back.setForeground(Color.WHITE);
    }

    // unused
    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void stateChanged(ChangeEvent e) {
        Object src = e.getSource();

        if(src.equals(gameSpeed)) {
            speedValue.setText(Integer.toString(gameSpeed.getValue()));
        }
    }
}
