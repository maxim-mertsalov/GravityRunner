package me.xmertsalov.scenes.inGame;

import me.xmertsalov.Game;
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

    private boolean reseted = false;

    // States
    private boolean ghostMode = false;

    public PlayingScene(Game game) {
        super(game);

        start();
    }

    private void start() {
        this.players = new ArrayList<>();
        levelsManager = new LevelsManager(this);
        phisicsControler = new PhisicsControler(this);
    }

    @Override
    public void update() {
        startGame();
        for (Player player : players) {
            player.update();
        }
        levelsManager.update();
        phisicsControler.update();
    }

    @Override
    public void draw(Graphics g) {
        for (Player player : players) {
            player.render(g);
        }
        levelsManager.render(g);
        phisicsControler.render(g);
    }

    private void startGame(){
        if (reseted) return;

        for (Player player : game.getPlayers()) {
            if (!player.isInActive()) {
                player.setDisableGravity(false);
                player.setDisableControls(false);
                players.add(player);
            }
        }
        levelsManager.resetLevelManager();
        levelsManager.generateSpawnLevel(players.size());

        System.out.println(players.getFirst().getPosY());

        switch (players.size()){
            case 1 -> {
                players.get(0).setPosX(Game.TILES_SIZE * 12);
                players.get(0).setPosY(Game.TILES_SIZE * 6);
            }
            case 2 -> {
                players.get(0).setPosX(Game.TILES_SIZE * 12);
                players.get(0).setPosY(Game.TILES_SIZE * 6);

                players.get(1).setPosX(Game.TILES_SIZE * 12);
                players.get(1).setPosY(Game.TILES_SIZE * 10);
            }
            case 3 -> {
                players.get(0).setPosX(Game.TILES_SIZE * 12);
                players.get(0).setPosY(Game.TILES_SIZE * 3);

                players.get(1).setPosX(Game.TILES_SIZE * 12);
                players.get(1).setPosY(Game.TILES_SIZE * 7);

                players.get(2).setPosX(Game.TILES_SIZE * 12);
                players.get(2).setPosY(Game.TILES_SIZE * 10);
            }
            case 4 -> {
                players.get(0).setPosX(Game.TILES_SIZE * 12);
                players.get(0).setPosY(Game.TILES_SIZE * 2);

                players.get(1).setPosX(Game.TILES_SIZE * 12);
                players.get(1).setPosY(Game.TILES_SIZE * 5);

                players.get(2).setPosX(Game.TILES_SIZE * 12);
                players.get(2).setPosY(Game.TILES_SIZE * 8);

                players.get(3).setPosX(Game.TILES_SIZE * 12);
                players.get(3).setPosY(Game.TILES_SIZE * 10);
            }
        }

        reseted = true;
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
        return game.getPlayers();
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

    public void reset(){
        reseted = false;
    }
}
