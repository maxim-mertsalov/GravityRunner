package me.xmertsalov.scenes.inGame;

import me.xmertsalov.Game;
import me.xmertsalov.background.BackgroundManager;
import me.xmertsalov.scenes.GameScene;
import me.xmertsalov.scenes.IScene;
import me.xmertsalov.scenes.Scene;
import me.xmertsalov.ui.MenuManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class MenuScene extends Scene implements IScene {

    // Game objects
    private MenuManager menuManager;


    public MenuScene(Game game) {
        super(game);
        start();
    }

    private void start() {
//        backgroundManager = new BackgroundManager();
        menuManager = new MenuManager();
    }

    @Override
    public void update() {
//        backgroundManager.update();
        menuManager.update();
    }

    @Override
    public void draw(Graphics g) {
//        backgroundManager.draw(g);
        menuManager.draw(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        menuManager.mouseClicked(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        menuManager.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        menuManager.mouseReleased(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        menuManager.mouseMoved(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            GameScene.scene = GameScene.PLAYING;
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
