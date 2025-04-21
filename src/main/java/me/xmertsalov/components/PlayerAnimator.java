package me.xmertsalov.components;

import me.xmertsalov.entities.Player;
import me.xmertsalov.utils.BundleLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

import static me.xmertsalov.components.PlayerState.*;

public class PlayerAnimator {
    private BufferedImage[][] animations;
    private int aniTick, aniIndex, aniSpeed = 25;
    private int playerState = RUNNING;//IDLE;
    private Player player;

    public PlayerAnimator(Player player) {
        this.player = player;
        this.playerState = RUNNING;//IDLE;
        loadAnimations();
    }

    public void updateAnimationTick(int playerStateL) {
        this.playerState = playerStateL;
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= getSpriteAnimTicks(playerState)) {
                aniIndex = 0;
//                attacking = false;
                player.setAttacking(false);
            }

        }
    }

    public void render(Graphics g, double x, double y, int width, int height) {
        BufferedImage sprite = animations[playerState][aniIndex];

        g.drawImage(sprite, //animations[playerState][aniIndex]
                (int) x, (int) y,
                width, height, null); // 256 x 160
    }

    public void resetAnimTick() {
        aniTick = 0;
        aniIndex = 0;
    }

    public void loadAnimations() {
        BufferedImage img = BundleLoader.getSpriteAtlas(BundleLoader.PIRATE_ATLAS);

        int spriteWidth = 64;
        int spriteHeight = 40;
        int rows = 9;
        int cols = 6;

        int imgWidth = img.getWidth();
        int imgHeight = img.getHeight();

        animations = new BufferedImage[rows][cols];
        for (int j = 0; j < rows; j++) {
            for (int i = 0; i < cols; i++) {
                int x = i * spriteWidth;
                int y = j * spriteHeight;
                if (x + spriteWidth <= imgWidth && y + spriteHeight <= imgHeight) {
                    animations[j][i] = img.getSubimage(x, y, spriteWidth, spriteHeight);
                } else {
                    System.err.println("Subimage coordinates are out of bounds: (" + x + ", " + y + ")");
                }
            }
        }
    }
}
