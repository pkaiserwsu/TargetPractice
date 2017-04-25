/*
 * This is the Player class for TargetPractice.
 * This class creates a Player object, and allows the player to be controlled.
 */
package TargetPractice;

import java.awt.*;

/**
 * Created by: Philip Kaiser-Parlette
 */
public class Player {
    //declare variables
    private int x;
    private int y;
    private int r;
    private int dx;
    private int speed;
    private boolean left;
    private boolean right;
    private boolean firing;
    private long firingTimer;
    private final long FIRING_DELAY = 1000;
    private int score;
    private int shotMiss;
    
    /**
     * This constructor creates a Player object with no arguments.
     * PreCondition: A Player object is needed. PostCondition: A Player
     * object has been constructed.
     */
    public Player() {
        x = GamePanel.WIDTH / 2;
        y = GamePanel.HEIGHT - 100;
        r = 15;
        dx = 0;
        speed = 10;
        firing = false;
        firingTimer = System.nanoTime();
    }//end of constuctor
    
    /**
     * The setLeft method sets a boolean to left. PreCondition: A boolean has
     * been given. PostCondition: left has been assigned to the boolean value.
     * 
     * @param b The boolean.
     */
    public void setLeft(boolean b) {
        left = b;
    }//end of setLeft method
    
    /**
     * The setRight method sets a boolean to right. PreCondition: A boolean has
     * been given. PostCondition: right has been assigned to the boolean value.
     * 
     * @param b The boolean.
     */
    public void setRight(boolean b) {
        right = b;
    }//end of setRight method
    
    /**
     * The setFiring method sets a boolean to firing. PreCondition: A boolean
     * has been given. PostCondition: firing has been assigned to the boolean
     * value.
     * 
     * @param b The boolean.
     */
    public void setFiring(boolean b) {
        firing = b;
    }//end of setFiring method
    
    /**
     * The addScore adds a given integer to score. PreCondition: An enemy has
     * been hit and an integer has been given. PostCondition: The integer has
     * been added to score.
     * 
     * @param i The integer.
     */
    public void addScore(int i) {
        score += i;
    }//end of addScore method
    
    /**
     * The getScore method returns the value of score. PreCondition: The value
     * of score is needed. PostCondition: The score has been returned.
     * 
     * @return score The player's current score.
     */
    public int getScore() {
        return score;
    }//end of getScore method
    
    /**
     * The addMiss increases the shotMiss variable by one. PreCondition: A shot
     * has gone offscreen. PostCondition: shotMiss increases by one.
     */
    public void addMiss() {
        shotMiss++;
    }//end of addMiss method
    
    /**
     * The getMiss method returns the value of shotMiss. PreCondition: The value
     * of shotMiss is needed. PostCondition: The number of misses has been
     * returned.
     * 
     * @return shotMiss The player's missed shots.
     */
    public int getMiss() {
        return shotMiss;
    }//end of getMiss method
    
    /**
     * The update method updates the player: allowing the player to move and
     * fire. PreCondition: The player needs to be updated. PostCondition: The
     * player has been updated.
     */
    public void update() {
        //move the player left or right
        if(left) {
            dx = -speed;
        }
        if(right) {
            dx = speed;
        }
        x += dx;
        //keep the player on the screen
        if(x < r) {
            x = r;
        }
        if(x > GamePanel.WIDTH - r) {
            x = GamePanel.HEIGHT - r;
        }
        //reset dx
        dx = 0;
        //player fires a shot if it is able to
        if(firing) {
            long elapsed = (System.nanoTime() - firingTimer) / 1000000;
            if(elapsed > FIRING_DELAY) {
                GamePanel.bullets.add(new Bullet(x,y));
                firingTimer = System.nanoTime();
            }
        }
    }//end of update method
    
    /**
     * The draw method draws the player. PreCondition: The player needs to be
     * drawn. PostCondition: The player has been drawn.
     * 
     * @param g The Graphics2D object used to draw.
     */
    public void draw(Graphics2D g) {
        g.setColor(Color.GREEN);
        g.fillOval(x - r, y - r, 2 * r, 2 * r);
        g.setStroke(new BasicStroke(3));
        g.setColor(Color.GREEN.darker());
        g.drawOval(x - r, y - r, 2 * r, 2 * r);
        g.setStroke(new BasicStroke(1));
    }//end of draw method 
}//end of class
