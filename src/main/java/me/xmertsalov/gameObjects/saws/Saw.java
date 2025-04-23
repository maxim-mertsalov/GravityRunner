package me.xmertsalov.gameObjects.saws;

import me.xmertsalov.Game;
import me.xmertsalov.components.Animator;
import me.xmertsalov.gameObjects.GameObject;
import me.xmertsalov.utils.BundleLoader;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.HashMap;
import java.util.List;

public class Saw extends GameObject {
    protected String animationState = "ROTATING";
    protected HashMap<String, List<Integer>> animationStates;

    protected Ellipse2D.Double bounds;
    protected Animator animator;

    public Saw(double x, double y) {
        super(x, y);
        bounds = new Ellipse2D.Double(x, y, 50 * Game.SCALE, 50 * Game.SCALE);

        animationStates = new HashMap<>();
        animationStates.put("IDLE", List.of(0, 1));
        animationStates.put("ROTATING", List.of(0, 8));

        animator = new Animator(BundleLoader.SAW_ATLAS,
                32,
                32,
                1,
                8,
                animationStates,
                animationState,
                5);
    }

    public void update(){
        animator.update();
    }

    @Override
    public void updatePos(double x, double y) {
        this.x = x;
        this.y = y;
        bounds.x = x;
        bounds.y = y;
    }


    public void draw(Graphics g){
        g.setColor(Game.DEBUG_COLOR);
        g.fillOval((int) x, (int) y, (int) bounds.getWidth(), (int) bounds.getHeight());
        animator.draw(g, x, y, (int) bounds.getWidth(), (int) bounds.getHeight());
    }

    public Ellipse2D.Double getBounds() {
        return bounds;
    }


    public Saw clone() {
        return new Saw(this.x, this.y);
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

}
