package me.xmertsalov.ui;

import me.xmertsalov.Game;
import me.xmertsalov.exeptions.BundleLoadException;
import me.xmertsalov.scenes.GameScene;
import me.xmertsalov.ui.buttons.IButton;
import me.xmertsalov.ui.buttons.SmallButtonFactory;
import me.xmertsalov.utils.BundleLoader;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class CreditsManager implements UIManager {
    // Dependencies
    private SmallButtonFactory smallButtonFactory;
    private Game game;

    // Storage
    private ArrayList<IButton> buttons;

    // Images
    private BufferedImage background;


    public CreditsManager(Game game) {
        this.game = game;
        loadImages();
        smallButtonFactory = new SmallButtonFactory();

        createButtons();
    }

    @Override
    public void update() {
        for (IButton button : buttons) {
            button.update();
        }
    }

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

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

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

    private void loadImages(){
        try {
            background = BundleLoader.getSpriteAtlas(BundleLoader.CREDITS_BACKGROUND);
        } catch (BundleLoadException e) {
            Game.logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
