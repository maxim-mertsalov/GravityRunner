package me.xmertsalov.gameObjects.powerUps;

import me.xmertsalov.components.Animator.Animator;
import me.xmertsalov.gameObjects.GameObject;

import java.util.HashMap;
import java.util.List;

/**
 * Represents a SpeedDown power-up that decreases the player's horizontal velocity.
 */
public class SpeedDown extends PowerUp {
    private double velocityXIncrement = -15f;

    /**
     * Constructs a SpeedDown power-up at the specified position.
     *
     * @param x The x-coordinate of the power-up.
     * @param y The y-coordinate of the power-up.
     */
    public SpeedDown(double x, double y) {
        super(x, y);
        animator.setAnimationState("TO_LEFT");
    }

    private SpeedDown(double x, double y, Animator animator, HashMap<String, List<Integer>> animationStates, String animationState, int zIndex) {
        super(x, y, animator, animationStates);
        this.animator.setAnimationState(animationState);
        this.zIndex = zIndex;
    }

    /**
     * Updates the animation state of the SpeedDown power-up.
     */
    @Override
    public void update() {
        super.update();
    }

    /**
     * Gets the velocity decrement applied by this power-up.
     *
     * @return The velocity decrement as a double.
     */
    public double getVelocityXIncrement() {
        return velocityXIncrement;
    }

    /**
     * Creates a new instance of SpeedDown with the same position.
     *
     * @return A new SpeedDown object.
     */
    @Override
    public GameObject clone() {
        return new SpeedDown(x, y, new Animator.Builder().clone(animator), animationStates, "TO_LEFT", zIndex);
    }
}
