package me.xmertsalov.components.Animator;

public class OneWayAnimationStrategy implements AnimationStrategy {
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
