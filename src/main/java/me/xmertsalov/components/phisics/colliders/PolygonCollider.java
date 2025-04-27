package me.xmertsalov.components.phisics.colliders;

import me.xmertsalov.Game;

import java.awt.*;

/**
 * The {@code PolygonCollider} class represents a polygonal collider used for collision detection.
 * It defines a polygonal boundary and provides methods to update its position and render it
 * for debugging purposes.
 */
public class PolygonCollider extends Collider {
    private Polygon polygon;

    /**
     * Constructs a {@code PolygonCollider} with the specified vertices.
     *
     * @param xPoints An array of x-coordinates for the vertices of the polygon.
     * @param yPoints An array of y-coordinates for the vertices of the polygon.
     */
    public PolygonCollider(int[] xPoints, int[] yPoints) {
        this.polygon = new Polygon(xPoints, yPoints, xPoints.length);
    }

    /**
     * Draws the collider's polygon on the screen for debugging purposes.
     * The fill and outline colors are determined by the {@code Game.DEBUG_COLOR}
     * and {@code Game.DEBUG_COLOR_SECOND} respectively.
     *
     * @param g The {@code Graphics} object used for rendering.
     */
    @Override
    public void draw(Graphics g) {
        if (!Game.DEBUG_COLLIDERS) return;
        g.setColor(Game.DEBUG_COLOR);
        g.fillPolygon(polygon);

        g.setColor(Game.DEBUG_COLOR_SECOND);
        g.drawPolygon(polygon);
    }

    /**
     * Updates the position of the collider's polygon by translating its vertices.
     *
     * @param x The x-offset to apply to all vertices.
     * @param y The y-offset to apply to all vertices.
     */
    public void updateBounds(double x, double y) {
        for (int i = 0; i < polygon.npoints; i++) {
            polygon.xpoints[i] = (int) (x + polygon.xpoints[i]);
            polygon.ypoints[i] = (int) (y + polygon.ypoints[i]);
        }
        polygon.invalidate();
    }

    /**
     * Returns the polygonal bounds of this collider.
     *
     * @return A {@code Polygon} representing the bounds of the collider.
     */
    public Polygon getBounds() {
        return polygon;
    }
}
