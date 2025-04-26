package me.xmertsalov.scenes.inGame;

import me.xmertsalov.Game;
import me.xmertsalov.scenes.GameScene;
import me.xmertsalov.scenes.IScene;
import me.xmertsalov.scenes.Scene;
import me.xmertsalov.ui.SettingsManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class SettingsScene extends Scene implements IScene {

    // UI
    private SettingsManager settingsManager;

    public SettingsScene(Game game) {
        super(game);
        settingsManager = new SettingsManager(this, game);
    }

    @Override
    public void update() {
        settingsManager.update();
    }

    @Override
    public void draw(Graphics g) {
        settingsManager.draw(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        settingsManager.mouseClicked(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        settingsManager.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        settingsManager.mouseReleased(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        settingsManager.mouseMoved(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        settingsManager.mouseDragged(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
