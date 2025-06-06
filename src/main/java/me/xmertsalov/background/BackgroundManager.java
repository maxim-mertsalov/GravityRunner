package me.xmertsalov.background;

import me.xmertsalov.Game;
import me.xmertsalov.components.Animator.Animator;
import me.xmertsalov.components.Animator.LoopingAnimationStrategy;
import me.xmertsalov.exceptions.BundleLoadException;
import me.xmertsalov.utils.Aggregation;
import me.xmertsalov.utils.BundleLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * The {@code BackgroundManager} class is responsible for managing and rendering the background elements
 * of the game, including clouds and water animations. It handles the generation, updating, and rendering
 * of both small and big clouds, as well as the water animation.
 */
public class BackgroundManager {
    // Dependencies
    private final Animator waterAnimator;

    // Images
    private BufferedImage background; // 1st layer
    private Cloud[] smallClouds; // 2nd layer
    private Cloud bigCloud; // 3rd layer

    // Constants
    private final double waterSizeK = 2f;
    private final int MAX_SMALL_CLOUDS = 4;

    // Storage
    private ArrayList<Cloud> smallCloudsList;
    private ArrayList<Cloud> bigCloudsList;

    private ArrayList<Cloud> toAddSmallCloudsList;
    private ArrayList<Cloud> toRemoveSmallCloudsList;

    private ArrayList<Cloud> toAddBigCloudsList;
    private ArrayList<Cloud> toRemoveBigCloudsList;

    // Position settings
    private double smallCloudsSpeed = 0.15f * Game.SCALE;
    private double bigCloudsSpeed = 0.45f * Game.SCALE;

    // Other
    int smallCloudsTicks = 0;

    /**
     * Constructs a new {@code BackgroundManager} instance. Initializes the cloud lists, loads assets,
     * and sets up the water animation.
     */
    public BackgroundManager() {
        smallCloudsList = new ArrayList<>();
        bigCloudsList = new ArrayList<>();
        toAddSmallCloudsList = new ArrayList<>();
        toRemoveSmallCloudsList = new ArrayList<>();
        toAddBigCloudsList = new ArrayList<>();
        toRemoveBigCloudsList = new ArrayList<>();

        HashMap<String, List<Integer>> waterStates = new HashMap<>();
        waterStates.put("WATER", Arrays.asList(0, 4));

        waterAnimator = new Animator.Builder()
                .setImageURL(BundleLoader.WATER_REFLECTS_ATLAS)
                .setSpriteWidth(170)
                .setSpriteHeight(10)
                .setRows(1)
                .setColumns(4)
                .setAnimationStates(waterStates)
                .setCurrentState("WATER")
                .setAnimationSpeed(-1)
                .setAnimationStrategy(new LoopingAnimationStrategy())
                .build_and_load();

        loadAssets();
    }

    /**
     * Updates the state of the background elements. This includes generating new clouds,
     * updating the position of existing clouds, and updating the water animation.
     */
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

    /**
     * Renders the background elements onto the provided {@link Graphics} object.
     * This includes the background image, clouds, and water animation.
     *
     * @param g the {@link Graphics} object used for rendering
     */
    public void draw(Graphics g) {
        g.drawImage(background, 0, 0, Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT, null);
        renderSmallClouds(g);
        renderBigClouds(g);
        waterAnimator.draw(g, (Game.WINDOW_WIDTH - (170 * waterSizeK * Game.SCALE)) / 2, (int) (Game.WINDOW_HEIGHT * 0.825), (int)(170 * waterSizeK * Game.SCALE), (int) (10 * waterSizeK * Game.SCALE));
    }

    /**
     * Renders the big clouds onto the provided {@link Graphics} object.
     *
     * @param g the {@link Graphics} object used for rendering
     */
    private void renderBigClouds(Graphics g) {
        List<Cloud> cloudsCopy = new ArrayList<>(bigCloudsList);
        for (Cloud bigCloud : cloudsCopy) {
            bigCloud.draw((Graphics2D) g);
        }
    }

    /**
     * Generates new big clouds if the current number of clouds is below the required threshold.
     * Also removes clouds that have moved out of the screen.
     */
    private void generateBigClouds() {
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

    /**
     * Renders the small clouds onto the provided {@link Graphics} object.
     *
     * @param g the {@link Graphics} object used for rendering
     */
    private void renderSmallClouds(Graphics g) {
        for (Cloud smallCloud : smallCloudsList) {
            smallCloud.draw((Graphics2D) g);
        }
    }

    /**
     * Generates new small clouds at random positions if the current number of clouds is below
     * the maximum allowed. Also removes clouds that have moved out of the screen.
     */
    private void generateSmallClouds() {
        int randomCloud = Aggregation.getRandomNumber(0, smallClouds.length - 1);
        int randomY = Aggregation.getRandomNumber((int) (Game.WINDOW_HEIGHT * 0.05), (int) (Game.WINDOW_HEIGHT * 0.5));
        int randomX = Aggregation.getRandomNumber((int) (Game.WINDOW_WIDTH * 0.01), (int) (Game.WINDOW_WIDTH * 0.1));

        smallCloudsTicks++;

        if (smallCloudsList.size() < MAX_SMALL_CLOUDS && smallCloudsTicks == Game.UPS_LIMIT * 4) {
            Cloud newCloud = smallClouds[randomCloud].clone();
            newCloud.setX(Game.WINDOW_WIDTH + randomX);
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

    /**
     * Loads the assets required for the background, including the background image,
     * small clouds, and big clouds. Throws a {@link RuntimeException} if any asset fails to load.
     */
    private void loadAssets() {
        smallClouds = new Cloud[3];

        try {
            background = BundleLoader.getSpriteAtlas(BundleLoader.BACKGROUND_ATLAS);

            smallClouds[0] = new Cloud(Game.WINDOW_WIDTH, 0, 0, BundleLoader.getSpriteAtlas(BundleLoader.SMALL_CLOUDS_1_ATLAS));
            smallClouds[1] = new Cloud(Game.WINDOW_WIDTH, 0, 0, BundleLoader.getSpriteAtlas(BundleLoader.SMALL_CLOUDS_2_ATLAS));
            smallClouds[2] = new Cloud(Game.WINDOW_WIDTH, 0, 0, BundleLoader.getSpriteAtlas(BundleLoader.SMALL_CLOUDS_3_ATLAS));

            bigCloud = new Cloud(Game.WINDOW_WIDTH, 0, 0, BundleLoader.getSpriteAtlas(BundleLoader.BIG_CLOUDS_ATLAS));
        }
        catch (BundleLoadException e) {
            Game.logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
