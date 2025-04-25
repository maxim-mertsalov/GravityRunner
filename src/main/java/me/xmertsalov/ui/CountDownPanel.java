package me.xmertsalov.ui;

import me.xmertsalov.Game;
import me.xmertsalov.components.Animator;
import me.xmertsalov.scenes.inGame.PlayingScene;
import me.xmertsalov.utils.BundleLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;

public class CountDownPanel {
    private PlayingScene playingScene;

    private int currentTime = 0; // 0, 1, 2, 3

    private int ticks;
    private final int ticksPerSecond = (int)(Game.UPS_LIMIT * 1.2);

    BufferedImage[] countDownImages;

    private boolean show;
    private boolean setMove = false;

    double k = 3.5;

    int width = (int)(22 * k * Game.SCALE);
    int height = (int)(11 * k * Game.SCALE);

    public CountDownPanel(PlayingScene playingScene) {
        this.playingScene = playingScene;
        currentTime = 0;
        ticks = 0;

        loadImages();
    }

    public void update() {
        if (show) {
            ticks++;
            if (ticks >= ticksPerSecond) {
                ticks = 0;
                System.out.println(3 - currentTime);
                currentTime++;
            }
        }
        else currentTime = 0;

        if (!setMove && currentTime == 2 && ticks >= ticksPerSecond / 1.1) {
            playingScene.setCanMove(true);
            playingScene.setContinuedSpeed(true);
            setMove = true;
        }

        if (currentTime > 3) {
            currentTime = 3;
            setMove = false;
            show = false;
        }
    }

    public void draw(Graphics g) {
        if (show) {
            g.drawImage(countDownImages[currentTime],
                    (int)(Game.WINDOW_WIDTH / 2 - width / 2),
                    (int)(Game.WINDOW_HEIGHT / 2 - height / 2),
                    width, height, null);
        }
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    private void loadImages(){
        BufferedImage image = BundleLoader.getSpriteAtlas(BundleLoader.COUNT_DOWN_ATLAS);
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
}
