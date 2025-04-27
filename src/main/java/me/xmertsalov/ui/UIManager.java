package me.xmertsalov.ui;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * The UIManager interface defines the structure for managing UI components in the game.
 * It provides methods for updating, rendering, and handling user input events such as
 * mouse and keyboard interactions.
 */
public interface UIManager {

    /**
     * Updates the state of the UI components.
     * This method is called periodically to refresh the UI state.
     */
    void update();

    /**
     * Renders the UI components on the screen.
     *
     * @param g The Graphics object used for drawing.
     */
    void draw(Graphics g);

    /**
     * Handles mouse click events.
     *
     * @param e The MouseEvent object containing details about the mouse click.
     */
    void mouseClicked(MouseEvent e);

    /**
     * Handles mouse press events.
     *
     * @param e The MouseEvent object containing details about the mouse press.
     */
    void mousePressed(MouseEvent e);

    /**
     * Handles mouse release events.
     *
     * @param e The MouseEvent object containing details about the mouse release.
     */
    void mouseReleased(MouseEvent e);

    /**
     * Handles mouse movement events.
     *
     * @param e The MouseEvent object containing details about the mouse movement.
     */
    void mouseMoved(MouseEvent e);

    /**
     * Handles key press events.
     *
     * @param e The KeyEvent object containing details about the key press.
     */
    void keyPressed(KeyEvent e);

    /**
     * Handles key release events.
     *
     * @param e The KeyEvent object containing details about the key release.
     */
    void keyReleased(KeyEvent e);
}
