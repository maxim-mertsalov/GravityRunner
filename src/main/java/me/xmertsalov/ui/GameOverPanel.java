package me.xmertsalov.ui;

import me.xmertsalov.Game;
import me.xmertsalov.entities.Player;
import me.xmertsalov.exceptions.BundleLoadException;
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

/**
 * The GameOverPanel class manages the game-over screen UI.
 * It displays the game-over background, player scores, and navigation buttons.
 */
public class GameOverPanel {
    // General
    private PlayingScene playingScene;
    private Game game;

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
    private int xMargin = (int)((float) Game.WINDOW_WIDTH / 2 - (182 * Game.SCALE) / 2);
    private int yMargin = (int)((float) Game.WINDOW_HEIGHT / 2 - (64 * Game.SCALE));
    private int yText = (int)((float) Game.WINDOW_HEIGHT / 2 - (150 * Game.SCALE));
    private int yBackground = (int)((float) Game.WINDOW_HEIGHT / 2 - (100 * Game.SCALE));
    private int xBtn = (int)((float) Game.WINDOW_WIDTH / 2 - (36 * Game.SCALE));
    private int yBtns = (int)((float) Game.WINDOW_HEIGHT / 2 - (-36 * Game.SCALE));

    // Storage
    private ArrayList<IButton> buttons;

    /**
     * Constructs a GameOverPanel instance.
     *
     * @param playingScene The PlayingScene instance to which this panel belongs.
     */
    public GameOverPanel(PlayingScene playingScene) {
        this.playingScene = playingScene;
        this.game = playingScene.getGame();

        loadImages();

        bigButtonFactory = new BigButtonFactory();
        smallButtonFactory = new SmallButtonFactory();

        createButtons();
    }

    /**
     * Renders the game-over panel components on the screen.
     *
     * @param g The Graphics object used for drawing.
     */
    public void draw(Graphics g) {
        if (!isShown) return;

        g.drawImage(background,
                (int)((float) Game.WINDOW_WIDTH / 2 - (128 * 2 * Game.SCALE) / 2),
                yBackground,
                (int)(128 * 2 * Game.SCALE),
                (int)(96 * 2 * Game.SCALE), null);

        g.drawImage(gameOverText,
                (int)((float) Game.WINDOW_WIDTH / 2 - (43 * 2 * Game.SCALE) / 2),
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

    /**
     * Updates the state of the game-over panel.
     */
    public void update() {
        if (!isShown) return;

        for (IButton button : buttons) {
            button.update();
        }
    }

    /**
     * Handles mouse click events for game-over buttons.
     *
     * @param e The MouseEvent object containing details about the mouse click.
     */
    public void mouseClicked(MouseEvent e) {
        for (IButton button : buttons) {
            button.mouseClicked(e);
        }
    }

    /**
     * Handles mouse press events for game-over buttons.
     *
     * @param e The MouseEvent object containing details about the mouse press.
     */
    public void mousePressed(MouseEvent e) {
        for (IButton button : buttons) {
            button.mousePressed(e);
        }
    }

    /**
     * Handles mouse release events for game-over buttons.
     *
     * @param e The MouseEvent object containing details about the mouse release.
     */
    public void mouseReleased(MouseEvent e) {
        for (IButton button : buttons) {
            button.mouseReleased(e);
        }
    }

    /**
     * Handles mouse movement events for game-over buttons.
     *
     * @param e The MouseEvent object containing details about the mouse movement.
     */
    public void mouseMoved(MouseEvent e) {
        for (IButton button : buttons) {
            button.mouseMoved(e);
        }
    }

    /**
     * Creates the navigation buttons for the game-over panel.
     */
    private void createButtons() {
        buttons = new ArrayList<>();

        // Retry button
        buttons.add(bigButtonFactory.createButton(
                xBtn,
                yBtns,
                (int)(56 * 1.8 * Game.SCALE),
                (int)(14 * 1.8 * Game.SCALE), 5, game));
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
                (int)(14 * 1.8 * Game.SCALE), 0, game));
        buttons.get(1).setOnClickListener(() -> {
            playingScene.getGame().getLobbyScene().reset();

            isShown = false;
            GameScene.scene = GameScene.MENU;
        });
    }

    /**
     * Loads the images used in the game-over panel.
     */
    private void loadImages(){
        playerIndexesTexts = new BufferedImage[4];
        BufferedImage tempPlayerIndexesTexts;

        try {
            background = BundleLoader.getSpriteAtlas(BundleLoader.BACKGROUND_GAME_OVER);
            gameOverText = BundleLoader.getSpriteAtlas(BundleLoader.TEXT_GAME_OVER);
            tempPlayerIndexesTexts = BundleLoader.getSpriteAtlas(BundleLoader.TEXT_PLAYER_INDEXES);
        } catch (BundleLoadException e) {
            throw new RuntimeException(e);
        }

        int rows = 4;
        int width = 52;
        int height = 24 / 4; // 6

        for (int row = 0; row < rows; row++) {
            playerIndexesTexts[row] = tempPlayerIndexesTexts.getSubimage(0, row * height, width, height);
        }
    }

    /**
     * Shows the game-over panel.
     */
    public void show() {isShown = true;}
}
