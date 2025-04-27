package me.xmertsalov.gameObjects.powerUps;

import me.xmertsalov.gameObjects.GameObject;

/**
 * Represents a SpeedUp power-up that increases the player's horizontal velocity.
 */
public class SpeedUp extends PowerUp {
    private double velocityXIncrement = 10f;

    /**
     * Constructs a SpeedUp power-up at the specified position.
     *
     * @param x The x-coordinate of the power-up.
     * @param y The y-coordinate of the power-up.
     */
    public SpeedUp(double x, double y) {
        super(x, y);
        animator.setAnimationState("TO_RIGHT");
    }

    /**
     * Updates the animation state of the SpeedUp power-up.
     */
    @Override
    public void update() {
        super.update();
    }

    /**
     * Gets the velocity increment applied by this power-up.
     *
     * @return The velocity increment as a double.
     */
    public double getVelocityXIncrement() {
        return velocityXIncrement;
    }

    /**
     * Creates a new instance of SpeedUp with the same position.
     *
     * @return A new SpeedUp object.
     */
    @Override
    public GameObject clone() {
        return new SpeedUp(x, y);
    }
}
