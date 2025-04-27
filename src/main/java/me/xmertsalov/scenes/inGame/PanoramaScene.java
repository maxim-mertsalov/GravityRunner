package me.xmertsalov.scenes.inGame;

import me.xmertsalov.Game;
import me.xmertsalov.scenes.GameScene;
import me.xmertsalov.scenes.IScene;
import me.xmertsalov.scenes.Scene;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * Represents the PanoramaScene in the game, which is a specific type of scene.
 * This scene handles user interactions such as mouse clicks and key presses
 * and transitions to the MENU scene when certain events occur.
 */
public class PanoramaScene extends Scene implements IScene {

    /**
     * Constructs a PanoramaScene instance.
     *
     * @param game The game instance to which this scene belongs.
     */
    public PanoramaScene(Game game) {
        super(game);
    }

    /**
     * Updates the state of the scene.
     * Currently, this method does not perform any actions.
     */
    @Override
    public void update() {}

    /**
     * Draws the scene on the screen.
     * Currently, this method does not perform any drawing.
     *
     * @param g The Graphics object used for rendering.
     */
    @Override
    public void draw(Graphics g) {}

    /**
     * Handles mouse click events.
     * Transitions the game to the MENU scene when the mouse is clicked.
     *
     * @param e The MouseEvent object containing details about the mouse click.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        GameScene.scene = GameScene.MENU;
    }

    /**
     * Handles mouse press events.
     * Currently, this method does not perform any actions.
     *
     * @param e The MouseEvent object containing details about the mouse press.
     */
    @Override
    public void mousePressed(MouseEvent e) {}

    /**
     * Handles mouse release events.
     * Currently, this method does not perform any actions.
     *
     * @param e The MouseEvent object containing details about the mouse release.
     */
    @Override
    public void mouseReleased(MouseEvent e) {}

    /**
     * Handles mouse movement events.
     * Currently, this method does not perform any actions.
     *
     * @param e The MouseEvent object containing details about the mouse movement.
     */
    @Override
    public void mouseMoved(MouseEvent e) {}

    /**
     * Handles mouse drag events.
     * Currently, this method does not perform any actions.
     *
     * @param e The MouseEvent object containing details about the mouse drag.
     */
    @Override
    public void mouseDragged(MouseEvent e) {}

    /**
     * Handles key press events.
     * Currently, this method does not perform any actions.
     *
     * @param e The KeyEvent object containing details about the key press.
     */
    @Override
    public void keyPressed(KeyEvent e) {}

    /**
     * Handles key release events.
     * Transitions the game to the MENU scene when a key is released.
     *
     * @param e The KeyEvent object containing details about the key release.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        GameScene.scene = GameScene.MENU;
    }
}
