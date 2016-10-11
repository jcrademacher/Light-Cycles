package jrad.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URI;

/**
 * Created by jackrademacher on 10/8/16.
 */
public class InfoPanel extends JPanel implements ActionListener, MouseListener {

    public final static String HOWTOPLAY = "htp";
    public final static String SETTINGS = "settings";
    public final static String ABOUT = "about";

    private String panelType;
    private JButton back = new JButton("Back");

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
        if(panelType.equals(this.HOWTOPLAY)) {

        }
        else if(panelType.equals(this.SETTINGS)) {

        }
        else if(panelType.equals(this.ABOUT)) {
            String msg1 = "A recreation of the arcade game \"Tron\"";
            String msg2 = "Created by Jack Rademacher - April 2016";
            String link = "View source here";

            JLabel title = new JLabel("Light Cycles");
            formatLabel(title, 40);

            JLabel sub1 = new JLabel(msg1);
            formatLabel(sub1, 20);

            JLabel sub2 = new JLabel(msg2);
            formatLabel(sub2, 20);

            JButton l = new JButton(link);
            l.setBorderPainted(false);
            l.addActionListener(this);
            l.setCursor(new Cursor(Cursor.HAND_CURSOR));
            formatLabel(l, 20);

            this.add(Box.createVerticalGlue());
            this.add(title);
            this.add(Box.createVerticalGlue());
            this.add(sub1);
            this.add(sub2);
            this.add(Box.createVerticalGlue());
            this.add(l);
            this.add(Box.createVerticalGlue());
            this.add(back);
        }
    }

    private void formatLabel(JComponent l, int fontSize) {
        l.setFont(new Font("Futura", Font.PLAIN, fontSize));
        l.setForeground(Color.WHITE);
        l.setAlignmentX(JLabel.CENTER_ALIGNMENT);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if(src.equals(back)) {
            new Main();

            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.setVisible(false);
            frame.dispose();
        }
        else {
            try {
                Desktop.getDesktop().browse(new URI("https://github.com/jcrademacher/Light-Cycles?files=1"));
            }
            catch(Exception ex) {
                ex.printStackTrace();
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


}
