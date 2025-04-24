package me.xmertsalov.world;

import me.xmertsalov.gameObjects.Tile;
import me.xmertsalov.Game;
import me.xmertsalov.gameObjects.GameObject;
import me.xmertsalov.gameObjects.saws.MovableSaw;

import java.awt.*;
import java.util.ArrayList;

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

    public Level(LevelsManager levelsManager, ArrayList<Tile> lvlData, String params) {
        this.lvlData = lvlData;
        this.levelsManager = levelsManager;
        this.params = params;
        this.gameObjects = new ArrayList<>();
    }

    public Level copyLevel(){
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

    public Level(LevelsManager levelsManager, String params) {
//        this.lvlData = new int[Game.TILES_IN_HEIGHT][Game.TILES_IN_WIDTH];
        this.lvlData = new ArrayList<Tile>(Game.TILES_IN_WIDTH * Game.TILES_IN_HEIGHT);
        this.levelsManager = levelsManager;
        this.params = params;
        this.gameObjects = new ArrayList<>();
    }

    // set relative position of the tile
    public void setTile(double x, double y, int spriteIndex, String tileType) {
        lvlData.add(new Tile(x, y, spriteIndex, tileType));
    }

    public void render(Graphics g) {
        for (Tile tile : lvlData) {
            int index = Math.max(tile.getTileIndex() - 1, 0);
            tile.render(g, levelsManager.getLevelSprite(index));
        }
        for (GameObject gameObject : gameObjects) {
            gameObject.draw(g);
        }
    }

    public void update() {
        for (Tile tile : lvlData) {
//            tile.updatePos(tile.getX() + xOffsetVelocity, tile.getY());
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

    public double getXOffsetVelocity() {
        return this.xOffsetVelocity;
    }

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

    public double getXOffset() {
        return this.xOffset;
    }

    public void setXOffset(double xOffset) {
        this.xOffset = xOffset;
        for (Tile tile : lvlData) {
            tile.updatePos(tile.getX() + xOffset, tile.getY());
        }
        for (GameObject gameObject : gameObjects) {
            gameObject.updatePos(gameObject.getX() + xOffset, gameObject.getY());
        }
    }

    public ArrayList<Tile> getTiles(){
        return lvlData;
    }

    public String getParams() {
        return params;
    }

    public void setGameObject(GameObject gameObject) {
        this.gameObjects.add(gameObject);
    }

    public ArrayList<GameObject> getGameObjects() {
        return gameObjects;
    }
}