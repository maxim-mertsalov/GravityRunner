package me.xmertsalov.components;

import me.xmertsalov.gameObjects.Tile;

public class TilePhisicsComponents {
    private double acceleration_x, acceleration_y;
    private double velocity_x, velocity_y;
    private Tile tile;

    public TilePhisicsComponents(Tile tile) {
        this.tile = tile;
    }

    public void update() {
        // Update velocity based on acceleration
        velocity_x += acceleration_x;
        velocity_y += acceleration_y;

        tile.updatePos(tile.getX() + velocity_x, tile.getY() + velocity_y);
    }

    public void setVelocity_x(double velocity_x) {
        this.velocity_x = velocity_x;
    }

    public void setVelocity_y(double velocity_y) {
        this.velocity_y = velocity_y;
    }

    public double getVelocity_x() {
        return velocity_x;
    }

    public double getVelocity_y() {
        return velocity_y;
    }


}
