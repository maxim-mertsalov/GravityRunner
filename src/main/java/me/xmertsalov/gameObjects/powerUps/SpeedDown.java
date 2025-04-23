package me.xmertsalov.gameObjects.powerUps;

import me.xmertsalov.components.Animator;

import java.awt.*;

public class SpeedDown extends PowerUp {
    private double velocityXIncrement = -15f;

    public SpeedDown(double x, double y) {
        super(x, y);
        animator.setAnimationState("TO_LEFT");
    }

    @Override
    public void update() {
        super.update();
    }


    public double getVelocityXIncrement() {
        return velocityXIncrement;
    }

    public SpeedDown clone() {
        return new SpeedDown(x, y);
    }
}
