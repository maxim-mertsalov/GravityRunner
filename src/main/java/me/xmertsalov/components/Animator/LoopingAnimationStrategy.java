package me.xmertsalov.components.Animator;

/**
 * A strategy for looping animations.
 * <p>
 * This class implements the {@link AnimationStrategy} interface and provides a mechanism
 * to update the animation tick for an {@link Animator} object. The animation loops
 * continuously, restarting from the first frame after reaching the last frame.
 * </p>
 */
public class LoopingAnimationStrategy implements AnimationStrategy {

    /**
     * Updates the animation tick for the given {@link Animator}.
     * <p>
     * This method increments the animation tick (`aniTick`) of the animator. When the tick
     * reaches or exceeds the animation speed (`aniSpeed`), the tick is reset to 0, and the
     * animation index (`aniIndex`) is updated. If the animation index reaches the last frame
     * of the current animation state, it loops back to the first frame.
     * </p>
     *
     * @param animator The {@link Animator} object whose animation tick is being updated.
     */
    @Override
    public void updateAnimationTick(Animator animator) {
        animator.aniTick++;
        if (animator.aniTick >= animator.aniSpeed) {
            animator.aniTick = 0;
            if (animator.aniIndex < animator.animationStates.get(animator.animationState).get(1) - 1) {
                animator.aniIndex++;
            } else {
                animator.aniIndex = 0;
            }
        }
    }
}
