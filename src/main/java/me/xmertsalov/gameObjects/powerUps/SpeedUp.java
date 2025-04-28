package me.xmertsalov.gameObjects.powerUps;

import me.xmertsalov.components.Animator.Animator;
import me.xmertsalov.gameObjects.GameObject;

import java.util.HashMap;
import java.util.List;

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

    private SpeedUp(double x, double y, Animator animator, HashMap<String, List<Integer>> animationStates, String animationState, int zIndex) {
        super(x, y, animator, animationStates);
        this.animator.setAnimationState(animationState);
        this.zIndex = zIndex;
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
        return new SpeedUp(x, y, animator, animationStates, "TO_RIGHT", zIndex);
    }
}
