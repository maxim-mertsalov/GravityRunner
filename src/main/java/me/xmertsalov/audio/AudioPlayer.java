package me.xmertsalov.audio;

import me.xmertsalov.Game;
import me.xmertsalov.scenes.GameScene;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

/**
 * The {@code AudioPlayer} class is responsible for managing and playing audio files, including music and sound effects (SFX),
 * in the game. It provides functionality to load, play, stop, and adjust the volume of audio clips.
 * 
 * <p>This class supports:
 * <ul>
 *     <li>Playing background music with automatic transitions between tracks.</li>
 *     <li>Playing sound effects (SFX) for specific game events.</li>
 *     <li>Adjusting the volume of music and SFX globally or individually.</li>
 * </ul>
 */
public class AudioPlayer {
    // Constants
    /** Total number of music tracks available. */
    public static final int MUSIC_COUNT = 3;
    /** Identifier for the first music track, played everywhere. */
    public static final int MUSIC_1 = 0;
    /** Identifier for the second music track, played only during gameplay. */
    public static final int MUSIC_2 = 1;
    /** Identifier for the third music track, played everywhere. */
    public static final int MUSIC_3 = 2;

    /** Total number of sound effects available. */
    public static final int SFX_COUNT = 6;
    /** Identifier for the countdown sound effect. */
    public static final int SFX_COUNTDOWN = 0;
    /** Identifier for the jump sound effect. */
    public static final int SFX_JUMP = 1;
    /** Identifier for the click sound effect. */
    public static final int SFX_CLICK = 2;
    /** Identifier for the coin collection sound effect. */
    public static final int SFX_COIN = 3;
    /** Identifier for the first countdown sound effect variation. */
    public static final int SFX_COUNTDOWN_F = 4;
    /** Identifier for the second countdown sound effect variation. */
    public static final int SFX_COUNTDOWN_S = 5;

    // Sounds files
    private static final String FILE_SFX_CLICK = "res/Sounds/wav/click.wav";
    private static final String FILE_SFX_JUMP = "res/Sounds/wav/jump.wav";
    private static final String FILE_SFX_COUNTDOWN = "res/Sounds/wav/countdown.wav";
    private static final String FILE_SFX_COUNTDOWN_FIRST = "res/Sounds/wav/countdown first.wav";
    private static final String FILE_SFX_COUNTDOWN_SECOND = "res/Sounds/wav/countdown second.wav";
    private static final String FILE_SFX_COIN = "res/Sounds/wav/coin.wav";
    private static final String FILE_MUSIC_1 = "res/Sounds/wav/music1.wav";
    private static final String FILE_MUSIC_2 = "res/Sounds/wav/music2.wav";
    private static final String FILE_MUSIC_3 = "res/Sounds/wav/music3.wav";

    // Storage
    private Clip[] musicClips;
    private static Clip[] sfxClips;

    // States
    private int currentMusicId;
    private double musicVolume = 1;
    private double sfxVolume = 1;

    /**
     * Constructs an {@code AudioPlayer} instance and loads all audio clips into memory.
     */
    public AudioPlayer() {
        loadClips();
    }

    /**
     * Plays the specified music track.
     * 
     * @param musicId The identifier of the music track to play.
     */
    public void playMusic(int musicId) {
        if (musicClips[musicId].isRunning()) {
            musicClips[musicId].stop();
        }

        currentMusicId = musicId;
        updateMusicVolume();

        musicClips[musicId].setMicrosecondPosition(0);
        musicClips[musicId].start();
    }

    /**
     * Stops the currently playing music track.
     */
    private void stopCurrentMusic() {
        if (musicClips[currentMusicId].isRunning()) {
            musicClips[currentMusicId].stop();
        }
    }

    /**
     * Updates the audio player state. This method is currently unused but can be extended for future functionality.
     */
    public void update() {
    }

    /**
     * Automatically transitions to the next music track when the current track ends.
     */
    public void autoGenerateMusic() {
        if (musicClips[currentMusicId].getMicrosecondPosition() >= musicClips[currentMusicId].getMicrosecondLength() - 1 ||
                !musicClips[currentMusicId].isRunning()) {
            playNextMusic();
        }
    }

    /**
     * Plays the next music track in the sequence. Skips tracks based on the current game scene.
     */
    public void playNextMusic() {
        stopCurrentMusic();
        if (GameScene.scene == GameScene.PLAYING) {
            currentMusicId++;
            if (currentMusicId >= MUSIC_COUNT) {
                currentMusicId = 0;
            }
            playMusic(currentMusicId);
        }
        else{
            currentMusicId++;
            if (currentMusicId == MUSIC_2) currentMusicId ++;

            if (currentMusicId >= MUSIC_COUNT) {
                currentMusicId = 0;
            }
            playMusic(currentMusicId);
        }
    }

    /**
     * Plays the specified sound effect.
     * 
     * @param sfxId The identifier of the sound effect to play.
     */
    public void playSfx(int sfxId) {
        sfxClips[sfxId].setMicrosecondPosition(0);
        sfxClips[sfxId].start();
    }

    /**
     * Sets the global music volume.
     * 
     * @param musicVolume A float value between 0 (mute) and 1 (maximum volume).
     */
    public void setMusicVolume(float musicVolume) {
        this.musicVolume = musicVolume;
        updateMusicVolume();
    }

    /**
     * Sets the global sound effects volume.
     * 
     * @param sfxVolume A double value between 0 (mute) and 1 (maximum volume).
     */
    public void setSfxVolume(double sfxVolume) {
        this.sfxVolume = sfxVolume;
        updateSfxVolume();
    }

    /**
     * Sets the volume for a specific sound effect.
     * 
     * @param sfxId The identifier of the sound effect.
     * @param sfxVolume A double value between 0 (mute) and 1 (maximum volume).
     */
    public void setSfxCurrentVolume(int sfxId, double sfxVolume) {
        double volume = sfxVolume * this.sfxVolume;
        updateSfxVolumeSingle(sfxId, volume);
    }

    /**
     * Updates the volume of the currently playing music track.
     */
    private void updateMusicVolume() {
        FloatControl gainControl = (FloatControl) musicClips[currentMusicId].getControl(FloatControl.Type.MASTER_GAIN);
        double value = gainControl.getMinimum() + this.musicVolume * (gainControl.getMaximum() - gainControl.getMinimum());
        try{
            gainControl.setValue((float) value);
        } catch (Exception e) {
            Game.logger.error(e.getMessage());
        }
    }

    /**
     * Updates the volume of all sound effects.
     */
    private void updateSfxVolume() {
        for (Clip clip : sfxClips) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            double value = gainControl.getMinimum() + this.sfxVolume * (gainControl.getMaximum() - gainControl.getMinimum());
            try{
                gainControl.setValue((float) value);
            } catch (Exception e) {
                Game.logger.error(e.getMessage());
            }
        }
    }

    /**
     * Updates the volume of a specific sound effect.
     * 
     * @param sfxId The identifier of the sound effect.
     * @param volume The new volume level for the sound effect.
     */
    private void updateSfxVolumeSingle(int sfxId, double volume) {
        for (Clip clip : sfxClips) {
            if (clip != sfxClips[sfxId]) continue;

            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            double value = gainControl.getMinimum() + volume * (gainControl.getMaximum() - gainControl.getMinimum());
            try{
                gainControl.setValue((float) value);
            } catch (Exception e) {
                Game.logger.error(e.getMessage());
            }
        }
    }

    /**
     * Loads all music and sound effect clips into memory.
     */
    private void loadClips(){
        musicClips = new Clip[MUSIC_COUNT];
        sfxClips = new Clip[SFX_COUNT];

        musicClips[MUSIC_1] = getClip(FILE_MUSIC_1);
        musicClips[MUSIC_2] = getClip(FILE_MUSIC_2);
        musicClips[MUSIC_3] = getClip(FILE_MUSIC_3);

        sfxClips[SFX_COUNTDOWN] = getClip(FILE_SFX_COUNTDOWN);
        sfxClips[SFX_JUMP] = getClip(FILE_SFX_JUMP);
        sfxClips[SFX_CLICK] = getClip(FILE_SFX_CLICK);
        sfxClips[SFX_COIN] = getClip(FILE_SFX_COIN);
        sfxClips[SFX_COUNTDOWN_F] = getClip(FILE_SFX_COUNTDOWN_FIRST);
        sfxClips[SFX_COUNTDOWN_S] = getClip(FILE_SFX_COUNTDOWN_SECOND);

        updateSfxVolume();
    }

    /**
     * Retrieves a {@code Clip} object for the specified audio file.
     * 
     * @param fileName The path to the audio file.
     * @return A {@code Clip} object representing the audio file, or {@code null} if the file could not be loaded.
     */
    private Clip getClip(String fileName) {
        URL url = getClass().getClassLoader().getResource(fileName);
        AudioInputStream audioInputStream;

        if (url == null) {
            Game.logger.error("Audio file not found: {}", fileName);
            return null;
        }

        try {
            audioInputStream = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            return clip;
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            Game.logger.error(e.getMessage());
        }
        return null;
    }

    /**
     * Gets the current global music volume.
     * 
     * @return The current music volume as a double between 0 and 1.
     */
    public double getMusicVolume() {
        return musicVolume;
    }

    /**
     * Gets the current global sound effects volume.
     * 
     * @return The current sound effects volume as a double between 0 and 1.
     */
    public double getSfxVolume() {
        return sfxVolume;
    }
}
