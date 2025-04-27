package me.xmertsalov.components.Animator;

import java.awt.*;

/**
 * The {@code IAnimator} interface defines the contract for animation components.
 * It provides methods to manage animation states, render animations, and update their logic.
 * Implementations of this interface are expected to handle specific animation strategies
 * and provide a way to draw and update animations based on the current state.
 */
interface IAnimator {

    /**
     * Sets the current animation state.
     * 
     * @param state the name of the animation state to set. This could represent
     *              different animation phases such as "idle", "running", or "jumping".
     */
    void setAnimationState(String state);

    /**
     * Draws the current animation frame on the provided {@link Graphics} context.
     * 
     * @param g      the {@link Graphics} context used for rendering.
     * @param x      the x-coordinate where the animation should be drawn.
     * @param y      the y-coordinate where the animation should be drawn.
     * @param width  the width of the animation frame.
     * @param height the height of the animation frame.
     */
    void draw(Graphics g, double x, double y, int width, int height);

    /**
     * Updates the animation logic. This method is typically called on each frame
     * to progress the animation based on the current state and elapsed time.
     */
    void update();

    /**
     * Sets the animation strategy to be used by this animator.
     * 
     * @param animationStrategy the {@link AnimationStrategy} that defines how
     *                          animations are handled, such as frame transitions
     *                          and timing.
     */
    public void setAnimationStrategy(AnimationStrategy animationStrategy);
}
