package me.xmertsalov.world;

import me.xmertsalov.Game;
import me.xmertsalov.gameObjects.powerUps.SpeedDown;
import me.xmertsalov.gameObjects.powerUps.SpeedUp;
import me.xmertsalov.gameObjects.saws.MovableSaw;
import me.xmertsalov.gameObjects.saws.Saw;
import me.xmertsalov.scenes.inGame.PlayingScene;
import me.xmertsalov.utils.Agragation;
import me.xmertsalov.utils.BundleLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class LevelsManager {
    private PlayingScene playingScene;
    private BufferedImage[] tilesetAtlas;
    private ArrayList<Level> levels = new ArrayList<Level>();

    private ArrayList<Level> spawnLevels = new ArrayList<Level>(); // only in the start of game


    private ArrayList<Level> activeLevels;
    private int maxCurrentLevels = 3;

    private ArrayList<Level> toRemoveActiveLevels;
    private ArrayList<Level> toAddActiveLevels;

    private boolean left, right;

    private final static double speed = 0.8f * Game.SCALE;

    public LevelsManager(PlayingScene playingScene) {
        this.playingScene = playingScene;
        importTilesetAtlas();
        String levelData = BundleLoader.getFileContent(BundleLoader.WORLD_DATA);
        parseLevelData(levelData);

        activeLevels = new ArrayList<>();
        toRemoveActiveLevels = new ArrayList<>();
        toAddActiveLevels = new ArrayList<>();
    }

    private void importTilesetAtlas() {
        BufferedImage img = BundleLoader.getSpriteAtlas(BundleLoader.TILESET_ATLAS);
        int columns = img.getWidth() / Game.TILES_DEFAULT_SIZE;
        int rows = img.getHeight() / Game.TILES_DEFAULT_SIZE;

        if (img.getWidth() % Game.TILES_DEFAULT_SIZE != 0 || img.getHeight() % Game.TILES_DEFAULT_SIZE != 0) {
            System.err.println("Warning: Tileset image dimensions are not divisible by the tile size.");
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
                    System.err.println("Error: " + e.getMessage());
                }
            }
    }

    private void parseLevelData(String data) {
        String[] lines = data.split("\n");
        int levelCount = -1;
//        ArrayList<Level> levels = new ArrayList<Level>(2);

        for (int i = 0; i < lines.length; i++) {
            // Start of a new level: line like: #level-0,"Some data"
            if (lines[i].startsWith("#level-")) {
                levelCount++;

                String[] levelData = lines[i].split(",");

                String params = levelData[1].substring(1, levelData[1].length() - 1); // get params and remove quotes

                levels.add(new Level(this, params));
                System.out.println("Level " + levelCount + " loaded" + " with params: " + params);
                continue;
            }

            // End of all levels
            if (lines[i].startsWith("\n")) {
                break;
            }

            // if there are no level beginning string - incorrect data
            if (levelCount < 0) {
                System.err.println("Error: Level data not found");
                break;
            }

            // Tile data: line like: position X,position Y,TileType,"Special params"
            {
                String[] values = lines[i].split(","); // 0 - x, 1 - y, 2 - spriteIndex, 3 - params
                if (values.length < 4) {
                    throw new RuntimeException("Invalid level data");
                }
                int x = (Integer.parseInt(values[0]) - 1) * Game.TILES_SIZE;
                int y = (Integer.parseInt(values[1]) - 1) * Game.TILES_SIZE;
                int spriteIndex = Integer.parseInt(values[2]);
                String tileType = values[3].substring(1, values[3].length() - 1); // remove quotes

                // Special elements like entities, power-ups and other
                if (spriteIndex < 0){
                    if (tileType.equals("SpeedUp")) {
                        SpeedUp speedUp = new SpeedUp(x, y);
                        levels.get(levelCount).setGameObject(speedUp);
                    }
                    else if (tileType.equals("SpeedDown")) {
                        SpeedDown speedDown = new SpeedDown(x, y);
                        levels.get(levelCount).setGameObject(speedDown);
                    }
                    else if (tileType.equals("Saw")) {
                        Saw saw = new Saw(x, y);
                        levels.get(levelCount).setGameObject(saw);
                    }
                    else if (tileType.equals("MovableSaw")) {
                        int dx = (Integer.parseInt(values[4])) * Game.TILES_SIZE;
                        int dy = (Integer.parseInt(values[5])) * Game.TILES_SIZE;
                        MovableSaw movableSaw = new MovableSaw(x, y, dx, dy);
                        levels.get(levelCount).setGameObject(movableSaw);
                    }
                    continue;
                }

                // Normal tile
                levels.get(levelCount).setTile(x, y, spriteIndex, tileType);
            }
        }

        System.out.println("Levels loaded: " + levels.size());

        for (Level level : levels) {
            if (level.getParams().startsWith("spawn")) {
                spawnLevels.add(level);
            }
        }
        levels.removeAll(spawnLevels);
    }

    public void generateSpawnLevel(int playerCount){
        if (activeLevels.isEmpty()){ // game have been started
            // add spawn levels

            ArrayList<String> spawnLevelNames = new ArrayList<>();
            spawnLevelNames.add("spawn_p1");
            spawnLevelNames.add("spawn_p2");
            spawnLevelNames.add("spawn_p3");
            spawnLevelNames.add("spawn_p4");

            for (Level level : spawnLevels) {
                if (level.getParams().startsWith(spawnLevelNames.get(Math.abs(playerCount - 1)) )) {
                    System.out.println("Spawning " + level.getParams());
                    Level newLevel = level.copyLevel();
                    newLevel.setXOffset(activeLevels.size() * Game.TILES_SIZE * Game.TILES_IN_WIDTH);
                    activeLevels.add(newLevel);
                }
            }

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
        int random = Agragation.getRandomNumber(0, maxLevel);

        Level newLevel = levels.get(random).copyLevel();
        newLevel.setXOffset(activeLevels.size() * Game.TILES_SIZE * Game.TILES_IN_WIDTH);

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

//        for (Level level : toAddActiveLevels) {level.update();}

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

    public double getSpeed() {
        return speed;
    }

    public void resetLevelManager(){
        activeLevels.clear();
        toRemoveActiveLevels.clear();
        toAddActiveLevels.clear();
    }

}
