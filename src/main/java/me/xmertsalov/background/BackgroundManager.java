package me.xmertsalov.background;

import me.xmertsalov.Game;
import me.xmertsalov.components.Animator;
import me.xmertsalov.scenes.inGame.PlayingScene;
import me.xmertsalov.utils.Agragation;
import me.xmertsalov.utils.BundleLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BackgroundManager {
    private BufferedImage background; // 1st layer

    private Cloud[] smallClouds; // 2nd layer
    private Cloud bigCloud; // 3rd layer

    private Animator waterAnimator;
    private double waterSizeK = 2f;

    private PlayingScene playingScene;

    private final int MAX_SMALL_CLOUDS = 4;

    private ArrayList<Cloud> smallCloudsList;
    private ArrayList<Cloud> bigCloudsList;

    private ArrayList<Cloud> toAddSmallCloudsList;
    private ArrayList<Cloud> toRemoveSmallCloudsList;

    private ArrayList<Cloud> toAddBigCloudsList;
    private ArrayList<Cloud> toRemoveBigCloudsList;

    private double smallCloudsSpeed = 0.9f * Game.SCALE;
    private double bigCloudsSpeed = 1.2f * Game.SCALE;

    int smallCloudsTicks = 0;


    public BackgroundManager(PlayingScene playingScene) {
        this.playingScene = playingScene;

        smallCloudsList = new ArrayList<>();
        bigCloudsList = new ArrayList<>();
        toAddSmallCloudsList = new ArrayList<>();
        toRemoveSmallCloudsList = new ArrayList<>();
        toAddBigCloudsList = new ArrayList<>();
        toRemoveBigCloudsList = new ArrayList<>();

        HashMap<String, List<Integer>> waterStates = new HashMap<>();
        waterStates.put("WATER", List.of(0, 4));

        waterAnimator = new Animator(BundleLoader.WATER_REFLECTS_ATLAS,
                170,
                10,
                1,
                4,
                waterStates,
                "WATER",
                -1);

        loadAssets();
    }

    public void update() {
        generateSmallClouds();
        generateBigClouds();
        for (Cloud smallCloud : smallCloudsList) {
            smallCloud.update();
        }
        for (Cloud bigCloud : bigCloudsList) {
            bigCloud.update();
        }
        waterAnimator.update();
    }

    public void draw(Graphics g) {
        g.drawImage(background, 0, 0, Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT, null);
        renderSmallClouds(g);
        renderBigClouds(g);
        waterAnimator.draw(g, (Game.WINDOW_WIDTH - (170 * waterSizeK * Game.SCALE)) / 2, (int) (Game.WINDOW_HEIGHT * 0.825), (int)(170 * waterSizeK * Game.SCALE), (int) (10 * waterSizeK * Game.SCALE));
    }

    private void renderBigClouds(Graphics g) {
        for (Cloud bigCloud : bigCloudsList) {
            bigCloud.draw((Graphics2D) g);
        }
    }

    private void generateBigClouds(){
        if (bigCloudsList.size() < (Game.WINDOW_WIDTH / bigCloud.getWidth()) * 2) {
            Cloud newCloud = bigCloud.clone();
            newCloud.setVelocity_x(-bigCloudsSpeed);
            newCloud.setY(Game.WINDOW_HEIGHT * 0.37);
            if (bigCloudsList.isEmpty()) {
                newCloud.setX(0);
            } else {
                Cloud lastCloud = bigCloudsList.get(bigCloudsList.size() - 1);
                newCloud.setX(lastCloud.getX() + lastCloud.getWidth() - 1); // Subtract 1 to overlap slightly
            }
            toAddBigCloudsList.add(newCloud);
        }

        for (Cloud bigCloud : bigCloudsList) {
            if (bigCloud.getX() + bigCloud.getWidth() <= 0) {
                toRemoveBigCloudsList.add(bigCloud);
            }
        }

        bigCloudsList.addAll(toAddBigCloudsList);
        toAddBigCloudsList.clear();

        bigCloudsList.removeAll(toRemoveBigCloudsList);
        toRemoveBigCloudsList.clear();
    }

    private void renderSmallClouds(Graphics g) {
        for (Cloud smallCloud : smallCloudsList) {
            smallCloud.draw((Graphics2D) g);
        }
    }

    private void generateSmallClouds(){
        int randomCloud = Agragation.getRandomNumber(0, smallClouds.length - 1);
        int randomY = Agragation.getRandomNumber((int) (Game.WINDOW_HEIGHT * 0.05), (int) (Game.WINDOW_HEIGHT * 0.5));

        smallCloudsTicks++;

        if (smallCloudsList.size() < MAX_SMALL_CLOUDS && smallCloudsTicks == Game.UPS_LIMIT * 1.5) {
            Cloud newCloud = smallClouds[randomCloud].clone();
            newCloud.setVelocity_x(-smallCloudsSpeed);
            newCloud.setY(randomY);
            toAddSmallCloudsList.add(newCloud);

            smallCloudsTicks = 0;
        }

        for (Cloud smallCloud : smallCloudsList) {
            if (smallCloud.getX() + smallCloud.getWidth() <= 0) {
                toRemoveSmallCloudsList.add(smallCloud);
            }
        }

        smallCloudsList.addAll(toAddSmallCloudsList);
        toAddSmallCloudsList.clear();

        smallCloudsList.removeAll(toRemoveSmallCloudsList);
        toRemoveSmallCloudsList.clear();
    }

    private void loadAssets() {
        background = BundleLoader.getSpriteAtlas(BundleLoader.BACKGROUND_ATLAS);

        smallClouds = new Cloud[3];

        smallClouds[0] = new Cloud(Game.WINDOW_WIDTH, 0, 0, BundleLoader.getSpriteAtlas(BundleLoader.SMALL_CLOUDS_1_ATLAS));
        smallClouds[1] = new Cloud(Game.WINDOW_WIDTH, 0, 0, BundleLoader.getSpriteAtlas(BundleLoader.SMALL_CLOUDS_2_ATLAS));
        smallClouds[2] = new Cloud(Game.WINDOW_WIDTH, 0, 0, BundleLoader.getSpriteAtlas(BundleLoader.SMALL_CLOUDS_3_ATLAS));

        bigCloud = new Cloud(Game.WINDOW_WIDTH, 0, 0, BundleLoader.getSpriteAtlas(BundleLoader.BIG_CLOUDS_ATLAS));
    }
}

