package me.xmertsalov.scenes.inGame;

import me.xmertsalov.Game;
import me.xmertsalov.audio.AudioPlayer;
import me.xmertsalov.components.phisics.PhysicsController;
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

/**
 * The PlayingScene class represents the main gameplay scene in the GravityRunner game.
 * It manages the players, levels, physics, UI, and game modes during gameplay.
 * This class extends the Scene class and implements the IScene interface.
 */
public class PlayingScene extends Scene implements IScene {

    // Game objects
    private ArrayList<Player> players;
    private LevelsManager levelsManager;
    private PhysicsController phisicsControler;

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
    private double reservedSpeed;

    // States
    private boolean canMove = false;
    private boolean continuedSpeed = false;

    /**
     * Constructs a new PlayingScene instance.
     * Initializes the game, UI manager, and starts the scene.
     *
     * @param game The Game instance to which this scene belongs.
     */
    public PlayingScene(Game game) {
        super(game);
        start();
        uiManager = new PlayingUIManager(this);
    }

    /**
     * Initializes the players, levels manager, and physics controller.
     * This method is called during the construction of the PlayingScene.
     */
    private void start() {
        this.players = new ArrayList<>();
        levelsManager = new LevelsManager(this);
        phisicsControler = new PhysicsController(this);
    }

    /**
     * Updates the state of the scene, including players, levels, physics, and UI.
     * Handles game speed adjustments and player controls based on the current game state.
     */
    @Override
    public void update() {
        startGame();
        for (Player player : players) {
            player.update();
        }
        // Count Down started
        if (!canMove) {
            reservedSpeed = levelsManager.getSpeed();
            levelsManager.setSpeed(0);

            players.forEach(player -> {
                player.setDisableControls(true);
                if (player.getPhisicsComponent().getGravityDirection() < 0){
                    player.getPhisicsComponent().setGravityDirection(1);
                    player.setFlipH(1);
                    player.setFlipY(0);
                }
            });

        }
        // Count Down finished
        else if (continuedSpeed) {
            levelsManager.setSpeed(reservedSpeed);

            players.forEach(player -> {
                player.setDisableControls(false);
            });

            if (isSlowMode()) levelsManager.setSpeed(0.4f * Game.SCALE);
            else if (isIncreasedGameSpeedMode()) levelsManager.setSpeed(0.7f * Game.SCALE);
            else levelsManager.setSpeed(0.8f * Game.SCALE);

            players.forEach(player -> player.setDisableControls(false));

            continuedSpeed = false;
        }

        levelsManager.update();
        phisicsControler.update();
        uiManager.update();
    }

    /**
     * Renders the scene, including players, levels, and UI components.
     *
     * @param g The Graphics object used for rendering.
     */
    @Override
    public void draw(Graphics g) {
        levelsManager.render(g, -1); // background layer

        for (Player player : players) {
            player.render(g);
        }

        levelsManager.render(g, 0); // tile layer (interaction layer)

        levelsManager.render(g, 1); // foreground layer

        uiManager.draw(g);
    }

    /**
     * Starts the game by resetting players, levels, and game modes.
     * Initializes player positions and starts the countdown.
     */
    private void startGame() {
        if (reseted) return;

        canMove = false;
        continuedSpeed = false;

        players.clear();

        for (Player player : game.getPlayers()) {
            if (!player.isInActive()) {
                player.setDisableGravity(false);
                player.setDead(false, 0);
                players.add(player);
                System.out.println("XX:" + player.getPosX() + ", " + player.getPosY());
            }
            else{
                player.setPosX(-1000);
                player.setPosY(1000);
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
            case 1: {
                players.get(0).setPosX(Game.TILES_SIZE * 11);
                players.get(0).setPosY(Game.TILES_SIZE * 5);
                break;
            }
            case 2: {
                players.get(0).setPosX(Game.TILES_SIZE * 11);
                players.get(0).setPosY(Game.TILES_SIZE * 5);

                players.get(1).setPosX(Game.TILES_SIZE * 11);
                players.get(1).setPosY(Game.TILES_SIZE * 10);
                break;
            }
            case 3: {
                players.get(0).setPosX(Game.TILES_SIZE * 11);
                players.get(0).setPosY(Game.TILES_SIZE * 2);

                players.get(1).setPosX(Game.TILES_SIZE * 11);
                players.get(1).setPosY(Game.TILES_SIZE * 7);

                players.get(2).setPosX(Game.TILES_SIZE * 11);
                players.get(2).setPosY(Game.TILES_SIZE * 10);
                break;
            }
            case 4: {
                players.get(0).setPosX(Game.TILES_SIZE * 11);
                players.get(0).setPosY(Game.TILES_SIZE * 2);

                players.get(1).setPosX(Game.TILES_SIZE * 11);
                players.get(1).setPosY(Game.TILES_SIZE * 5);

                players.get(2).setPosX(Game.TILES_SIZE * 11);
                players.get(2).setPosY(Game.TILES_SIZE * 8);

                players.get(3).setPosX(Game.TILES_SIZE * 11);
                players.get(3).setPosY(Game.TILES_SIZE * 10);
                break;
            }
            default: break;
        }

        uiManager.startCountDown();
        game.getAudioPlayer().playNextMusic();

        game.getScore().setStarted(true);

        reseted = true;
    }

    /**
     * Handles mouse click events and delegates them to the UI manager.
     *
     * @param e The MouseEvent object containing details about the mouse click.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        uiManager.mouseClicked(e);
    }

    /**
     * Handles mouse press events and delegates them to the UI manager.
     *
     * @param e The MouseEvent object containing details about the mouse press.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        uiManager.mousePressed(e);
    }

    /**
     * Handles mouse release events and delegates them to the UI manager.
     *
     * @param e The MouseEvent object containing details about the mouse release.
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        uiManager.mouseReleased(e);
    }

    /**
     * Handles mouse movement events and delegates them to the UI manager.
     *
     * @param e The MouseEvent object containing details about the mouse movement.
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        uiManager.mouseMoved(e);
    }

    /**
     * Handles mouse drag events. Currently, this method is empty.
     *
     * @param e The MouseEvent object containing details about the mouse drag.
     */
    @Override
    public void mouseDragged(MouseEvent e) {}

    /**
     * Handles key press events. Currently, this method is empty.
     *
     * @param e The KeyEvent object containing details about the key press.
     */
    @Override
    public void keyPressed(KeyEvent e) {}

    /**
     * Handles key release events. Changes gravity for players when their gravity key is released.
     *
     * @param e The KeyEvent object containing details about the key release.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        for (Player player : players) {
            if (e.getKeyCode() == player.getChangeGravityKey()) {
                game.getAudioPlayer().setSfxCurrentVolume(AudioPlayer.SFX_JUMP, 0.6f);
                game.getAudioPlayer().playSfx(AudioPlayer.SFX_JUMP);
                player.changeGravity();
            }
        }
    }

    // Getters and setters for various properties of the PlayingScene.
    public ArrayList<Player> getPlayers() { return game.getPlayers(); }
    public LevelsManager getLevelsManager() { return levelsManager; }
    public void reset() { reseted = false; }
    public boolean isGhostMode() { return ghostMode; }
    public void setGhostMode(boolean ghostMode) { this.ghostMode = ghostMode; }
    public boolean isIncreasedGameSpeedMode() { return increasedGameSpeedMode; }
    public void setIncreasedGameSpeedMode(boolean increasedGameSpeedMode) { this.increasedGameSpeedMode = increasedGameSpeedMode; }
    public boolean isGodMode() { return godMode; }
    public void setGodMode(boolean godMode) { this.godMode = godMode; }
    public boolean isSlowMode() { return slowMode; }
    public void setSlowMode(boolean slowMode) { this.slowMode = slowMode; }
    public boolean isBorderlessMode() { return borderlessMode; }
    public void setBorderlessMode(boolean borderlessMode) { this.borderlessMode = borderlessMode; }
    public boolean isViewerMode() { return viewerMode; }
    public void setViewerMode(boolean viewerMode) { this.viewerMode = viewerMode; }
    public Score getScore() { return game.getScore(); }
    public int getNumPlayers() { return numPlayers; }
    public Game getGame() { return game; }
    public boolean isReseted() { return reseted; }
    public void setCanMove(boolean canMove) { this.canMove = canMove; }
    public void setContinuedSpeed(boolean continuedSpeed) { this.continuedSpeed = continuedSpeed; }
}
