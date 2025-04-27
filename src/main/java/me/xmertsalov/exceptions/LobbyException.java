package me.xmertsalov.exceptions;

import java.awt.*;

/**
 * This exception is thrown when there is an issue related to the game lobby.
 * It provides a method to visually display the error message on a graphical interface.
 */
public class LobbyException extends RuntimeException {

    /**
     * Constructs a new LobbyException with a specific message.
     *
     * @param message The error message describing the issue.
     */
    public LobbyException(String message) {
        super(message);
    }

    /**
     * Draws the error message on a graphical interface at the specified position and font size.
     *
     * @param g        The Graphics object used for drawing.
     * @param x        The x-coordinate where the message should be drawn.
     * @param y        The y-coordinate where the message should be drawn.
     * @param fontSize The font size of the error message.
     */
    public void draw(Graphics g, int x, int y, int fontSize) {
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, fontSize));
        g.drawString(getMessage(), x, y);
    }
}
