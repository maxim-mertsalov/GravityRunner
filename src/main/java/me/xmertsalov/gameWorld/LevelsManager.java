package me.xmertsalov.gameWorld;

import me.xmertsalov.Game;
import me.xmertsalov.utils.Agragation;
import me.xmertsalov.utils.BundleLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class LevelsManager {
    private Game game;
    private BufferedImage[] tilesetAtlas;
    private ArrayList<Level> levels = new ArrayList<Level>();

    private ArrayList<Level> activeLevels;
    private int maxCurrentLevels = 3;

    private ArrayList<Level> toRemoveActiveLevels;
    private ArrayList<Level> toAddActiveLevels;

    private boolean left, right;

    private final static double speed = 0.8f * Game.SCALE;

    public LevelsManager(Game game) {
        this.game = game;
        importTilesetAtlas();
        String levelData = BundleLoader.getFileContent(BundleLoader.WORLD_DATA);
        parseLevelData(levelData);

        activeLevels = new ArrayList<>();
        activeLevels.add(levels.get(1).copyLevel());
        toRemoveActiveLevels = new ArrayList<>();
        toAddActiveLevels = new ArrayList<>();
//        generateLevels();
    }

    private void importTilesetAtlas() {
        BufferedImage img = BundleLoader.getSpriteAtlas(BundleLoader.TILESET_ATLAS);
        int columns = img.getWidth() / Game.TILES_DEFAULT_SIZE;
        int rows = img.getHeight() / Game.TILES_DEFAULT_SIZE;
//        System.out.println("columns: " + columns + ", rows: " + rows);

        tilesetAtlas = new BufferedImage[columns * rows]; // columns * rows
        for (int j = 0; j < rows; j++)
            for (int i = 0; i < columns; i++) {
                int index = j * columns + i;
                tilesetAtlas[index] = img.getSubimage(i * Game.TILES_DEFAULT_SIZE, j * Game.TILES_DEFAULT_SIZE, Game.TILES_DEFAULT_SIZE, Game.TILES_DEFAULT_SIZE);
            }
    }

    private void parseLevelData(String data) {
        String[] lines = data.split("\n");
        int levelCount = -1;
//        ArrayList<Level> levels = new ArrayList<Level>(2);

        for (int i = 0; i < lines.length; i++) {
            if (lines[i].startsWith("#level-")) {
                levelCount++;
                levels.add(new Level(this));
                System.out.println("Level " + levelCount + " loaded");
                continue;
            }
            if (lines[i].startsWith("\n")) {
                break;
            }
            String[] values = lines[i].split(","); // 0 - x, 1 - y, 2 - spriteIndex
            if (values.length < 4) {
                throw new RuntimeException("Invalid level data");
            }
            int x = (Integer.parseInt(values[0]) - 1) * Game.TILES_SIZE;
            int y = (Integer.parseInt(values[1]) - 1) * Game.TILES_SIZE;
            int spriteIndex = Integer.parseInt(values[2]);
            String tileType = values[3].substring(1, values[3].length() - 1); // remove quotes

            levels.get(levelCount).setTile(x, y, spriteIndex, tileType);
        }
    }

    private void generateLevels() {
        if (activeLevels.size() < maxCurrentLevels) {
            addNewRandomLevel();
        }
        activeLevels.addAll(toAddActiveLevels);
    }

    private void moveLevels(double velocity_x){
        for (Level level : activeLevels) {
            level.setXOffsetVelocity(velocity_x);
//            System.out.println(level.getXOffsetVelocity());

            if (level.getXOffset() <= Game.TILES_SIZE * Game.TILES_IN_WIDTH * -1){
//                activeLevels.remove(level);
                toRemoveActiveLevels.add(level);
            }
        }
        activeLevels.removeAll(toRemoveActiveLevels);
    }

    private void addNewRandomLevel() {
        int maxLevel = levels.size();
        int random = new Agragation().getRandomNumber(0, maxLevel);

        Level newLevel = levels.get(random).copyLevel();
        newLevel.setXOffset(activeLevels.size() * Game.TILES_SIZE * Game.TILES_IN_WIDTH);

//        activeLevels.add(newLevel);
        toAddActiveLevels.add(newLevel);


        System.out.println("New level added: " + random);
    }

    public void render(Graphics g) {
//        activeLevels.get(1).render(g);

        ArrayList<Level> levelsToRender = new ArrayList<>(activeLevels);

        for (Level level : levelsToRender) {
            level.render(g);
        }
    }

    public void update() {
        generateLevels();

        moveLevels(-speed);


//        if (left) {
//            moveLevels(speed);
////            activeLevels.forEach(level -> {
////                level.setXOffsetVelocity(-speed);
////            });
//        }
//        else if (right) {
//            moveLevels(-speed);
////            activeLevels.forEach(level -> {
////                level.setXOffsetVelocity(speed);
////            });
//        }
//        else{
//            moveLevels(0);
////            activeLevels.forEach(level -> {
////                level.setXOffsetVelocity(0);
////            });
//        }

//        activeLevels.forEach(Level::update);
        for (Level level : toAddActiveLevels) {level.update();}

//        activeLevels.addAll(toAddActiveLevels);
//        activeLevels.removeAll(toRemoveActiveLevels);

        toAddActiveLevels.clear();
        toRemoveActiveLevels.clear();
    }

    public BufferedImage getLevelSprite(int index) {
        return tilesetAtlas[index];
    }

    public ArrayList<Level> getActiveLevels() {
        return activeLevels;
    }

    public ArrayList<Level> getLevels() {
        return levels;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }
    public void setRight(boolean right) {
        this.right = right;
    }

}
