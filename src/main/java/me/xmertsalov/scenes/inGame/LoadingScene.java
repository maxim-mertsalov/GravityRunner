package me.xmertsalov.scenes.inGame;

import me.xmertsalov.Game;
import me.xmertsalov.exceptions.BundleLoadException;
import me.xmertsalov.scenes.GameScene;
import me.xmertsalov.scenes.IScene;
import me.xmertsalov.scenes.Scene;
import me.xmertsalov.utils.BundleLoader;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * The {@code LoadingScene} class represents the loading screen of the game.
 * It displays a logo and waits for a specified amount of time before transitioning
 * to the main menu scene.
 */
public class LoadingScene extends Scene implements IScene {

    // Images
    private BufferedImage background;

    // States
    private final int timeToWait = 3; // Time to wait in seconds before transitioning
    private int currentTimeToWait = 0; // Tracks the elapsed waiting time

    // Other
    private int ticksToWait = 0; // Tracks the number of ticks elapsed

    // UI Settings
    private int initLogoWidth = 76; // Initial width of the logo
    private int initLogoHeight = 25; // Initial height of the logo

    private int logoWidth = (int)(initLogoWidth * Game.SCALE * 3); // Scaled width of the logo
    private int logoHeight = (int)(initLogoHeight * Game.SCALE * 3); // Scaled height of the logo

    /**
     * Constructs a new {@code LoadingScene}.
     *
     * @param game The {@code Game} instance to which this scene belongs.
     * @throws RuntimeException if the background image cannot be loaded.
     */
    public LoadingScene(Game game) {
        super(game);
        try {
            background = BundleLoader.getSpriteAtlas(BundleLoader.LOGO);
        } catch (BundleLoadException e) {
            Game.logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates the state of the loading scene.
     * Tracks the elapsed time and transitions to the menu scene
     * once the waiting time has been reached.
     */
    @Override
    public void update() {
        ticksToWait++;
        if (ticksToWait >= Game.UPS_LIMIT) {
            ticksToWait = 0;
            currentTimeToWait++;
        }
        if (game.getMenuScene() != null && currentTimeToWait >= timeToWait) {
            GameScene.scene = GameScene.MENU;
        }
    }

    /**
     * Draws the loading scene on the screen.
     * Displays the logo centered on the screen.
     *
     * @param g The {@code Graphics} object used for rendering.
     */
    @Override
    public void draw(Graphics g) {
        g.drawImage(background, (int)(Game.WINDOW_WIDTH / 2 - logoWidth / 2), (int)(Game.WINDOW_HEIGHT / 2 - logoHeight / 2 - 25 * Game.SCALE), logoWidth, logoHeight, null);
    }

    /**
     * Handles mouse click events. Not used in this scene.
     *
     * @param e The {@code MouseEvent} object.
     */
    @Override
    public void mouseClicked(MouseEvent e) {}

    /**
     * Handles mouse press events. Not used in this scene.
     *
     * @param e The {@code MouseEvent} object.
     */
    @Override
    public void mousePressed(MouseEvent e) {}

    /**
     * Handles mouse release events. Not used in this scene.
     *
     * @param e The {@code MouseEvent} object.
     */
    @Override
    public void mouseReleased(MouseEvent e) {}

    /**
     * Handles mouse movement events. Not used in this scene.
     *
     * @param e The {@code MouseEvent} object.
     */
    @Override
    public void mouseMoved(MouseEvent e) {}

    /**
     * Handles mouse drag events. Not used in this scene.
     *
     * @param e The {@code MouseEvent} object.
     */
    @Override
    public void mouseDragged(MouseEvent e) {}

    /**
     * Handles key press events. Not used in this scene.
     *
     * @param e The {@code KeyEvent} object.
     */
    @Override
    public void keyPressed(KeyEvent e) {}

    /**
     * Handles key release events. Not used in this scene.
     *
     * @param e The {@code KeyEvent} object.
     */
    @Override
    public void keyReleased(KeyEvent e) {}
}
