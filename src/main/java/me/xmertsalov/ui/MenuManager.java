package me.xmertsalov.ui;

import me.xmertsalov.Game;
import me.xmertsalov.exceptions.BundleLoadException;
import me.xmertsalov.scenes.GameScene;
import me.xmertsalov.ui.buttons.BigButtonFactory;
import me.xmertsalov.ui.buttons.IButton;
import me.xmertsalov.utils.BundleLoader;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * The MenuManager class manages the main menu UI of the game.
 * It handles the display of the menu background, logo, and navigation buttons.
 */
public class MenuManager implements UIManager {

    // Dependencies
    private final BigButtonFactory bigButtonFactory;
    private Game game;

    // Storage
    private ArrayList<IButton> buttons;

    // Images
    private BufferedImage background;
    private BufferedImage logo;

    // Constants
    private final double btn_scale = 2;

    // UI Settings
    double backgroundWidth = (96 * Game.SCALE) * 2;
    double backgroundHeight =(128 * Game.SCALE) * 2;
    int backgroundY = (int)(120 * Game.SCALE);

    double logoWidth = (76 * Game.SCALE) * 2;
    double logoHeight = (25 * Game.SCALE) * 2;
    int logoY = (int)(60 * Game.SCALE);

    private int buttonsX = (int)((double) Game.WINDOW_WIDTH / 2 - (56 * btn_scale * Game.SCALE) / 2);

    /**
     * Constructs a MenuManager instance.
     *
     * @param game The Game instance to which this manager belongs.
     */
    public MenuManager(Game game) {
        this.game = game;
        bigButtonFactory = new BigButtonFactory();

        createButtons();

        loadImages();
    }

    /**
     * Updates the state of the menu UI.
     */
    public void update() {
        for (IButton button : buttons) {
            button.update();
        }
    }

    /**
     * Renders the menu UI components on the screen.
     *
     * @param g The Graphics object used for drawing.
     */
    public void draw(Graphics g) {
        g.drawImage(background, (int)((double) Game.WINDOW_WIDTH / 2 - backgroundWidth / 2), backgroundY, (int)backgroundWidth, (int)backgroundHeight, null);
        g.drawImage(logo, (int)((double) Game.WINDOW_WIDTH / 2 - logoWidth / 2), logoY , (int)logoWidth, (int)logoHeight, null);

        for (IButton button : buttons) {
            button.draw(g);
        }
    }

    /**
     * Loads the images used in the menu UI.
     */
    private void loadImages() {
        try {
            logo = BundleLoader.getSpriteAtlas(BundleLoader.LOGO);

            background = BundleLoader.getSpriteAtlas(BundleLoader.MENU_BACKGROUND);

        } catch (BundleLoadException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Handles mouse click events for menu buttons.
     *
     * @param e The MouseEvent object containing details about the mouse click.
     */
    public void mouseClicked(MouseEvent e) {
        for (IButton button : buttons) {
            button.mouseClicked(e);
        }
    }

    /**
     * Handles mouse press events for menu buttons.
     *
     * @param e The MouseEvent object containing details about the mouse press.
     */
    public void mousePressed(MouseEvent e) {
        for (IButton button : buttons) {
            button.mousePressed(e);
        }
    }

    /**
     * Handles mouse release events for menu buttons.
     *
     * @param e The MouseEvent object containing details about the mouse release.
     */
    public void mouseReleased(MouseEvent e) {
        for (IButton button : buttons) {
            button.mouseReleased(e);
        }
    }

    /**
     * Handles mouse movement events for menu buttons.
     *
     * @param e The MouseEvent object containing details about the mouse movement.
     */
    public void mouseMoved(MouseEvent e) {
        for (IButton button : buttons) {
            button.mouseMoved(e);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    /**
     * Creates the navigation buttons for the menu.
     */
    private void createButtons() {
        buttons = new ArrayList<>();

        // Play button
        buttons.add(bigButtonFactory.createButton(buttonsX, (int)(160 * Game.SCALE), (int) (56 * btn_scale * Game.SCALE), (int) (14 * btn_scale * Game.SCALE), 0, game));
        buttons.get(0).setOnClickListener(() -> GameScene.scene = GameScene.LOBBY);

        // Tutorial button
        buttons.add(bigButtonFactory.createButton(buttonsX, (int)(195 * Game.SCALE), (int) (56 * btn_scale * Game.SCALE), (int) (14 * btn_scale * Game.SCALE), 6, game));
        buttons.get(1).setOnClickListener(() -> GameScene.scene = GameScene.TUTORIAL);

        // Settings button
        buttons.add(bigButtonFactory.createButton(buttonsX, (int)(230 * Game.SCALE), (int) (56 * btn_scale * Game.SCALE), (int) (14 * btn_scale * Game.SCALE), 1, game));
        buttons.get(2).setOnClickListener(() -> GameScene.scene = GameScene.SETTINGS);

        // Credits button
        buttons.add(bigButtonFactory.createButton(buttonsX, (int)(265 * Game.SCALE), (int) (56 * btn_scale * Game.SCALE), (int) (14 * btn_scale * Game.SCALE), 2, game));
        buttons.get(3).setOnClickListener(() -> GameScene.scene = GameScene.CREDITS);

        // Exit button
        buttons.add(bigButtonFactory.createButton(buttonsX, (int)(300 * Game.SCALE), (int) (56 * btn_scale * Game.SCALE), (int) (14 * btn_scale * Game.SCALE), 3, game));
        buttons.get(4).setOnClickListener(() -> GameScene.scene = GameScene.EXIT);
    }

}
