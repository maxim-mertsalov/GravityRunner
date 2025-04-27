package me.xmertsalov.scenes.inGame;

import me.xmertsalov.Game;
import me.xmertsalov.scenes.GameScene;
import me.xmertsalov.scenes.IScene;
import me.xmertsalov.scenes.Scene;
import me.xmertsalov.ui.MenuManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * The {@code MenuScene} class represents the menu screen of the game.
 * It handles the rendering and interaction logic for the menu, such as
 * drawing menu items, responding to mouse clicks, and handling key presses.
 */
public class MenuScene extends Scene implements IScene {

    // Game objects
    private MenuManager menuManager;

    /**
     * Constructs a new {@code MenuScene} instance.
     *
     * @param game the main game instance
     */
    public MenuScene(Game game) {
        super(game);
        start();
    }

    /**
     * Initializes the menu scene by creating a {@link MenuManager} instance.
     */
    private void start() {
        menuManager = new MenuManager(game);
    }

    /**
     * Updates the state of the menu scene.
     * This method is called on each game update cycle.
     */
    @Override
    public void update() {
        menuManager.update();
    }

    /**
     * Draws the menu scene on the screen.
     *
     * @param g the {@link Graphics} object used for rendering
     */
    @Override
    public void draw(Graphics g) {
        menuManager.draw(g);
    }

    /**
     * Handles mouse click events within the menu scene.
     * If the user clicks on a specific area, it transitions to the panorama scene.
     *
     * @param e the {@link MouseEvent} containing details about the mouse click
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        menuManager.mouseClicked(e);
        if (e.getX() >= 0 && e.getX() <= 50 * Game.SCALE && e.getY() >= 0 && e.getY() <= 50 * Game.SCALE) {
            GameScene.scene = GameScene.PANORAMA;
        }
    }

    /**
     * Handles mouse press events within the menu scene.
     *
     * @param e the {@link MouseEvent} containing details about the mouse press
     */
    @Override
    public void mousePressed(MouseEvent e) {
        menuManager.mousePressed(e);
    }

    /**
     * Handles mouse release events within the menu scene.
     *
     * @param e the {@link MouseEvent} containing details about the mouse release
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        menuManager.mouseReleased(e);
    }

    /**
     * Handles mouse movement events within the menu scene.
     *
     * @param e the {@link MouseEvent} containing details about the mouse movement
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        menuManager.mouseMoved(e);
    }

    /**
     * Handles mouse drag events within the menu scene.
     * This method is currently not implemented.
     *
     * @param e the {@link MouseEvent} containing details about the mouse drag
     */
    @Override
    public void mouseDragged(MouseEvent e) {}

    /**
     * Handles key press events within the menu scene.
     * If the Enter key is pressed, it transitions to the playing scene.
     *
     * @param e the {@link KeyEvent} containing details about the key press
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            GameScene.scene = GameScene.PLAYING;
    }

    /**
     * Handles key release events within the menu scene.
     * This method is currently not implemented.
     *
     * @param e the {@link KeyEvent} containing details about the key release
     */
    @Override
    public void keyReleased(KeyEvent e) {}
}
