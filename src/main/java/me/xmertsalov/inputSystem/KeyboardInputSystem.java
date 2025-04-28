package me.xmertsalov.inputSystem;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import me.xmertsalov.GamePanel;
import me.xmertsalov.scenes.GameScene;

/**
 * The {@code KeyboardInputSystem} class handles keyboard input events for the game.
 * It implements the {@link KeyListener} interface to capture key presses and releases.
 * Depending on the current game scene, it delegates the input handling to the appropriate scene.
 */
public class KeyboardInputSystem implements KeyListener {

	private GamePanel gamePanel;

	/**
	 * Constructs a {@code KeyboardInputSystem} with the specified {@link GamePanel}.
	 *
	 * @param gamePanel the game panel that this input system interacts with
	 */
	public KeyboardInputSystem(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}

	/**
	 * This method is triggered when a key is typed.
	 * Currently, it is not implemented and does nothing.
	 *
	 * @param e the {@link KeyEvent} associated with the key typed
	 */
	@Override
	public void keyTyped(KeyEvent e) {}

	/**
	 * This method is triggered when a key is released.
	 * It delegates the key release event to the appropriate scene based on the current game state.
	 *
	 * @param e the {@link KeyEvent} associated with the key released
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		switch (GameScene.scene){
			case PLAYING:
				gamePanel.getGame().getPlayingScene().keyReleased(e);
				break;
			case MENU:
				gamePanel.getGame().getMenuScene().keyReleased(e);
				break;
			case LOBBY:
				gamePanel.getGame().getLobbyScene().keyReleased(e);
				break;
			case SETTINGS:
				gamePanel.getGame().getSettingsScene().keyReleased(e);
				break;
			case CREDITS:
				gamePanel.getGame().getCreditsScene().keyReleased(e);
				break;
			case LOADING:
				break;
			default:
				throw new IllegalStateException("Unexpected value: " + GameScene.scene);
		}
	}

	/**
	 * This method is triggered when a key is pressed.
	 * It delegates the key press event to the appropriate scene based on the current game state.
	 *
	 * @param e the {@link KeyEvent} associated with the key pressed
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		switch (GameScene.scene){
			case PLAYING:
				gamePanel.getGame().getPlayingScene().keyPressed(e);
				break;
			case MENU:
				gamePanel.getGame().getMenuScene().keyPressed(e);
				break;
			case LOBBY:
				gamePanel.getGame().getLobbyScene().keyPressed(e);
				break;
			case SETTINGS:
				gamePanel.getGame().getSettingsScene().keyPressed(e);
				break;
			case CREDITS:
				gamePanel.getGame().getCreditsScene().keyPressed(e);
				break;
			case LOADING:
				break;
			default:
				throw new IllegalStateException("Unexpected value: " + GameScene.scene);
		}
	}
}
