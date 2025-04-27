package me.xmertsalov.scenes;

/**
 * Represents the different scenes or states in the GravityRunner game.
 * <p>
 * This enum defines all the possible scenes that the game can transition to,
 * such as the main menu, gameplay, settings, and more. It also includes a 
 * static field to track the current active scene.
 */
public enum GameScene {
    /**
     * The main menu scene where the player can start the game or navigate to other options.
     */
    MENU,

    /**
     * The gameplay scene where the main game logic and player interaction occur.
     */
    PLAYING,

    /**
     * The lobby scene, potentially used for multiplayer or pre-game setup.
     */
    LOBBY,

    /**
     * The settings scene where the player can adjust game configurations.
     */
    SETTINGS,

    /**
     * The exit scene, used when the player chooses to quit the game.
     */
    EXIT,

    /**
     * The panorama scene, possibly used for showcasing visuals or a cinematic view.
     */
    PANORAMA,

    /**
     * The tutorial scene where the player learns how to play the game.
     */
    TUTORIAL,

    /**
     * The credits scene, displaying information about the game's creators.
     */
    CREDITS,

    /**
     * The loading scene, shown while the game is preparing resources or transitioning in the start of game.
     */
    LOADING;

    /**
     * Tracks the current active scene in the game.
     * <p>
     * By default, the game starts in the LOADING scene.
     */
    public static GameScene scene = LOADING;
}
