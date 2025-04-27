package me.xmertsalov.scenes.inGame;

import me.xmertsalov.Game;
import me.xmertsalov.exeptions.BundleLoadException;
import me.xmertsalov.scenes.GameScene;
import me.xmertsalov.scenes.IScene;
import me.xmertsalov.scenes.Scene;
import me.xmertsalov.utils.BundleLoader;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class LoadingScene extends Scene implements IScene {

    // Images
    private BufferedImage background;

    // States
    private final int timeToWait = 3;
    private int currentTimeToWait = 0;

    // Other
    private int ticksToWait = 0;

    // UI Settings
    private int initLogoWidth = 76;
    private int initLogoHeight = 25;

    private int logoWidth = (int)(initLogoWidth * Game.SCALE * 3);
    private int logoHeight = (int)(initLogoHeight * Game.SCALE * 3);

    public LoadingScene(Game game) {
        super(game);
        try {
            background = BundleLoader.getSpriteAtlas(BundleLoader.LOGO);
        } catch (BundleLoadException e) {
            Game.logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update() {
        ticksToWait++;
        if (ticksToWait >= Game.UPS_LIMIT) {
            ticksToWait = 0;
            currentTimeToWait++;
        }
        if (game.getMenuScene() != null && currentTimeToWait >= timeToWait) {
            GameScene.scene = GameScene.MENU;
        }
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(background, (int)(Game.WINDOW_WIDTH / 2 - logoWidth / 2), (int)(Game.WINDOW_HEIGHT / 2 - logoHeight / 2 - 25 * Game.SCALE), logoWidth, logoHeight, null);
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
