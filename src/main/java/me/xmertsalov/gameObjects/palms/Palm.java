package me.xmertsalov.gameObjects.palms;

import me.xmertsalov.Game;
import me.xmertsalov.components.Animator.Animator;
import me.xmertsalov.gameObjects.GameObject;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;

public class Palm extends GameObject {
    private Animator animator;

    private double spriteWidth, spriteHeight;

    private double incrementSize = 1;

    public Palm(double x, double y, String imageURL, int spriteWidth, int spriteHeight) {
        super(x, y);

        this.spriteWidth = spriteWidth;
        this.spriteHeight = spriteHeight;

        HashMap<String, List<Integer>> animationStates = new HashMap<>();
        animationStates.put("IDLE", Arrays.asList(0, 4));

        this.animator = new Animator.Builder()
                .setImageURL(imageURL)
                .setSpriteWidth(spriteWidth)
                .setSpriteHeight(spriteHeight)
                .setRows(1)
                .setColumns(4)
                .setAnimationStates(animationStates)
                .setCurrentState("IDLE")
                .build_and_load();

    }

    private Palm(double x, double y, Animator animator, int zIndex, double spriteWidth, double spriteHeight, double sizeIncrement) {
        super(x, y);
        this.animator = animator;
        this.zIndex = zIndex;
        this.spriteWidth = spriteWidth;
        this.spriteHeight = spriteHeight;
        this.incrementSize = sizeIncrement;
    }

    @Override
    public void update() {
        animator.update();
    }

    @Override
    public void draw(Graphics g) {
        animator.draw(g, (int) getX(), (int) getY(), (int) (spriteWidth * incrementSize * Game.SCALE), (int) (spriteHeight * incrementSize * Game.SCALE));
    }

    @Override
    public void updatePos(double x, double y) {
        this.setX(x);
        this.setY(y);
    }

    @Override
    public GameObject clone() {
        return new Palm(x, y, new Animator.Builder().clone(this.animator), zIndex, spriteWidth, spriteHeight, incrementSize);
    }

    public void setIncrementSize(double incrementSize) {this.incrementSize = incrementSize;}

    public double getIncrementSize() {return incrementSize;}
}
