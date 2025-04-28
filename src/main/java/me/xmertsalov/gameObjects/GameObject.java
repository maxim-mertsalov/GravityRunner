package me.xmertsalov.gameObjects;

import java.awt.*;

/**
 * The {@code GameObject} class serves as an abstract base class for all game objects in the application.
 * It provides common properties such as position (x, y) and defines abstract methods that must be implemented
 * by subclasses to handle updates, rendering, and cloning.
 */
public abstract class GameObject {
    /**
     * The x-coordinate of the game object.
     */
    protected double x;

    /**
     * The y-coordinate of the game object.
     */
    protected double y;

    protected int zIndex = 0; // -1 - background, 0 - not render, 1 - foreground

    /**
     * Constructs a new {@code GameObject} with the specified initial position.
     *
     * @param x the initial x-coordinate of the game object.
     * @param y the initial y-coordinate of the game object.
     */
    public GameObject(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the x-coordinate of the game object.
     *
     * @return the x-coordinate of the game object.
     */
    public double getX() {
        return x;
    }

    /**
     * Gets the y-coordinate of the game object.
     *
     * @return the y-coordinate of the game object.
     */
    public double getY() {
        return y;
    }

    /**
     * Sets the x-coordinate of the game object.
     *
     * @param x the new x-coordinate of the game object.
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Sets the y-coordinate of the game object.
     *
     * @param y the new y-coordinate of the game object.
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Updates the state of the game object. This method is called on each game loop iteration
     * and should be implemented by subclasses to define specific update logic.
     */
    public abstract void update();

    /**
     * Renders the game object on the screen. This method is called during the rendering phase
     * and should be implemented by subclasses to define specific drawing logic.
     *
     * @param g the {@code Graphics} object used for rendering.
     */
    public abstract void draw(Graphics g);

    /**
     * Updates the position of the game object. This method allows subclasses to define
     * how the position should be updated based on the provided coordinates.
     *
     * @param x the new x-coordinate.
     * @param y the new y-coordinate.
     */
    public abstract void updatePos(double x, double y);

    /**
     * Creates and returns a copy of this game object. This method should be implemented
     * by subclasses to provide a deep copy of the object.
     *
     * @return a new {@code GameObject} instance that is a copy of this object.
     */
    public abstract GameObject clone();

    public int getZIndex() {return zIndex;}

    public void setZIndex(int zIndex) {this.zIndex = zIndex;}
}
