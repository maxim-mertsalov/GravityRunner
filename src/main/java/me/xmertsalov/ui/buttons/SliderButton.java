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
 * Represents a slider button that allows users to adjust a value by dragging the slider.
 * The slider's position corresponds to a percentage value between 0 and 100.
 */
public class SliderButton implements IButton {
    // Dependencies
    private Game game;

    // Size & position
    private Rectangle2D.Float rectangle;
    private Rectangle2D.Float trackRectangle;

    private int trackMinX;
    private int trackMaxX;

    // Images
    private BufferedImage[] track;
    private BufferedImage slider;

    // State
    private int state = 0;
    private int data = 0;
    private boolean dragging = false;

    // Listener
    private Runnable onClickListener;

    /**
     * Constructs a SliderButton with specified position, size, variant, and game context.
     *
     * @param x      The x-coordinate of the slider.
     * @param y      The y-coordinate of the slider.
     * @param width  The width of the slider.
     * @param height The height of the slider.
     * @param variant The variant of the slider's appearance.
     * @param game   The game context.
     */
    public SliderButton(int x, int y, int width, int height, int variant, Game game) {
        this.game = game;
        this.rectangle = new Rectangle2D.Float(x, y, width, height);

        loadImage();

        trackMinX = (int) (rectangle.x + 5 * Game.SCALE);
        trackMaxX = (int) (rectangle.x + rectangle.width - (7 * 2.25f * Game.SCALE) - 4 * Game.SCALE);
        trackRectangle = new Rectangle2D.Float(getTrackXFromData(), y + 2.5f * Game.SCALE, 7 * 2.25f * Game.SCALE, 11 * 2.25f * Game.SCALE);
    }

    /**
     * Draws the slider and its track on the screen.
     *
     * @param g The Graphics object used for drawing.
     */
    @Override
    public void draw(Graphics g) {
        g.drawImage(slider, (int)rectangle.x, (int)rectangle.y, (int)(rectangle.width), (int)(rectangle.height * Game.SCALE), null);
        g.drawImage(track[state], (int) trackRectangle.x, (int) (trackRectangle.y), (int) (trackRectangle.width), (int) (trackRectangle.height), null);
    }

    /**
     * Updates the slider's state. Currently, no specific update logic is implemented.
     */
    @Override
    public void update() {

    }

    /**
     * Handles mouse click events. Updates the slider's state if the track is clicked.
     *
     * @param e The MouseEvent object containing details of the mouse click.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (trackRectangle.contains(e.getX(), e.getY())) {
            state = 1;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (trackRectangle.contains(e.getX(), e.getY())) {
            state = 2;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (trackRectangle.contains(e.getX(), e.getY()) || dragging) {
            if (onClickListener != null) onClickListener.run();
            state = 1;
            dragging = false;
            game.getAudioPlayer().playSfx(AudioPlayer.SFX_CLICK);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (trackRectangle.contains(e.getX(), e.getY())) {
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

    /**
     * Handles mouse drag events. Updates the slider's position and value based on the drag.
     *
     * @param e The MouseEvent object containing details of the mouse drag.
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        if (trackRectangle.contains(e.getX(), e.getY())) {
            dragging = true;
            int x = e.getX() - (int) (trackRectangle.width / 2);
            if (x <= trackMinX) {
                x = trackMinX;
            }
            else if (x >= trackMaxX) {
                x = trackMaxX;
            }

            trackRectangle.x = x;
            data = getDataFromX();
        }
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
    public void setVariant(int variant) {}

    @Override
    public void setOnClickListener(Runnable listener) {
        this.onClickListener = listener;
    }

    /**
     * Loads the slider's images from a sprite atlas.
     */
    @Override
    public void loadImage() {
        BufferedImage image = null;
        try {
            image = BundleLoader.getSpriteAtlas(BundleLoader.SLIDER_BUTTON);
        } catch (BundleLoadException e) {
            Game.logger.error(e.getMessage());
            throw new RuntimeException(e);
        }

        // for track
        int cols = 3;
        int tWidth = 7;

        // for slider
        int sWdith = 53;

        int height = 11;

        track = new BufferedImage[cols];

        for (int i = 0; i < cols; i++) {
            track[i] = image.getSubimage(i * tWidth, 0, tWidth, height);
        }
        slider = image.getSubimage(3 * tWidth, 0, sWdith, height);
    }

    private int getTrackXFromData() {
        float t = data / 100.0f;
        return (int) (trackMinX + t * (trackMaxX - trackMinX));
    }

    private int getDataFromX(){
        double x = trackRectangle.x;
        double t = (x - trackMinX) / (trackMaxX - trackMinX);
        return (int) (t * 100);
    }

    /**
     * Gets the rectangle representing the slider's position and size.
     *
     * @return The rectangle of the slider.
     */
    @Override
    public Rectangle2D getRectangle() {
        return rectangle;
    }

    /**
     * Gets the slider's current value as a percentage (0-100).
     *
     * @return The slider's value.
     */
    @Override
    public int getData() {
        return data;
    }

    /**
     * Sets the slider's value as a percentage (0-100) and updates its position.
     *
     * @param data The value to set.
     */
    @Override
    public void setData(int data) {
        this.data = data;
        trackRectangle.x = getTrackXFromData();
    }
}
