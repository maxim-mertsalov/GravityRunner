package me.xmertsalov.audio;

import me.xmertsalov.scenes.GameScene;
import me.xmertsalov.utils.BundleLoader;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class AudioPlayer {
    // Constants
    public static final int MUSIC_COUNT = 3;
    public static final int MUSIC_1 = 0; // everywhere
    public static final int MUSIC_2 = 1; // only in game
    public static final int MUSIC_3 = 2; // everywhere

    public static final int SFX_COUNT = 6;
    public static final int SFX_COUNTDOWN = 0;
    public static final int SFX_JUMP = 1;
    public static final int SFX_CLICK = 2;
    public static final int SFX_COIN = 3;
    public static final int SFX_COUNTDOWN_F = 4;
    public static final int SFX_COUNTDOWN_S = 5;

    // Sounds files
    private static final String FILE_SFX_CLICK = "/res/Sounds/wav/click.wav";
    private static final String FILE_SFX_JUMP = "/res/Sounds/wav/jump.wav";
    private static final String FILE_SFX_COUNTDOWN = "/res/Sounds/wav/countdown.wav";
    private static final String FILE_SFX_COUNTDOWN_FIRST = "/res/Sounds/wav/countdown first.wav";
    private static final String FILE_SFX_COUNTDOWN_SECOND = "/res/Sounds/wav/countdown second.wav";
    private static final String FILE_SFX_COIN = "/res/Sounds/wav/coin.wav";
    private static final String FILE_MUSIC_1 = "/res/Sounds/wav/music1.wav";
    private static final String FILE_MUSIC_2 = "/res/Sounds/wav/music2.wav";
    private static final String FILE_MUSIC_3 = "/res/Sounds/wav/music3.wav";

    // Storage
    private Clip[] musicClips;
    private static Clip[] sfxClips;

    // States
    private int currentMusicId;
    private double musicVolume = 1;
    private double sfxVolume = 1;

    public AudioPlayer() {
        loadClips();
    }

    public void playMusic(int musicId) {
        if (musicClips[musicId].isRunning()) {
            musicClips[musicId].stop();
        }

        currentMusicId = musicId;
        updateMusicVolume();

        musicClips[musicId].setMicrosecondPosition(0);
        musicClips[musicId].start();
    }

    private void stopCurrentMusic(){
        if (musicClips[currentMusicId].isRunning()) {
            musicClips[currentMusicId].stop();
        }
    }

    public void update(){
    }

    public void autoGenerateMusic(){
        // if music is ended
        if (musicClips[currentMusicId].getMicrosecondPosition() == musicClips[currentMusicId].getMicrosecondLength() ||
                !musicClips[currentMusicId].isActive()) {

            // play next music
            playNextMusic();
        }
    }

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

    public void playSfx(int sfxId) {
        sfxClips[sfxId].setMicrosecondPosition(0);
        sfxClips[sfxId].start();
    }



    public void setMusicVolume(float musicVolume) {
        this.musicVolume = musicVolume;
        updateMusicVolume();
    }

    public void setSfxVolume(double sfxVolume) {
        this.sfxVolume = sfxVolume;
        updateSfxVolume();
    }

    public void setSfxCurrentVolume(int sfxId, double sfxVolume) {
        double volume = sfxVolume * this.sfxVolume;
        updateSfxVolumeSingle(sfxId, volume);
    }

    private void updateMusicVolume() {
        FloatControl gainControl = (FloatControl) musicClips[currentMusicId].getControl(FloatControl.Type.MASTER_GAIN);
        double value = gainControl.getMinimum() + this.musicVolume * (gainControl.getMaximum() - gainControl.getMinimum());
        try{
            gainControl.setValue((float) value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void updateSfxVolume() {
        for (Clip clip : sfxClips) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            double value = gainControl.getMinimum() + this.sfxVolume * (gainControl.getMaximum() - gainControl.getMinimum());
            try{
                gainControl.setValue((float) value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void updateSfxVolumeSingle(int sfxId, double volume) {
        for (Clip clip : sfxClips) {
            if (clip != sfxClips[sfxId]) continue;

            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            double value = gainControl.getMinimum() + volume * (gainControl.getMaximum() - gainControl.getMinimum());
            try{
                gainControl.setValue((float) value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


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

    private Clip getClip(String fileName) {
        URL url = getClass().getResource(fileName);
        AudioInputStream audioInputStream;

        try {
            audioInputStream = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
//            clip.start();
            return clip;
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
        return null;
    }

    public double getMusicVolume() {return musicVolume;}
    public double getSfxVolume() {return sfxVolume;}
}
