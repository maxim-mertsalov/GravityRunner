package me.xmertsalov.ui.lobby;

import me.xmertsalov.Game;
import me.xmertsalov.entities.Player;
import me.xmertsalov.exceptions.BundleLoadException;
import me.xmertsalov.exceptions.LobbyException;
import me.xmertsalov.scenes.GameScene;
import me.xmertsalov.ui.UIManager;
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

/**
 * The LobbyManager class is responsible for managing the lobby UI in the game.
 * It handles player placeholders, buttons, game modes, and their interactions.
 * This class implements the UIManager interface and provides methods for updating,
 * drawing, and handling user input in the lobby.
 */
public class LobbyManager implements UIManager {

    // General
    private Game game;

    // Dependencies
    private BigButtonFactory bigButtonFactory;
    private SmallButtonFactory smallButtonFactory;
    
    // Storage
    private ArrayList<Player> players;
    private ArrayList<IButton> buttons;
    private ArrayList<PlayerPlaceholder> playerPlaceholders;

    // Images
    private BufferedImage playerPlaceholderImage;
    private BufferedImage longBackgoundImage;
    
    private BufferedImage textBorderlessModeImage;
    private BufferedImage textGhostModeImage;
    private BufferedImage textGodModeImage;
    private BufferedImage textSpeedModeImage;
    private BufferedImage textSlowModeImage;
    private BufferedImage textViewerModeImage;

    // Constants
    private final double btn_scale = 1.5;
    
    // States
    private boolean borderlessMode = false;
    private boolean ghostMode = false;
    private boolean godMode = false;
    private boolean speedMode = false;
    private boolean slowMode = false;
    private boolean viewerMode = false;

    private boolean reset = true;

    // UI Settings
    private int sizeToggleBtn =  (int)(16 * btn_scale * Game.SCALE);

    private final int firstColBtnX = (int)(Game.SCALE * 24 + 100);
    private final int secondColBtnX = (int)(Game.SCALE * 450);

    private final int firstRowBtnY = (int)(Game.SCALE * 318);
    private final int secondRowBtnY = (int)(Game.SCALE * 350);
    private final int thirdRowBtnY = (int)(Game.SCALE * 382);

    private final int paddingXText = (int)(28 * Game.SCALE);
    private final int paddingYText = (int)(6 * Game.SCALE);
    
    /**
     * Constructs a LobbyManager instance.
     * Initializes players, buttons, modes, images, and placeholders.
     *
     * @param game The game instance to associate with this lobby manager.
     */
    public LobbyManager(Game game) {
        this.game = game;

        // Load players
        loadPlayers();

        // Initialize factories
        smallButtonFactory = new SmallButtonFactory();
        bigButtonFactory = new BigButtonFactory();

        playerPlaceholders = new ArrayList<>(4);

        // Create buttons
        createButtons();

        // Load modes
        loadModes();

        // Load images
        loadImages();

        for (int i = 0; i < 4; i++) {
            playerPlaceholders.add(new PlayerPlaceholder(ListUtils.getOrDefault(players, i, null), i, playerPlaceholderImage, this));
        }
    }

    /**
     * Updates the state of the lobby, including player placeholders, buttons, and modes.
     */
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

    /**
     * Draws the lobby UI, including the background, player placeholders, buttons, and text.
     *
     * @param g The Graphics object used for rendering.
     */
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

    /**
     * Resets all player stats in the lobby if the reset flag is set to false.
     */
    private void resetAll(){
        if (reset) return;

        for (PlayerPlaceholder placeholder : playerPlaceholders) {
            placeholder.resetPlayerStats();
        }

        reset = true;
    }

    /**
     * Draws the text labels for the game modes on the lobby UI.
     *
     * @param g The Graphics object used for rendering.
     */
    private void drawText(Graphics g) {
        g.drawImage(textSpeedModeImage, (firstColBtnX + paddingXText), (firstRowBtnY + paddingYText), (int)(textSpeedModeImage.getWidth() * btn_scale * Game.SCALE), (int)(textSpeedModeImage.getHeight() * btn_scale * Game.SCALE), null);
        g.drawImage(textGhostModeImage, (firstColBtnX + paddingXText), (secondRowBtnY + paddingYText), (int)(textGhostModeImage.getWidth() * btn_scale * Game.SCALE), (int)(textGhostModeImage.getHeight() * btn_scale * Game.SCALE), null);
        g.drawImage(textGodModeImage, (firstColBtnX + paddingXText), (thirdRowBtnY + paddingYText), (int)(textGodModeImage.getWidth() * btn_scale * Game.SCALE), (int)(textGodModeImage.getHeight() * btn_scale * Game.SCALE), null);
        g.drawImage(textSlowModeImage, (secondColBtnX + paddingXText), (firstRowBtnY + paddingYText), (int)(textSlowModeImage.getWidth() * btn_scale * Game.SCALE), (int)(textSlowModeImage.getHeight() * btn_scale * Game.SCALE), null);
        g.drawImage(textBorderlessModeImage, (secondColBtnX + paddingXText), (secondRowBtnY + paddingYText), (int)(textBorderlessModeImage.getWidth() * btn_scale * Game.SCALE), (int)(textBorderlessModeImage.getHeight() * btn_scale * Game.SCALE), null);
//        g.drawImage(textViewerModeImage, (secondColBtnX + paddingXText), (thirdRowBtnY + paddingYText), (int)(textViewerModeImage.getWidth() * btn_scale * Game.SCALE), (int)(textViewerModeImage.getHeight() * btn_scale * Game.SCALE), null);
    }

    /**
     * Loads the initial states of the game modes from the game instance.
     */
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

    /**
     * Updates the game modes based on the current state of the lobby.
     */
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

    /**
     * Handles mouse click events for buttons and player placeholders.
     *
     * @param e The MouseEvent object containing event details.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        buttons.forEach(button -> button.mouseClicked(e));
        for (PlayerPlaceholder placeholder : playerPlaceholders) {
            placeholder.mouseClicked(e);
        }
    }

    /**
     * Handles mouse press events for buttons and player placeholders.
     *
     * @param e The MouseEvent object containing event details.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        buttons.forEach(button -> button.mousePressed(e));
        for (PlayerPlaceholder placeholder : playerPlaceholders) {
            placeholder.mousePressed(e);
        }
    }

    /**
     * Handles mouse release events for buttons and player placeholders.
     *
     * @param e The MouseEvent object containing event details.
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        buttons.forEach(button -> button.mouseReleased(e));
        for (PlayerPlaceholder placeholder : playerPlaceholders) {
            placeholder.mouseReleased(e);
        }
    }

    /**
     * Handles mouse movement events for buttons and player placeholders.
     *
     * @param e The MouseEvent object containing event details.
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        buttons.forEach(button -> button.mouseMoved(e));
        for (PlayerPlaceholder placeholder : playerPlaceholders) {
            placeholder.mouseMoved(e);
        }
    }

    /**
     * Handles key press events for player placeholders.
     *
     * @param e The KeyEvent object containing event details.
     */
    public void keyPressed(KeyEvent e) {
        for (PlayerPlaceholder placeholder : playerPlaceholders) {
            placeholder.keyPressed(e);
        }
    }

    /**
     * Handles key release events for player placeholders.
     *
     * @param e The KeyEvent object containing event details.
     */
    public void keyReleased(KeyEvent e) {
        for (PlayerPlaceholder placeholder : playerPlaceholders) {
            placeholder.keyReleased(e);
        }
    }

    /**
     * Creates and initializes the buttons for the lobby UI.
     */
    private void createButtons(){
        buttons = new ArrayList<>();

        // ready button
        buttons.add(bigButtonFactory.createButton(
                (int) ((Game.WINDOW_WIDTH - 200) / 2), (int) (392 * Game.SCALE), (int)(56 * btn_scale * Game.SCALE), (int)(14 * btn_scale * Game.SCALE), 4, game
        ));
        buttons.get(0).setOnClickListener(() -> {

            int readyPlayers = (int) players.stream().filter(player -> !player.isInActive()).count();

            if (readyPlayers == 0) return;

            game.getPlayingScene().reset();
            GameScene.scene = GameScene.PLAYING;
        });

        // increase speed mode
        buttons.add(smallButtonFactory.createButton(
                firstColBtnX, firstRowBtnY, sizeToggleBtn, sizeToggleBtn, 4, game
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
                firstColBtnX, secondRowBtnY, sizeToggleBtn, sizeToggleBtn, 4, game
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
                firstColBtnX, thirdRowBtnY, sizeToggleBtn, sizeToggleBtn, 4, game
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
                secondColBtnX, firstRowBtnY, sizeToggleBtn, sizeToggleBtn, 4, game
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
                secondColBtnX, secondRowBtnY, sizeToggleBtn, sizeToggleBtn, 4, game
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
//                secondColBtnX, thirdRowBtnY, sizeTogleBtn, sizeTogleBtn, 4, game
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

        // menu page(back btn)
        buttons.add(smallButtonFactory.createButton(
                (int) ((Game.WINDOW_WIDTH - 200) / 2) - sizeToggleBtn - (int)(6 * Game.SCALE), (int) (392 * Game.SCALE), sizeToggleBtn, sizeToggleBtn, 0, game
        ));
        buttons.get(6).setOnClickListener(() -> {
            GameScene.scene = GameScene.MENU;
        });
    }

    /**
     * Loads the players from the game instance into the lobby.
     * Throws a LobbyException if no players are found.
     */
    private void loadPlayers(){
        if (!game.getPlayers().isEmpty()) {
            this.players = game.getPlayers();
        }
        else{
            throw new LobbyException("No players found in the game.");
        }
    }

    /**
     * Loads the images required for the lobby UI from the resource bundle.
     * Throws a RuntimeException if the images cannot be loaded.
     */
    private void loadImages(){
        try {
            playerPlaceholderImage = BundleLoader.getSpriteAtlas(BundleLoader.PLAYER_PLACEHOLDER);

            longBackgoundImage = BundleLoader.getSpriteAtlas(BundleLoader.MENU_BACKGROUND_LONG);

            textBorderlessModeImage = BundleLoader.getSpriteAtlas(BundleLoader.TEXT_BORDERLESS_MODE);
            textGhostModeImage = BundleLoader.getSpriteAtlas(BundleLoader.TEXT_GHOST_MODE);
            textGodModeImage = BundleLoader.getSpriteAtlas(BundleLoader.TEXT_GOD_MODE);
            textSpeedModeImage = BundleLoader.getSpriteAtlas(BundleLoader.TEXT_SPEED_MODE);
            textSlowModeImage = BundleLoader.getSpriteAtlas(BundleLoader.TEXT_SLOW_MODE);
            textViewerModeImage = BundleLoader.getSpriteAtlas(BundleLoader.TEXT_VIEWER_MODE);

        } catch (BundleLoadException e) {
            Game.logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Resets the lobby state, allowing all player stats to be reset.
     */
    public void reset() {this.reset = false;}

    /**
     * Retrieves the associated game instance.
     *
     * @return The game instance.
     */
    public Game getGame(){return game;}

}
