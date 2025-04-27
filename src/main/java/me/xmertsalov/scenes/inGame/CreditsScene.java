package me.xmertsalov.scenes.inGame;

import me.xmertsalov.Game;
import me.xmertsalov.scenes.IScene;
import me.xmertsalov.scenes.Scene;
import me.xmertsalov.ui.CreditsManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * The {@code CreditsScene} class represents the scene in the game where credits are displayed.
 * It extends the {@link Scene} class and implements the {@link IScene} interface.
 * This class is responsible for managing the credits screen, including rendering and handling user input.
 */
public class CreditsScene extends Scene implements IScene {
    // UI
     private CreditsManager creditsManager;

    /**
     * Constructs a new {@code CreditsScene} instance.
     *
     * @param game the {@link Game} instance to which this scene belongs.
     *             This is used to initialize the {@link CreditsManager}.
     */
    public CreditsScene(Game game) {
        super(game);
        creditsManager = new CreditsManager(game);
    }

    /**
     * Updates the state of the credits scene.
     * This method delegates the update logic to the {@link CreditsManager}.
     */
    @Override
    public void update() {
        creditsManager.update();
    }

    /**
     * Draws the credits scene on the screen.
     * This method delegates the drawing logic to the {@link CreditsManager}.
     *
     * @param g the {@link Graphics} object used for rendering.
     */
    @Override
    public void draw(Graphics g) {
        creditsManager.draw(g);
    }

    /**
     * Handles mouse click events within the credits scene.
     * This method delegates the event handling to the {@link CreditsManager}.
     *
     * @param e the {@link MouseEvent} containing details about the mouse click.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        creditsManager.mouseClicked(e);
    }

    /**
     * Handles mouse press events within the credits scene.
     * This method delegates the event handling to the {@link CreditsManager}.
     *
     * @param e the {@link MouseEvent} containing details about the mouse press.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        creditsManager.mousePressed(e);
    }

    /**
     * Handles mouse release events within the credits scene.
     * This method delegates the event handling to the {@link CreditsManager}.
     *
     * @param e the {@link MouseEvent} containing details about the mouse release.
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        creditsManager.mouseReleased(e);
    }

    /**
     * Handles mouse movement events within the credits scene.
     * This method delegates the event handling to the {@link CreditsManager}.
     *
     * @param e the {@link MouseEvent} containing details about the mouse movement.
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        creditsManager.mouseMoved(e);
    }

    /**
     * Handles mouse drag events within the credits scene.
     * This method is currently not implemented.
     *
     * @param e the {@link MouseEvent} containing details about the mouse drag.
     */
    @Override
    public void mouseDragged(MouseEvent e) {}

    /**
     * Handles key press events within the credits scene.
     * This method is currently not implemented.
     *
     * @param e the {@link KeyEvent} containing details about the key press.
     */
    @Override
    public void keyPressed(KeyEvent e) {}

    /**
     * Handles key release events within the credits scene.
     * This method is currently not implemented.
     *
     * @param e the {@link KeyEvent} containing details about the key release.
     */
    @Override
    public void keyReleased(KeyEvent e) {}
}
