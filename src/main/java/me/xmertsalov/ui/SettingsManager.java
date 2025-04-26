package me.xmertsalov.ui;

import me.xmertsalov.Game;
import me.xmertsalov.config.Config;
import me.xmertsalov.scenes.GameScene;
import me.xmertsalov.scenes.inGame.SettingsScene;
import me.xmertsalov.ui.buttons.*;
import me.xmertsalov.utils.BundleLoader;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class SettingsManager implements UIManager{
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
    private int currentResolutionIndex = 0;
    private int musicVolume = 0;
    private int sfxVolume = 0;

    // Images
    private BufferedImage background;

    // UI Settings
    private int backX = (int)(Game.WINDOW_WIDTH / 2 - (96 * 2 * Game.SCALE) / 2);
    private int backY = (int)(Game.WINDOW_HEIGHT / 2 - (160 * 2 * Game.SCALE) / 2);


    public SettingsManager(SettingsScene settingsScene, Game game) {
        this.settingsScene = settingsScene;
        this.game = game;

        bigButtonFactory = new BigButtonFactory();
        sliderButtonFactory = new SliderButtonFactory();
        arrowsButtonFactory = new ArrowsButtonFactory();
        smallButtonFactory = new SmallButtonFactory();

        // Load resolutions
        resolutions = Config.getResolutions();
        currentResolutionIndex = Config.getResolutionIndex();

        // load sounds
        sfxVolume = Config.getSfxVolume();
        musicVolume = Config.getMusicVolume();

        // Load images
        loadImages();

        // Create buttons
        createButtons();
    }

    @Override
    public void update() {

    }

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

    @Override
    public void mouseClicked(MouseEvent e) {
        for (IButton button : buttons) {
            button.mouseClicked(e);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (IButton button : buttons) {
            button.mousePressed(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (IButton button : buttons) {
            button.mouseReleased(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for (IButton button : buttons) {
            button.mouseMoved(e);
        }
    }

    public void mouseDragged(MouseEvent e) {
        for (IButton button : buttons) {
            button.mouseDragged(e);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    private void loadImages() {
        background = BundleLoader.getSpriteAtlas(BundleLoader.SETTINGS_BACKGROUND);
    }

    private void createButtons() {
        buttons = new ArrayList<>();

        // Arrow left
        buttons.add(arrowsButtonFactory.createButton(
                (int)(backX + 24 * Game.SCALE),
                (int)(backY + 64 * Game.SCALE),
                (int)(24 * Game.SCALE),
                (int)(24 * Game.SCALE),
                0
        ));
        buttons.get(0).setOnClickListener(() -> {
            currentResolutionIndex++;
            if (currentResolutionIndex >= resolutions.size()) {
                currentResolutionIndex = 0;
            }
        });

        // Arrow right
        buttons.add(arrowsButtonFactory.createButton(
                (int)(backX + (96 * 2 * Game.SCALE) - 40 * Game.SCALE),
                (int)(backY + 64 * Game.SCALE),
                (int)(24 * Game.SCALE),
                (int)(24 * Game.SCALE),
                1
        ));
        buttons.get(1).setOnClickListener(() -> {
            currentResolutionIndex--;
            if (currentResolutionIndex < 0) {
                currentResolutionIndex = resolutions.size() - 1;
            }
        });


        // Music slider
        buttons.add(sliderButtonFactory.createButton(
                (int)(backX + 24 * Game.SCALE),
                (int)(backY + 136 * Game.SCALE),
                (int)((96 * 2 * Game.SCALE) - 24 * 2 * Game.SCALE),
                (int)(11 * 1.35 * Game.SCALE),
                0
        ));
        buttons.get(2).setData(musicVolume);
        buttons.get(2).setOnClickListener(() -> {
            musicVolume = buttons.get(2).getData();
        });

        // SFX slider
        buttons.add(sliderButtonFactory.createButton(
                (int)(backX + 24 * Game.SCALE),
                (int)(backY + 200 * Game.SCALE),
                (int)((96 * 2 * Game.SCALE) - 24 * 2 * Game.SCALE),
                (int)(11 * 1.35 * Game.SCALE),
                0
        ));
        buttons.get(3).setData(sfxVolume);
        buttons.get(3).setOnClickListener(() -> {
            sfxVolume = buttons.get(3).getData();
        });

        // Menu button
        buttons.add(smallButtonFactory.createButton(
                (int)(backX + 24 * Game.SCALE),
                (int)(backY + 250 * Game.SCALE),
                (int)(14 * 2 * Game.SCALE),
                (int)(14 * 2 * Game.SCALE),
                0
        ));
        buttons.get(4).setOnClickListener(() -> {
            currentResolutionIndex = Config.getResolutionIndex();
            GameScene.scene = GameScene.MENU;
        });


        // Apply button
        buttons.add(bigButtonFactory.createButton(
                (int)(backX + (Game.SCALE * 3) + (14 * 2 * Game.SCALE) + 24 * Game.SCALE),
                (int)(backY + 250 * Game.SCALE),
                (int)(56 * 2 * Game.SCALE),
                (int)(14 * 2 * Game.SCALE),
                8
        ));
        buttons.get(5).setOnClickListener(this::applySettings);


    }

    private void applySettings() {
        game.getConfig().setResolution(resolutions.get(currentResolutionIndex));
        game.getConfig().setMusicVolume(musicVolume);
        game.getConfig().setSfxVolume(sfxVolume);
        game.getConfig().applySettings();
    }
}
