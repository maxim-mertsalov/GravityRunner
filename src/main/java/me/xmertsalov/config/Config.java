package me.xmertsalov.config;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Config {
    // Config file
    private static final String CONFIG_FILE = "settings.config";

    // Resolution
    private static ArrayList<String> resolutions;

    private static String currentResolution;
    private static String temporaryResolution; // need reload

    private static String defaultResolution = "1280x720";
    private static int defaultWidth = 1280;
    private static int defaultHeight = 720;



    // Sound
    private static int sfxVolume = 100;
    private static int musicVolume = 100;


    public Config() {
        resolutions = new ArrayList<>();
        resolutions.add("Fullscreen");
        resolutions.add("1920x1200");
        resolutions.add("1920x1080");
        resolutions.add("1600x900");
        resolutions.add("1680x1050");
        resolutions.add("1440x900");
        resolutions.add("1280x800");
        resolutions.add("1280x720");
        resolutions.add("1024x768");
        resolutions.add("1024x640");

        currentResolution = defaultResolution;
    }

    public void setResolution(String resolution) {
        if (resolutions.contains(resolution)) {
            temporaryResolution = resolution;
        } else {
            System.err.println("Invalid resolution: " + resolution);
        }
    }

    public void setSoundVolume(int musicVolume, int sfxVolume) {
        if (sfxVolume >= 0 && sfxVolume <= 100 && musicVolume >= 0 && musicVolume <= 100) {
            this.sfxVolume = sfxVolume;
            this.musicVolume = musicVolume;
        } else {
            System.err.println("Invalid sound volume: " + musicVolume + " or " + sfxVolume);
        }
    }

    public int getResolutionWidth() {
        return switch (currentResolution) {
            case "Fullscreen" -> 0;
            case "1920x1200", "1920x1080" -> 1920;
            case "1600x900" -> 1600;
            case "1680x1050" -> 1680;
            case "1440x900" -> 1440;
            case "1280x800", "1280x720" -> 1280;
            case "1024x768" -> 1024;
            default -> defaultWidth;
        };
    }

    public int getResolutionHeight() {
        return switch (currentResolution) {
            case "Fullscreen" -> 0;
            case "1920x1200" -> 1200;
            case "1920x1080" -> 1080;
            case "1600x900", "1440x900" -> 900;
            case "1680x1050" -> 1050;
            case "1280x800", "1280x720" -> 720;
            case "1024x768" -> 768;
            default -> defaultHeight;
        };
    }

    public static int getResolutionIndex() {
        return resolutions.indexOf(currentResolution);
    }

    public static ArrayList<String> getResolutions() {
        return resolutions;
    }

    public String getCurrentResolution() {
        return currentResolution;
    }

    public static int getSfxVolume() {
        return sfxVolume;
    }
    public void setSfxVolume(int sfxVolume) {
        Config.sfxVolume = sfxVolume;
    }
    public static int getMusicVolume() {
        return musicVolume;
    }
    public void setMusicVolume(int musicVolume) {
        Config.musicVolume = musicVolume;
    }

    public void loadConfig(){
        Map<String, String> config = loadConfigFile();
        if (config.containsKey("resolution")) {
            currentResolution = config.get("resolution");
        }
        if (config.containsKey("musicVolume")) {
            musicVolume = Integer.parseInt(config.get("musicVolume"));
        }
        if (config.containsKey("sfxVolume")) {
            sfxVolume = Integer.parseInt(config.get("sfxVolume"));
        }
    }

    private static Map<String, String> loadConfigFile() {
        Map<String, String> config = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(CONFIG_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    config.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading config: " + e.getMessage());
        }
        return config;
    }


    public void applySettings() {
        loadToFile();
    }

    private void loadToFile(){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CONFIG_FILE))) {
            writer.write("resolution=" + temporaryResolution + "\n");
            writer.write("musicVolume=" + musicVolume + "\n");
            writer.write("sfxVolume=" + sfxVolume + "\n");
        } catch (IOException e) {
            System.out.println("Error saving config: " + e.getMessage());
        }
        System.exit(1);
    }
}
