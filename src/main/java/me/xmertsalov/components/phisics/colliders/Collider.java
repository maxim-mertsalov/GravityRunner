package me.xmertsalov.components.phisics.colliders;

import java.awt.*;

/**
 * The {@code Collider} abstract class serves as a base for all collider types.
 * It defines the structure for collision detection components, requiring
 * subclasses to implement methods for rendering and updating their bounds.
 */
public abstract class Collider {

    /**
     * Renders the collider's bounds on the screen for debugging purposes.
     *
     * @param g The {@code Graphics} object used for rendering.
     */
    public abstract void draw(Graphics g);

    /**
     * Updates the position of the collider's bounds.
     *
     * @param x The new x-coordinate or x-offset for the collider.
     * @param y The new y-coordinate or y-offset for the collider.
     */
    public abstract void updateBounds(double x, double y);
}
