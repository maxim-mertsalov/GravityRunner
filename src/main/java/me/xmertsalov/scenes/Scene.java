package me.xmertsalov.scenes;

import me.xmertsalov.Game;

/**
 * Represents a generic scene in the game.
 * <p>
 * This class serves as a base for all specific scenes in the game. 
 * It holds a reference to the {@link Game} instance, allowing scenes to interact with the game state.
 * Subclasses can extend this class to implement specific scene behaviors.
 */
public class Scene {
    /**
     * The {@link Game} instance associated with this scene.
     * This allows the scene to interact with the game state and logic.
     */
    protected Game game;

    /**
     * Constructs a new Scene with the specified {@link Game} instance.
     *
     * @param game the {@link Game} instance to associate with this scene
     */
    public Scene(Game game) {
        this.game = game;
    }

    /**
     * Retrieves the {@link Game} instance associated with this scene.
     *
     * @return the {@link Game} instance
     */
    public Game getGame() {
        return game;
    }
}
