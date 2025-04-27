package me.xmertsalov.gameObjects.powerUps;

import me.xmertsalov.Game;
import me.xmertsalov.components.Animator.Animator;
import me.xmertsalov.components.Animator.LoopingAnimationStrategy;
import me.xmertsalov.gameObjects.GameObject;
import me.xmertsalov.utils.BundleLoader;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.List;

public abstract class PowerUp extends GameObject {
    // Dependencies
    protected Animator animator;

    /*
    Size of power-up is tile size
    Special collider for player consume detection
     */
    protected Rectangle2D.Double bounds;


    // States
    protected HashMap<String, List<Integer>> animationStates;

    protected PowerUp(double x, double y){
        super(x, y);
        bounds = new Rectangle2D.Double(x, y, Game.TILES_SIZE, Game.TILES_SIZE);

        animationStates = new HashMap<>();
        animationStates.put("TO_RIGHT", List.of(0, 4));
        animationStates.put("TO_LEFT", List.of(1, 4));

        animator = new Animator.Builder()
                .setImageURL(BundleLoader.POWER_UPS_ATLAS)
                .setSpriteWidth(32)
                .setSpriteHeight(32)
                .setRows(2)
                .setColumns(4)
                .setAnimationStates(animationStates)
                .setCurrentState("IDLE")
                .setAnimationSpeed(20)
                .setAnimationStrategy(new LoopingAnimationStrategy())
                .build_and_load();
    }

    public void updatePos(double x, double y) {
        this.x = x;
        this.y = y;
        bounds.x = x;
        bounds.y = y;
    }

    public void draw(Graphics g) {
        animator.draw(g, x, y, (int) bounds.getWidth(), (int) bounds.getHeight());
    }

    public void update() {
        animator.update();
    }

    @Override
    public void setX(double x) {
        super.setX(x);
        bounds.x = x;
    }
    @Override
    public void setY(double y) {
        super.setY(y);
        bounds.y = y;
    }

    public Rectangle2D.Double getBounds() {
        return bounds;
    }
}
