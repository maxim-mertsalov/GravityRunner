package me.xmertsalov.gameObjects.powerUps;

import java.awt.*;

public class SpeedUp extends PowerUp {
    private double velocityXIncrement = 10f;

    public SpeedUp(double x, double y) {
        super(x, y);
        animator.setAnimationState("TO_RIGHT");
    }

    @Override
    public void update() {
        super.update();
    }

    public double getVelocityXIncrement() {
        return velocityXIncrement;
    }

    public SpeedUp clone() {
        return new SpeedUp(x, y);
    }
}
