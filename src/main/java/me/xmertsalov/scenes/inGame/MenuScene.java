package me.xmertsalov.scenes.inGame;

import me.xmertsalov.Game;
import me.xmertsalov.scenes.GameScene;
import me.xmertsalov.scenes.IScene;
import me.xmertsalov.scenes.Scene;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class MenuScene extends Scene implements IScene {

    public MenuScene(Game game) {
        super(game);
    }

    private void start() {

    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.black);
        g.drawString("MENU", Game.WINDOW_WIDTH / 2, 200);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

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
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            GameScene.scene = GameScene.PLAYING;
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
