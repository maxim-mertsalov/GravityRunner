package me.xmertsalov.components.Animator;

/**
 * The {@code AnimationStrategy} interface defines a contract for implementing
 * different animation strategies. Classes implementing this interface are
 * responsible for updating the animation state of an {@link Animator} object.
 * 
 * <p>
 * This interface is designed to allow flexibility in how animations are handled
 * by providing a common method {@code updateAnimationTick} that can be
 * implemented with various strategies. For example, an implementation might
 * handle frame-based animations, time-based animations, or other custom
 * animation logic.
 * </p>
 */
public interface AnimationStrategy {

    /**
     * Updates the animation tick for the given {@link Animator} instance.
     * 
     * <p>
     * This method is called to progress the animation state of the provided
     * {@code Animator}. Implementations of this method should define how the
     * animation tick is updated, such as advancing frames, resetting states, or
     * applying specific animation logic.
     * </p>
     * 
     * @param animator the {@link Animator} instance whose animation state is to
     *                 be updated. This object contains the current state and
     *                 properties of the animation.
     */
    void updateAnimationTick(Animator animator);
}
