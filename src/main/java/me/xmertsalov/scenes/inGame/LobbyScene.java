package me.xmertsalov.scenes.inGame;

import me.xmertsalov.Game;
import me.xmertsalov.scenes.IScene;
import me.xmertsalov.scenes.Scene;
import me.xmertsalov.ui.lobby.LobbyManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class LobbyScene extends Scene implements IScene {

    private Game game;
    private LobbyManager lobbyManager;

    private boolean reseted = false;

    public LobbyScene(Game game) {
        super(game);
        this.game = game;
        this.lobbyManager = new LobbyManager(game);
    }

    @Override
    public void update() {
        resetAll();
        lobbyManager.update();
    }

    @Override
    public void draw(Graphics g) {
        lobbyManager.draw(g);
    }

    private void resetAll() {
        if (reseted) return;

        lobbyManager.reset();

        reseted = true;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        lobbyManager.mouseClicked(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        lobbyManager.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        lobbyManager.mouseReleased(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        lobbyManager.mouseMoved(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        lobbyManager.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        lobbyManager.keyReleased(e);
    }

    public void reset() {reseted = false;}
}
