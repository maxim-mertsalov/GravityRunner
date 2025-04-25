package me.xmertsalov.components;

import me.xmertsalov.entities.Player;

public class PhisicsComponent {
    private Player player;

    private double acceleration_x, acceleration_y;
    private double velocity_x, velocity_y;

    private double gravityScale = 3.2f; // 4.8
    private double gravityDirection = 1;

    private double mass;

    // For limiting the velocity in y direction
    private boolean isChanged = false;

    private boolean ableToUp = true;
    private boolean ableToDown = true;

    public PhisicsComponent(Player player, double mass) {
        this.player = player;
        this.mass = mass;
        this.acceleration_x = 0;
        this.acceleration_y = 0;
        this.velocity_x = 0;
        this.velocity_y = 0;
    }

    public void applyForce(double force_x, double force_y) {
        // F = m * a => a = F / m
        double applied_acceleration_x = force_x / mass;
        double applied_acceleration_y = force_y / mass;
        acceleration_x += applied_acceleration_x;
        acceleration_y += applied_acceleration_y;
    }

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

    public double predictFuturePosX() {
        BoxCollider playerCollider = (BoxCollider) player.getCollider();
        return playerCollider.getBounds().getMinX();
    }

    public void setVelocity(double velocity_x, double velocity_y) {
        this.velocity_x = velocity_x;
        this.velocity_y = velocity_y;
    }

    public void setVelocityX(double velocity_x) {
        this.velocity_x = velocity_x;
    }

    public void setVelocityY(double velocity_y) {
        this.velocity_y = velocity_y;
    }

    public void setIsChanged(boolean isChanged) {
        this.isChanged = isChanged;
    }

    public double getVelocityX() {
        return velocity_x;
    }
    public double getVelocityY() {
        return velocity_y;
    }

    public boolean isAbleToUp() {
        return ableToUp;
    }

    public void setAbleToUp(boolean ableToUp) {
        this.ableToUp = ableToUp;
    }

    public boolean isAbleToDown() {
        return ableToDown;
    }

    public void setAbleToDown(boolean ableToDown) {
        this.ableToDown = ableToDown;
    }

    public double getGravityDirection() {
        return gravityDirection;
    }

    public void setGravityDirection(double gravityDirection) {
        this.gravityDirection = gravityDirection;
    }

    public void setAcceleration_y(double acceleration_y) {
        this.acceleration_y = acceleration_y;
    }

    public void setAcceleration_x(double acceleration_x) {
        this.acceleration_x = acceleration_x;
    }
}
