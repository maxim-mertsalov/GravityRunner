package me.xmertsalov.scenes;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * The {@code IScene} interface defines the structure for a scene in the application.
 * A scene represents a specific state or screen in the game, such as a menu, gameplay, or settings.
 * This interface provides methods for updating the scene, rendering it, and handling user input events.
 */
public interface IScene {

    /**
     * Updates the state of the scene. This method is called periodically to update
     * the logic of the scene, such as animations, game state, or other dynamic elements.
     */
    void update();

    /**
     * Renders the scene onto the screen. This method is called to draw the visual
     * representation of the scene using the provided {@link Graphics} object.
     *
     * @param g the {@link Graphics} object used for rendering
     */
    void draw(Graphics g);

    /**
     * Handles a mouse click event. This method is triggered when the user clicks the mouse.
     *
     * @param e the {@link MouseEvent} containing details about the mouse click
     */
    void mouseClicked(MouseEvent e);

    /**
     * Handles a mouse press event. This method is triggered when the user presses a mouse button.
     *
     * @param e the {@link MouseEvent} containing details about the mouse press
     */
    void mousePressed(MouseEvent e);

    /**
     * Handles a mouse release event. This method is triggered when the user releases a mouse button.
     *
     * @param e the {@link MouseEvent} containing details about the mouse release
     */
    void mouseReleased(MouseEvent e);

    /**
     * Handles a mouse movement event. This method is triggered when the user moves the mouse.
     *
     * @param e the {@link MouseEvent} containing details about the mouse movement
     */
    void mouseMoved(MouseEvent e);

    /**
     * Handles a mouse drag event. This method is triggered when the user drags the mouse
     * (moves the mouse while holding a button).
     *
     * @param e the {@link MouseEvent} containing details about the mouse drag
     */
    void mouseDragged(MouseEvent e);

    /**
     * Handles a key press event. This method is triggered when the user presses a key on the keyboard.
     *
     * @param e the {@link KeyEvent} containing details about the key press
     */
    void keyPressed(KeyEvent e);

    /**
     * Handles a key release event. This method is triggered when the user releases a key on the keyboard.
     *
     * @param e the {@link KeyEvent} containing details about the key release
     */
    void keyReleased(KeyEvent e);
}
