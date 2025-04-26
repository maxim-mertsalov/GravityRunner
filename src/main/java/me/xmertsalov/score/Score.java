package me.xmertsalov.score;

import me.xmertsalov.Game;
import me.xmertsalov.audio.AudioPlayer;
import me.xmertsalov.entities.Player;
import me.xmertsalov.world.LevelsManager;

import java.util.ArrayList;

public class Score {
    private ArrayList<Double> scores;
    private Game game;
    private LevelsManager levelsManager;

    private ArrayList<Player> players;

    private boolean loadedPlayers = false;
    private boolean started = false;
    private boolean playedSound = false;


    public Score(Game game) {
        this.scores = new ArrayList<>(4);
        this.game = game;
        this.levelsManager = game.getPlayingScene().getLevelsManager();

        for (int i = 0; i < 4; i++) {
            scores.add(0.0);
        }
    }

    public void update(){
        loadPlayers();

        for (int i = 0; i < players.size(); i++) {
            if (!players.get(i).isDead() && started) {
                scores.set(i, scores.get(i) + levelsManager.getSpeed() / 10);

                // Check if the score is a multiple of 10000 call the sound
                if (scores.get(i) == 10000 && !playedSound) {
                    game.getAudioPlayer().playSfx(AudioPlayer.SFX_COIN);
                    playedSound = true;
                }
            }
        }

        playedSound = false;
    }

    private void loadPlayers() {
        if (loadedPlayers) return;

//        players = game.getPlayingScene().getPlayers();

        loadedPlayers = true;
    }

    public void addScore(int id, Double score) {
        scores.set(id, scores.get(id) + score);
    }

    public void resetScores() {
        scores.replaceAll(ignored -> 0.0);
    }

    public void setScore(int id, Double score) {
        scores.set(id, score);
    }

    public Double getScore(int id) {
        return scores.get(id);
    }

    public ArrayList<Double> getScores() {
        return scores;
    }

    public void resetPlayers(ArrayList<Player> players) {
//        loadedPlayers = false;

        this.players = players;

        resetScores();
    }

    public void setStarted(boolean started) {
        this.started = started;
    }
}
