package me.xmertsalov.ui;

import me.xmertsalov.Game;
import me.xmertsalov.scenes.inGame.PlayingScene;
import me.xmertsalov.utils.BundleLoader;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class PlayingUIManager implements UIManager {
    // Main
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

    // Other
    private boolean alldead;


    public PlayingUIManager(PlayingScene playingScene) {
        this.playingScene = playingScene;

        gameOverPanel = new GameOverPanel(playingScene);
        countDownPanel = new CountDownPanel(playingScene);

        loadImages();
        countPlayers = playingScene.getNumPlayers();

    }

    @Override
    public void update() {
        if (playingScene.getNumPlayers() != countPlayers) {
            countPlayers = playingScene.getNumPlayers();
        }

        alldead = playingScene.getPlayers().stream().filter(player -> !player.isDead()).count() == 0;

        if (alldead) {
            gameOverPanel.show();
        }

        gameOverPanel.update();
        countDownPanel.update();
    }

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

    @Override
    public void mouseClicked(MouseEvent e) {
        gameOverPanel.mouseClicked(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        gameOverPanel.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        gameOverPanel.mouseReleased(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        gameOverPanel.mouseMoved(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    private void loadImages(){
        textPlayers = new BufferedImage[4];
        BufferedImage tempTextPlayers = BundleLoader.getSpriteAtlas(BundleLoader.TEXT_PLAYER_INDEXES);

        int rows = 4;
        int width = 52;
        int height = 24 / 4; // 6

        for (int row = 0; row < rows; row++) {
            textPlayers[row] = tempTextPlayers.getSubimage(0, row * height, width, height);
        }

    }

    public void startCountDown() {
        countDownPanel.setShow(true);
    }
}
