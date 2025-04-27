package me.xmertsalov.ui.buttons;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

/**
 * The {@code IButton} interface defines the contract for a button component in the UI.
 * It provides methods for rendering, updating, handling user interactions (mouse and keyboard events),
 * and managing the button's state and appearance.
 */
public interface IButton {

    /**
     * Draws the button on the screen.
     *
     * @param g the {@link Graphics} object used for rendering the button.
     */
    void draw(Graphics g);

    /**
     * Updates the button's state. This method is typically called in the game loop.
     */
    void update();

    /**
     * Handles a mouse click event on the button.
     *
     * @param e the {@link MouseEvent} containing details about the mouse click.
     */
    void mouseClicked(MouseEvent e);

    /**
     * Handles a mouse press event on the button.
     *
     * @param e the {@link MouseEvent} containing details about the mouse press.
     */
    void mousePressed(MouseEvent e);

    /**
     * Handles a mouse release event on the button.
     *
     * @param e the {@link MouseEvent} containing details about the mouse release.
     */
    void mouseReleased(MouseEvent e);

    /**
     * Handles a mouse movement event over the button.
     *
     * @param e the {@link MouseEvent} containing details about the mouse movement.
     */
    void mouseMoved(MouseEvent e);

    /**
     * Handles a mouse enter event when the cursor enters the button's area.
     *
     * @param e the {@link MouseEvent} containing details about the mouse entering the button.
     */
    void mouseEntered(MouseEvent e);

    /**
     * Handles a mouse exit event when the cursor leaves the button's area.
     *
     * @param e the {@link MouseEvent} containing details about the mouse exiting the button.
     */
    void mouseExited(MouseEvent e);

    /**
     * Handles a mouse drag event on the button.
     *
     * @param e the {@link MouseEvent} containing details about the mouse drag.
     */
    void mouseDragged(MouseEvent e);

    /**
     * Handles a key press event when a key is pressed while the button is focused.
     *
     * @param e the {@link KeyEvent} containing details about the key press.
     */
    void keyPressed(KeyEvent e);

    /**
     * Handles a key release event when a key is released while the button is focused.
     *
     * @param e the {@link KeyEvent} containing details about the key release.
     */
    void keyReleased(KeyEvent e);

    /**
     * Sets the state of the button.
     *
     * @param state an integer representing the new state of the button.
     */
    void setState(int state);

    /**
     * Sets the variant of the button, which may affect its appearance or behavior.
     *
     * @param variant an integer representing the variant of the button.
     */
    void setVariant(int variant);

    /**
     * Sets a callback to be executed when the button is clicked.
     *
     * @param listener a {@link Runnable} representing the action to perform on a click.
     */
    void setOnClickListener(Runnable listener);

    /**
     * Loads the image or graphical resources associated with the button.
     */
    void loadImage();

    /**
     * Gets the rectangular bounds of the button.
     *
     * @return a {@link Rectangle2D} representing the button's bounds.
     */
    Rectangle2D getRectangle();

    /**
     * Gets the data associated with the button.
     *
     * @return an integer representing the button's data.
     */
    int getData();

    /**
     * Sets the data associated with the button.
     *
     * @param data an integer representing the new data for the button.
     */
    void setData(int data);
}
