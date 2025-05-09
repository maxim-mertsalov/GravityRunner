package me.xmertsalov.components.Animator;

import me.xmertsalov.Game;
import me.xmertsalov.exceptions.AnimatorCreatingException;
import me.xmertsalov.exceptions.BundleLoadException;
import me.xmertsalov.utils.BundleLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;

/**
 * The Animator class is responsible for managing and rendering sprite animations.
 * It supports multiple animation states, frame updates, and strategies for playing animations.
 * Animations can be loaded from a sprite atlas or directly from a 2D array of BufferedImages.
 */
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

    /**
     * Constructor for loading from file.
     *
     * @param builder The Builder instance containing the configuration for the Animator.
     * @throws AnimatorCreatingException If any required field is missing or invalid.
     */
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

    /**
     * Constructor for loading from BufferedImage.
     *
     * @param builder    The Builder instance containing the configuration for the Animator.
     * @param animations A 2D array of BufferedImages representing the animations.
     * @throws AnimatorCreatingException If any required field is missing or invalid.
     */
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

    /**
     * Updates the current animation frame based on the animation strategy.
     * This method should be called in the game loop to ensure smooth animation playback.
     */
    public void update() {
        updateAnimationTick();
    }

    /**
     * Draws the current frame of the animation onto the provided Graphics object.
     *
     * @param g      The Graphics object used for rendering.
     * @param x      The x-coordinate where the animation should be drawn.
     * @param y      The y-coordinate where the animation should be drawn.
     * @param width  The width of the animation frame.
     * @param height The height of the animation frame.
     */
    public void draw(Graphics g, double x, double y, int width, int height) {
        BufferedImage sprite = animations[animationStates.get(animationState).get(0)][aniIndex];

        g.drawImage(sprite,
                (int) x, (int) y,
                width, height, null);
    }

    /**
     * Sets the animation strategy, which determines how the animation frames are updated.
     * For example, the strategy can define whether the animation loops or plays only once.
     *
     * @param animationStrategy The strategy to use for updating animation frames.
     */
    public void setAnimationStrategy(AnimationStrategy animationStrategy) {
        this.animationStrategy = animationStrategy;
    }

    /**
     * Changes the current animation state to the specified state.
     * If the state is invalid or the same as the current state, the method does nothing.
     *
     * @param state The new animation state to switch to.
     */
    public void setAnimationState(String state) {
        if (!animationStates.containsKey(state) || state.equals(animationState)) {
            return;
        }

        resetAnimTick();
        animationState = state;
    }

    /**
     * Updates the animation tick and frame index based on the current animation strategy.
     * If no strategy is set, a default looping strategy is used.
     */
    private void updateAnimationTick() {
        if (animationStrategy != null) {
            animationStrategy.updateAnimationTick(this);
        }
        else {
            animationStrategy = new LoopingAnimationStrategy();
        }
    }

    /**
     * Resets the animation tick and frame index to the beginning of the animation.
     * This is useful when switching to a new animation state.
     */
    private void resetAnimTick() {
        aniTick = 0;
        aniIndex = 0;
    }

    /**
     * Loads animations from a sprite atlas specified by the imageURL.
     * The sprite atlas is divided into individual frames based on the sprite dimensions and grid layout.
     */
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

    /**
     * Builder class for constructing Animator instances.
     * Provides a flexible way to configure and create Animator objects with various settings.
     */
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

        /**
         * Sets the URL of the sprite atlas image.
         *
         * @param imageURL The URL of the sprite atlas.
         * @return The Builder instance for method chaining.
         */
        public Builder setImageURL(String imageURL) {
            this.imageURL = imageURL;
            return this;
        }

        /**
         * Sets the width of each sprite in the sprite atlas.
         *
         * @param spriteWidth The width of each sprite.
         * @return The Builder instance for method chaining.
         */
        public Builder setSpriteWidth(int spriteWidth) {
            this.spriteWidth = spriteWidth;
            return this;
        }

        /**
         * Sets the height of each sprite in the sprite atlas.
         *
         * @param spriteHeight The height of each sprite.
         * @return The Builder instance for method chaining.
         */
        public Builder setSpriteHeight(int spriteHeight) {
            this.spriteHeight = spriteHeight;
            return this;
        }

        /**
         * Sets the number of rows in the sprite atlas.
         *
         * @param rows The number of rows.
         * @return The Builder instance for method chaining.
         */
        public Builder setRows(int rows) {
            this.rows = rows;
            return this;
        }

        /**
         * Sets the number of columns in the sprite atlas.
         *
         * @param columns The number of columns.
         * @return The Builder instance for method chaining.
         */
        public Builder setColumns(int columns) {
            this.columns = columns;
            return this;
        }

        /**
         * Sets the animation states and their corresponding frames.
         *
         * @param animationStates A map of animation states to frame lists.
         * @return The Builder instance for method chaining.
         */
        public Builder setAnimationStates(HashMap<String, List<Integer>> animationStates) {
            this.animationStates = animationStates;
            return this;
        }

        /**
         * Sets the initial animation state.
         *
         * @param currentState The initial animation state.
         * @return The Builder instance for method chaining.
         */
        public Builder setCurrentState(String currentState) {
            this.currentState = currentState;
            return this;
        }

        /**
         * Sets the speed of the animation.
         *
         * @param animationSpeed The speed of the animation in ticks per frame.
         * @return The Builder instance for method chaining.
         */
        public Builder setAnimationSpeed(int animationSpeed) {
            if (animationSpeed <= -1) {
                this.animationSpeed = 25;
            }
            else this.animationSpeed = animationSpeed;

            return this;
        }

        /**
         * Sets the animation strategy for the Animator.
         *
         * @param animationStrategy The animation strategy to use.
         * @return The Builder instance for method chaining.
         */
        public Builder setAnimationStrategy(AnimationStrategy animationStrategy) {
            this.animationStrategy = animationStrategy;
            return this;
        }

        /**
         * Builds and loads an Animator instance using the configured settings.
         *
         * @return The constructed Animator instance, or null if an error occurs.
         */
        public Animator build_and_load() {
            try {
                return new Animator(this);
            } catch (AnimatorCreatingException e) {
                Game.logger.error(e.getMessage());
                return null;
            }
        }

        /**
         * Clones an existing Animator instance by copying its settings and animations.
         *
         * @param animator The Animator instance to clone.
         * @return A new Animator instance with the same settings and animations, or null if cloning fails.
         */
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

    public void setAnimationSpeed(int animationSpeed) {
        this.aniSpeed = animationSpeed;
    }

}

