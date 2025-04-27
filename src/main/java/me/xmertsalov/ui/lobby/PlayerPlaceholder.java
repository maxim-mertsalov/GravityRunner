package me.xmertsalov.ui.lobby;

import me.xmertsalov.Game;
import me.xmertsalov.entities.Player;
import me.xmertsalov.ui.buttons.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Objects;

/**
 * The PlayerPlaceholder class represents a UI element in the lobby for a single player.
 * It displays the player's avatar, allows skin selection, and manages player-specific settings.
 */
public class PlayerPlaceholder {
    // Dependencies
    private Game game;
    private LobbyManager lobbyManager;

    private ArrowsButtonFactory arrowsButtonFactory;
    private ButtonDetectKeyFactory buttonDetectKeyFactory;

    // Storage
    private Player player;
    private int id;
    private ArrayList<IButton> buttons;

    // States
    private int currentSkin; // 0 is empty
    private String currentSkinName;

    // Images
    private BufferedImage playerPlaceholderImage;

    // Constants
    private final int initialWidth = 96;
    private final int initialHeight = 128;

    // UI Settings
    private final int placeholderWidth = (int) (initialWidth * Game.SCALE * 1.4);
    private final int placeholderHeight = (int) (initialHeight * Game.SCALE * 1.4);

    private final int placeholderY = (int)(90 * Game.SCALE);

    private int offsetXBetween;

    /**
     * Constructs a PlayerPlaceholder instance.
     * Initializes buttons, player settings, and UI configurations.
     *
     * @param player               The player associated with this placeholder.
     * @param id                   The unique ID of the placeholder.
     * @param playerPlaceholderImage The image used for the placeholder background.
     * @param lobbyManager         The lobby manager instance managing this placeholder.
     */
    public PlayerPlaceholder(Player player, int id, BufferedImage playerPlaceholderImage, LobbyManager lobbyManager) {
        this.player = player;
        this.id = id;
        this.lobbyManager = lobbyManager;
        this.game = lobbyManager.getGame();

        this.arrowsButtonFactory = new ArrowsButtonFactory();
        this.buttonDetectKeyFactory = new ButtonDetectKeyFactory();

        this.buttons = new ArrayList<>();

        int arrowsOffsetX = (int)(12 * Game.SCALE);
        int arrowsOffsetY = (int)(placeholderY - 36 * Game.SCALE);
        int arrowSize = 24;

        int placeholdersContainer = (Game.WINDOW_WIDTH - 200); // 100 ____ 100
        offsetXBetween = (placeholdersContainer - placeholderWidth * 4) / 3; // _ var _ var _ var _

        buttons.add(arrowsButtonFactory.createButton(getXById() + arrowsOffsetX, placeholderY + arrowsOffsetY, (int)(arrowSize * Game.SCALE), (int)(arrowSize * Game.SCALE), 0, game));
        buttons.add(arrowsButtonFactory.createButton((int)(getXById() + placeholderWidth - arrowsOffsetX * 2.3), placeholderY + arrowsOffsetY, (int)(arrowSize * Game.SCALE), (int)(arrowSize * Game.SCALE), 1, game));

        buttons.get(0).setOnClickListener(this::setPrevSkin);
        buttons.get(1).setOnClickListener(this::setNextSkin);

        // 56 x 14
        buttons.add(
                buttonDetectKeyFactory.createButton((int) (getXById() + 32 * Game.SCALE), (int)(placeholderY + 130 * Game.SCALE), (int)(56 * 1.2 * Game.SCALE), (int)(14 * 1.8 * Game.SCALE), 0, game)
        );

        if (buttons.get(2) instanceof ButtonDetectedKey buttonDetectKey) {
            if (buttonDetectKey.getData() != player.getChangeGravityKey()){
                buttonDetectKey.setData(player.getChangeGravityKey());
            }
        }

        this.currentSkin = -1;
        this.currentSkinName = "None";

        this.playerPlaceholderImage = playerPlaceholderImage;

        resetPlayerStats();
    }

    /**
     * Draws the player placeholder, including the background, buttons, and player avatar.
     *
     * @param g The Graphics object used for rendering.
     */
    public void draw(Graphics g) {
        g.drawImage(playerPlaceholderImage, getXById(), placeholderY, placeholderWidth, placeholderHeight, null);
        for (IButton button : buttons) {
            button.draw(g);
        }

        g.setColor(new Color(51, 50, 61));
        g.setFont(new Font("Arial", Font.BOLD, (int)(8 * Game.SCALE)));
        g.drawString(currentSkinName, (int)(getXById() + 30 * Game.SCALE), (int)(placeholderY + 117.5 * Game.SCALE));

        player.render(g);
    }

    /**
     * Updates the state of the player placeholder, including buttons and player settings.
     */
    public void update() {
        for (IButton button : buttons) {
            button.update();
        }
        player.update();

        if (currentSkin < 0){
            currentSkinName = "None";
            player.setInActive(true);
        }
        else if (player.isInActive() && currentSkin >= 0) {
            player.setInActive(false);
        }

        if (!Objects.equals(player.getPlayerSkin(), currentSkinName)){
            player.setAnimator(player.getPlayerAnimator(), currentSkinName);
        }

        if (buttons.get(2) instanceof ButtonDetectedKey buttonDetectKey) {
            if (buttonDetectKey.getData() != player.getChangeGravityKey()){
                player.setChangeGravityKey(buttonDetectKey.getData());
            }
        }
    }

    /**
     * Resets the player's stats to their initial state.
     */
    public void resetPlayerStats(){
        player.setPosX(getXById() + 20 * Game.SCALE);
        player.setPosY(placeholderY + 20 * Game.SCALE);
        player.setDisableGravity(true);
        player.setDisableControls(true);
        player.getPhisicsComponent().setVelocity(0, 0);
        player.setDead(false, 0);

        if (player.getPhisicsComponent().getGravityDirection() < 0){
            player.getPhisicsComponent().setGravityDirection(1);
            player.setFlipH(1);
            player.setFlipY(0);
        }
    }

    /**
     * Calculates the X-coordinate of the placeholder based on its ID.
     *
     * @return The X-coordinate of the placeholder.
     */
    private int getXById() {
        switch (id) {
            case 0 -> {
                return 100;
            }
            case 1 -> {
                return 100 + offsetXBetween + placeholderWidth;
            }
            case 2 -> {
                return 100 + offsetXBetween * 2 + placeholderWidth * 2;
            }
            case 3 -> {
                return 100 + offsetXBetween * 3 + placeholderWidth * 3;
            }
        }
        return -1;
    }

    public void mouseClicked(MouseEvent e) {
        buttons.forEach(button -> button.mouseClicked(e));
    }

    public void mousePressed(MouseEvent e) {
        buttons.forEach(button -> button.mousePressed(e));
    }

    public void mouseReleased(MouseEvent e) {
        buttons.forEach(button -> button.mouseReleased(e));
    }

    public void mouseMoved(MouseEvent e) {
        buttons.forEach(button -> button.mouseMoved(e));
    }

    public void keyPressed(KeyEvent e) {
        buttons.forEach(button -> button.keyPressed(e));
    }

    public void keyReleased(KeyEvent e) {
        buttons.forEach(button -> button.keyReleased(e));
    }

    private void setNextSkin() {
        currentSkin++;

        if (currentSkin >= player.getPlayerAnimator().getSkinsURL().size()) {
            currentSkin = player.getPlayerAnimator().getSkinsURL().size() - 1;
        }

        currentSkinName = player.getPlayerAnimator().getAnimationStates().keySet().toArray()[currentSkin].toString();
    }

    private void setPrevSkin() {
        currentSkin--;

        if (currentSkin <= -1) {
            currentSkin = -1;
        }

        currentSkinName = player.getPlayerAnimator().getAnimationStates().keySet().toArray()[Math.abs(currentSkin)].toString();
    }

}
