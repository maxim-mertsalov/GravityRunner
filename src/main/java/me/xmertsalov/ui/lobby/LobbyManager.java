package me.xmertsalov.ui.lobby;

import me.xmertsalov.Game;
import me.xmertsalov.components.PlayerAnimator;
import me.xmertsalov.entities.Player;
import me.xmertsalov.scenes.GameScene;
import me.xmertsalov.scenes.inGame.PlayingScene;
import me.xmertsalov.ui.UIManager;
import me.xmertsalov.ui.buttons.ArrowsButtonFactory;
import me.xmertsalov.ui.buttons.BigButtonFactory;
import me.xmertsalov.ui.buttons.IButton;
import me.xmertsalov.ui.buttons.SmallButtonFactory;
import me.xmertsalov.utils.BundleLoader;
import me.xmertsalov.utils.ListUtils;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class LobbyManager implements UIManager {
    private Game game;
    private ArrayList<Player> players;

    private ArrayList<IButton> buttons;

    private ArrowsButtonFactory arrowsButtonFactory;
    private BigButtonFactory bigButtonFactory;
    private SmallButtonFactory smallButtonFactory;


    private BufferedImage playerPlaceholderImage;
    private BufferedImage longBackgoundImage;

    private BufferedImage textBorderlessModeImage;
    private BufferedImage textGhostModeImage;
    private BufferedImage textGodModeImage;
    private BufferedImage textSpeedModeImage;
    private BufferedImage textSlowModeImage;
    private BufferedImage textViewerModeImage;

    private final double btn_scale = 1.5;

    private ArrayList<PlayerPlaceholder> playerPlaceholders;

    private boolean borderlessMode = false;
    private boolean ghostMode = false;
    private boolean godMode = false;
    private boolean speedMode = false;
    private boolean slowMode = false;
    private boolean viewerMode = false;


    private int sizeTogleBtn =  (int)(16 * btn_scale * Game.SCALE);

    private final int firstColBtnX = (int)(Game.SCALE * 24 + 100);
    private final int secondColBtnX = (int)(Game.SCALE * 450);

    private final int firstRowBtnY = (int)(Game.SCALE * 318);
    private final int secondRowBtnY = (int)(Game.SCALE * 350);
    private final int thirdRowBtnY = (int)(Game.SCALE * 382);

    private final int paddingXText = (int)(28 * Game.SCALE);
    private final int paddingYText = (int)(6 * Game.SCALE);

    private boolean reseted = true;

    public LobbyManager(Game game) {
        this.game = game;
//        this.players = new ArrayList<>();

        loadPlayers();

        arrowsButtonFactory = new ArrowsButtonFactory();
        smallButtonFactory = new SmallButtonFactory();
        bigButtonFactory = new BigButtonFactory();

        buttons = new ArrayList<>();
        buttons.add(bigButtonFactory.createButton(
                (int) ((Game.WINDOW_WIDTH - 200) / 2), (int) (392 * Game.SCALE), (int)(56 * btn_scale * Game.SCALE), (int)(14 * btn_scale * Game.SCALE), 4
        ));

        buttons.getFirst().setOnClickListener(() -> {
            game.getPlayingScene().reset();
            GameScene.scene = GameScene.PLAYING;
        });

        setStateBtns();

        loadModes();

        // menu page(back btn)
        buttons.add(smallButtonFactory.createButton(
                (int) ((Game.WINDOW_WIDTH - 200) / 2) - sizeTogleBtn - (int)(6 * Game.SCALE), (int) (392 * Game.SCALE), sizeTogleBtn, sizeTogleBtn, 0
        ));
        buttons.get(6).setOnClickListener(() -> {
            GameScene.scene = GameScene.MENU;
        });

        loadImages();

        playerPlaceholders = new ArrayList<>(4);

        for (int i = 0; i < 4; i++) {
            playerPlaceholders.add(new PlayerPlaceholder(ListUtils.getOrDefault(players, i, null), i, playerPlaceholderImage, this));
        }
    }

    @Override
    public void update() {
        resetAll();
        for (PlayerPlaceholder placeholder : playerPlaceholders) {
            placeholder.update();
        }
        for (IButton button : buttons) {
            button.update();
        }

        updateModes();
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(longBackgoundImage, 100, (int)(Game.SCALE * 290), Game.WINDOW_WIDTH - 200, (int)(96 * 1.5 * Game.SCALE), null);

        for (PlayerPlaceholder placeholder : playerPlaceholders) {
            placeholder.draw(g);
        }

        for (IButton button : buttons) {
            button.draw(g);
        }

        drawText(g);
    }

    private void resetAll(){
        if (reseted) return;

        for (PlayerPlaceholder placeholder : playerPlaceholders) {
            placeholder.resetPlayerStats();
        }

        reseted = true;
    }

    private void drawText(Graphics g) {
        g.drawImage(textSpeedModeImage, (firstColBtnX + paddingXText), (firstRowBtnY + paddingYText), (int)(textSpeedModeImage.getWidth() * btn_scale * Game.SCALE), (int)(textSpeedModeImage.getHeight() * btn_scale * Game.SCALE), null);
        g.drawImage(textGhostModeImage, (firstColBtnX + paddingXText), (secondRowBtnY + paddingYText), (int)(textGhostModeImage.getWidth() * btn_scale * Game.SCALE), (int)(textGhostModeImage.getHeight() * btn_scale * Game.SCALE), null);
        g.drawImage(textGodModeImage, (firstColBtnX + paddingXText), (thirdRowBtnY + paddingYText), (int)(textGodModeImage.getWidth() * btn_scale * Game.SCALE), (int)(textGodModeImage.getHeight() * btn_scale * Game.SCALE), null);
        g.drawImage(textSlowModeImage, (secondColBtnX + paddingXText), (firstRowBtnY + paddingYText), (int)(textSlowModeImage.getWidth() * btn_scale * Game.SCALE), (int)(textSlowModeImage.getHeight() * btn_scale * Game.SCALE), null);
        g.drawImage(textBorderlessModeImage, (secondColBtnX + paddingXText), (secondRowBtnY + paddingYText), (int)(textBorderlessModeImage.getWidth() * btn_scale * Game.SCALE), (int)(textBorderlessModeImage.getHeight() * btn_scale * Game.SCALE), null);
//        g.drawImage(textViewerModeImage, (secondColBtnX + paddingXText), (thirdRowBtnY + paddingYText), (int)(textViewerModeImage.getWidth() * btn_scale * Game.SCALE), (int)(textViewerModeImage.getHeight() * btn_scale * Game.SCALE), null);
    }

    private void loadModes(){
        speedMode = game.isIncreasedGameSpeedMode();
        if (speedMode) {
            buttons.get(1).setVariant(3);
        }
        ghostMode = game.isGhostMode();
        if (ghostMode) {
            buttons.get(2).setVariant(3);
        }
        godMode = game.isGodMode();
        if (godMode) {
            buttons.get(3).setVariant(3);
        }
        slowMode = game.isSlowMode();
        if (slowMode) {
            buttons.get(4).setVariant(3);
        }
        borderlessMode = game.isBorderlessMode();
        if (borderlessMode) {
            buttons.get(5).setVariant(3);
        }
        viewerMode = game.isViewerMode();
        if (viewerMode) {
            buttons.get(6).setVariant(3);
        }
    }

    private void updateModes(){
        if (speedMode != game.isIncreasedGameSpeedMode()) {
            game.setIncreasedGameSpeedMode(true);
        }
        if (ghostMode != game.isGhostMode()) {
            game.setGhostMode(true);
        }
        if (godMode != game.isGodMode()) {
            game.setGodMode(true);
        }
        if (slowMode != game.isSlowMode()) {
            game.setSlowMode(true);
        }
        if (borderlessMode != game.isBorderlessMode()) {
            game.setBorderlessMode(true);
        }
        if (viewerMode != game.isViewerMode()) {
            game.setViewerMode(true);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        buttons.forEach(button -> button.mouseClicked(e));
        for (PlayerPlaceholder placeholder : playerPlaceholders) {
            placeholder.mouseClicked(e);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        buttons.forEach(button -> button.mousePressed(e));
        for (PlayerPlaceholder placeholder : playerPlaceholders) {
            placeholder.mousePressed(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        buttons.forEach(button -> button.mouseReleased(e));
        for (PlayerPlaceholder placeholder : playerPlaceholders) {
            placeholder.mouseReleased(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        buttons.forEach(button -> button.mouseMoved(e));
        for (PlayerPlaceholder placeholder : playerPlaceholders) {
            placeholder.mouseMoved(e);
        }
    }

    public void keyPressed(KeyEvent e) {
        for (PlayerPlaceholder placeholder : playerPlaceholders) {
            placeholder.keyPressed(e);
        }
    }

    public void keyReleased(KeyEvent e) {
        for (PlayerPlaceholder placeholder : playerPlaceholders) {
            placeholder.keyReleased(e);
        }
    }

    private void setStateBtns(){
        // index starts from 1
        // increase speed mode
        buttons.add(smallButtonFactory.createButton(
                firstColBtnX, firstRowBtnY, sizeTogleBtn, sizeTogleBtn, 4
        ));
        buttons.get(1).setOnClickListener(() -> {
            if (speedMode) {
                speedMode = false;
                buttons.get(1).setVariant(4);
            } else {
                speedMode = true;
                buttons.get(1).setVariant(3);
            }
            buttons.get(1).setState(1);
        });

        // ghost mode
        buttons.add(smallButtonFactory.createButton(
                firstColBtnX, secondRowBtnY, sizeTogleBtn, sizeTogleBtn, 4
        ));
        buttons.get(2).setOnClickListener(() -> {
            if (ghostMode) {
                ghostMode = false;
                buttons.get(2).setVariant(4);
            } else {
                ghostMode = true;
                buttons.get(2).setVariant(3);
            }
            buttons.get(2).setState(1);
        });

        // god mode
        buttons.add(smallButtonFactory.createButton(
                firstColBtnX, thirdRowBtnY, sizeTogleBtn, sizeTogleBtn, 4
        ));
        buttons.get(3).setOnClickListener(() -> {
            if (godMode) {
                godMode = false;
                buttons.get(3).setVariant(4);
            } else {
                godMode = true;
                buttons.get(3).setVariant(3);
            }
            buttons.get(3).setState(1);
        });

        // slow mode
        buttons.add(smallButtonFactory.createButton(
                secondColBtnX, firstRowBtnY, sizeTogleBtn, sizeTogleBtn, 4
        ));
        buttons.get(4).setOnClickListener(() -> {
            if (slowMode) {
                slowMode = false;
                buttons.get(4).setVariant(4);
            } else {
                slowMode = true;
                buttons.get(4).setVariant(3);
            }
            buttons.get(4).setState(1);
        });

        // borderless mode
        buttons.add(smallButtonFactory.createButton(
                secondColBtnX, secondRowBtnY, sizeTogleBtn, sizeTogleBtn, 4
        ));
        buttons.get(5).setOnClickListener(() -> {
            if (borderlessMode) {
                borderlessMode = false;
                buttons.get(5).setVariant(4);
            } else {
                borderlessMode = true;
                buttons.get(5).setVariant(3);
            }
            buttons.get(5).setState(1);
        });

        // viewer mode
//        buttons.add(smallButtonFactory.createButton(
//                secondColBtnX, thirdRowBtnY, sizeTogleBtn, sizeTogleBtn, 4
//        ));
//        buttons.get(6).setOnClickListener(() -> {
//            if (viewerMode) {
//                viewerMode = false;
//                buttons.get(6).setVariant(4);
//            } else {
//                viewerMode = true;
//                buttons.get(6).setVariant(3);
//            }
//            buttons.get(6).setState(1);
//        });
    }

    private void loadPlayers(){
        if (!game.getPlayers().isEmpty()) {
            this.players = game.getPlayers();
        }
        else{
            throw new IllegalStateException("No players found in the game.");
        }
    }

    private void loadImages(){
        playerPlaceholderImage = BundleLoader.getSpriteAtlas(BundleLoader.PLAYER_PLACEHOLDER);

        longBackgoundImage = BundleLoader.getSpriteAtlas(BundleLoader.MENU_BACKGROUND_LONG);

        textBorderlessModeImage = BundleLoader.getSpriteAtlas(BundleLoader.TEXT_BORDERLESS_MODE);
        textGhostModeImage = BundleLoader.getSpriteAtlas(BundleLoader.TEXT_GHOST_MODE);
        textGodModeImage = BundleLoader.getSpriteAtlas(BundleLoader.TEXT_GOD_MODE);
        textSpeedModeImage = BundleLoader.getSpriteAtlas(BundleLoader.TEXT_SPEED_MODE);
        textSlowModeImage = BundleLoader.getSpriteAtlas(BundleLoader.TEXT_SLOW_MODE);
        textViewerModeImage = BundleLoader.getSpriteAtlas(BundleLoader.TEXT_VIEWER_MODE);
    }

    public void reset() {this.reseted = false;}

}
