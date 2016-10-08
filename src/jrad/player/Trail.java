package jrad.player;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by jackrademacher on 10/6/16.
 */
public class Trail {

    private Color color;
    // dynamic array of Point(x,y) coordinates of individual square segments to be placed down
    private ArrayList<Point> segments;

    public Trail(Color color) {
        this.color = color;
        segments = new ArrayList<Point>(0);
    }

    // adds a rectangular segment to arraylist
    public void addSegment(Point p) {
        segments.add(p);
    }

    public void clear() {
        segments.clear();
    }

    public void drawSegments(Graphics g) {
        g.setColor(color);
        for(Point cur: segments) {
            g.fillRect(cur.x,cur.y,6,6);
        }
    }

    public boolean overlaps(Rectangle r1) {
        Rectangle r2;
        for(Point cur: segments) {
            r2 = new Rectangle(cur,new Dimension(6,6));
            if(r2.intersects(r1) || r2.contains(r1))
                return true;
        }

        return false;
    }
}
