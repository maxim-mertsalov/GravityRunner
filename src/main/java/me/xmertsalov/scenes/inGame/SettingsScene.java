package me.xmertsalov.scenes.inGame;

import me.xmertsalov.Game;
import me.xmertsalov.scenes.GameScene;
import me.xmertsalov.scenes.IScene;
import me.xmertsalov.scenes.Scene;
import me.xmertsalov.ui.SettingsManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * The {@code SettingsScene} class represents the settings screen of the game.
 * It extends the {@link Scene} class and implements the {@link IScene} interface.
 * This class is responsible for managing the settings UI and handling user interactions
 * such as mouse and keyboard events.
 */
public class SettingsScene extends Scene implements IScene {

    // UI
    private SettingsManager settingsManager;

    /**
     * Constructs a new {@code SettingsScene} instance.
     *
     * @param game the {@link Game} instance to which this scene belongs.
     *             It is used to access shared game resources and state.
     */
    public SettingsScene(Game game) {
        super(game);
        settingsManager = new SettingsManager(this, game);
    }

    /**
     * Updates the state of the settings scene.
     * This method is called on every game update cycle.
     */
    @Override
    public void update() {
        settingsManager.update();
    }

    /**
     * Draws the settings scene on the screen.
     *
     * @param g the {@link Graphics} object used for rendering the scene.
     */
    @Override
    public void draw(Graphics g) {
        settingsManager.draw(g);
    }

    /**
     * Handles mouse click events within the settings scene.
     *
     * @param e the {@link MouseEvent} containing details about the mouse click.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        settingsManager.mouseClicked(e);
    }

    /**
     * Handles mouse press events within the settings scene.
     *
     * @param e the {@link MouseEvent} containing details about the mouse press.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        settingsManager.mousePressed(e);
    }

    /**
     * Handles mouse release events within the settings scene.
     *
     * @param e the {@link MouseEvent} containing details about the mouse release.
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        settingsManager.mouseReleased(e);
    }

    /**
     * Handles mouse movement events within the settings scene.
     *
     * @param e the {@link MouseEvent} containing details about the mouse movement.
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        settingsManager.mouseMoved(e);
    }

    /**
     * Handles mouse drag events within the settings scene.
     *
     * @param e the {@link MouseEvent} containing details about the mouse drag.
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        settingsManager.mouseDragged(e);
    }

    /**
     * Handles key press events within the settings scene.
     * Currently, this method does not perform any actions.
     *
     * @param e the {@link KeyEvent} containing details about the key press.
     */
    @Override
    public void keyPressed(KeyEvent e) {}

    /**
     * Handles key release events within the settings scene.
     * Currently, this method does not perform any actions.
     *
     * @param e the {@link KeyEvent} containing details about the key release.
     */
    @Override
    public void keyReleased(KeyEvent e) {}
}
