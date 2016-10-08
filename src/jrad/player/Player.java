package jrad.player;

import java.awt.*;

/**
 * Created by jackrademacher on 10/5/16.
 */
public class Player {
    protected Bike playerBike;
    private boolean onScreen;
    private boolean hasCrashed;
    private int boostLevel;
    private String name;
    private boolean AI = false;

    public Player(Color color, int startx, int starty, int dir, String name) {
        playerBike = new Bike(color,startx,starty,dir);
        onScreen = false;
        hasCrashed = false;
        this.name = name;

        boostLevel = 0;
    }

    public void setOnScreen(boolean b)     {onScreen = b;}
    public boolean isOnScreen()            {return onScreen;}

    public Bike getBike() {
        return playerBike;
    }

    public String getName() {
        return name;
    }

    public boolean hasHitEdge()  {
        int x = playerBike.getPositionX();
        int y = playerBike.getPositionY();

        if(x < 10 || x > 983 || y < 13 || y > 765) {
            playerBike.getTrail().clear();
            return true;
        }

        return false;
    }

    // code for self is slightly different
    public boolean hasCollidedWithSelf() {
        int x = playerBike.getPositionX();
        int y = playerBike.getPositionY();

        Rectangle r = new Rectangle();

        switch(playerBike.getDirection()) {
            case 1:
                r = new Rectangle(x+6,y,1,6);
                break;
            case 2:
                r = new Rectangle(x,y-1,6,1);
                break;
            case 3:
                r = new Rectangle(x-1,y,1,6);
                break;
            case 4:
                r = new Rectangle(x,y+6,6,1);
                break;
        }

        if(playerBike.getTrail().overlaps(r)) {
            playerBike.getTrail().clear();
            return true;
        }
        else return false;
    }

    // method takes 3 players and tests if the very front rectangular sliver of their bike
    // is intersecting with any of the rectangles contained in the Trail class
    public boolean hasCollidedWith(Player p1, Player p2, Player p3) {
        int x = playerBike.getPositionX();
        int y = playerBike.getPositionY();

        Trail t1 = p1.getBike().getTrail();
        Trail t2 = p2.getBike().getTrail();
        Trail t3 = p3.getBike().getTrail();

        Rectangle r = new Rectangle();

        // switch, different cases for each direction
        switch(playerBike.getDirection()) {
            case 1:
                r = new Rectangle(x+5,y,1,6);
                break;
            // repeated only with slightly different rectangles
            case 2:
                r = new Rectangle(x,y,6,1);
                break;
            case 3:
                r = new Rectangle(x,y,1,6);
                break;
            case 4:
                r = new Rectangle(x,y+5,6,1);
                break;
        }

        // if trail overlaps rectangle at any position
        if(t1.overlaps(r) || t2.overlaps(r) || t3.overlaps(r)) {
            playerBike.getTrail().clear(); // clears trail so bike wont run into a nonexistent trail
            return true;
        }

        return false;
    }

    // moves player forward 1 pixel in whichever direction
    public void move(Graphics g) {
        int curX = playerBike.getPositionX();
        int curY = playerBike.getPositionY();
        int dir = playerBike.getDirection();

        if(dir == 1)
            playerBike.setPositionX(curX + (2 + boostLevel));
        if(dir == 2)
            playerBike.setPositionY(curY - (2 + boostLevel));
        if(dir == 3)
            playerBike.setPositionX(curX - (2 + boostLevel));
        if(dir == 4)
            playerBike.setPositionY(curY + (2 + boostLevel));

        playerBike.draw(g);
    }

    public void setBoostLevel(int level) {
        boostLevel = level;
    }

    public int getBoostLevel() {
        return boostLevel;
    }

    // turns player & bike left
    public void turnLeft() {
        int dir = playerBike.getDirection();

        if(dir == 4)
            dir = 1;
        else
            dir++;

        playerBike.setDirection(dir);
    }

    // turns player & bike right
    public void turnRight() {
        int dir = playerBike.getDirection();

        if(dir == 1)
            dir = 4;
        else
            dir--;

        playerBike.setDirection(dir);
    }

    protected void setAI(boolean b) {
        AI = b;
    }
}
