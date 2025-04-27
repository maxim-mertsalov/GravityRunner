package me.xmertsalov.components.Animator;

public class LoopingAnimationStrategy implements AnimationStrategy {
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
