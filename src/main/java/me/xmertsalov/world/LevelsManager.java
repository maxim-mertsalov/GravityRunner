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

        try {
            String levelData = BundleLoader.getFileContent(BundleLoader.WORLD_DATA);
            parseLevelData(levelData);

        } catch (BundleLoadException | LevelLoadingException e) {
            Game.logger.error(e.getMessage());
            throw new RuntimeException(e);
        }

        activeLevels = new ArrayList<>();
        toRemoveActiveLevels = new ArrayList<>();
        toAddActiveLevels = new ArrayList<>();
    }

    /**
     * Renders all active levels.
     *
     * @param g The Graphics object used for rendering.
     */
    public void render(Graphics g) {
        ArrayList<Level> levelsToRender = new ArrayList<>(activeLevels);

        for (Level level : levelsToRender) {
            level.render(g);
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
     * Parses level data from a string and initializes levels.
     *
     * @param data The level data as a string.
     * @throws LevelLoadingException If the level data is invalid.
     */
    private void parseLevelData(String data) throws LevelLoadingException {
        String[] lines = data.split("\n");
        int levelCount = -1;

        for (String line : lines) {
            // Start of a new level: line like: #level-0,"Some data"
            if (line.startsWith("#level-")) {
                levelCount++;

                String[] levelData = line.split(",");

                String params = levelData[1].substring(1, levelData[1].length() - 1); // get params and remove quotes

                levels.add(new Level(this, params));
                continue;
            }

            // End of all levels
            if (line.startsWith("\n")) {
                break;
            }

            // if there are no level beginning string - incorrect data
            if (levelCount < 0) {
                throw new LevelLoadingException("Level data not found");
            }

            // Tile data: line like: <position X>,<position Y>,<TileType>,"<Special params>"
            {
                String[] values = line.split(","); // 0 - x, 1 - y, 2 - spriteIndex, 3 - params
                if (values.length < 4) {
                    throw new LevelLoadingException("Invalid level data");
                }
                int x = (Integer.parseInt(values[0]) - 1) * Game.TILES_SIZE;
                int y = (Integer.parseInt(values[1]) - 1) * Game.TILES_SIZE;
                int spriteIndex = Integer.parseInt(values[2]);
                String tileType = values[3].substring(1, values[3].length() - 1); // remove quotes

                // Special elements like entities, power-ups and other
                if (spriteIndex < 0) {
                    switch (tileType) {
                        case "SpeedUp" -> {
                            SpeedUp speedUp = new SpeedUp(x, y);
                            levels.get(levelCount).setGameObject(speedUp);
                        }
                        case "SpeedDown" -> {
                            SpeedDown speedDown = new SpeedDown(x, y);
                            levels.get(levelCount).setGameObject(speedDown);
                        }
                        case "Saw" -> {
                            Saw saw = new Saw(x, y);
                            levels.get(levelCount).setGameObject(saw);
                        }
                        case "MovableSaw" -> {
                            int dx = (Integer.parseInt(values[4])) * Game.TILES_SIZE;
                            int dy = (Integer.parseInt(values[5])) * Game.TILES_SIZE;
                            MovableSaw movableSaw = new MovableSaw(x, y, dx, dy);
                            levels.get(levelCount).setGameObject(movableSaw);
                        }
                    }
                    continue;
                }

                // Normal tile
                levels.get(levelCount).setTile(x, y, spriteIndex, tileType);
            }
        }

        Game.logger.info("Loaded {} levels", levels.size());

        for (Level level : levels) {
            if (level.getParams().startsWith("spawn")) {
                spawnLevels.add(level);
            }
        }
        levels.removeAll(spawnLevels);
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
