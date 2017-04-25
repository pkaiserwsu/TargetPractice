/*
 * This is the Player class for TargetPractice.
 * This class creates Bullet objects that will move across the screen and might hit
 * enemies.
 */
package TargetPractice;

import java.awt.*;

/**
 * Created by: Philip Kaiser-Parlette
 */
public class Bullet {

    //declare variables
    private int x;
    private int y;
    private int r;
    private double dy;
    private Color color;

    /**
     * This constructor creates a Bullet object at coordinates (x, y).
     * PreCondition: A Bullet is needed. PostCondition: A Bullet has been
     * created at the x and y coordinates.
     *
     * @param x The x coordinate of the bullet.
     * @param y The y coordinate of the bullet.
     */
    public Bullet(int x, int y) {
        this.x = x;
        this.y = y;
        r = 5;
        dy = 25;
        color = Color.YELLOW;
    }//end of constructor

    /**
     * The getX method returns the value of x. PreCondition: The value of x is
     * needed. PostCondition: The value of x has been returned.
     *
     * @return x The x coordinate of the bullet.
     */
    public int getX() {
        return x;
    }//end of getX method

    /**
     * The getY method returns the value of y. PreCondition: The value of y is
     * needed. PostCondition: The value of y has been returned.
     *
     * @return y The y coordinate of the bullet.
     */
    public int getY() {
        return y;
    }//end of getY method

    /**
     * The getR method returns the value of r. PreCondition: The value of r is
     * needed. PostCondition: The value of r has been returned.
     *
     * @return r The radius of the bullet.
     */
    public int getR() {
        return r;
    }//end of getR method

    /**
     * The update method updates the bullet. PreCondition: The bullet needs to
     * be updated. PostCondition: The bullet has been updated.
     */
    public boolean update() {
        y -= dy;
        if (y < -r) {
            return true;
        }
        return false;
    }//end of update method

    /**
     * The draw method draws the bullet. PreCondition: The bullet needs to be
     * drawn. PostCondition: The bullet has been drawn.
     *
     * @param g The Graphics2D object used to draw.
     */
    public void draw(Graphics2D g) {
        g.setColor(color);
        g.fillOval(x - r, y - r, 2 * r, 2 * r);
    }//end of draw method
}//end of class
