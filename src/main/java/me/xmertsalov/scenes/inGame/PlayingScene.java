package me.xmertsalov.scenes.inGame;

import me.xmertsalov.Game;
import me.xmertsalov.components.PhisicsControler;
import me.xmertsalov.entities.Player;
import me.xmertsalov.score.Score;
import me.xmertsalov.ui.PlayingUIManager;
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

    // Modes
    private boolean increasedGameSpeedMode = false;
    private boolean ghostMode = false;
    private boolean godMode = false;
    private boolean slowMode = false;
    private boolean borderlessMode = false;
    private boolean viewerMode = false;

    // UI
    private PlayingUIManager uiManager;

    // Nums
    private int numPlayers = 0;


    public PlayingScene(Game game) {
        super(game);

        start();
        uiManager = new PlayingUIManager(this);
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
        uiManager.update();
    }

    @Override
    public void draw(Graphics g) {
        for (Player player : players) {
            player.render(g);
        }
        levelsManager.render(g);
        uiManager.draw(g);
    }

    private void startGame(){
        if (reseted) return;

        players.clear();

        for (Player player : game.getPlayers()) {
            if (!player.isInActive()) {
                player.setDisableGravity(false);
                player.setDisableControls(false);
                player.setDead(false, 0);
                players.add(player);
                System.out.println("XX:" + player.getPosX() + ", " + player.getPosY());
            }
        }

        increasedGameSpeedMode = game.isIncreasedGameSpeedMode();
        ghostMode = game.isGhostMode();
        godMode = game.isGodMode();
        slowMode = game.isSlowMode();
        borderlessMode = game.isBorderlessMode();
        viewerMode = game.isViewerMode();

        levelsManager.resetLevelManager();
        levelsManager.generateSpawnLevel(players.size());

        System.out.println("Game started for " + players.size() + " players");
        numPlayers = players.size();

        game.getScore().resetPlayers(players);

        switch (players.size()){
            case 1 -> {
                players.get(0).setPosX(Game.TILES_SIZE * 11);
                players.get(0).setPosY(Game.TILES_SIZE * 5);
            }
            case 2 -> {
                players.get(0).setPosX(Game.TILES_SIZE * 11);
                players.get(0).setPosY(Game.TILES_SIZE * 5);

                players.get(1).setPosX(Game.TILES_SIZE * 11);
                players.get(1).setPosY(Game.TILES_SIZE * 10);
            }
            case 3 -> {
                players.get(0).setPosX(Game.TILES_SIZE * 11);
                players.get(0).setPosY(Game.TILES_SIZE * 2);

                players.get(1).setPosX(Game.TILES_SIZE * 11);
                players.get(1).setPosY(Game.TILES_SIZE * 7);

                players.get(2).setPosX(Game.TILES_SIZE * 11);
                players.get(2).setPosY(Game.TILES_SIZE * 10);
            }
            case 4 -> {
                players.get(0).setPosX(Game.TILES_SIZE * 11);
                players.get(0).setPosY(Game.TILES_SIZE * 2);

                players.get(1).setPosX(Game.TILES_SIZE * 11);
                players.get(1).setPosY(Game.TILES_SIZE * 5);

                players.get(2).setPosX(Game.TILES_SIZE * 11);
                players.get(2).setPosY(Game.TILES_SIZE * 8);

                players.get(3).setPosX(Game.TILES_SIZE * 11);
                players.get(3).setPosY(Game.TILES_SIZE * 10);
            }
        }

        game.getScore().setStarted(true);

        reseted = true;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        uiManager.mouseClicked(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        uiManager.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        uiManager.mouseReleased(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        uiManager.mouseMoved(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {
        for (Player player : players) {
            if (e.getKeyCode() == player.getChangeGravityKey()){
                player.changeGravity();
            }
        }
    }

    public ArrayList<Player> getPlayers() {return game.getPlayers();}

    public LevelsManager getLevelsManager() {return levelsManager;}

    public void reset(){reseted = false;}

    public boolean isGhostMode() {return ghostMode;}
    public void setGhostMode(boolean ghostMode) {this.ghostMode = ghostMode;}

    public boolean isIncreasedGameSpeedMode() {return increasedGameSpeedMode;}
    public void setIncreasedGameSpeedMode(boolean increasedGameSpeedMode) {this.increasedGameSpeedMode = increasedGameSpeedMode;}

    public boolean isGodMode() {return godMode;}
    public void setGodMode(boolean godMode) {this.godMode = godMode;}

    public boolean isSlowMode() {return slowMode;}
    public void setSlowMode(boolean slowMode) {this.slowMode = slowMode;}

    public boolean isBorderlessMode() {return borderlessMode;}
    public void setBorderlessMode(boolean borderlessMode) {this.borderlessMode = borderlessMode;}

    public boolean isViewerMode() {return viewerMode;}
    public void setViewerMode(boolean viewerMode) {this.viewerMode = viewerMode;}

    public Score getScore() {return game.getScore();}

    public int getNumPlayers() {return numPlayers;}

    public Game getGame() {return game;}
}
