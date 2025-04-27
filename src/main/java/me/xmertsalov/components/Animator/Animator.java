package me.xmertsalov.components.Animator;

import me.xmertsalov.Game;
import me.xmertsalov.exeptions.AnimatorCreatingException;
import me.xmertsalov.exeptions.BundleLoadException;
import me.xmertsalov.utils.BundleLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;

interface IAnimator {
    void setAnimationState(String state);
    void draw(Graphics g, double x, double y, int width, int height);
    void update();
}

public class Animator implements IAnimator {
    // Sprite Atlas Settings
    private String imageURL;
    private int spriteWidth;
    private int spriteHeight;
    private int rows;
    private int cols;

    // Animation frames
    private BufferedImage[][] animations;

    // Animation variables
    protected int aniTick;
    protected int aniIndex;
    protected int aniSpeed = 25;

    // States
    private AnimationStrategy animationStrategy;
    protected boolean inOneWay = false; // if true, animation will be played only once

    // Animation states
    protected String animationState;
    protected HashMap<String, List<Integer>> animationStates; // first - animation state, second - number of frames in animation


    // Constructor for loading from file
    private Animator(Builder builder) throws AnimatorCreatingException {
        if (builder.imageURL == null) {
            throw new AnimatorCreatingException("Image URL is null", AnimatorCreatingException.FieldError.incorrectURL);
        }
        else if (builder.spriteWidth == 0 || builder.spriteHeight == 0 || builder.rows == 0 || builder.columns == 0) {
            throw new AnimatorCreatingException("Sprite dimensions or rows/columns are zero", AnimatorCreatingException.FieldError.thereAreNotEnoughImageSettings);
        }
        else if (builder.animationStates == null || builder.currentState == null) {
            throw new AnimatorCreatingException("Animation states or current state are null", AnimatorCreatingException.FieldError.noStates);
        }
        this.imageURL = builder.imageURL;
        this.spriteWidth = builder.spriteWidth;
        this.spriteHeight = builder.spriteHeight;
        this.rows = builder.rows;
        this.cols = builder.columns;

        this.aniSpeed = builder.animationSpeed;

        this.animationStates = builder.animationStates;
        this.animationState = builder.currentState;

        loadAnimations();
    }

    // Constructor for loading from BufferedImage
    private Animator(Builder builder, BufferedImage[][] animations) throws AnimatorCreatingException {
        if (builder.animationStates == null || builder.currentState == null) {
            throw new AnimatorCreatingException("Animation states or current state are null", AnimatorCreatingException.FieldError.noStates);
        } else if (builder.animations.length == 0) {
            throw new AnimatorCreatingException("Animations are empty", AnimatorCreatingException.FieldError.noAnimations);
        }

        this.spriteWidth = builder.spriteWidth;
        this.spriteHeight = builder.spriteHeight;
        this.rows = builder.rows;
        this.cols = builder.columns;

        this.animationStates = builder.animationStates;
        this.animationState = builder.currentState;

        this.aniSpeed = builder.animationSpeed;

        this.animations = animations;
    }

    public void update(){
        updateAnimationTick();
    }

    public void draw(Graphics g, double x, double y, int width, int height) {
        BufferedImage sprite = animations[animationStates.get(animationState).getFirst()][aniIndex];

        g.drawImage(sprite,
                (int) x, (int) y,
                width, height, null);
    }

    // Set strategy for animation (looping or one way)
    public void setAnimationStrategy(AnimationStrategy animationStrategy) {
        this.animationStrategy = animationStrategy;
    }

    // set animation state (other animation)
    public void setAnimationState(String state) {
        if (!animationStates.containsKey(state) || state.equals(animationState)) {
            return;
        }

        resetAnimTick();
        animationState = state;
    }

    private void updateAnimationTick() {
        if (animationStrategy != null) {
            animationStrategy.updateAnimationTick(this);
        }
        else {
            animationStrategy = new LoopingAnimationStrategy();
        }
    }

    private void resetAnimTick() {
        aniTick = 0;
        aniIndex = 0;
    }

    private void loadAnimations() {
        // Sprite Atlas
        BufferedImage image;

        try {
            image = BundleLoader.getSpriteAtlas(imageURL);
        } catch (BundleLoadException e) {
            Game.logger.error(e.getMessage());
            throw new RuntimeException(e);
        }

        int imgWidth = image.getWidth();
        int imgHeight = image.getHeight();

        animations = new BufferedImage[rows][cols];
        for (int j = 0; j < rows; j++) {
            for (int i = 0; i < cols; i++) {
                int x = i * spriteWidth;
                int y = j * spriteHeight;
                if (x + spriteWidth <= imgWidth && y + spriteHeight <= imgHeight) {
                    animations[j][i] = image.getSubimage(x, y, spriteWidth, spriteHeight);
                } else {
                    Game.logger.warn("ERR: Sub-image coordinates are out of bounds: ({}, {})", x, y);
                }
            }
        }
    }

    public static class Builder{
        private String imageURL;
        private int spriteWidth;
        private int spriteHeight;
        private int rows;
        private int columns;
        private HashMap<String, List<Integer>> animationStates; // first - animation state, second - number of frames in animation
        private String currentState;
        private int animationSpeed = 25;
        private AnimationStrategy animationStrategy;

        private BufferedImage[][] animations;

        public Builder() {}

        public Builder(BufferedImage[][] animations) {
            this.animations = animations;
        }

        public Builder setImageURL(String imageURL) {
            this.imageURL = imageURL;
            return this;
        }

        public Builder setSpriteWidth(int spriteWidth) {
            this.spriteWidth = spriteWidth;
            return this;
        }

        public Builder setSpriteHeight(int spriteHeight) {
            this.spriteHeight = spriteHeight;
            return this;
        }

        public Builder setRows(int rows) {
            this.rows = rows;
            return this;
        }

        public Builder setColumns(int columns) {
            this.columns = columns;
            return this;
        }

        public Builder setAnimationStates(HashMap<String, List<Integer>> animationStates) {
            this.animationStates = animationStates;
            return this;
        }

        public Builder setCurrentState(String currentState) {
            this.currentState = currentState;
            return this;
        }

        public Builder setAnimationSpeed(int animationSpeed) {
            if (animationSpeed <= -1) {
                this.animationSpeed = 25;
            }
            else this.animationSpeed = animationSpeed;

            return this;
        }

        public Builder setAnimationStrategy(AnimationStrategy animationStrategy) {
            this.animationStrategy = animationStrategy;
            return this;
        }

        public Animator build_and_load() {
            try {
                return new Animator(this);
            } catch (AnimatorCreatingException e) {
                Game.logger.error(e.getMessage());
                return null;
            }
        }

        public Animator clone(Animator animator) {
            if (animator == null) {
                Game.logger.error("Cannot clone a null Animator");
                return null; // Return null or handle this case appropriately
            }

            this.spriteWidth = animator.spriteWidth;
            this.spriteHeight = animator.spriteHeight;
            this.rows = animator.rows;
            this.columns = animator.cols;
            this.animationStates = animator.animationStates;
            this.currentState = animator.animationState;
            this.animationSpeed = animator.aniSpeed;
            this.animations = animator.animations;

            try {
                return new Animator(this, animations);
            } catch (AnimatorCreatingException e) {
                Game.logger.error(e.getMessage());
                return null;
            }
        }

    }

}
