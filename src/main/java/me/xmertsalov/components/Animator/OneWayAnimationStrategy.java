package me.xmertsalov.components.Animator;

/**
 * A strategy for one-way animations.
 * <p>
 * This class implements the {@link AnimationStrategy} interface and provides a mechanism
 * to update the animation tick for an {@link Animator} object. The animation progresses
 * through its frames in a single direction and stops at the last frame.
 * </p>
 */
public class OneWayAnimationStrategy implements AnimationStrategy {

    /**
     * Updates the animation tick for the given {@link Animator}.
     * <p>
     * This method increments the animation tick (`aniTick`) of the animator. When the tick
     * reaches or exceeds the animation speed (`aniSpeed`), the tick is reset to 0, and the
     * animation index (`aniIndex`) is updated. The animation stops updating the index once
     * it reaches the last frame of the current animation state.
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
            }
        }
    }
}
