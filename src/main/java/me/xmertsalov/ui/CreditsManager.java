package me.xmertsalov.ui;

import me.xmertsalov.Game;
import me.xmertsalov.exceptions.BundleLoadException;
import me.xmertsalov.scenes.GameScene;
import me.xmertsalov.ui.buttons.IButton;
import me.xmertsalov.ui.buttons.SmallButtonFactory;
import me.xmertsalov.utils.BundleLoader;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * The CreditsManager class manages the credits screen UI of the game.
 * It handles the display of the credits background and navigation buttons.
 */
public class CreditsManager implements UIManager {
    // Dependencies
    private SmallButtonFactory smallButtonFactory;
    private Game game;

    // Storage
    private ArrayList<IButton> buttons;

    // Images
    private BufferedImage background;

    /**
     * Constructs a CreditsManager instance.
     *
     * @param game The Game instance to which this manager belongs.
     */
    public CreditsManager(Game game) {
        this.game = game;
        loadImages();
        smallButtonFactory = new SmallButtonFactory();

        createButtons();
    }

    /**
     * Updates the state of the credits UI.
     */
    @Override
    public void update() {
        for (IButton button : buttons) {
            button.update();
        }
    }

    /**
     * Renders the credits UI components on the screen.
     *
     * @param g The Graphics object used for drawing.
     */
    @Override
    public void draw(Graphics g) {
        g.drawImage(background,
                (int)(Game.WINDOW_WIDTH / 2 - (1.8 * Game.SCALE * background.getWidth()) / 2),
                (int)(Game.WINDOW_HEIGHT / 2 - Game.SCALE * 125),
                (int)(1.8 * Game.SCALE * background.getWidth()),
                (int)(1.8 * Game.SCALE * background.getHeight()), null);

        for (IButton button : buttons) {
            button.draw(g);
        }
    }

    /**
     * Handles mouse click events for credits buttons.
     *
     * @param e The MouseEvent object containing details about the mouse click.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        for (IButton button : buttons) {
            button.mouseClicked(e);
        }
    }

    /**
     * Handles mouse press events for credits buttons.
     *
     * @param e The MouseEvent object containing details about the mouse press.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        for (IButton button : buttons) {
            button.mousePressed(e);
        }
    }

    /**
     * Handles mouse release events for credits buttons.
     *
     * @param e The MouseEvent object containing details about the mouse release.
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        for (IButton button : buttons) {
            button.mouseReleased(e);
        }
    }

    /**
     * Handles mouse movement events for credits buttons.
     *
     * @param e The MouseEvent object containing details about the mouse movement.
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        for (IButton button : buttons) {
            button.mouseMoved(e);
        }
    }

    /**
     * Handles key press events. Currently not implemented.
     *
     * @param e The KeyEvent object containing details about the key press.
     */
    @Override
    public void keyPressed(KeyEvent e) {}

    /**
     * Handles key release events. Currently not implemented.
     *
     * @param e The KeyEvent object containing details about the key release.
     */
    @Override
    public void keyReleased(KeyEvent e) {}

    /**
     * Creates the navigation buttons for the credits screen.
     */
    private void createButtons() {
        buttons = new ArrayList<>();

        buttons.add(smallButtonFactory.createButton(
                (int)(Game.WINDOW_WIDTH / 2 - 80 * Game.SCALE),
                (int)(Game.WINDOW_HEIGHT - 120 * Game.SCALE),
                (int)(14 * 1.8 * Game.SCALE),
                (int)(14 * 1.8 * Game.SCALE), 0, game
        ));
        buttons.get(0).setOnClickListener(() -> {
            GameScene.scene = GameScene.MENU;
        });
    }

    /**
     * Loads the images used in the credits UI.
     */
    private void loadImages(){
        try {
            background = BundleLoader.getSpriteAtlas(BundleLoader.CREDITS_BACKGROUND);
        } catch (BundleLoadException e) {
            Game.logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
