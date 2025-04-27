package me.xmertsalov.components.phisics;

import me.xmertsalov.gameObjects.Tile;

/**
 * The {@code TilePhisicsComponents} class is responsible for managing the physics properties
 * of a {@link Tile} object, such as velocity and acceleration. It updates the tile's position
 * based on its velocity and acceleration values.
 */
public class TilePhisicsComponents {
    private double acceleration_x, acceleration_y;
    private double velocity_x, velocity_y;
    private Tile tile;

    /**
     * Constructs a {@code TilePhisicsComponents} object for the specified {@link Tile}.
     *
     * @param tile the tile associated with this physics component
     */
    public TilePhisicsComponents(Tile tile) {
        this.tile = tile;
    }

    /**
     * Updates the tile's position by applying the current velocity and acceleration.
     * The velocity is updated based on the acceleration, and the tile's position
     * is updated based on the velocity.
     */
    public void update() {
        // Update velocity based on acceleration
        velocity_x += acceleration_x;
        velocity_y += acceleration_y;

        tile.updatePos(tile.getX() + velocity_x, tile.getY() + velocity_y);
    }

    /**
     * Sets the horizontal velocity of the tile.
     *
     * @param velocity_x the horizontal velocity to set
     */
    public void setVelocity_x(double velocity_x) {
        this.velocity_x = velocity_x;
    }

    /**
     * Sets the vertical velocity of the tile.
     *
     * @param velocity_y the vertical velocity to set
     */
    public void setVelocity_y(double velocity_y) {
        this.velocity_y = velocity_y;
    }

    /**
     * Gets the current horizontal velocity of the tile.
     *
     * @return the horizontal velocity
     */
    public double getVelocity_x() {
        return velocity_x;
    }

    /**
     * Gets the current vertical velocity of the tile.
     *
     * @return the vertical velocity
     */
    public double getVelocity_y() {
        return velocity_y;
    }
}
