package me.xmertsalov.ui.buttons;

import me.xmertsalov.Game;
import me.xmertsalov.audio.AudioPlayer;
import me.xmertsalov.exceptions.BundleLoadException;
import me.xmertsalov.utils.BundleLoader;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * Represents a small button with multiple variants and states.
 * The button can respond to mouse events and execute a click listener when clicked.
 */
public class SmallButton implements IButton {
    // Dependencies
    private Game game;

    // Position && Size
    private Rectangle2D.Float rectangle;

    // Images
    private BufferedImage[][] images;

    // States
    private int state = 0;
    private int variant = 0;

    // Runnable
    private Runnable onClickListener;

    /**
     * Constructs a SmallButton with specified position, size, variant, and game context.
     *
     * @param x      The x-coordinate of the button.
     * @param y      The y-coordinate of the button.
     * @param width  The width of the button.
     * @param height The height of the button.
     * @param variant The variant of the button's appearance.
     * @param game   The game context.
     */
    public SmallButton(int x, int y, int width, int height, int variant, Game game) {
        this.game = game;
        this.rectangle = new Rectangle2D.Float(x, y, width, height);
        this.variant = variant;
    }

    /**
     * Draws the button on the screen.
     *
     * @param g The Graphics object used for drawing.
     */
    @Override
    public void draw(Graphics g) {
        g.drawImage(images[variant][state], (int) rectangle.x, (int) rectangle.y, (int) rectangle.width, (int) rectangle.height, null);
    }

    /**
     * Updates the button's state. Currently, no specific update logic is implemented.
     */
    @Override
    public void update() {
        // Implementation
    }

    /**
     * Handles mouse click events. Executes the click listener if the button is clicked.
     *
     * @param e The MouseEvent object containing details of the mouse click.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (rectangle.contains(e.getX(), e.getY()) && onClickListener != null) {
            onClickListener.run();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (rectangle.contains(e.getX(), e.getY())) {
            state = 2;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (rectangle.contains(e.getX(), e.getY())) {
            state = 1;
            game.getAudioPlayer().playSfx(AudioPlayer.SFX_CLICK);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (rectangle.contains(e.getX(), e.getY())) {
            mouseEntered(e);
        } else {
            mouseExited(e);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        state = 1;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        state = 0;
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void setState(int state) {
        this.state = state;
    }

    @Override
    public void setVariant(int variant) {
        this.variant = variant;
    }

    @Override
    public void setOnClickListener(Runnable listener) {
        this.onClickListener = listener;
    }

    /**
     * Loads the button's images from a sprite atlas.
     */
    @Override
    public void loadImage() {
        BufferedImage image = null;
        try {
            image = BundleLoader.getSpriteAtlas(BundleLoader.SMALL_BUTTONS);
        } catch (BundleLoadException e) {
            Game.logger.error(e.getMessage());
            throw new RuntimeException(e);
        }

        int rows = 5;
        int cols = 3;
        int width = 14;
        int height = 14;

        images = new BufferedImage[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                images[i][j] = image.getSubimage(j * width, i * height, width, height);
            }
        }
    }

    /**
     * Gets the rectangle representing the button's position and size.
     *
     * @return The rectangle of the button.
     */
    @Override
    public Rectangle2D getRectangle() {
        return rectangle;
    }

    /**
     * Gets the button's data. For SmallButton, this always returns 0.
     *
     * @return The button's data.
     */
    @Override
    public int getData() {
        return 0;
    }

    /**
     * Sets the button's data. For SmallButton, this method does nothing.
     *
     * @param data The data to set.
     */
    @Override
    public void setData(int data) {}
}
