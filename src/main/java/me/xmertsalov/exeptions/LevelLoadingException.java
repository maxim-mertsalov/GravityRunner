package me.xmertsalov.exeptions;

import me.xmertsalov.Game;

public class LevelLoadingException extends RuntimeException {
    public LevelLoadingException(String message) {
        super(message);
        Game.logger.error(message);
    }
}
