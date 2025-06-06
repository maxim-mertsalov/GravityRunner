package me.xmertsalov.ui;

import me.xmertsalov.Game;
import me.xmertsalov.audio.AudioPlayer;
import me.xmertsalov.exceptions.BundleLoadException;
import me.xmertsalov.scenes.inGame.PlayingScene;
import me.xmertsalov.utils.BundleLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * The CountDownPanel class manages the countdown UI before the game starts.
 * It displays countdown numbers and plays corresponding sound effects.
 */
public class CountDownPanel {

    // Dependencies
    private PlayingScene playingScene;

    // Images
    BufferedImage[] countDownImages;

    // Constants
    private final int ticksPerSecond = (int)(Game.UPS_LIMIT * 1.2);
    private final double k = 3.5;

    // States
    private int currentTime = 0; // 0, 1, 2, 3
    private boolean show;
    private boolean setMove = false;
    private boolean sounded = false;

    // Other
    private int ticks;

    // UI Settings
    int width = (int)(22 * k * Game.SCALE);
    int height = (int)(11 * k * Game.SCALE);

    /**
     * Constructs a CountDownPanel instance.
     *
     * @param playingScene The PlayingScene instance to which this panel belongs.
     */
    public CountDownPanel(PlayingScene playingScene) {
        this.playingScene = playingScene;

        currentTime = 0;
        ticks = 0;

        loadImages();
    }

    /**
     * Updates the state of the countdown panel.
     * Handles the countdown logic and triggers game start events.
     */
    public void update() {
        if (show) {
            if (!sounded) {
                playingScene.getGame().getAudioPlayer().playSfx(AudioPlayer.SFX_COUNTDOWN_F);
                sounded = true;
            }
            ticks++;
            if (ticks >= ticksPerSecond) {
                ticks = 0;

                if (currentTime == 3) playingScene.getGame().getAudioPlayer().playSfx(AudioPlayer.SFX_COUNTDOWN_S);
                else if(currentTime != 4) playingScene.getGame().getAudioPlayer().playSfx(AudioPlayer.SFX_COUNTDOWN_F);

                Game.logger.info("Game starts in {} seconds", 4 - currentTime);
                currentTime++;
            }
        }
        else currentTime = 0;

        if (!setMove && currentTime >= 3 && ticks >= ticksPerSecond * 0.9) {
            playingScene.setCanMove(true);
            playingScene.setContinuedSpeed(true);
            setMove = true;
        }

        if (currentTime > 4) {
            currentTime = 4;
            setMove = false;
            show = false;
        }
    }

    /**
     * Renders the countdown panel components on the screen.
     *
     * @param g The Graphics object used for drawing.
     */
    public void draw(Graphics g) {
        if (show && currentTime != 0) {
            g.drawImage(countDownImages[currentTime - 1],
                    (int)(Game.WINDOW_WIDTH / 2 - width / 2),
                    (int)(Game.WINDOW_HEIGHT / 2 - height / 2),
                    width, height, null);
        }
    }

    /**
     * Loads the images used in the countdown panel.
     */
    private void loadImages(){
        BufferedImage image = null;
        try {
            image = BundleLoader.getSpriteAtlas(BundleLoader.COUNT_DOWN_ATLAS);
        } catch (BundleLoadException e) {
            Game.logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
        countDownImages = new BufferedImage[4];

        int spriteWidth = 22;
        int spriteHeight = 11;
        for (int i = 0; i < countDownImages.length; i++) {
            countDownImages[i] = image.getSubimage(
                    spriteWidth * i,
                    0,
                    spriteWidth,
                    spriteHeight
            );
        }
    }

    /**
     * Sets whether the countdown panel should be displayed.
     *
     * @param show True to display the panel, false to hide it.
     */
    public void setShow(boolean show) {this.show = show;}
}
