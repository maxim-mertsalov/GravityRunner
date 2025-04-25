package me.xmertsalov.components;

import me.xmertsalov.utils.BundleLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static me.xmertsalov.components.PlayerState.*;

interface IAnimator {
    public void setAnimationState(String state);
    public void draw(Graphics g, double x, double y, int width, int height);
    public void update();
}

public class Animator implements IAnimator {
    private BufferedImage image;
    private String imageURL;
    private int spriteWidth = 64;
    private int spriteHeight = 40;
    private int rows = 9;
    private int cols = 6;

    private BufferedImage[][] animations;

    private int aniTick, aniIndex, aniSpeed = 25;
    private boolean inOneWay = false; // if true, animation will be played only once

    private String animationState = "RUNNING";
    private HashMap<String, List<Integer>> animationStates = new HashMap<>(); // first - animation state, second - number of frames in animation


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

//        System.out.println(sprite.toString());

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
        image = BundleLoader.getSpriteAtlas(imageURL);

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
                    System.err.println("ERR: Subimage coordinates are out of bounds: (" + x + ", " + y + ")");
                }
            }
        }
    }

}
