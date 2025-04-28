package me.xmertsalov.world;

import me.xmertsalov.Game;
import me.xmertsalov.exceptions.LevelLoadingException;
import me.xmertsalov.gameObjects.GameObject;
import me.xmertsalov.gameObjects.palms.Palm;
import me.xmertsalov.gameObjects.powerUps.SpeedDown;
import me.xmertsalov.gameObjects.powerUps.SpeedUp;
import me.xmertsalov.gameObjects.saws.MovableSaw;
import me.xmertsalov.gameObjects.saws.Saw;
import me.xmertsalov.utils.BundleLoader;

import java.util.ArrayList;
import java.util.HashMap;

public class LevelLoader {

    private ArrayList<Level> levels; // unique levels

    private ArrayList<Level> spawnLevels; // unique levels, but only in the start of game

    private HashMap<String, GameObject> gameObjects; // all game objects without tiles

    private LevelsManager levelsManager;

    private String levelData;

    public LevelLoader(LevelsManager levelsManager) throws LevelLoadingException {
        this.levelsManager = levelsManager;
        levels = new ArrayList<>();
        spawnLevels = new ArrayList<>();

        initGameObjects();

        importLevelData();

        parseLevelData(levelData);
    }


    private void initGameObjects() {
        gameObjects = new HashMap<>();

        gameObjects.put("Saw", new Saw(0, 0));
        gameObjects.put("MovableSaw", new MovableSaw(0, 0, 0, 0));
        gameObjects.put("SpeedUp", new SpeedUp(0, 0));
        gameObjects.put("SpeedDown", new SpeedDown(0, 0));

        gameObjects.put("BackPalm1", new Palm(0, 0, BundleLoader.PALM_BACK_1_ATLAS, 64, 64));
        gameObjects.put("BackPalm2", new Palm(0, 0, BundleLoader.PALM_BACK_2_ATLAS, 51, 53));
        gameObjects.put("BackPalm3", new Palm(0, 0, BundleLoader.PALM_BACK_3_ATLAS, 52, 53));
        gameObjects.put("FrontPalm1", new Palm(0, 0, BundleLoader.PALM_FRONT_1_ATLAS, 39, 96));
        gameObjects.put("FrontPalm2", new Palm(0, 0, BundleLoader.PALM_FRONT_2_ATLAS, 78, 64));
        gameObjects.put("FrontPalm3", new Palm(0, 0, BundleLoader.PALM_FRONT_3_ATLAS, 39, 64));
    }

    private void importLevelData() {
        try{
            levelData = BundleLoader.getFileContent(BundleLoader.WORLD_DATA);
        } catch (Exception e) {
            Game.logger.error("Error loading level data: {}", e.getMessage());
            levelData = "";
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

                levels.add(new Level(levelsManager, params));
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
                int x = (int)((Double.parseDouble(values[0]) - 1) * Game.TILES_SIZE);
                int y = (int)((Double.parseDouble(values[1]) - 1) * Game.TILES_SIZE);
                int spriteIndex = Integer.parseInt(values[2]);
                String tileType = values[3].substring(1, values[3].length() - 1); // remove quotes

                // Special elements like entities, power-ups and other
                if (spriteIndex < 0) {
                    switch (tileType) {
                        case "SpeedUp": {
                            SpeedUp speedUp = (SpeedUp) gameObjects.get("SpeedUp").clone();
                            speedUp.updatePos(x, y);
                            speedUp.setZIndex(0);
                            levels.get(levelCount).setGameObject(speedUp);
                            break;
                        }
                        case "SpeedDown": {
                            SpeedDown speedDown = (SpeedDown) gameObjects.get("SpeedDown").clone();
                            speedDown.updatePos(x, y);
                            speedDown.setZIndex(0);
                            levels.get(levelCount).setGameObject(speedDown);
                            break;
                        }
                        case "Saw": {
                            Saw saw = (Saw) gameObjects.get("Saw").clone();
                            saw.updatePos(x, y);
                            saw.setZIndex(1);
                            levels.get(levelCount).setGameObject(saw);
                            break;
                        }
                        case "MovableSaw": {
                            int dx = (Integer.parseInt(values[4])) * Game.TILES_SIZE;
                            int dy = (Integer.parseInt(values[5])) * Game.TILES_SIZE;
                            MovableSaw movableSaw = (MovableSaw) gameObjects.get("MovableSaw").clone();
                            movableSaw.updatePos(x, y);
                            movableSaw.setDestination(dx, dy);
                            movableSaw.setZIndex(1);
                            levels.get(levelCount).setGameObject(movableSaw);
                            break;
                        }
                        case "BackPalm1": {
                            Palm palm = (Palm) gameObjects.get("BackPalm1").clone();
                            palm.updatePos(x, y);
                            palm.setZIndex(-1);
                            palm.setIncrementSize(Double.parseDouble(values[4]));
                            levels.get(levelCount).setGameObject(palm);
                            break;
                        }
                        case "BackPalm2": {
                            Palm palm = (Palm) gameObjects.get("BackPalm2").clone();
                            palm.updatePos(x, y);
                            palm.setZIndex(-1);
                            palm.setIncrementSize(Double.parseDouble(values[4]));
                            levels.get(levelCount).setGameObject(palm);
                            break;
                        }
                        case "BackPalm3": {
                            Palm palm = (Palm) gameObjects.get("BackPalm3").clone();
                            palm.updatePos(x, y);
                            palm.setZIndex(-1);
                            palm.setIncrementSize(Double.parseDouble(values[4]));
                            levels.get(levelCount).setGameObject(palm);
                            break;
                        }
                        case "FrontPalm1": {
                            Palm palm = (Palm) gameObjects.get("FrontPalm1").clone();
                            palm.updatePos(x, y);
                            palm.setZIndex(1);
                            palm.setIncrementSize(Double.parseDouble(values[4]));
                            levels.get(levelCount).setGameObject(palm);
                            break;
                        }
                        case "FrontPalm2": {
                            Palm palm = (Palm) gameObjects.get("FrontPalm2").clone();
                            palm.updatePos(x, y);
                            palm.setZIndex(1);
                            palm.setIncrementSize(Double.parseDouble(values[4]));
                            levels.get(levelCount).setGameObject(palm);
                            break;
                        }
                        case "FrontPalm3": {
                            Palm palm = (Palm) gameObjects.get("FrontPalm3").clone();
                            palm.updatePos(x, y);
                            palm.setZIndex(1);
                            palm.setIncrementSize(Double.parseDouble(values[4]));
                            levels.get(levelCount).setGameObject(palm);
                            break;
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

    public ArrayList<Level> getLevels() {return levels;}
    public ArrayList<Level> getSpawnLevels() {return spawnLevels;}

}
