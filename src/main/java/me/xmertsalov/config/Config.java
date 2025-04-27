package me.xmertsalov.config;

import java.io.*;
import java.util.ArrayList;

public class Config implements Serializable {
    private static final long serialVersionUID = 1L; // For versioning during serialization
    private static final String CONFIG_FILE = "settings.ser";

    // Resolution
    private ArrayList<String> resolutions;

    private String currentResolution;
    private String temporaryResolution;

    private static final String defaultResolution = "1280x720";

    private int resolutionWidth;
    private int resolutionHeight;

    // Sound
    private int sfxVolume = 100;
    private int musicVolume = 100;

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
        updateResolutionDimensions();
    }

    // Save the configuration to a file
    public void saveConfig() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CONFIG_FILE))) {
            oos.writeObject(this);
            System.out.println("Config saved to: " + CONFIG_FILE);
        } catch (IOException e) {
            System.err.println("Error saving config: " + e.getMessage());
        }
    }

    // Load the configuration from a file
    public static Config loadConfig() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(CONFIG_FILE))) {
            Config config = (Config) ois.readObject();
            config.currentResolution = config.temporaryResolution;
            config.updateResolutionDimensions();
            return config;
        } catch (FileNotFoundException e) {
            System.out.println("Config file not found. Using default settings.");
            return new Config();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading config: " + e.getMessage());
            return new Config();
        }
    }

    // Getters and setters (unchanged)
    public void setResolution(String resolution) {
        if (resolutions.contains(resolution)) {
            temporaryResolution = resolution;
            updateResolutionDimensions();
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

    public String getCurrentResolution() {
        return currentResolution;
    }

    public int getSfxVolume() {
        return sfxVolume;
    }

    public int getMusicVolume() {
        return musicVolume;
    }

    private void updateResolutionDimensions() {
        try {
            if (currentResolution.equals("Fullscreen")) {
                resolutionWidth = 0; // Special value for fullscreen
                resolutionHeight = 0;
            } else {
                String[] dimensions = currentResolution.split("x");
                resolutionWidth = Integer.parseInt(dimensions[0]);
                resolutionHeight = Integer.parseInt(dimensions[1]);
            }
        } catch (Exception e) {
            System.err.println("Error parsing resolution: " + currentResolution);
            resolutionWidth = 1280;
            resolutionHeight = 720;
        }
    }

    public int getResolutionWidth() {
        return resolutionWidth;
    }

    public int getResolutionHeight() {
        return resolutionHeight;
    }

    public ArrayList<String> getResolutions() {
        return resolutions;
    }
    public int getResolutionIndex() {
        return resolutions.indexOf(currentResolution);
    }
}
