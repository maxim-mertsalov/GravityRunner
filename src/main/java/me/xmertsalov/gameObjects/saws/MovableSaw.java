package me.xmertsalov.gameObjects.saws;

import me.xmertsalov.Game;
import me.xmertsalov.gameObjects.GameObject;

import java.awt.*;

/**
 * Represents a movable saw object in the game.
 * The saw moves back and forth between two points (start and destination).
 * The movement is determined by a velocity vector, and the saw reverses direction
 * when it reaches either the start or destination point.
 */
public class MovableSaw extends Saw {
    private final double speed = 1.5; // Base speed of the saw.

    private double velocityX; // Current velocity in the X direction.
    private double velocityY; // Current velocity in the Y direction.

    private double velocityXOffset = 0; // Offset applied to the X velocity.

    private double baseVelocityX; // Base velocity in the X direction.
    private double baseVelocityY; // Base velocity in the Y direction.

    private double lengthBetweenStartX = 0; // Distance traveled from the start point in the X direction.
    private double lengthBetweenStartY = 0; // Distance traveled from the start point in the Y direction.

    private boolean direction = true; // Current direction of movement (true = toward destination, false = toward start).

    private double dx, dy; // Destination coordinates.
    private double sx, sy; // Start coordinates.

    private double lengthX, lengthY; // Distance between start and destination in X and Y directions.

    /**
     * Constructs a MovableSaw object.
     *
     * @param x  The starting X coordinate.
     * @param y  The starting Y coordinate.
     * @param dx The destination X coordinate.
     * @param dy The destination Y coordinate.
     */
    public MovableSaw(double x, double y, double dx, double dy) {
        super(x, y);

        this.dx = dx;
        this.dy = dy;
        this.sx = x;
        this.sy = y;

        this.lengthX = dx - sx;
        this.lengthY = dy - sy;

        double length = Math.sqrt(lengthX * lengthX + lengthY * lengthY);
        this.baseVelocityX = (lengthX / length) * speed;
        this.baseVelocityY = (lengthY / length) * speed;

        this.velocityX = baseVelocityX;
        this.velocityY = baseVelocityY;
    }

    /**
     * Updates the position of the saw based on its velocity.
     * Checks if the saw has reached its destination or start point and reverses direction if necessary.
     */
    @Override
    public void update() {
        checkPosition();

        x += velocityX;
        lengthBetweenStartX += velocityX;
        y += velocityY;
        lengthBetweenStartY += velocityY;

        bounds.x = x;
        bounds.y = y;

        animator.update();
    }

    /**
     * Checks the current position of the saw and reverses its direction
     * if it reaches the destination or start point.
     */
    private void checkPosition() {
        if (direction) {
            // Moving toward (dx, dy)
            if ((baseVelocityX > 0 && x >= dx) || (baseVelocityX < 0 && x <= dx)
                    || (baseVelocityY > 0 && y >= dy) || (baseVelocityY < 0 && y <= dy)) {
                direction = false;
                velocityX = -baseVelocityX - Math.abs(velocityXOffset);
                velocityY = -baseVelocityY;
            }
        } else {
            // Moving back to (sx, sy)
            if ((velocityX < 0 && x <= sx) || (velocityX > 0 && x >= sx)
                    || (velocityY < 0 && y <= sy) || (velocityY > 0 && y >= sy)) {
                direction = true;
                velocityX = baseVelocityX;
                velocityY = baseVelocityY;
            }
        }
    }

    /**
     * Draws the saw on the screen.
     * If debug mode is enabled, it also draws a line representing the movement path.
     *
     * @param g The Graphics object used for drawing.
     */
    @Override
    public void draw(Graphics g) {
        super.draw(g);
        if (Game.DEBUG_COLLIDERS) {
            g.setColor(Game.DEBUG_COLOR);
            g.drawLine((int) sx, (int) sy, (int) dx, (int) dy);
        }
    }

    /**
     * Updates the position of the saw and recalculates its start and destination points.
     *
     * @param x The new X coordinate.
     * @param y The new Y coordinate.
     */
    @Override
    public void updatePos(double x, double y) {
        super.updatePos(x, y);
        this.sx = x - lengthBetweenStartX;
        this.sy = y - lengthBetweenStartY;
        this.dx = x - lengthBetweenStartX + lengthX;
        this.dy = y -lengthBetweenStartY + lengthY;
    }

    /**
     * Sets the velocity of the saw.
     *
     * @param velocityX The new velocity in the X direction.
     * @param velocityY The new velocity in the Y direction.
     */
    public void setVelocity(double velocityX, double velocityY) {
        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }

    /**
     * Gets the current velocity in the X direction.
     *
     * @return The X velocity.
     */
    public double getVelocityX() {
        return velocityX;
    }

    /**
     * Gets the current velocity in the Y direction.
     *
     * @return The Y velocity.
     */
    public double getVelocityY() {
        return velocityY;
    }

    /**
     * Creates a clone of the MovableSaw object.
     *
     * @return A new MovableSaw object with the same properties.
     */
    @Override
    public GameObject clone() {
        return new MovableSaw(x, y, dx, dy);
    }

    /**
     * Sets an offset for the X velocity.
     *
     * @param offsetX The offset to apply to the X velocity.
     */
    public void setVelocityOffset(double offsetX) {
        this.velocityXOffset = offsetX;
    }
}
