package me.xmertsalov.ui;

import me.xmertsalov.Game;
import me.xmertsalov.scenes.GameScene;
import me.xmertsalov.ui.buttons.ArrowsButtonFactory;
import me.xmertsalov.ui.buttons.BigButtonFactory;
import me.xmertsalov.ui.buttons.IButton;
import me.xmertsalov.ui.buttons.SmallButtonFactory;
import me.xmertsalov.utils.BundleLoader;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class MenuManager implements UIManager {
    private ArrayList<IButton> buttons;

    private BufferedImage background;
    double backgroundWidth = (96 * Game.SCALE) * 2;
    double backgroundHeight =(128 * Game.SCALE) * 2;
    int backgroundY = (int)(120 * Game.SCALE);

    private BufferedImage logo;
    double logoWidth = (76 * Game.SCALE) * 2;
    double logoHeight = (25 * Game.SCALE) * 2;
    int logoY = (int)(60 * Game.SCALE);

    private final double btn_scale = 2;

    private int buttonsX = (int)(Game.WINDOW_WIDTH / 2 - (56 * btn_scale * Game.SCALE) / 2);


    private ArrowsButtonFactory arrowsButtonFactory;
    private SmallButtonFactory smallButtonFactory;
    private BigButtonFactory bigButtonFactory;

    public MenuManager() {
        arrowsButtonFactory = new ArrowsButtonFactory();
        smallButtonFactory = new SmallButtonFactory();
        bigButtonFactory = new BigButtonFactory();

        buttons = new ArrayList<>();
        buttons.add(bigButtonFactory.createButton(buttonsX, (int)(170 * Game.SCALE), (int) (56 * btn_scale * Game.SCALE), (int) (14 * btn_scale * Game.SCALE), 0));
        buttons.add(bigButtonFactory.createButton(buttonsX, (int)(205 * Game.SCALE), (int) (56 * btn_scale * Game.SCALE), (int) (14 * btn_scale * Game.SCALE), 1));
        buttons.add(bigButtonFactory.createButton(buttonsX, (int)(240 * Game.SCALE), (int) (56 * btn_scale * Game.SCALE), (int) (14 * btn_scale * Game.SCALE), 2));
        buttons.add(bigButtonFactory.createButton(buttonsX, (int)(275 * Game.SCALE), (int) (56 * btn_scale * Game.SCALE), (int) (14 * btn_scale * Game.SCALE), 3));

        buttons.get(0).setOnClickListener(() -> {
            GameScene.scene = GameScene.LOBBY;
        });
        buttons.get(1).setOnClickListener(() -> {
            GameScene.scene = GameScene.SETTINGS;
        });
        buttons.get(2).setOnClickListener(() -> {
            GameScene.scene = GameScene.CREDITS;
        });
        buttons.get(3).setOnClickListener(() -> {
            GameScene.scene = GameScene.EXIT;
        });

        loadBackground();
        loadLogo();
    }


    public void update() {
        for (IButton button : buttons) {
            button.update();
        }
    }

    public void draw(Graphics g) {
        g.drawImage(background, (int)(Game.WINDOW_WIDTH / 2 - backgroundWidth / 2), backgroundY, (int)backgroundWidth, (int)backgroundHeight, null);
        g.drawImage(logo, (int)(Game.WINDOW_WIDTH / 2 - logoWidth / 2), logoY , (int)logoWidth, (int)logoHeight, null);

        for (IButton button : buttons) {
            button.draw(g);
        }
    }

    private void loadLogo() {
        logo = BundleLoader.getSpriteAtlas(BundleLoader.LOGO);
    }

    private void loadBackground() {
        background = BundleLoader.getSpriteAtlas(BundleLoader.MENU_BACKGROUND);
    }

    public void mouseClicked(MouseEvent e) {
        for (IButton button : buttons) {
            button.mouseClicked(e);
        }
    }

    public void mousePressed(MouseEvent e) {
        for (IButton button : buttons) {
            button.mousePressed(e);
        }
    }

    public void mouseReleased(MouseEvent e) {
        for (IButton button : buttons) {
            button.mouseReleased(e);
        }
    }

    public void mouseMoved(MouseEvent e) {
        for (IButton button : buttons) {
            button.mouseMoved(e);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
