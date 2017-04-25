/*
 * This is the GamePanel class for TargetPractice.
 * This class creates a GamePanel, and controls all changes made in the game:
 * updating, rendering, and drawing the game.
 */
package TargetPractice;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import javax.swing.*;

/**
 * Created by: Philip Kaiser-Parlette
 */
public class GamePanel extends JPanel implements Runnable, KeyListener {

    //declare variables and constants
    public static final int WIDTH = 600;
    public static final int HEIGHT = 600;
    private static Thread thread;
    private boolean running;
    private BufferedImage image;
    private Graphics2D g;
    public static Player player;
    public static ArrayList<Bullet> bullets;
    public static ArrayList<Enemy> enemies;
    public static ArrayList<Explosion> explosions;
    private long enemyTimer;
    private long enemyTimerDiff;
    private final int ENEMY_DELAY = 1000;
    private boolean enemySpawn;

    /**
     * This constructor creates a GamePanel object with no arguments.
     * PreCondition: A GamePanel object is needed. PostCondition: A GamePanel
     * object has been constructed.
     */
    public GamePanel() {
        super();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        requestFocus();
    }//end of constructor

    /**
     * The addNotify method creates a thread for the game to execute on, and a
     * key listener for game controls. PreCondition: A thread and key listener
     * are needed. PostCondition: A thread and key listener have been created.
     */
    @Override
    public void addNotify() {
        super.addNotify();
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
        addKeyListener(this);
    }

    /**
     * The run method sets up the game and runs it until the player wins or
     * loses. PreCondition: The game needs to run. PostCondition: The game has
     * been won or lost.
     */
    @Override
    public void run() {
        running = true;
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        player = new Player();
        bullets = new ArrayList<Bullet>();
        enemies = new ArrayList<Enemy>();
        explosions = new ArrayList<Explosion>();
        enemyTimer = 0;
        enemyTimerDiff = 0;
        while (running) {
            pause();
            gameUpdate();
            running = gameRender();
            gameDraw();
        }
    }//end of run method

    /**
     * The pause method forces the game to slow down to a playable speed by
     * making the thread stop for 50 ms. PreCondition: The game needs to pause.
     * PostCondition: The game has paused for 50 ms.
     */
    public static void pause() {
        try {
            Thread.sleep(50);
        } catch (Exception e) {
        }
    }//end of pause method

    /**
     * The gameUpdate method updates any changes in the game: adding enemies,
     * updating the player, bullets, enemies, and explosions, and checking for
     * collisions and enemy death. PreCondition: The game needs to update.
     * PostCondition: The game has updated.
     */
    private void gameUpdate() {
        //enemy spawn
        if (enemyTimer == 0) { //delay on enemy spawn
            enemySpawn = false;
            enemyTimer = System.nanoTime();
        } else {
            enemyTimerDiff = (System.nanoTime() - enemyTimer) / 1000000;
            if (enemyTimerDiff > ENEMY_DELAY) {
                enemySpawn = true;
                enemyTimer = 0;
                enemyTimerDiff = 0;
            }
        }
        if (enemySpawn) {
            //~75% chance of type 1 enemies and ~25% chance of type 2 enemies
            double type = Math.random();
            if (type > .25) {
                enemies.add(new Enemy(1));
            } else {
                enemies.add(new Enemy(2));
            }
        }
        //player update
        player.update();
        //bullet update
        for (int i = 0; i < bullets.size(); i++) {
            boolean remove = bullets.get(i).update();
            if (remove) {
                player.addMiss();
                bullets.remove(i);
                i--;
            }
        }
        //enemy update
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).update();
        }
        //explosion update
        for (int i = 0; i < explosions.size(); i++) {
            boolean remove = explosions.get(i).update();
            if (remove) {
                explosions.remove(i);
                i--;
            }
        }
        //check collision
        for (int i = 0; i < bullets.size(); i++) {
            Bullet b = bullets.get(i);
            int bX = b.getX();
            int bY = b.getY();
            int bR = b.getR();
            for (int j = 0; j < enemies.size(); j++) {
                Enemy e = enemies.get(j);
                int eX = e.getX();
                int eY = e.getY();
                int eR = e.getR();
                /* calculate distance and compare to smallest possible
                 * non-collision distance
                 */
                int dX = bX - eX;
                int dY = bY - eY;
                double dist = Math.sqrt((dX * dX) + (dY * dY));
                if (dist < bR + eR) {
                    e.hit();
                    bullets.remove(i);
                    i--;
                    break;
                }
            }
        }
        //check dead
        for (int i = 0; i < enemies.size(); i++) {
            if (enemies.get(i).isDead()) {
                Enemy e = enemies.get(i);
                player.addScore(e.getType());
                enemies.remove(i);
                i--;
                explosions.add(new Explosion(e.getX(), e.getY(), e.getR(), e.getR() + 20));
            }
        }
    }//end of gameUpdate method

    /*
     * The gameRender method draws the game in a buffer before it is drawn with
     * the gameDraw method. PreCondition: The game has been updated and needs
     * to be drawn. PostCondition: The game has been drawn in a buffer.
     * 
     * @return running Whether the game is still running or has been finished.
     */
    private boolean gameRender() {
        //draw background
        g.setColor(Color.BLUE);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        //draw text
        g.setColor(Color.BLACK);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        g.drawString("Score: " + player.getScore(), 10, HEIGHT - 30);
        g.drawString("Shots missed: " + player.getMiss(), 10, HEIGHT - 10);
        //draw player
        player.draw(g);
        //draw bullet
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).draw(g);
        }
        //draw enemy
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).draw(g);
        }
        //draw explosion
        for (int i = 0; i < explosions.size(); i++) {
            explosions.get(i).draw(g);
        }
        //draw win or loss
        if (player.getScore() >= 20) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
            int length = (int) g.getFontMetrics().getStringBounds("You win!", g).getWidth();
            g.drawString("You win!", ((WIDTH / 2) - (length / 2)), HEIGHT / 2);
            return false;
        }
        if (player.getMiss() >= 10) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
            int length = (int) g.getFontMetrics().getStringBounds("You lose...", g).getWidth();
            g.drawString("You lose...", ((WIDTH / 2) - (length / 2)), HEIGHT / 2);
            return false;
        }
        return true;
    }//end of gameRender method

    /*
     * The gameDraw method draws the game. PreCondition: The game has been
     * rendered and needs to be drawn. PostCondition: The game has been drawn.
     */
    private void gameDraw() {
        Graphics g2 = this.getGraphics();
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
    }

    /**
     * The keyTyped method does nothing. It simply needed to override
     * KeyListener's keyTyped method.
     *
     * @param key The key typed.
     */
    @Override
    public void keyTyped(KeyEvent key) {
    }

    /**
     * The keyPressed method sets player control booleans to true if the correct
     * buttons are pressed. PreCondition: A key has been pressed. PostCondition:
     * If the key is for player control, the proper boolean is set to true.
     *
     * @param key The key pressed.
     */
    @Override
    public void keyPressed(KeyEvent key) {
        int k = key.getKeyCode();
        if (k == KeyEvent.VK_LEFT) {
            player.setLeft(true);
        }
        if (k == KeyEvent.VK_RIGHT) {
            player.setRight(true);
        }
        if (k == KeyEvent.VK_SPACE) {
            player.setFiring(true);
        }
    }

    /**
     * The keyReleased method sets player control booleans to false if the
     * correct buttons are released. PreCondition: A key has been released.
     * PostCondition: If the key is for player control, the proper boolean is
     * set to false.
     *
     * @param key The key released.
     */
    @Override
    public void keyReleased(KeyEvent key) {
        int k = key.getKeyCode();
        if (k == KeyEvent.VK_LEFT) {
            player.setLeft(false);
        }
        if (k == KeyEvent.VK_RIGHT) {
            player.setRight(false);
        }
        if (k == KeyEvent.VK_SPACE) {
            player.setFiring(false);
        }
    }
}
