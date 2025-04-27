package me.xmertsalov.world;

import me.xmertsalov.gameObjects.Tile;
import me.xmertsalov.Game;
import me.xmertsalov.gameObjects.GameObject;
import me.xmertsalov.gameObjects.saws.MovableSaw;

import java.awt.*;
import java.util.ArrayList;

/**
 * Represents a level in the game, containing tiles and game objects.
 * Handles rendering, updating, and copying of levels.
 */
public class Level {
    // Level coordinates of x
    private double xOffset = 0;
    private double xOffsetVelocity = 0;

    // level parameters
    private String params;

    // Objects
    private ArrayList<Tile> lvlData;
    private ArrayList<GameObject> gameObjects; // all game objects without tiles

    // dependencies
    private final LevelsManager levelsManager;

    /**
     * Constructor for creating a level with specific tiles and parameters.
     *
     * @param levelsManager The manager responsible for handling levels.
     * @param lvlData       The list of tiles in the level.
     * @param params        Additional parameters for the level.
     */
    public Level(LevelsManager levelsManager, ArrayList<Tile> lvlData, String params) {
        this.lvlData = lvlData;
        this.levelsManager = levelsManager;
        this.params = params;
        this.gameObjects = new ArrayList<>();
    }

    /**
     * Creates a copy of the current level, including its tiles and game objects.
     *
     * @return A new Level instance that is a copy of the current level.
     */
    public Level copyLevel() {
        Level newLevel = new Level(levelsManager, params);
        for (Tile tile : this.lvlData) {
            newLevel.setTile(tile.getX(), tile.getY(), tile.getTileIndex(), tile.getTileType());
        }
        for (GameObject gameObject : this.gameObjects) {
            GameObject clone = gameObject.clone();
            newLevel.setGameObject(clone);
        }
        return newLevel;
    }

    /**
     * Constructor for creating an empty level with parameters.
     *
     * @param levelsManager The manager responsible for handling levels.
     * @param params        Additional parameters for the level.
     */
    public Level(LevelsManager levelsManager, String params) {
        this.lvlData = new ArrayList<Tile>(Game.TILES_IN_WIDTH * Game.TILES_IN_HEIGHT);
        this.levelsManager = levelsManager;
        this.params = params;
        this.gameObjects = new ArrayList<>();
    }

    /**
     * Adds a tile to the level at the specified position with the given sprite index and type.
     *
     * @param x           The x-coordinate of the tile.
     * @param y           The y-coordinate of the tile.
     * @param spriteIndex The index of the sprite for the tile.
     * @param tileType    The type of the tile.
     */
    public void setTile(double x, double y, int spriteIndex, String tileType) {
        lvlData.add(new Tile(x, y, spriteIndex, tileType));
    }

    /**
     * Renders the level, including all tiles and game objects.
     *
     * @param g The Graphics object used for rendering.
     */
    public void render(Graphics g) {
        for (Tile tile : lvlData) {
            int index = Math.max(tile.getTileIndex() - 1, 0);
            tile.setImage(levelsManager.getLevelSprite(index));
            tile.draw(g);

            // broken tiles
            if (index == 0 && Game.DEBUG_COLLIDERS) {
                g.setColor(Color.RED);
                g.fillRect((int) tile.getX(), (int) tile.getY(), Game.TILES_SIZE, Game.TILES_SIZE);
            }
        }
        for (GameObject gameObject : gameObjects) {
            gameObject.draw(g);
        }
    }

    /**
     * Updates the level, including the positions of tiles and game objects.
     */
    public void update() {
        for (Tile tile : lvlData) {
            tile.getTilePhisicsComponents().setVelocity_x(xOffsetVelocity);
            tile.update();
        }
        for (GameObject gameObject : gameObjects) {
            gameObject.updatePos(gameObject.getX() + xOffsetVelocity, gameObject.getY());

            if (gameObject instanceof MovableSaw) {
                ((MovableSaw) gameObject).setVelocityOffset(xOffsetVelocity);
            }
        }
        xOffset += xOffsetVelocity;
    }

    /**
     * Gets the current x-offset velocity of the level.
     *
     * @return The x-offset velocity.
     */
    public double getXOffsetVelocity() {
        return this.xOffsetVelocity;
    }

    /**
     * Sets the x-offset velocity of the level and updates all tiles and game objects accordingly.
     *
     * @param xOffsetVelocity The new x-offset velocity.
     */
    public void setXOffsetVelocity(double xOffsetVelocity) {
        this.xOffsetVelocity = xOffsetVelocity;

        for (Tile tile : lvlData) {
            tile.getTilePhisicsComponents().setVelocity_x(xOffsetVelocity);
            tile.update();
        }
        for (GameObject gameObject : gameObjects) {
            gameObject.updatePos(gameObject.getX() + xOffsetVelocity, gameObject.getY());
            gameObject.update();

            if (gameObject instanceof MovableSaw) {
                ((MovableSaw) gameObject).setVelocityOffset(xOffsetVelocity);
            }
        }
        xOffset += xOffsetVelocity;
    }

    /**
     * Gets the current x-offset of the level.
     *
     * @return The x-offset.
     */
    public double getXOffset() {
        return this.xOffset;
    }

    /**
     * Sets the x-offset of the level and updates the positions of all tiles and game objects.
     *
     * @param xOffset The new x-offset.
     */
    public void setXOffset(double xOffset) {
        this.xOffset = xOffset;
        for (Tile tile : lvlData) {
            tile.updatePos(tile.getX() + xOffset, tile.getY());
        }
        for (GameObject gameObject : gameObjects) {
            gameObject.updatePos(gameObject.getX() + xOffset, gameObject.getY());
        }
    }

    /**
     * Gets the list of tiles in the level.
     *
     * @return The list of tiles.
     */
    public ArrayList<Tile> getTiles() {
        return lvlData;
    }

    /**
     * Gets the parameters of the level.
     *
     * @return The parameters as a string.
     */
    public String getParams() {
        return params;
    }

    /**
     * Adds a game object to the level.
     *
     * @param gameObject The game object to add.
     */
    public void setGameObject(GameObject gameObject) {
        this.gameObjects.add(gameObject);
    }

    /**
     * Gets the list of game objects in the level.
     *
     * @return The list of game objects.
     */
    public ArrayList<GameObject> getGameObjects() {
        return gameObjects;
    }
}