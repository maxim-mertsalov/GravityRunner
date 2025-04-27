package me.xmertsalov.ui;

import me.xmertsalov.Game;
import me.xmertsalov.exceptions.BundleLoadException;
import me.xmertsalov.scenes.GameScene;
import me.xmertsalov.scenes.inGame.SettingsScene;
import me.xmertsalov.ui.buttons.*;
import me.xmertsalov.utils.BundleLoader;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * The {@code SettingsManager} class is responsible for managing the settings UI in the game.
 * It handles the creation and interaction of buttons, sliders, and other UI elements
 * for adjusting game settings such as resolution, music volume, and sound effects volume.
 */
public class SettingsManager implements UIManager {
    // General
    private SettingsScene settingsScene;
    private Game game;

    // Dependencies
    private BigButtonFactory bigButtonFactory;
    private SliderButtonFactory sliderButtonFactory;
    private ArrowsButtonFactory arrowsButtonFactory;
    private SmallButtonFactory smallButtonFactory;

    // Storage
    private ArrayList<IButton> buttons;
    private ArrayList<String> resolutions;

    // States
    private int currentResolutionIndex;
    private int musicVolume;
    private int sfxVolume;

    // Images
    private BufferedImage background;

    // UI Settings
    private int backX = (int)((float) Game.WINDOW_WIDTH / 2 - (96 * 2 * Game.SCALE) / 2);
    private int backY = (int)((float) Game.WINDOW_HEIGHT / 2 - (160 * 2 * Game.SCALE) / 2);

    /**
     * Constructs a {@code SettingsManager} instance.
     *
     * @param settingsScene the settings scene this manager belongs to
     * @param game          the game instance
     */
    public SettingsManager(SettingsScene settingsScene, Game game) {
        this.settingsScene = settingsScene;
        this.game = game;

        bigButtonFactory = new BigButtonFactory();
        sliderButtonFactory = new SliderButtonFactory();
        arrowsButtonFactory = new ArrowsButtonFactory();
        smallButtonFactory = new SmallButtonFactory();

        // Load resolutions
        resolutions = game.getConfig().getResolutions();
        currentResolutionIndex = game.getConfig().getResolutionIndex();

        // Load sound settings
        sfxVolume = game.getConfig().getSfxVolume();
        musicVolume = game.getConfig().getMusicVolume();

        // Load images
        loadImages();

        // Create buttons
        createButtons();
    }

    /**
     * Updates the state of the settings manager.
     * Currently, this method does not perform any operations.
     */
    @Override
    public void update() {
        // ...existing code...
    }

    /**
     * Draws the settings UI on the screen.
     *
     * @param g the {@code Graphics} object used for rendering
     */
    @Override
    public void draw(Graphics g) {
        g.drawImage(background, backX, backY, (int)(96 * 2 * Game.SCALE), (int)(160 * 2 * Game.SCALE), null);

        g.setColor(new Color(51, 50, 61));
        g.setFont(new Font("Arial", Font.BOLD, (int)(10 * Game.SCALE)));
        g.drawString(resolutions.get(currentResolutionIndex), (int)(backX + 54 * Game.SCALE), (int)(backY + 73 * Game.SCALE) + 10);

        for (IButton button : buttons) {
            button.draw(g);
        }
    }

    /**
     * Handles mouse click events and delegates them to the buttons.
     *
     * @param e the {@code MouseEvent} object containing event details
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        for (IButton button : buttons) {
            button.mouseClicked(e);
        }
    }

    /**
     * Handles mouse press events and delegates them to the buttons.
     *
     * @param e the {@code MouseEvent} object containing event details
     */
    @Override
    public void mousePressed(MouseEvent e) {
        for (IButton button : buttons) {
            button.mousePressed(e);
        }
    }

    /**
     * Handles mouse release events and delegates them to the buttons.
     *
     * @param e the {@code MouseEvent} object containing event details
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        for (IButton button : buttons) {
            button.mouseReleased(e);
        }
    }

    /**
     * Handles mouse movement events and delegates them to the buttons.
     *
     * @param e the {@code MouseEvent} object containing event details
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        for (IButton button : buttons) {
            button.mouseMoved(e);
        }
    }

    /**
     * Handles mouse drag events and delegates them to the buttons.
     *
     * @param e the {@code MouseEvent} object containing event details
     */
    public void mouseDragged(MouseEvent e) {
        for (IButton button : buttons) {
            button.mouseDragged(e);
        }
    }

    /**
     * Handles key press events. Currently, this method does not perform any operations.
     *
     * @param e the {@code KeyEvent} object containing event details
     */
    @Override
    public void keyPressed(KeyEvent e) {}

    /**
     * Handles key release events. Currently, this method does not perform any operations.
     *
     * @param e the {@code KeyEvent} object containing event details
     */
    @Override
    public void keyReleased(KeyEvent e) {
        // ...existing code...
    }

    /**
     * Loads the background image for the settings UI.
     * If the image fails to load, a {@code RuntimeException} is thrown.
     */
    private void loadImages() {
        try {
            background = BundleLoader.getSpriteAtlas(BundleLoader.SETTINGS_BACKGROUND);
        } catch (BundleLoadException e) {
            Game.logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates and initializes the buttons for the settings UI.
     * This includes buttons for changing resolution, adjusting volume, and navigating menus.
     */
    private void createButtons() {
        buttons = new ArrayList<>();

        // Arrow left button for cycling resolutions
        buttons.add(arrowsButtonFactory.createButton(
                (int)(backX + 24 * Game.SCALE),
                (int)(backY + 64 * Game.SCALE),
                (int)(24 * Game.SCALE),
                (int)(24 * Game.SCALE),
                0, game
        ));
        buttons.get(0).setOnClickListener(() -> {
            currentResolutionIndex++;
            if (currentResolutionIndex >= resolutions.size()) {
                currentResolutionIndex = 0;
            }
        });

        // Arrow right button for cycling resolutions
        buttons.add(arrowsButtonFactory.createButton(
                (int)(backX + (96 * 2 * Game.SCALE) - 40 * Game.SCALE),
                (int)(backY + 64 * Game.SCALE),
                (int)(24 * Game.SCALE),
                (int)(24 * Game.SCALE),
                1, game
        ));
        buttons.get(1).setOnClickListener(() -> {
            currentResolutionIndex--;
            if (currentResolutionIndex < 0) {
                currentResolutionIndex = resolutions.size() - 1;
            }
        });

        // Music volume slider
        buttons.add(sliderButtonFactory.createButton(
                (int)(backX + 24 * Game.SCALE),
                (int)(backY + 136 * Game.SCALE),
                (int)((96 * 2 * Game.SCALE) - 24 * 2 * Game.SCALE),
                (int)(11 * 1.35 * Game.SCALE),
                0, game
        ));
        buttons.get(2).setData(musicVolume);
        buttons.get(2).setOnClickListener(() -> {
            musicVolume = buttons.get(2).getData();
            game.getAudioPlayer().setMusicVolume((float) musicVolume / 100);
        });

        // SFX volume slider
        buttons.add(sliderButtonFactory.createButton(
                (int)(backX + 24 * Game.SCALE),
                (int)(backY + 200 * Game.SCALE),
                (int)((96 * 2 * Game.SCALE) - 24 * 2 * Game.SCALE),
                (int)(11 * 1.35 * Game.SCALE),
                0, game
        ));
        buttons.get(3).setData(sfxVolume);
        buttons.get(3).setOnClickListener(() -> {
            sfxVolume = buttons.get(3).getData();
            game.getAudioPlayer().setSfxVolume((float) sfxVolume / 100);
        });

        // Menu button for returning to the main menu
        buttons.add(smallButtonFactory.createButton(
                (int)(backX + 24 * Game.SCALE),
                (int)(backY + 250 * Game.SCALE),
                (int)(14 * 2 * Game.SCALE),
                (int)(14 * 2 * Game.SCALE),
                0, game
        ));
        buttons.get(4).setOnClickListener(() -> {
            currentResolutionIndex = game.getConfig().getResolutionIndex();
            GameScene.scene = GameScene.MENU;
        });

        // Apply button for saving settings
        buttons.add(bigButtonFactory.createButton(
                (int)(backX + (Game.SCALE * 3) + (14 * 2 * Game.SCALE) + 24 * Game.SCALE),
                (int)(backY + 250 * Game.SCALE),
                (int)(56 * 2 * Game.SCALE),
                (int)(14 * 2 * Game.SCALE),
                8, game
        ));
        buttons.get(5).setOnClickListener(this::applySettings);
    }

    /**
     * Applies the current settings by updating the game's configuration and saving it to disk.
     * The application is then restarted to apply the changes.
     */
    private void applySettings() {
        game.getConfig().setResolution(resolutions.get(currentResolutionIndex));
        game.getConfig().setSoundVolume(musicVolume, sfxVolume);
        game.getConfig().saveConfig();
        System.exit(1);
    }
}
