package me.xmertsalov.scenes;

import me.xmertsalov.Game;

public class Scene {
    protected Game game;

    public Scene(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }
}
