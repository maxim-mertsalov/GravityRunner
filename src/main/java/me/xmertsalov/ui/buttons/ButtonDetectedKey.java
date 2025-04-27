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
 * Represents a button that listens for a key press and displays the detected key.
 * This button can be clicked to start listening for a key press, and the detected key is displayed on the button.
 */
public class ButtonDetectedKey implements IButton {
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

    private boolean listening = false;
    private int keyCode = 0;

    /**
     * Constructs a ButtonDetectedKey with specified position, size, variant, and game context.
     *
     * @param x      The x-coordinate of the button.
     * @param y      The y-coordinate of the button.
     * @param width  The width of the button.
     * @param height The height of the button.
     * @param variant The variant of the button's appearance.
     * @param game   The game context.
     */
    public ButtonDetectedKey(int x, int y, int width, int height, int variant, Game game) {
        this.game = game;
        this.rectangle = new Rectangle2D.Float(x, y, width, height);
        this.variant = variant;

        loadImage();
    }

    /**
     * Draws the button on the screen, including the detected key text.
     *
     * @param g The Graphics object used for drawing.
     */
    @Override
    public void draw(Graphics g) {
        g.drawImage(images[variant][state], (int) rectangle.x, (int) rectangle.y, (int) rectangle.width, (int) rectangle.height, null);
        g.setColor(Color.BLACK);
        g.drawString(KeyEvent.getKeyText(keyCode), (int) (rectangle.x + rectangle.getWidth() / 2 - 5), (int) (rectangle.y + rectangle.getHeight() / 2));
    }

    /**
     * Updates the button's state. If the button is listening for a key press, its state is updated accordingly.
     */
    @Override
    public void update() {
        if (listening) {
            state = 2;
        }
    }

    /**
     * Handles mouse click events. Starts or stops listening for a key press based on the button's state.
     *
     * @param e The MouseEvent object containing details of the mouse click.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (rectangle.contains(e.getX(), e.getY()) && !listening) {
            listening = true;
            state = 2;
        }
        else if (listening) {
            listening = false;
            state = 0;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (listening) return;

        if (rectangle.contains(e.getX(), e.getY())) {
            mouseEntered(e);
        }
        else {
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

    /**
     * Handles key release events. Detects the key code and updates the button's state.
     *
     * @param e The KeyEvent object containing details of the key release.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        if (listening) {
            int newCode = e.getKeyCode();
            if (newCode != this.keyCode) {
                this.keyCode = newCode;
            }
            state = 0;
            listening = false;
            game.getAudioPlayer().playSfx(AudioPlayer.SFX_CLICK);
        }
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
            image = BundleLoader.getSpriteAtlas(BundleLoader.EMPTY_BUTTON);
        } catch (BundleLoadException e) {
            Game.logger.error(e.getMessage());
            throw new RuntimeException(e);
        }

        int rows = 1;
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
     * Gets the detected key code.
     *
     * @return The key code of the detected key.
     */
    @Override
    public int getData() {
        return keyCode;
    }

    /**
     * Sets the detected key code.
     *
     * @param data The key code to set.
     */
    @Override
    public void setData(int data) {
        this.keyCode = data;
    }
}
