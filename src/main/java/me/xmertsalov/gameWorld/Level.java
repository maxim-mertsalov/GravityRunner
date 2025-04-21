package me.xmertsalov.gameWorld;

import me.xmertsalov.gameObjects.Tile;
import me.xmertsalov.Game;

import java.awt.*;
import java.util.ArrayList;

public class Level {
    private double xOffset = 0;
    private double xOffsetVelocity = 0;

    private ArrayList<Tile> lvlData;
    private LevelsManager levelsManager;

    public Level(LevelsManager levelsManager, ArrayList<Tile> lvlData) {
        this.lvlData = lvlData;
        this.levelsManager = levelsManager;
    }

    public Level copyLevel(){
        Level newLevel = new Level(levelsManager);
        for (Tile tile : this.lvlData) {
            newLevel.setTile(tile.getX(), tile.getY(), tile.getTileIndex(), tile.getTileType());
        }
        return newLevel;
    }

    public Level(LevelsManager levelsManager) {
//        this.lvlData = new int[Game.TILES_IN_HEIGHT][Game.TILES_IN_WIDTH];
        this.lvlData = new ArrayList<Tile>(Game.TILES_IN_WIDTH * Game.TILES_IN_HEIGHT);
        this.levelsManager = levelsManager;
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
    }

    public void update() {
        for (Tile tile : lvlData) {
//            tile.updatePos(tile.getX() + xOffsetVelocity, tile.getY());
            tile.getTilePhisicsComponents().setVelocity_x(xOffsetVelocity);
            tile.update();
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
    }

    public ArrayList<Tile> getTiles(){
        return lvlData;
    }
}