package me.xmertsalov.config;

import java.io.*;
import java.util.ArrayList;

/**
 * The {@code Config} class is responsible for managing the application's configuration settings.
 * It supports saving and loading settings to/from a serialized file and provides methods to
 * manipulate resolution and sound settings.
 *
 * <p>This class implements {@link Serializable} to allow saving its state to a file.
 * It includes default settings and ensures that invalid values are handled gracefully.
 */
public class Config implements Serializable {
    @Serial
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

    /**
     * Constructs a new {@code Config} object with default settings.
     * Initializes the list of supported resolutions and sets the default resolution.
     */
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

    /**
     * Saves the current configuration to a file.
     * The configuration is serialized and written to {@code settings.ser}.
     */
    public void saveConfig() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CONFIG_FILE))) {
            oos.writeObject(this);
            System.out.println("Config saved to: " + CONFIG_FILE);
        } catch (IOException e) {
            System.err.println("Error saving config: " + e.getMessage());
        }
    }

    /**
     * Loads the configuration from a file.
     * If the file does not exist or an error occurs, a new {@code Config} object with default settings is returned.
     *
     * @return the loaded {@code Config} object or a new instance with default settings.
     */
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

    /**
     * Sets the resolution to the specified value if it is valid.
     * Updates the resolution dimensions accordingly.
     *
     * @param resolution the resolution to set (e.g., "1920x1080").
     */
    public void setResolution(String resolution) {
        if (resolutions.contains(resolution)) {
            temporaryResolution = resolution;
            updateResolutionDimensions();
        } else {
            System.err.println("Invalid resolution: " + resolution);
        }
    }

    /**
     * Sets the sound volumes for music and sound effects.
     * Ensures that the volume values are within the range of 0 to 100.
     *
     * @param musicVolume the volume level for music.
     * @param sfxVolume the volume level for sound effects.
     */
    public void setSoundVolume(int musicVolume, int sfxVolume) {
        if (sfxVolume >= 0 && sfxVolume <= 100 && musicVolume >= 0 && musicVolume <= 100) {
            this.sfxVolume = sfxVolume;
            this.musicVolume = musicVolume;
        } else {
            System.err.println("Invalid sound volume: " + musicVolume + " or " + sfxVolume);
        }
    }

    /**
     * Returns the current resolution.
     *
     * @return the current resolution as a {@code String}.
     */
    public String getCurrentResolution() {
        return currentResolution;
    }

    /**
     * Returns the sound effects volume.
     *
     * @return the sound effects volume as an integer.
     */
    public int getSfxVolume() {
        return sfxVolume;
    }

    /**
     * Returns the music volume.
     *
     * @return the music volume as an integer.
     */
    public int getMusicVolume() {
        return musicVolume;
    }

    /**
     * Updates the resolution dimensions based on the current resolution.
     * If the resolution is "Fullscreen", special values (0, 0) are used.
     * If parsing fails, default dimensions (1280x720) are applied.
     */
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

    /**
     * Returns the width of the current resolution.
     *
     * @return the resolution width as an integer.
     */
    public int getResolutionWidth() {
        return resolutionWidth;
    }

    /**
     * Returns the height of the current resolution.
     *
     * @return the resolution height as an integer.
     */
    public int getResolutionHeight() {
        return resolutionHeight;
    }

    /**
     * Returns the list of supported resolutions.
     *
     * @return an {@code ArrayList} of supported resolution strings.
     */
    public ArrayList<String> getResolutions() {
        return resolutions;
    }

    /**
     * Returns the index of the current resolution in the list of supported resolutions.
     *
     * @return the index of the current resolution, or -1 if not found.
     */
    public int getResolutionIndex() {
        return resolutions.indexOf(currentResolution);
    }
}
