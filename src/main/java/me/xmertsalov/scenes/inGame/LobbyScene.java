package me.xmertsalov.scenes.inGame;

import me.xmertsalov.Game;
import me.xmertsalov.scenes.IScene;
import me.xmertsalov.scenes.Scene;
import me.xmertsalov.ui.lobby.LobbyManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * Represents the lobby scene in the game.
 * <p>
 * This class is responsible for managing the lobby state, including resetting the lobby,
 * updating its state, and delegating input events (mouse and keyboard) to the {@link LobbyManager}.
 * It extends the {@link Scene} class and implements the {@link IScene} interface.
 */
public class LobbyScene extends Scene implements IScene {
    // Dependencies
    private Game game;
    private LobbyManager lobbyManager;

    // States
    private boolean reseted = false;

    /**
     * Constructs a new LobbyScene.
     *
     * @param game The main game instance.
     */
    public LobbyScene(Game game) {
        super(game);
        this.game = game;
        this.lobbyManager = new LobbyManager(game);
    }

    /**
     * Updates the state of the lobby scene.
     * <p>
     * This method ensures that the lobby is reset if needed and delegates the update logic to the {@link LobbyManager}.
     */
    @Override
    public void update() {
        resetAll();
        lobbyManager.update();
    }

    /**
     * Draws the lobby scene.
     * <p>
     * Delegates the drawing logic to the {@link LobbyManager}.
     *
     * @param g The {@link Graphics} object used for rendering.
     */
    @Override
    public void draw(Graphics g) {
        lobbyManager.draw(g);
    }

    /**
     * Resets the lobby state if it hasn't been reset already.
     * <p>
     * This method ensures that the {@link LobbyManager} is reset only once per lifecycle.
     */
    private void resetAll() {
        if (reseted) return;

        lobbyManager.reset();

        reseted = true;
    }

    /**
     * Handles mouse click events.
     * <p>
     * Delegates the event to the {@link LobbyManager}.
     *
     * @param e The {@link MouseEvent} object containing event details.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        lobbyManager.mouseClicked(e);
    }

    /**
     * Handles mouse press events.
     * <p>
     * Delegates the event to the {@link LobbyManager}.
     *
     * @param e The {@link MouseEvent} object containing event details.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        lobbyManager.mousePressed(e);
    }

    /**
     * Handles mouse release events.
     * <p>
     * Delegates the event to the {@link LobbyManager}.
     *
     * @param e The {@link MouseEvent} object containing event details.
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        lobbyManager.mouseReleased(e);
    }

    /**
     * Handles mouse movement events.
     * <p>
     * Delegates the event to the {@link LobbyManager}.
     *
     * @param e The {@link MouseEvent} object containing event details.
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        lobbyManager.mouseMoved(e);
    }

    /**
     * Handles mouse drag events.
     * <p>
     * This method is currently not implemented.
     *
     * @param e The {@link MouseEvent} object containing event details.
     */
    @Override
    public void mouseDragged(MouseEvent e) {}

    /**
     * Handles key press events.
     * <p>
     * Delegates the event to the {@link LobbyManager}.
     *
     * @param e The {@link KeyEvent} object containing event details.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        lobbyManager.keyPressed(e);
    }

    /**
     * Handles key release events.
     * <p>
     * Delegates the event to the {@link LobbyManager}.
     *
     * @param e The {@link KeyEvent} object containing event details.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        lobbyManager.keyReleased(e);
    }

    /**
     * Resets the lobby scene state.
     * <p>
     * This method sets the reset flag to false, allowing the lobby to be reset again.
     */
    public void reset() {
        reseted = false;
    }
}
