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
 * Represents a button with arrow icons that can be clicked, hovered, or pressed.
 * The button supports different visual states and variants.
 */
public class ArrowsButton implements IButton {
    // Dependencies
    private Game game;

    // Position && Size
    private Rectangle2D.Float rectangle;

    // Images
    private BufferedImage[][] images;

    // States
    private int variant = 0;
    private int state = 0;

    // Runnable
    private Runnable onClickListener;

    /**
     * Constructs an ArrowsButton with specified position, size, variant, and game context.
     *
     * @param x      The x-coordinate of the button.
     * @param y      The y-coordinate of the button.
     * @param width  The width of the button.
     * @param height The height of the button.
     * @param variant The variant of the button (e.g., different arrow styles).
     * @param game   The game instance for accessing shared resources.
     */
    public ArrowsButton(int x, int y, int width, int height, int variant, Game game) {
        this.game = game;
        this.rectangle = new Rectangle2D.Float(x, y, width, height);
        this.variant = variant;

        loadImage();
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
     * Updates the button's state. Can be used for animations or other dynamic behavior.
     */
    @Override
    public void update() {
        // Can be used for animations
    }

    /**
     * Handles mouse click events. Executes the onClickListener if the button is clicked.
     *
     * @param e The MouseEvent object containing event details.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (rectangle.contains(e.getX(), e.getY()) && onClickListener != null) {
            onClickListener.run();
        }
    }

    /**
     * Handles mouse press events. Changes the button's state to "pressed".
     *
     * @param e The MouseEvent object containing event details.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (rectangle.contains(e.getX(), e.getY())) {
            state = 2;
        }
    }

    /**
     * Handles mouse release events. Changes the button's state to "hovered" and plays a click sound.
     *
     * @param e The MouseEvent object containing event details.
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        if (rectangle.contains(e.getX(), e.getY())) {
            state = 1;
            game.getAudioPlayer().playSfx(AudioPlayer.SFX_CLICK);
        }
    }

    /**
     * Handles mouse movement events. Updates the button's state based on whether the mouse is over it.
     *
     * @param e The MouseEvent object containing event details.
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        if (rectangle.contains(e.getX(), e.getY())) {
            mouseEntered(e);
        }
        else{
            mouseExited(e);
        }
    }

    /**
     * Handles mouse enter events. Changes the button's state to "hovered".
     *
     * @param e The MouseEvent object containing event details.
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        state = 1;
    }

    /**
     * Handles mouse exit events. Changes the button's state to "default".
     *
     * @param e The MouseEvent object containing event details.
     */
    @Override
    public void mouseExited(MouseEvent e) {
        state = 0;
    }

    /**
     * Handles mouse drag events. Currently not implemented.
     *
     * @param e The MouseEvent object containing event details.
     */
    @Override
    public void mouseDragged(MouseEvent e) {

    }

    /**
     * Handles key press events. Currently not implemented.
     *
     * @param e The KeyEvent object containing event details.
     */
    @Override
    public void keyPressed(KeyEvent e) {

    }

    /**
     * Handles key release events. Currently not implemented.
     *
     * @param e The KeyEvent object containing event details.
     */
    @Override
    public void keyReleased(KeyEvent e) {

    }

    /**
     * Sets the button's state (e.g., default, hovered, pressed).
     *
     * @param state The new state of the button.
     */
    @Override
    public void setState(int state) {
        this.state = state;
    }

    /**
     * Sets the button's variant (e.g., different arrow styles).
     *
     * @param variant The new variant of the button.
     */
    @Override
    public void setVariant(int variant) {
        this.variant = variant;
    }

    /**
     * Sets the onClickListener to be executed when the button is clicked.
     *
     * @param listener The Runnable to execute on click.
     */
    @Override
    public void setOnClickListener(Runnable listener) {
        this.onClickListener = listener;
    }

    /**
     * Loads the button's images from a sprite atlas.
     * Splits the atlas into individual images based on rows and columns.
     */
    @Override
    public void loadImage() {
        BufferedImage image = null;
        try {
            image = BundleLoader.getSpriteAtlas(BundleLoader.ARROWS_BUTTONS);
        } catch (BundleLoadException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }

        int rows = 2;
        int cols = 3;
        int width = 12;
        int height = 12;

        images = new BufferedImage[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                images[i][j] = image.getSubimage(j * width, i * height, width, height);
            }
        }
    }

    /**
     * Gets the button's bounding rectangle for collision detection.
     *
     * @return The Rectangle2D representing the button's bounds.
     */
    @Override
    public Rectangle2D getRectangle() {
        return rectangle;
    }

    /**
     * Gets the button's data. Currently returns 0 as no data is associated.
     *
     * @return The button's data.
     */
    @Override
    public int getData() {
        return 0;
    }

    /**
     * Sets the button's data. Currently does nothing as no data is associated.
     *
     * @param data The data to set.
     */
    @Override
    public void setData(int data) {}
}
