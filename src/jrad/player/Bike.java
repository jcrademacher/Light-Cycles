package jrad.player;

import java.awt.*;

/**
 * Created by jackrademacher on 10/5/16.
 */
public class Bike {

    private Trail trail;
    private int ypos;
    private int xpos;
    private int direction; // 1 = right, 2 = up, 3 = left, 4 = down
    private Color color;

    public Bike(Color color, int xpos, int ypos, int dir) { //only this constructor should be used
        this.color = color;
        this.xpos = xpos;
        this.ypos = ypos;
        direction = dir;
        trail = new Trail(color);
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(xpos,ypos,6,6);

        trail.drawSegments(g);
        trail.addSegment(new Point(xpos,ypos));
    }

    // methods following are pretty self explanatory
    public int getPositionX() {
        return xpos;
    }

    public int getPositionY() {
        return ypos;
    }

    public int getDirection() {
        return direction;
    }

    public Trail getTrail() {
        return trail;
    }

    public void setPositionX(int xpos) {
        this.xpos = xpos;
    }

    public void setPositionY(int ypos) {
        this.ypos = ypos;
    }

    public void setDirection(int dir) {
        direction = dir;
    }
}