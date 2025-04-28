package me.xmertsalov.world;

import me.xmertsalov.Game;
import me.xmertsalov.exceptions.BundleLoadException;
import me.xmertsalov.exceptions.LevelLoadingException;
import me.xmertsalov.gameObjects.powerUps.SpeedDown;
import me.xmertsalov.gameObjects.powerUps.SpeedUp;
import me.xmertsalov.gameObjects.saws.MovableSaw;
import me.xmertsalov.gameObjects.saws.Saw;
import me.xmertsalov.scenes.inGame.PlayingScene;
import me.xmertsalov.utils.Aggregation;
import me.xmertsalov.utils.BundleLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Manages the levels in the game, including loading, rendering, updating, and generating levels.
 */
public class LevelsManager {
    // General
    private PlayingScene playingScene;
    private LevelLoader levelLoader;

    // Images
    private BufferedImage[] tilesetAtlas;

    // Constants
    private final int maxCurrentLevels = 3;
    private double speed = 0.8f * Game.SCALE;
    private final static double SECONDS_TO_SPEED = 6;

    // Storage
    private ArrayList<Level> levels; // unique levels

    private ArrayList<Level> spawnLevels; // unique levels, but only in the start of game

    private ArrayList<Level> activeLevels;

    private ArrayList<Level> toRemoveActiveLevels;
    private ArrayList<Level> toAddActiveLevels;

    // Other
    private int ticksBeforeIncreaseSpeed = 0;

    /**
     * Constructor for the LevelsManager.
     *
     * @param playingScene The scene in which the levels are managed.
     */
    public LevelsManager(PlayingScene playingScene) {
        this.playingScene = playingScene;

        levels = new ArrayList<>();
        spawnLevels = new ArrayList<>();

        importTilesetAtlas();

        levelLoader = new LevelLoader(this);

        this.levels = levelLoader.getLevels();
        this.spawnLevels = levelLoader.getSpawnLevels();

        activeLevels = new ArrayList<>();
        toRemoveActiveLevels = new ArrayList<>();
        toAddActiveLevels = new ArrayList<>();
    }

    /**
     * Renders all active levels.
     *
     * @param g The Graphics object used for rendering.
     */
    public void render(Graphics g, int zIndex) {
        ArrayList<Level> levelsToRender = new ArrayList<>(activeLevels);

        for (Level level : levelsToRender) {
            level.render(g, zIndex);
        }
    }

    /**
     * Updates the levels, including generating new levels and moving existing ones.
     */
    public void update() {
        generateLevels();

        if (playingScene.isIncreasedGameSpeedMode()) increaseSpeed();

        moveLevels(-speed);

        toAddActiveLevels.clear();
        toRemoveActiveLevels.clear();
    }

    /**
     * Imports the tileset atlas from a sprite sheet.
     */
    private void importTilesetAtlas() {
        BufferedImage img = null;
        try {
            img = BundleLoader.getSpriteAtlas(BundleLoader.TILESET_ATLAS);
        } catch (BundleLoadException e) {
            Game.logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
        int columns = img.getWidth() / Game.TILES_DEFAULT_SIZE;
        int rows = img.getHeight() / Game.TILES_DEFAULT_SIZE;

        if (img.getWidth() % Game.TILES_DEFAULT_SIZE != 0 || img.getHeight() % Game.TILES_DEFAULT_SIZE != 0) {
            Game.logger.warn("Tileset image dimensions are not divisible by the tile size.");
        }

        tilesetAtlas = new BufferedImage[columns * rows];
        for (int j = 0; j < rows; j++)
            for (int i = 0; i < columns; i++) {
                int x = i * Game.TILES_DEFAULT_SIZE;
                int y = j * Game.TILES_DEFAULT_SIZE;

                try{
                    int index = j * columns + i;
                    tilesetAtlas[index] = img.getSubimage(x, y, Game.TILES_DEFAULT_SIZE, Game.TILES_DEFAULT_SIZE);
                } catch (Exception e) {
                    Game.logger.error(e.getMessage());
                }
            }
    }


    /**
     * Generates spawn levels based on the number of players.
     *
     * @param playerCount The number of players.
     */
    public void generateSpawnLevel(int playerCount) {
        if (activeLevels.isEmpty()) { // game have been started
            // add spawn levels

            ArrayList<String> spawnLevelNames = new ArrayList<>();
            spawnLevelNames.add("spawn_p1");
            spawnLevelNames.add("spawn_p2");
            spawnLevelNames.add("spawn_p3");
            spawnLevelNames.add("spawn_p4");

            for (Level level : spawnLevels) {
                if (level.getParams().startsWith(spawnLevelNames.get(Math.abs(playerCount - 1)) )) {
                    Game.logger.info("Spawn level added: {}", level.getParams());
                    Level newLevel = level.copyLevel();
                    newLevel.setXOffset(activeLevels.size() * Game.TILES_SIZE * Game.TILES_IN_WIDTH);
                    activeLevels.add(newLevel);
                }
            }

        }
    }

    /**
     * Generates new levels if the number of active levels is below the maximum.
     */
    private void generateLevels() {
        if (activeLevels.size() < maxCurrentLevels) {
            addNewRandomLevel();
        }
        activeLevels.addAll(toAddActiveLevels);
    }

    /**
     * Moves all active levels by a specified velocity.
     *
     * @param velocity_x The velocity to move the levels.
     */
    private void moveLevels(double velocity_x) {
        for (Level level : activeLevels) {
            level.setXOffsetVelocity(velocity_x);

            if (level.getXOffset() <= Game.TILES_SIZE * Game.TILES_IN_WIDTH * -1){
                toRemoveActiveLevels.add(level);
            }
        }
        activeLevels.removeAll(toRemoveActiveLevels);
    }

    /**
     * Adds a new random level to the active levels.
     */
    private void addNewRandomLevel() {
        int maxLevel = levels.size();
        int random = Aggregation.getRandomNumber(0, maxLevel);

        Level newLevel = levels.get(random).copyLevel();
        newLevel.setXOffset(activeLevels.size() * Game.TILES_SIZE * Game.TILES_IN_WIDTH);

        toAddActiveLevels.add(newLevel);

        Game.logger.info("New level added: {}", random);
    }

    /**
     * Increases the speed of the levels over time.
     */
    private void increaseSpeed() {
        ticksBeforeIncreaseSpeed++;
        if (playingScene.isIncreasedGameSpeedMode() && ticksBeforeIncreaseSpeed >= Game.UPS_LIMIT * SECONDS_TO_SPEED) {

            if (speed >= 1.5) speed += 0.02 * Game.SCALE;
            else speed += 0.08f * Game.SCALE;

            ticksBeforeIncreaseSpeed = 0;
        }
    }

    /**
     * Resets the level manager, clearing active levels and resetting speed.
     */
    public void resetLevelManager() {
        activeLevels.clear();
        toRemoveActiveLevels.clear();
        toAddActiveLevels.clear();

        // reset speed
        if (playingScene.isSlowMode()) speed = 0.4f * Game.SCALE;
        else if (playingScene.isIncreasedGameSpeedMode()) speed = 0.7f * Game.SCALE;
        else speed = 0.8f * Game.SCALE;
    }

    /**
     * Gets a sprite from the tileset atlas by index.
     *
     * @param index The index of the sprite.
     * @return The sprite as a BufferedImage.
     */
    public BufferedImage getLevelSprite(int index) {
        return tilesetAtlas[index];
    }

    /**
     * Gets the list of active levels.
     *
     * @return The list of active levels.
     */
    public ArrayList<Level> getActiveLevels() {
        return activeLevels;
    }

    /**
     * Gets the list of all levels.
     *
     * @return The list of levels.
     */
    public ArrayList<Level> getLevels() {
        return levels;
    }

    /**
     * Gets the current speed of the levels.
     *
     * @return The speed.
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Sets the speed of the levels.
     *
     * @param speed The new speed.
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
