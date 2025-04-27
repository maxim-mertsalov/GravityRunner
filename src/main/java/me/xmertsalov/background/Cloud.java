package me.xmertsalov.background;

import me.xmertsalov.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * The {@code Cloud} class represents a cloud object in the game.
 * It handles the cloud's position, size, image, and movement.
 * Clouds are drawn on the screen and move horizontally based on their velocity.
 */
public class Cloud {
    // Position && Size
    private double x, y; // The x and y coordinates of the cloud.
    private final double width; // The width of the cloud, scaled from the image.
    private final double height; // The height of the cloud, scaled from the image.

    // Image
    private BufferedImage image; // The image representing the cloud.

    // States
    private double velocity_x; // The horizontal velocity of the cloud.

    /**
     * Constructs a new {@code Cloud} object with the specified position, velocity, and image.
     *
     * @param x          The initial x-coordinate of the cloud.
     * @param y          The initial y-coordinate of the cloud.
     * @param velocity_x The horizontal velocity of the cloud.
     * @param image      The image representing the cloud.
     */
    public Cloud(double x, double y, double velocity_x, BufferedImage image) {
        this.x = x;
        this.y = y;
        this.velocity_x = velocity_x;
        this.image = image;
        this.width = image.getWidth() * Game.SCALE * 2;
        this.height = image.getHeight() * Game.SCALE * 2;
    }

    /**
     * Creates and returns a copy of this {@code Cloud} object.
     *
     * @return A new {@code Cloud} object with the same properties as this one.
     */
    public Cloud clone() {
        return new Cloud(x, y, velocity_x, image);
    }

    /**
     * Updates the position of the cloud based on its velocity.
     * This method should be called in the game loop to animate the cloud.
     */
    public void update() {
        x += velocity_x;
    }

    /**
     * Draws the cloud on the screen at its current position.
     *
     * @param g The {@code Graphics2D} object used to draw the cloud.
     */
    public void draw(Graphics2D g) {
        g.drawImage(image, (int) x, (int) y, (int) (width), (int) (height), null);
    }

    /**
     * Gets the current x-coordinate of the cloud.
     *
     * @return The x-coordinate of the cloud.
     */
    public double getX() {return x;}

    /**
     * Gets the current y-coordinate of the cloud.
     *
     * @return The y-coordinate of the cloud.
     */
    public double getY() {return y;}

    /**
     * Sets the x-coordinate of the cloud.
     *
     * @param x The new x-coordinate of the cloud.
     */
    public void setX(double x) {this.x = x;}

    /**
     * Sets the y-coordinate of the cloud.
     *
     * @param y The new y-coordinate of the cloud.
     */
    public void setY(double y) {this.y = y;}

    /**
     * Gets the width of the cloud.
     *
     * @return The width of the cloud.
     */
    public double getWidth() {return width;}

    /**
     * Gets the height of the cloud.
     *
     * @return The height of the cloud.
     */
    public double getHeight() {return height;}

    /**
     * Sets the horizontal velocity of the cloud.
     *
     * @param velocity_x The new horizontal velocity of the cloud.
     */
    public void setVelocity_x(double velocity_x) {
        this.velocity_x = velocity_x;
    }
}
