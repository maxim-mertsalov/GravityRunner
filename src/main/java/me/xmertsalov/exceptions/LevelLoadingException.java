package me.xmertsalov.exceptions;

import me.xmertsalov.Game;

/**
 * This exception is thrown when there is an issue loading a game level.
 * It logs the error message using the game's logger.
 */
public class LevelLoadingException extends RuntimeException {

    /**
     * Constructs a new LevelLoadingException with a specific message.
     * The error message is also logged using the game's logger.
     *
     * @param message The error message describing the issue.
     */
    public LevelLoadingException(String message) {
        super(message);
        Game.logger.error(message);
    }
}
