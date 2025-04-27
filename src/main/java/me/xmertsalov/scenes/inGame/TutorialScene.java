package me.xmertsalov.scenes.inGame;

import me.xmertsalov.Game;
import me.xmertsalov.scenes.IScene;
import me.xmertsalov.scenes.Scene;
import me.xmertsalov.ui.TutorialManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * Represents the tutorial scene in the game.
 * This scene is responsible for managing and displaying the tutorial UI
 * and handling user interactions during the tutorial phase.
 */
public class TutorialScene extends Scene implements IScene {

    // UI
    TutorialManager tutorialManager;

    /**
     * Constructs a new TutorialScene.
     *
     * @param game The main game instance, used to access shared resources and state.
     */
    public TutorialScene(Game game) {
        super(game);
        tutorialManager = new TutorialManager(game);
    }

    /**
     * Updates the state of the tutorial scene.
     * Delegates the update logic to the {@link TutorialManager}.
     */
    @Override
    public void update() {
        tutorialManager.update();
    }

    /**
     * Draws the tutorial scene on the screen.
     * Delegates the drawing logic to the {@link TutorialManager}.
     *
     * @param g The {@link Graphics} object used for rendering.
     */
    @Override
    public void draw(Graphics g) {
        tutorialManager.draw(g);
    }

    /**
     * Handles mouse click events within the tutorial scene.
     * Delegates the event handling to the {@link TutorialManager}.
     *
     * @param e The {@link MouseEvent} containing details about the mouse click.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        tutorialManager.mouseClicked(e);
    }

    /**
     * Handles mouse press events within the tutorial scene.
     * Delegates the event handling to the {@link TutorialManager}.
     *
     * @param e The {@link MouseEvent} containing details about the mouse press.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        tutorialManager.mousePressed(e);
    }

    /**
     * Handles mouse release events within the tutorial scene.
     * Delegates the event handling to the {@link TutorialManager}.
     *
     * @param e The {@link MouseEvent} containing details about the mouse release.
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        tutorialManager.mouseReleased(e);
    }

    /**
     * Handles mouse movement events within the tutorial scene.
     * Delegates the event handling to the {@link TutorialManager}.
     *
     * @param e The {@link MouseEvent} containing details about the mouse movement.
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        tutorialManager.mouseMoved(e);
    }

    /**
     * Handles mouse drag events within the tutorial scene.
     * Currently, this method is not implemented.
     *
     * @param e The {@link MouseEvent} containing details about the mouse drag.
     */
    @Override
    public void mouseDragged(MouseEvent e) {}

    /**
     * Handles key press events within the tutorial scene.
     * Currently, this method is not implemented.
     *
     * @param e The {@link KeyEvent} containing details about the key press.
     */
    @Override
    public void keyPressed(KeyEvent e) {}

    /**
     * Handles key release events within the tutorial scene.
     * Currently, this method is not implemented.
     *
     * @param e The {@link KeyEvent} containing details about the key release.
     */
    @Override
    public void keyReleased(KeyEvent e) {}
}
