package me.xmertsalov.components;

import me.xmertsalov.Game;
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
    private int aniTick, aniIndex, aniSpeed = 25;
    private boolean inOneWay = false; // if true, animation will be played only once

    // Animation states
    private String animationState;
    private HashMap<String, List<Integer>> animationStates; // first - animation state, second - number of frames in animation


    public Animator(String imageURL,
                    int spriteWidth,
                    int spriteHeight,
                    int rows,
                    int columns,
                    HashMap<String, List<Integer>> animationStates, // first - animation state, second - number of frames in animation
                    String currentState,
                    int animationSpeed // -1 is default value (25)
    ) {
        this.imageURL = imageURL;
        this.spriteWidth = spriteWidth;
        this.spriteHeight = spriteHeight;
        this.rows = rows;
        this.cols = columns;

        if (animationSpeed != -1) {
            this.aniSpeed = animationSpeed;
        }

        this.animationStates = animationStates;
        this.animationState = currentState;

        loadAnimations();
    }

    private Animator(
            int spriteWidth,
            int spriteHeight,
            int rows,
            int columns,
            HashMap<String, List<Integer>> animationStates,
            BufferedImage[][] animations,
            String currentState
    ){
        this.spriteWidth = spriteWidth;
        this.spriteHeight = spriteHeight;
        this.rows = rows;
        this.cols = columns;

        this.animationStates = animationStates;
        this.animations = animations;
        this.animationState = currentState;
    }

    public Animator clone(){
        return new Animator(
                this.spriteWidth,
                this.spriteHeight,
                this.rows,
                this.cols,
                this.animationStates,
                this.animations,
                this.animationState
        );
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

    // if animation states that we want exists and this animation state is changed we change animation
    public void setAnimationState(String state) {
        if (state.equals(animationState) || !animationStates.containsKey(state)){
            return;
        }
        this.inOneWay = false;
        resetAnimTick();
        animationState = state;
    }

    public void setAnimationState(String state, boolean inOneWay) {
        if (!animationStates.containsKey(state) || state.equals(animationState) && !inOneWay) {
            return;
        }

        this.inOneWay = inOneWay;
        resetAnimTick();
        animationState = state;
    }

    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;

            if (!inOneWay) {
                if (aniIndex < animationStates.get(animationState).get(1) - 1) {
                    aniIndex++;
                } else {
                    aniIndex = 0;
                }
            }// in one way animation
            else {
                if (aniIndex < animationStates.get(animationState).get(1) - 1) {
                    aniIndex++;
                } else {
                    aniIndex = animationStates.get(animationState).get(1) - 1;
                }
            }
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

}
