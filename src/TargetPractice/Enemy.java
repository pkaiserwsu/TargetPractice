/*
 * This is the Enemy class for TargetPractice.
 * This class creates Enemy objects that move across the screen.
 */
package TargetPractice;

import java.awt.*;

/**
 * Created by: Philip Kaiser-Parlette
 */
public class Enemy {

    //declare variables
    private int x;
    private int y;
    private int r;
    private int dx;
    private int speed;
    private Color color;
    private int type;
    private boolean dead;

    /**
     * This constructor creates an Enemy with the given type. PreCondition: An
     * Enemy is needed. PostCondition: An Enemy has been created with the
     * correct type.
     *
     * @param type The type of enemy.
     */
    public Enemy(int type) {
        this.type = type;
        //two type of enemies with different speeds, sizes, and colors
        if (type == 1) {
            color = Color.RED;
            speed = 6;
            r = 15;
        } else if (type == 2) {
            color = Color.YELLOW;
            speed = 10;
            r = 10;
        }
        //chooses if the enemy will move from left to right or vice-versa
        if (Math.random() < .5) {
            x = -r;
            dx = speed;
        } else {
            x = GamePanel.WIDTH + r;
            dx = -speed;
        }
        //random y position on upper part of screen
        y = (int) (Math.random() * GamePanel.HEIGHT / 2) + r;
        dead = false;
    }//end of constructor

    /**
     * The getX method returns the value of x. PreCondition: The value of x is
     * needed. PostCondition: The value of x has been returned.
     *
     * @return x The x coordinate of the enemy.
     */
    public int getX() {
        return x;
    }//end of getX method

    /**
     * The getY method returns the value of y. PreCondition: The value of y is
     * needed. PostCondition: The value of y has been returned.
     *
     * @return y The y coordinate of the enemy.
     */
    public int getY() {
        return y;
    }//end of getY method

    /**
     * The getR method returns the value of r. PreCondition: The value of r is
     * needed. PostCondition: The value of r has been returned.
     *
     * @return r The radius of the enemy.
     */
    public int getR() {
        return r;
    }//end of getR method

    /**
     * The getType method returns the value of type. PreCondition: The value of
     * type is needed. PostCondition: The value of type has been returned.
     *
     * @return type The type of the enemy.
     */
    public int getType() {
        return type;
    }//end of getType method

    /**
     * The isDead method returns the value of dead. PreCondition: The value of
     * dead is needed. PostCondition: The value of dead has been returned.
     *
     * @return dead Whether or not the enemy is dead.
     */
    public boolean isDead() {
        return dead;
    }//end of isDead method

    /**
     * The hit method sets the value of dead to true for the enemy that is hit.
     * PreCondition: An enemy is hit. PostCondition: The enemy is dead.
     */
    public void hit() {
        dead = true;
    }//end of hit method

    /**
     * The update method updates the enemy. PreCondition: The enemy needs to be
     * updated. PostCondition: The enemy has been updated.
     */
    public void update() {
        x += dx;
    }//end of update method

    /**
     * The draw method draws the enemy. PreCondition: The enemy needs to be
     * drawn. PostCondition: The enemy has been drawn.
     *
     * @param g The Graphics2D object used to draw.
     */
    public void draw(Graphics2D g) {
        g.setColor(color);
        g.fillOval(x - r, y - r, 2 * r, 2 * r);
        g.setStroke(new BasicStroke(3));
        g.setColor(color.darker());
        g.drawOval(x - r, y - r, 2 * r, 2 * r);
        g.setStroke(new BasicStroke(1));
    }//end of draw method
}//end of class
