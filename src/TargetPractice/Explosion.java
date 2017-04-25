/*
 * This is the Explosion class for TargetPractice.
 * This class creates Explosion objects when enemies are hit with bullets.
 */
package TargetPractice;

import java.awt.*;

/**
 * Created by: Philip Kaiser-Parlette
 */
public class Explosion {

    //declare variables
    private int x;
    private int y;
    private int r;
    private int maxR;

    /**
     * This constructor creates an Explosion object at coordinates (x, y) with
     * radius r and max radius max. PreCondition: An Explosion is needed.
     * PostCondition: An Explosion has been created at the x and y coordinates
     * with the given radius and max radius.
     *
     * @param x The x coordinate of the explosion.
     * @param y The y coordinate of the explosion.
     * @param r The starting radius of the explosion.
     * @param max The max radius of the explosion.
     */
    public Explosion(int x, int y, int r, int max) {
        this.x = x;
        this.y = y;
        this.r = r;
        maxR = max;
    }//end of constructor

    /**
     * The update method updates the explosion. PreCondition: The explosion
     * needs to be updated. PostCondition: The explosion has been updated.
     *
     * @return boolean Whether the explosion is finished or not.
     */
    public boolean update() {
        r += 2;
        if (r >= maxR) {
            return true;
        }
        return false;
    }//end of update method

    /**
     * The draw method draws the explosion. PreCondition: The explosion needs to
     * be drawn. PostCondition: The explosion has been drawn.
     *
     * @param g The Graphics2D object used to draw.
     */
    public void draw(Graphics2D g) {
        g.setColor(new Color(255, 255, 255, 128));
        g.setStroke(new BasicStroke(3));
        g.drawOval(x - r, y - r, 2 * r, 2 * r);
        g.setStroke(new BasicStroke(1));
    }//end of draw method
}//end of class
