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
 * Represents a large button in the UI with multiple states and variants.
 * The button can respond to mouse and keyboard events and execute a specified action when clicked.
 */
public class BigButton implements IButton {
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
     * Constructs a BigButton with specified position, size, variant, and game context.
     *
     * @param x      The x-coordinate of the button.
     * @param y      The y-coordinate of the button.
     * @param width  The width of the button.
     * @param height The height of the button.
     * @param variant The variant of the button (used for selecting images).
     * @param game   The game instance for accessing shared resources.
     */
    public BigButton(int x, int y, int width, int height, int variant, Game game) {
        this.game = game;
        this.rectangle = new Rectangle2D.Float(x, y, width, height);
        this.variant = variant;
    }

    /**
     * Draws the button on the screen using the current state and variant.
     *
     * @param g The Graphics object used for rendering.
     */
    @Override
    public void draw(Graphics g) {
        g.drawImage(images[variant][state], (int) rectangle.x, (int) rectangle.y, (int) rectangle.width, (int) rectangle.height, null);
    }

    /**
     * Updates the button's state. This method is intended to be called periodically
     * (e.g., in a game loop) to handle any state changes.
     */
    @Override
    public void update() {
        // Implementation
    }

    /**
     * Handles mouse click events. Executes the onClickListener if the button is clicked.
     *
     * @param e The MouseEvent containing details about the click.
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
        }
        else{
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

    /**
     * Sets the action to be executed when the button is clicked.
     *
     * @param listener The Runnable containing the action to execute.
     */
    @Override
    public void setOnClickListener(Runnable listener) {
        this.onClickListener = listener;
    }

    /**
     * Loads the button's images from a sprite atlas. The images are divided into rows and columns
     * based on the button's variants and states.
     *
     * @throws RuntimeException if the sprite atlas cannot be loaded.
     */
    @Override
    public void loadImage() {
        BufferedImage image = null;
        try {
            image = BundleLoader.getSpriteAtlas(BundleLoader.BIG_BUTTONS);
        } catch (BundleLoadException e) {
            Game.logger.error(e.getMessage());
            throw new RuntimeException(e);
        }

        int rows = 12;
        int cols = 3;
        int width = 56;
        int height = 14;

        images = new BufferedImage[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                images[i][j] = image.getSubimage(j * width, i * height, width, height);
            }
        }
    }

    @Override
    public Rectangle2D getRectangle() {
        return rectangle;
    }

    @Override
    public int getData() {
        return 0;
    }

    @Override
    public void setData(int data) {}
}
