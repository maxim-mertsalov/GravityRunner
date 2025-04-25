package me.xmertsalov.ui.lobby;

import me.xmertsalov.Game;
import me.xmertsalov.components.PlayerAnimator;
import me.xmertsalov.entities.Player;
import me.xmertsalov.ui.buttons.*;
import me.xmertsalov.utils.BundleLoader;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Objects;

public class PlayerPlaceholder {
    private Player player;
    private int id;

    private int currentSkin; // 0 is empty
    private String currentSkinName;

    private BufferedImage playerPlaceholderImage;

    private final int initialWidth = 96;
    private final int initialHeight = 128;

    private final int placeholderWidth = (int) (initialWidth * Game.SCALE * 1.4);
    private final int placeholderHeight = (int) (initialHeight * Game.SCALE * 1.4);

    private final int placeholderY = (int)(90 * Game.SCALE);

    private int offsetXBetween;

    private boolean isReady = false;

    private ArrowsButtonFactory arrowsButtonFactory;
    private BigButtonFactory bigButtonFactory;
    private ButtonDetectKeyFactory buttonDetectKeyFactory;

    private ArrayList<IButton> buttons;

    private LobbyManager lobbyManager;


    public PlayerPlaceholder(Player player, int id, BufferedImage playerPlaceholderImage, LobbyManager lobbyManager) {
        this.player = player;
        this.id = id;
        this.lobbyManager = lobbyManager;

        this.arrowsButtonFactory = new ArrowsButtonFactory();
        this.bigButtonFactory = new BigButtonFactory();
        this.buttonDetectKeyFactory = new ButtonDetectKeyFactory();

        this.buttons = new ArrayList<>();

        int arrowsOffsetX = (int)(12 * Game.SCALE);
        int arrowsOffsetY = (int)(placeholderY - 36 * Game.SCALE);
        int arrowSize = 24;

        int placeholdersContainer = (int) (Game.WINDOW_WIDTH - 200); // 100 ____ 100
        offsetXBetween = (int) (placeholdersContainer - placeholderWidth * 4) / 3; // _ var _ var _ var _

        buttons.add(arrowsButtonFactory.createButton(getXById() + arrowsOffsetX, placeholderY + arrowsOffsetY, (int)(arrowSize * Game.SCALE), (int)(arrowSize * Game.SCALE), 0));
        buttons.add(arrowsButtonFactory.createButton((int)(getXById() + placeholderWidth - arrowsOffsetX * 2.3), placeholderY + arrowsOffsetY, (int)(arrowSize * Game.SCALE), (int)(arrowSize * Game.SCALE), 1));

        buttons.get(0).setOnClickListener(this::setPrevSkin);
        buttons.get(1).setOnClickListener(this::setNextSkin);

        // 56 x 14
        buttons.add(
                buttonDetectKeyFactory.createButton((int) (getXById() + 32 * Game.SCALE), (int)(placeholderY + 130 * Game.SCALE), (int)(56 * 1.2 * Game.SCALE), (int)(14 * 1.8 * Game.SCALE), 0)
        );

        if (buttons.get(2) instanceof ButtonDetectedKey buttonDetectKey) {
            if (buttonDetectKey.getKeyCode() != player.getChangeGravityKey()){
                buttonDetectKey.setKeyCode(player.getChangeGravityKey());
            }
        }

        this.currentSkin = -1;
        this.currentSkinName = "None";

        this.playerPlaceholderImage = playerPlaceholderImage;

        player.setPosX(getXById() + 20 * Game.SCALE);
        player.setPosY(placeholderY + 20 * Game.SCALE);
        player.setDisableGravity(true);
        player.setDisableControls(true);
    }

    public void draw(Graphics g) {
        g.drawImage(playerPlaceholderImage, getXById(), placeholderY, placeholderWidth, placeholderHeight, null);
        for (IButton button : buttons) {
            button.draw(g);
        }
        g.setColor(Color.black);
        g.drawString(currentSkinName, (int)(getXById() + 30 * Game.SCALE), (int)(placeholderY + 117 * Game.SCALE));
        player.render(g);
    }

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
            if (buttonDetectKey.getKeyCode() != player.getChangeGravityKey()){
                player.setChangeGravityKey(buttonDetectKey.getKeyCode());
            }
        }
    }

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

    public void setCurrentSkin(int currentSkin, String currentSkinName) {
        this.currentSkin = currentSkin;
        this.currentSkinName = currentSkinName;
    }

    public void setReady(boolean isReady) {
        this.isReady = isReady;
    }
    public boolean isReady() {
        return isReady;
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
        System.out.println(currentSkinName);
    }

    private void setPrevSkin() {
        currentSkin--;

        if (currentSkin <= -1) {
            currentSkin = -1;
        }

        currentSkinName = player.getPlayerAnimator().getAnimationStates().keySet().toArray()[Math.abs(currentSkin)].toString();
        System.out.println(currentSkinName);
    }

}
