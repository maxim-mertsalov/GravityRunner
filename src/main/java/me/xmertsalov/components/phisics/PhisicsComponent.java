package me.xmertsalov.components.phisics;

import me.xmertsalov.components.phisics.colliders.BoxCollider;
import me.xmertsalov.entities.Player;

/**
 * The PhisicsComponent class is responsible for handling the physics calculations
 * for a player entity, including forces, velocity, acceleration, and gravity.
 * It updates the player's position based on these calculations and manages collision states.
 */
public class PhisicsComponent {
    private Player player;

    private double acceleration_x, acceleration_y;
    private double velocity_x, velocity_y;

    private double gravityScale = 3.2f; // Gravity multiplier
    private double gravityDirection = 1; // Direction of gravity (1 for down, -1 for up)

    private double mass; // Mass of the player, used in force calculations

    // For limiting the velocity in the y direction
    private boolean isChanged = false;

    private boolean ableToUp = true; // Indicates if the player can move upward
    private boolean ableToDown = true; // Indicates if the player can move downward

    /**
     * Constructs a PhisicsComponent for a player with a specified mass.
     *
     * @param player The player entity this component is associated with.
     * @param mass   The mass of the player, used in force calculations.
     */
    public PhisicsComponent(Player player, double mass) {
        this.player = player;
        this.mass = mass;
        this.acceleration_x = 0;
        this.acceleration_y = 0;
        this.velocity_x = 0;
        this.velocity_y = 0;
    }

    /**
     * Applies a force to the player, modifying its acceleration.
     *
     * @param force_x The force applied in the x direction.
     * @param force_y The force applied in the y direction.
     */
    public void applyForce(double force_x, double force_y) {
        // F = m * a => a = F / m
        double applied_acceleration_x = force_x / mass;
        double applied_acceleration_y = force_y / mass;
        acceleration_x += applied_acceleration_x;
        acceleration_y += applied_acceleration_y;
    }

    /**
     * Updates the player's velocity and position based on the current acceleration,
     * applies gravity, and handles collision states.
     */
    public void update() {
        // Apply gravity
        if (ableToDown && !player.isDisableGravity()) {
            applyForce(0, gravityScale * gravityDirection * mass);
        }

        // Update velocity based on acceleration
        velocity_x += acceleration_x;
        velocity_y += acceleration_y;

        // Limit velocity based on collision states
        if (!ableToDown && velocity_y > 0) {
            velocity_y = 0;
        }
        if (!ableToUp && velocity_y < 0) {
            velocity_y = 0;
        }

        // Predict future position and adjust for collisions
        double futurePosX = player.getPosX() + velocity_x;
        double futurePosY = player.getPosY() + velocity_y;

        player.setPosX(futurePosX);
        player.setPosY(futurePosY);

        // Reset acceleration after applying it
        acceleration_x = 0;
        acceleration_y = 0;

        // Reset collision states after position update
        ableToDown = true;
        ableToUp = true;
    }

    /**
     * Predicts the future x position of the player based on its collider.
     *
     * @return The predicted future x position.
     */
    public double predictFuturePosX() {
        BoxCollider playerCollider = (BoxCollider) player.getCollider();
        return playerCollider.getBounds().getMinX();
    }

    /**
     * Sets the player's velocity in both x and y directions.
     *
     * @param velocity_x The velocity in the x direction.
     * @param velocity_y The velocity in the y direction.
     */
    public void setVelocity(double velocity_x, double velocity_y) {
        this.velocity_x = velocity_x;
        this.velocity_y = velocity_y;
    }

    /**
     * Sets the player's velocity in the x direction.
     *
     * @param velocity_x The velocity in the x direction.
     */
    public void setVelocityX(double velocity_x) {this.velocity_x = velocity_x;}

    /**
     * Sets the player's velocity in the y direction.
     *
     * @param velocity_y The velocity in the y direction.
     */
    public void setVelocityY(double velocity_y) {this.velocity_y = velocity_y;}

    /**
     * Sets whether the physics state has changed.
     *
     * @param isChanged True if the state has changed, false otherwise.
     */
    public void setIsChanged(boolean isChanged) {this.isChanged = isChanged;}

    /**
     * Gets the player's velocity in the x direction.
     *
     * @return The velocity in the x direction.
     */
    public double getVelocityX() {return velocity_x;}

    /**
     * Gets the player's velocity in the y direction.
     *
     * @return The velocity in the y direction.
     */
    public double getVelocityY() {return velocity_y;}

    /**
     * Checks if the player is able to move upward.
     *
     * @return True if the player can move upward, false otherwise.
     */
    public boolean isAbleToUp() {return ableToUp;}

    /**
     * Sets whether the player is able to move upward.
     *
     * @param ableToUp True if the player can move upward, false otherwise.
     */
    public void setAbleToUp(boolean ableToUp) {this.ableToUp = ableToUp;}

    /**
     * Checks if the player is able to move downward.
     *
     * @return True if the player can move downward, false otherwise.
     */
    public boolean isAbleToDown() {return ableToDown;}

    /**
     * Sets whether the player is able to move downward.
     *
     * @param ableToDown True if the player can move downward, false otherwise.
     */
    public void setAbleToDown(boolean ableToDown) {this.ableToDown = ableToDown;}

    /**
     * Gets the direction of gravity.
     *
     * @return The gravity direction (1 for down, -1 for up).
     */
    public double getGravityDirection() {return gravityDirection;}

    /**
     * Sets the direction of gravity.
     *
     * @param gravityDirection The gravity direction (1 for down, -1 for up).
     */
    public void setGravityDirection(double gravityDirection) {this.gravityDirection = gravityDirection;}

    /**
     * Sets the acceleration in the y direction.
     *
     * @param acceleration_y The acceleration in the y direction.
     */
    public void setAcceleration_y(double acceleration_y) {this.acceleration_y = acceleration_y;}

    /**
     * Sets the acceleration in the x direction.
     *
     * @param acceleration_x The acceleration in the x direction.
     */
    public void setAcceleration_x(double acceleration_x) {
        this.acceleration_x = acceleration_x;
    }
}
