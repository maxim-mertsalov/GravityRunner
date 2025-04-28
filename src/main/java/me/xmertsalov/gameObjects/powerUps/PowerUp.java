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

/**
 * Abstract base class for all power-up objects in the game.
 * Power-ups are collectible items that provide temporary effects to the player.
 * This class handles the common functionality such as animation, position, and collision bounds.
 */
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

    /**
     * Constructs a PowerUp object at the specified position.
     *
     * @param x The x-coordinate of the power-up.
     * @param y The y-coordinate of the power-up.
     */
    protected PowerUp(double x, double y) {
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

        setZIndex(0);
    }

    protected PowerUp(double x, double y, Animator animator, HashMap<String, List<Integer>> animationStates) {
        super(x, y);
        this.animator = animator;
        this.animationStates = animationStates;
        bounds = new Rectangle2D.Double(x, y, Game.TILES_SIZE, Game.TILES_SIZE);
        this.animator.setAnimationSpeed(250);
        setZIndex(0);
    }

    /**
     * Updates the position of the power-up and its collision bounds.
     *
     * @param x The new x-coordinate.
     * @param y The new y-coordinate.
     */
    public void updatePos(double x, double y) {
        this.x = x;
        this.y = y;
        bounds.x = x;
        bounds.y = y;
    }

    /**
     * Draws the power-up on the screen using its animator.
     *
     * @param g The Graphics object used for rendering.
     */
    public void draw(Graphics g) {
        animator.draw(g, x, y, (int) bounds.getWidth(), (int) bounds.getHeight());
    }

    /**
     * Updates the animation state of the power-up.
     */
    public void update() {
        animator.update();
    }

    /**
     * Sets the x-coordinate of the power-up and updates its collision bounds.
     *
     * @param x The new x-coordinate.
     */
    @Override
    public void setX(double x) {
        super.setX(x);
        bounds.x = x;
    }

    /**
     * Sets the y-coordinate of the power-up and updates its collision bounds.
     *
     * @param y The new y-coordinate.
     */
    @Override
    public void setY(double y) {
        super.setY(y);
        bounds.y = y;
    }

    /**
     * Gets the collision bounds of the power-up.
     *
     * @return A Rectangle2D.Double representing the collision bounds.
     */
    public Rectangle2D.Double getBounds() {
        return bounds;
    }
}
