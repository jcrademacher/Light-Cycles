package jrad.player;

import java.awt.*;
import java.util.*;

/**
 * Created by jackrademacher on 10/5/16.
 */
public class ComputerPlayer extends Player {

    private Random rand = new Random();

    private Player p1;
    private Player p2;
    private Player p3;

    private int goalX;
    private int goalY;

    private Color color;

    public ComputerPlayer(Color color, int startx, int starty, int dir, String name) {
        super(color,startx,starty,dir,name);
        super.setAI(true);

        this.color = color;

        goalX = rand.nextInt(900) + 20;
        goalY = rand.nextInt(700) + 20;

        System.out.println(color + ": " + goalX + ", " + goalY);
    }

    public void move(Graphics g) {

        int bikeX = super.playerBike.getPositionX();
        int bikeY = super.playerBike.getPositionY();
        int bikeDir = super.playerBike.getDirection();

        boolean turnRightCapable;
        boolean turnLeftCapable;

        boolean cellAheadOccupied = isOccupied(bikeX,bikeY,bikeDir);

        boolean rightPref = false;
        boolean leftPref = false;

        // if bike is within a 10x10 square of goal, no need to turn anymore
        if(Math.abs(bikeX - goalX) < 10 && Math.abs(bikeY - goalY) < 10) {

            goalX = rand.nextInt(900) + 20;
            goalY = rand.nextInt(700) + 20;

            System.out.println(color + ": " + goalX + ", " + goalY);
        }

        super.move(g);

        // switch test case for any collisions that may occur
        switch(bikeDir) {
            case 1:
                turnRightCapable = !isOccupied(bikeX,bikeY,4);
                turnLeftCapable = !isOccupied(bikeX,bikeY,2);

                // testing if turning left or right will result in more room to play
                for(int y = 2; y < 400; y++) {
                    if(isOccupied(bikeX,bikeY + y,4)) {
                        rightPref = false;
                        leftPref = true;
                        break;
                    }
                    if(isOccupied(bikeX,bikeY - y,2)) {
                        leftPref = false;
                        rightPref = true;
                        break;
                    }
                }

                // test case for if cell in front of bike is occupied
                if(cellAheadOccupied) {
                    if(turnRightCapable && turnLeftCapable) {

                        if(rightPref) super.turnRight();
                        else if(leftPref) super.turnLeft();

                        break;
                    }
                    // if cannot turn right, must turn left to escape
                    else if(turnRightCapable) {
                        super.turnRight();
                        break;
                    }
                    else if(turnLeftCapable) {
                        super.turnLeft();
                        break;
                    }
                }

                // if cell right of bike is unoccupied, bike can turn right
                if(turnRightCapable && rightPref) {
                    if(rand.nextInt(200) == 20)
                        super.turnRight();
                }
                // if cell left of bike is unoccupied, bike can turn left
                if(turnLeftCapable && leftPref) {
                    if(rand.nextInt(200) == 20)
                        super.turnLeft();
                }

                break;
            case 2:
                turnRightCapable = !isOccupied(bikeX,bikeY,1);
                turnLeftCapable = !isOccupied(bikeX,bikeY,3);

                // testing if turning left or right will result in more room to play
                for(int x = 2; x < 400; x++) {
                    if(isOccupied(bikeX + x,bikeY,1)) {
                        rightPref = false;
                        leftPref = true;
                        break;
                    }
                    if(isOccupied(bikeX - x,bikeY,3)) {
                        leftPref = false;
                        rightPref = true;
                        break;
                    }
                }

                // test case for if cell in front of bike is occupied
                if(cellAheadOccupied) {
                    if(turnRightCapable && turnLeftCapable) {

                        if(rightPref) super.turnRight();
                        else if(leftPref) super.turnLeft();

                        break;
                    }
                    // if cannot turn right, must turn left to escape
                    else if(turnRightCapable) {
                        super.turnRight();
                        break;
                    }
                    else if(turnLeftCapable) {
                        super.turnLeft();
                        break;
                    }
                }

                // if cell right of bike is unoccupied, bike can turn right
                if(turnRightCapable && rightPref) {
                    if(rand.nextInt(200) == 20)
                        super.turnRight();
                }

                // if cell left of bike is unoccupied, bike can turn left
                if(turnLeftCapable && leftPref) {
                    if(rand.nextInt(200) == 20)
                        super.turnLeft();
                }

                break;
            case 3:
                turnRightCapable = !isOccupied(bikeX,bikeY,2);
                turnLeftCapable = !isOccupied(bikeX,bikeY,4);

                // testing if turning left or right will result in more room to play
                for(int y = 2; y < 400; y++) {
                    if(isOccupied(bikeX,bikeY - y,2)) {
                        rightPref = false;
                        leftPref = true;
                        break;
                    }
                    if(isOccupied(bikeX,bikeY + y,4)) {
                        leftPref = false;
                        rightPref = true;
                        break;
                    }
                }

                // test case for if cell in front of bike is occupied
                if(cellAheadOccupied) {
                    if(turnRightCapable && turnLeftCapable) {

                        if(rightPref) super.turnRight();
                        else if(leftPref) super.turnLeft();

                        break;
                    }
                    // if cannot turn right, must turn left to escape
                    else if(turnRightCapable) {
                        super.turnRight();
                        break;
                    }
                    else if(turnLeftCapable) {
                        super.turnLeft();
                        break;
                    }
                }

                // if cell right of bike is unoccupied, bike can turn right
                if(turnRightCapable && rightPref) {
                    if(rand.nextInt(200) == 20)
                        super.turnRight();
                }

                // if cell left of bike is unoccupied, bike can turn left
                if(turnLeftCapable && leftPref) {
                    if(rand.nextInt(200) == 20)
                        super.turnLeft();
                }

                break;
            case 4:
                turnRightCapable = !isOccupied(bikeX,bikeY,3);
                turnLeftCapable = !isOccupied(bikeX,bikeY,1);

                // testing if turning left or right will result in more room to play
                for(int x = 2; x < 400; x++) {
                    if(isOccupied(bikeX - x,bikeY,3)) {
                        rightPref = false;
                        leftPref = true;
                        break;
                    }
                    if(isOccupied(bikeX + x,bikeY,1)) {
                        leftPref = false;
                        rightPref = true;
                        break;
                    }
                }

                // test case for if cell in front of bike is occupied
                if(cellAheadOccupied) {
                    if(turnRightCapable && turnLeftCapable) {

                        if(rightPref) super.turnRight();
                        else if(leftPref) super.turnLeft();

                        break;
                    }
                    // if can turn right, must turn right to escape
                    else if(turnRightCapable) {
                        super.turnRight();
                        break;
                    }
                    else if(turnLeftCapable) {
                        super.turnLeft();
                        break;
                    }
                }

                // if cell right of bike is unoccupied, bike can turn right
                // random turns to make more realistic
                if(turnRightCapable && rightPref) {
                    if(rand.nextInt(200) == 20)
                        super.turnRight();
                }

                // if cell left of bike is unoccupied, bike can turn left
                if(turnLeftCapable && leftPref) {
                    if(rand.nextInt(200) == 20)
                        super.turnLeft();
                }

                break;
        }
    }

    public void addPlayerList(Player p1, Player p2, Player p3) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }

    public boolean isOccupied(int x, int y, int testDir) {

        Trail t1 = p1.getBike().getTrail();
        Trail t2 = p2.getBike().getTrail();
        Trail t3 = p3.getBike().getTrail();
        Trail playerTrail = super.playerBike.getTrail();

        Rectangle r = new Rectangle();

        // switch, different cases for each direction
        switch(testDir) {
            case 1:
                if(x+8 > 983)
                    return true;
                r = new Rectangle(x+8,y,2,6);
                break;
            // repeated only with slightly different rectangles
            case 2:
                if(y-4 < 13)
                    return true;
                r = new Rectangle(x,y-4,6,2);
                break;
            case 3:
                if(x-4 < 10)
                    return true;
                r = new Rectangle(x-4,y,2,6);
                break;
            case 4:
                if(y+8 > 763)
                    return true;
                r = new Rectangle(x,y+8,6,2);
                break;
        }

        // if trail overlaps rectangle at any position
        if(t1.overlaps(r) || t2.overlaps(r) ||
                t3.overlaps(r) || playerTrail.overlaps(r))
            return true;

        return false;
    }
}