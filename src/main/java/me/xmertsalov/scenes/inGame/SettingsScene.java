package me.xmertsalov.scenes.inGame;

import me.xmertsalov.Game;
import me.xmertsalov.scenes.GameScene;
import me.xmertsalov.scenes.IScene;
import me.xmertsalov.scenes.Scene;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class SettingsScene extends Scene implements IScene {


    public SettingsScene(Game game) {
        super(game);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics g) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        GameScene.scene = GameScene.MENU;
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
