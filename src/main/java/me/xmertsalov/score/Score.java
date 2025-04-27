package me.xmertsalov.score;

import me.xmertsalov.Game;
import me.xmertsalov.audio.AudioPlayer;
import me.xmertsalov.entities.Player;
import me.xmertsalov.world.LevelsManager;

import java.util.ArrayList;

/**
 * The {@code Score} class is responsible for managing the scores of players in the game.
 * It tracks the scores of up to four players, updates their scores based on game progress,
 * and handles events such as playing sound effects when certain score milestones are reached.
 */
public class Score {

    // Dependencies
    private Game game;
    private LevelsManager levelsManager;

    // Storage
    private ArrayList<Double> scores;
    private ArrayList<Player> players;

    // States
    private boolean loadedPlayers = false;
    private boolean started = false;
    private boolean playedSound = false;

    /**
     * Constructs a {@code Score} object and initializes the scores for up to four players.
     * 
     * @param game The {@code Game} instance to which this score manager belongs.
     */
    public Score(Game game) {
        this.scores = new ArrayList<>(4);
        this.game = game;
        this.levelsManager = game.getPlayingScene().getLevelsManager();

        for (int i = 0; i < 4; i++) {
            scores.add(0.0);
        }
    }

    /**
     * Updates the scores of all players. If a player is not dead and the game has started,
     * their score is incremented based on the current speed of the level. Additionally,
     * plays a sound effect when a player's score reaches 10,000 for the first time.
     */
    public void update() {
        loadPlayers();

        for (int i = 0; i < players.size(); i++) {
            if (!players.get(i).isDead() && started) {
                scores.set(i, scores.get(i) + levelsManager.getSpeed() / 10);

                // Check if the score is a multiple of 10000 and play the sound
                if (scores.get(i) == 10000 && !playedSound) {
                    game.getAudioPlayer().playSfx(AudioPlayer.SFX_COIN);
                    playedSound = true;
                }
            }
        }

        playedSound = false;
    }

    /**
     * Loads the players into the score manager. This method ensures that players are only loaded once.
     */
    private void loadPlayers() {
        if (loadedPlayers) return;

        // Uncomment and implement this line to load players from the game scene.
        // players = game.getPlayingScene().getPlayers();

        loadedPlayers = true;
    }

    /**
     * Adds a specified score to a player's current score.
     * 
     * @param id    The ID of the player (index in the scores list).
     * @param score The score to add to the player's current score.
     */
    public void addScore(int id, Double score) {
        scores.set(id, scores.get(id) + score);
    }

    /**
     * Resets all players' scores to zero.
     */
    public void resetScores() {
        scores.replaceAll(ignored -> 0.0);
    }

    /**
     * Sets a specific player's score to a given value.
     * 
     * @param id    The ID of the player (index in the scores list).
     * @param score The new score to set for the player.
     */
    public void setScore(int id, Double score) {
        scores.set(id, score);
    }

    /**
     * Retrieves the score of a specific player.
     * 
     * @param id The ID of the player (index in the scores list).
     * @return The score of the specified player.
     */
    public Double getScore(int id) {
        return scores.get(id);
    }

    /**
     * Retrieves the list of all players' scores.
     * 
     * @return An {@code ArrayList} containing the scores of all players.
     */
    public ArrayList<Double> getScores() {
        return scores;
    }

    /**
     * Resets the players managed by this score manager and clears their scores.
     * 
     * @param players The new list of players to manage.
     */
    public void resetPlayers(ArrayList<Player> players) {
        // Uncomment this line if players need to be reloaded.
        // loadedPlayers = false;

        this.players = players;

        resetScores();
    }

    /**
     * Sets the started state of the game. When started is {@code true}, scores will be updated.
     * 
     * @param started {@code true} to start the game, {@code false} to stop it.
     */
    public void setStarted(boolean started) {
        this.started = started;
    }
}
