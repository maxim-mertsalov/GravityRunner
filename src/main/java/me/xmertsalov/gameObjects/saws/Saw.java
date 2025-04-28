package me.xmertsalov.gameObjects.saws;

import me.xmertsalov.Game;
import me.xmertsalov.components.Animator.Animator;
import me.xmertsalov.components.Animator.LoopingAnimationStrategy;
import me.xmertsalov.gameObjects.GameObject;
import me.xmertsalov.utils.BundleLoader;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.HashMap;
import java.util.List;

/**
 * Represents a stationary saw object in the game.
 * The saw has a circular collider and an animated rotating appearance.
 */
public class Saw extends GameObject {
    protected Ellipse2D.Double bounds; // Circular bounds of the saw.

    // Dependencies
    protected Animator animator; // Animator responsible for the saw's rotation animation.

    // States
    protected String animationState = "ROTATING"; // Current animation state.
    protected HashMap<String, List<Integer>> animationStates; // Map of animation states and their frame ranges.

    /**
     * Constructs a Saw object.
     *
     * @param x The X coordinate of the saw.
     * @param y The Y coordinate of the saw.
     */
    public Saw(double x, double y) {
        super(x, y);
        bounds = new Ellipse2D.Double(x, y, 50 * Game.SCALE, 50 * Game.SCALE);

        animationStates = new HashMap<>();
        animationStates.put("IDLE", List.of(0, 1));
        animationStates.put("ROTATING", List.of(0, 8));

        animator = new Animator.Builder()
                .setImageURL(BundleLoader.SAW_ATLAS)
                .setSpriteWidth(32)
                .setSpriteHeight(32)
                .setRows(1)
                .setColumns(8)
                .setAnimationStates(animationStates)
                .setCurrentState(animationState)
                .setAnimationSpeed(5)
                .setAnimationStrategy(new LoopingAnimationStrategy())
                .build_and_load();
    }

    protected Saw(double x, double y, Animator animator, HashMap<String, List<Integer>> animationStates, String animationState, Ellipse2D.Double bounds, int zIndex) {
        super(x, y);
        this.animationStates = animationStates;
        this.animationState = animationState;
        this.animator = animator;
        this.bounds = bounds;
        this.zIndex = zIndex;
    }

    /**
     * Updates the animation of the saw.
     */
    public void update() {
        animator.update();
    }

    /**
     * Updates the position of the saw.
     *
     * @param x The new X coordinate.
     * @param y The new Y coordinate.
     */
    @Override
    public void updatePos(double x, double y) {
        this.x = x;
        this.y = y;
        bounds.x = x;
        bounds.y = y;
    }

    /**
     * Draws the saw on the screen.
     * If debug mode is enabled, it also draws the collider.
     *
     * @param g The Graphics object used for drawing.
     */
    public void draw(Graphics g) {
        if (Game.DEBUG_COLLIDERS) {
            g.setColor(Game.DEBUG_COLOR);
            g.fillOval((int) x, (int) y, (int) bounds.getWidth(), (int) bounds.getHeight());
        }

        animator.draw(g, x, y, (int) bounds.getWidth(), (int) bounds.getHeight());
    }

    /**
     * Gets the circular bounds of the saw.
     *
     * @return The bounds of the saw.
     */
    public Ellipse2D.Double getBounds() {
        return bounds;
    }

    /**
     * Creates a clone of the Saw object.
     *
     * @return A new Saw object with the same properties.
     */
    @Override
    public GameObject clone() {
        return new Saw(x, y, new Animator.Builder().clone(this.animator), animationStates, animationState, bounds, zIndex);
    }

    /**
     * Sets the X coordinate of the saw and updates its bounds.
     *
     * @param x The new X coordinate.
     */
    @Override
    public void setX(double x) {
        super.setX(x);
        bounds.x = x;
    }

    /**
     * Sets the Y coordinate of the saw and updates its bounds.
     *
     * @param y The new Y coordinate.
     */
    @Override
    public void setY(double y) {
        super.setY(y);
        bounds.y = y;
    }
}

