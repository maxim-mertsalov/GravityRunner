package me.xmertsalov.components.phisics.colliders;

import me.xmertsalov.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * The {@code BoxCollider} class represents a rectangular collider used for collision detection.
 * It defines a rectangular boundary and provides methods to update its position and render it
 * for debugging purposes.
 */
public class BoxCollider extends Collider {
    private Rectangle2D.Double bounds;

    /**
     * Constructs a {@code BoxCollider} with the specified position and dimensions.
     *
     * @param x      The x-coordinate of the top-left corner of the rectangle.
     * @param y      The y-coordinate of the top-left corner of the rectangle.
     * @param width  The width of the rectangle.
     * @param height The height of the rectangle.
     */
    public BoxCollider(double x, double y, double width, double height) {
        bounds = new Rectangle2D.Double(x, y, width, height);
    }

    /**
     * Returns the rectangular bounds of this collider.
     *
     * @return A {@code Rectangle2D.Double} representing the bounds of the collider.
     */
    public Rectangle2D.Double getBounds() {
        return bounds;
    }

    /**
     * Updates the position of the collider's bounds while keeping its dimensions unchanged.
     *
     * @param x The new x-coordinate of the top-left corner of the rectangle.
     * @param y The new y-coordinate of the top-left corner of the rectangle.
     */
    public void updateBounds(double x, double y) {
        bounds.setRect(x, y, bounds.getWidth(), bounds.getHeight());
    }

    /**
     * Draws the collider's bounds on the screen for debugging purposes.
     * The fill and outline colors are determined by the {@code Game.DEBUG_COLOR}
     * and {@code Game.DEBUG_COLOR_SECOND} respectively.
     *
     * @param g The {@code Graphics} object used for rendering.
     */
    public void draw(Graphics g) {
        if (!Game.DEBUG_COLLIDERS) return;

        g.setColor(Game.DEBUG_COLOR);
        g.fillRect((int) bounds.getX(), (int) bounds.getY(), (int) bounds.getWidth(), (int) bounds.getHeight());

        g.setColor(Game.DEBUG_COLOR_SECOND);
        g.drawRect((int) bounds.getX(), (int) bounds.getY(), (int) bounds.getWidth(), (int) bounds.getHeight());
    }
}
