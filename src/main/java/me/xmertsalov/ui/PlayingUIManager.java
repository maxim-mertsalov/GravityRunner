package me.xmertsalov.ui;

import me.xmertsalov.Game;
import me.xmertsalov.exceptions.BundleLoadException;
import me.xmertsalov.scenes.inGame.PlayingScene;
import me.xmertsalov.utils.BundleLoader;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * The PlayingUIManager class manages the UI during gameplay.
 * It handles the display of player scores, countdowns, and game-over panels.
 */
public class PlayingUIManager implements UIManager {
    // General
    private PlayingScene playingScene;
    private int countPlayers;

    // Images
    BufferedImage[] textPlayers;

    // UI Settings
    private int xGap = (int)(3 * Game.SCALE);
    private int yGap = (int)(12 * Game.SCALE);
    private int xMargin = (int)(18 * Game.SCALE);
    private int yMargin = (int)(18 * Game.SCALE);

    // Panels
    GameOverPanel gameOverPanel;
    CountDownPanel countDownPanel;

    // States
    private boolean alldead;

    /**
     * Constructs a PlayingUIManager instance.
     *
     * @param playingScene The PlayingScene instance to which this manager belongs.
     */
    public PlayingUIManager(PlayingScene playingScene) {
        this.playingScene = playingScene;

        gameOverPanel = new GameOverPanel(playingScene);
        countDownPanel = new CountDownPanel(playingScene);

        loadImages();
        countPlayers = playingScene.getNumPlayers();
    }

    /**
     * Updates the state of the gameplay UI.
     */
    @Override
    public void update() {
        if (playingScene.getNumPlayers() != countPlayers) {
            countPlayers = playingScene.getNumPlayers();
        }

        alldead = playingScene.getPlayers().stream().filter(player -> !player.isDead() && !player.isInActive()).count() == 0;

        if (alldead) {
            gameOverPanel.show();
        }

        gameOverPanel.update();
        countDownPanel.update();
    }

    /**
     * Renders the gameplay UI components on the screen.
     *
     * @param g The Graphics object used for drawing.
     */
    @Override
    public void draw(Graphics g) {
        for (int i = 0; i < countPlayers; i++) {
            if (!alldead) {
                g.drawImage(textPlayers[i], xMargin, yMargin + (i * (6 + yGap)), (int)(52 * Game.SCALE) , (int)(6 * Game.SCALE), null);

                g.setColor(new Color(51, 50, 61));
                g.setFont(new Font("Arial", Font.BOLD, (int)(8 * Game.SCALE)));
                g.drawString(String.valueOf(Math.round(playingScene.getScore().getScore(i))), xMargin + (int)(52 * Game.SCALE) + xGap, yMargin + (i * (6 + yGap)) + (int)(5.5 * Game.SCALE));
            }
        }

        countDownPanel.draw(g);
        gameOverPanel.draw(g);
    }

    /**
     * Handles mouse click events for the game-over panel.
     *
     * @param e The MouseEvent object containing details about the mouse click.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        gameOverPanel.mouseClicked(e);
    }

    /**
     * Handles mouse press events for the game-over panel.
     *
     * @param e The MouseEvent object containing details about the mouse press.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        gameOverPanel.mousePressed(e);
    }

    /**
     * Handles mouse release events for the game-over panel.
     *
     * @param e The MouseEvent object containing details about the mouse release.
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        gameOverPanel.mouseReleased(e);
    }

    /**
     * Handles mouse movement events for the game-over panel.
     *
     * @param e The MouseEvent object containing details about the mouse movement.
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        gameOverPanel.mouseMoved(e);
    }

    /**
     * Handles key press events. Currently not implemented.
     *
     * @param e The KeyEvent object containing details about the key press.
     */
    @Override
    public void keyPressed(KeyEvent e) {}

    /**
     * Handles key release events. Currently not implemented.
     *
     * @param e The KeyEvent object containing details about the key release.
     */
    @Override
    public void keyReleased(KeyEvent e) {}

    /**
     * Loads the images used in the gameplay UI.
     */
    private void loadImages(){
        textPlayers = new BufferedImage[4];
        BufferedImage tempTextPlayers;

        try {
            tempTextPlayers = BundleLoader.getSpriteAtlas(BundleLoader.TEXT_PLAYER_INDEXES);
        } catch (BundleLoadException e) {
            Game.logger.error(e.getMessage());
            throw new RuntimeException(e);
        }

        int rows = 4;
        int width = 52;
        int height = 24 / 4; // 6

        for (int row = 0; row < rows; row++) {
            textPlayers[row] = tempTextPlayers.getSubimage(0, row * height, width, height);
        }
    }

    /**
     * Starts the countdown before the game begins.
     */
    public void startCountDown() {
        countDownPanel.setShow(true);
    }
}
