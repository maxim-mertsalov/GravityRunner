package me.xmertsalov.ui;

import me.xmertsalov.Game;
import me.xmertsalov.entities.Player;
import me.xmertsalov.scenes.GameScene;
import me.xmertsalov.scenes.inGame.PlayingScene;
import me.xmertsalov.ui.buttons.BigButtonFactory;
import me.xmertsalov.ui.buttons.IButton;
import me.xmertsalov.ui.buttons.SmallButtonFactory;
import me.xmertsalov.utils.BundleLoader;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class GameOverPanel {
    // General
    PlayingScene playingScene;

    // Dependencies
    private BigButtonFactory bigButtonFactory;
    private SmallButtonFactory smallButtonFactory;

    // States
    private boolean isShown = false;

    // Images
    private BufferedImage background;
    private BufferedImage gameOverText;
    private BufferedImage[] playerIndexesTexts;

    // UI Settings
    private int xGap = (int)(3 * Game.SCALE);
    private int yGap = (int)(18 * Game.SCALE);
    private int xMargin = (int)(Game.WINDOW_WIDTH / 2 - (182 * Game.SCALE) / 2);
    private int yMargin = (int)(Game.WINDOW_HEIGHT / 2 - (64 * Game.SCALE));
    private int yText = (int)(Game.WINDOW_HEIGHT / 2 - (150 * Game.SCALE));
    private int yBackground = (int)(Game.WINDOW_HEIGHT / 2 - (100 * Game.SCALE));
    private int xBtn = (int)(Game.WINDOW_HEIGHT / 2 - (-102 * Game.SCALE));
    private int yBtns = (int)(Game.WINDOW_HEIGHT / 2 - (-36 * Game.SCALE));

    // Storage
    private ArrayList<IButton> buttons;

    // Other
//    private int ticksToShowStats = 0;


    public GameOverPanel(PlayingScene playingScene) {
        this.playingScene = playingScene;
        loadImages();
        bigButtonFactory = new BigButtonFactory();
        smallButtonFactory = new SmallButtonFactory();

        // Retry button
        buttons = new ArrayList<>();
        buttons.add(bigButtonFactory.createButton(
                xBtn,
                yBtns,
                (int)(56 * 1.8 * Game.SCALE),
                (int)(14 * 1.8 * Game.SCALE), 5));
        buttons.get(0).setOnClickListener(() -> {
                playingScene.reset();
                for (Player player : playingScene.getPlayers()) {
                    player.setDisableControls(true);
                }
                isShown = false;
        });


        // Home button
        buttons.add(smallButtonFactory.createButton(
                (int)(xBtn - (14 * 1.8 * Game.SCALE) - (Game.SCALE * 3)),
                yBtns,
                (int)(14 * 1.8 * Game.SCALE),
                (int)(14 * 1.8 * Game.SCALE), 0));
        buttons.get(1).setOnClickListener(() -> {
            for (Player player : playingScene.getPlayers()) {
                playingScene.getGame().getLobbyScene().reset();
            }
            isShown = false;
            GameScene.scene = GameScene.MENU;
        });
    }


    public void draw(Graphics g) {
        if (isShown) {
            g.drawImage(background,
                    (int)(Game.WINDOW_WIDTH / 2 - (128 * 2 * Game.SCALE) / 2),
                    yBackground,
                    (int)(128 * 2 * Game.SCALE),
                    (int)(96 * 2 * Game.SCALE), null);

            g.drawImage(gameOverText,
                    (int)(Game.WINDOW_WIDTH / 2 - (43 * 2 * Game.SCALE) / 2),
                    yText,
                    (int)(43 * 2 * Game.SCALE),
                    (int)(25 * 2 * Game.SCALE), null);

            for (int i = 0; i < playingScene.getNumPlayers(); i++) {
                g.drawImage(playerIndexesTexts[i],
                        xMargin,
                        yMargin + (i * (6 + yGap)),
                        (int)(52 * Game.SCALE) ,
                        (int)(6 * Game.SCALE), null);
                g.setColor(new Color(51, 50, 61));
                g.setFont(new Font("Arial", Font.BOLD, (int)(8 * Game.SCALE)));
                g.drawString(String.valueOf(Math.round(playingScene.getScore().getScore(i))),
                        xMargin + (int)(52 * Game.SCALE) + xGap,
                        yMargin + (i * (6 + yGap)) + (int)(5.5 * Game.SCALE));
            }

            for(IButton button : buttons) {
                button.draw(g);
            }


        }
    }

    public void update() {
        if (!isShown) return;

        for (IButton button : buttons) {
            button.update();
        }

    }



    public void show() {isShown = true;}


    public void mouseClicked(MouseEvent e) {
        for (IButton button : buttons) {
            button.mouseClicked(e);
        }
    }

    public void mousePressed(MouseEvent e) {
        for (IButton button : buttons) {
            button.mousePressed(e);
        }
    }

    public void mouseReleased(MouseEvent e) {
        for (IButton button : buttons) {
            button.mouseReleased(e);
        }
    }

    public void mouseMoved(MouseEvent e) {
        for (IButton button : buttons) {
            button.mouseMoved(e);
        }
    }

    private void loadImages(){
        background = BundleLoader.getSpriteAtlas(BundleLoader.BACKGROUND_GAME_OVER);
        gameOverText = BundleLoader.getSpriteAtlas(BundleLoader.TEXT_GAME_OVER);

        BufferedImage tempPlayerIndexesTexts = BundleLoader.getSpriteAtlas(BundleLoader.TEXT_PLAYER_INDEXES);
        playerIndexesTexts = new BufferedImage[4];

        int rows = 4;
        int width = 52;
        int height = 24 / 4; // 6

        for (int row = 0; row < rows; row++) {
            playerIndexesTexts[row] = tempPlayerIndexesTexts.getSubimage(0, row * height, width, height);
        }
    }
}
