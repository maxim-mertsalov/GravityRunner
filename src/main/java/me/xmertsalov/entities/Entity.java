package me.xmertsalov.entities;

/**
 * The {@code Entity} class serves as an abstract base class for all game entities.
 * It provides basic properties for position (x, y) and a constructor to initialize them.
 * Subclasses of {@code Entity} can extend this class to define specific behaviors and attributes.
 */
public abstract class Entity {
    // The x-coordinate of the entity's position
    protected double x;
    // The y-coordinate of the entity's position
    protected double y;

    /**
     * Constructs an {@code Entity} with the specified initial position.
     *
     * @param x the initial x-coordinate of the entity
     * @param y the initial y-coordinate of the entity
     */
    public Entity(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
