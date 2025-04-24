package me.xmertsalov.scenes.inGame;

import me.xmertsalov.Game;
import me.xmertsalov.background.BackgroundManager;
import me.xmertsalov.components.PhisicsControler;
import me.xmertsalov.entities.Player;
import me.xmertsalov.world.LevelsManager;
import me.xmertsalov.scenes.IScene;
import me.xmertsalov.scenes.Scene;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class PlayingScene extends Scene implements IScene {

    // Game objects
    private ArrayList<Player> players;
    private LevelsManager levelsManager;
    private PhisicsControler phisicsControler;
    private BackgroundManager backgroundManager;

    // States
    private boolean ghostMode = false;

    public PlayingScene(Game game, ArrayList<Player> players) {
        super(game);

        this.players = players;

        start();
    }

    private void start() {
//        player = new Player( Game.TILES_SIZE * 12, Game.TILES_SIZE * 5, KeyEvent.VK_SPACE);

        levelsManager = new LevelsManager(this);
        phisicsControler = new PhisicsControler(this);
        backgroundManager = new BackgroundManager(this);
    }

    @Override
    public void update() {
        for (Player player : players) {
            player.update();
        }
        levelsManager.update();
        phisicsControler.update();
        backgroundManager.update();
    }

    @Override
    public void draw(Graphics g) {
        backgroundManager.draw(g);
        for (Player player : players) {
            player.render(g);
        }
        levelsManager.render(g);
        phisicsControler.render(g);
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

    }

    @Override
    public void keyReleased(KeyEvent e) {
        for (Player player : players) {
            if (e.getKeyCode() == player.getChangeGravityKey()){
                player.changeGravity();
            }
        }
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public LevelsManager getLevelsManager() {
        return levelsManager;
    }

    public boolean isGhostMode() {
        return ghostMode;
    }

    public void setGhostMode(boolean ghostMode) {
        this.ghostMode = ghostMode;
    }
}
