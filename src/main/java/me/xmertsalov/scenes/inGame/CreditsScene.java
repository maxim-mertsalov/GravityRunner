package me.xmertsalov.scenes.inGame;

import me.xmertsalov.Game;
import me.xmertsalov.scenes.IScene;
import me.xmertsalov.scenes.Scene;
import me.xmertsalov.ui.CreditsManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class CreditsScene extends Scene implements IScene {
    // UI
     private CreditsManager creditsManager;


    public CreditsScene(Game game) {
        super(game);
        creditsManager = new CreditsManager(game);
    }

    @Override
    public void update() {
        creditsManager.update();
    }

    @Override
    public void draw(Graphics g) {
        creditsManager.draw(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        creditsManager.mouseClicked(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        creditsManager.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        creditsManager.mouseReleased(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        creditsManager.mouseMoved(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
