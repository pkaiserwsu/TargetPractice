/*
 * This is a simple target practice style 2D shooter made when learning Java.
 * The player moves and shoots at moving targets and wins if they score 20
 * points or loses if they miss 10 shots.
 */
package TargetPractice;

import javax.swing.*;

/**
 * Created by: Philip Kaiser-Parlette
 */
public class TargetPractice {

    /**
     * Frame to hold the game panel, implemented in the GamePanel class.
     */
    public static class Game extends JFrame {

        private GamePanel gamePanel;

        /**
         * This constructor creates a Game object with no arguments.
         * PreCondition: A Game object is needed. PostCondition: A Game object
         * has been constructed.
         */
        public Game() {
            super("Project 6 Game");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gamePanel = new GamePanel();
            setContentPane(gamePanel);
            pack();
            setLocationRelativeTo(null);
            setVisible(true);
        }//end of constructor
    }//end of Game class

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Game game = new Game();
    }//end of main method
}//end of class
