package me.xmertsalov.scenes.inGame;

import me.xmertsalov.Game;
import me.xmertsalov.scenes.IScene;
import me.xmertsalov.scenes.Scene;
import me.xmertsalov.ui.TutorialManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class TutorialScene extends Scene implements IScene {

    // UI
    TutorialManager tutorialManager;

    public TutorialScene(Game game) {
        super(game);
        tutorialManager = new TutorialManager(game);
    }

    @Override
    public void update() {
        tutorialManager.update();
    }

    @Override
    public void draw(Graphics g) {
        tutorialManager.draw(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        tutorialManager.mouseClicked(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        tutorialManager.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        tutorialManager.mouseReleased(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        tutorialManager.mouseMoved(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
