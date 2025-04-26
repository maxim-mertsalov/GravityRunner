package me.xmertsalov.inputSystem;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import me.xmertsalov.GamePanel;
import me.xmertsalov.scenes.GameScene;

public class KeyboardInputSystem implements KeyListener {

	private GamePanel gamePanel;

	public KeyboardInputSystem(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	// Key up
	@Override
	public void keyReleased(KeyEvent e) {
		switch (GameScene.scene){
			case PLAYING -> gamePanel.getGame().getPlayingScene().keyReleased(e);
			case MENU -> gamePanel.getGame().getMenuScene().keyReleased(e);
			case LOBBY -> gamePanel.getGame().getLobbyScene().keyReleased(e);
			case SETTINGS -> gamePanel.getGame().getSettingsScene().keyReleased(e);
			case CREDITS -> gamePanel.getGame().getCreditsScene().keyReleased(e);
			case LOADING -> {
				break;
			}
			default -> throw new IllegalStateException("Unexpected value: " + GameScene.scene);
		}
	}

	// Key down
	@Override
	public void keyPressed(KeyEvent e) {
		switch (GameScene.scene){
			case PLAYING -> gamePanel.getGame().getPlayingScene().keyPressed(e);
			case MENU -> gamePanel.getGame().getMenuScene().keyPressed(e);
			case LOBBY -> gamePanel.getGame().getLobbyScene().keyPressed(e);
			case SETTINGS -> gamePanel.getGame().getSettingsScene().keyPressed(e);
			case CREDITS -> gamePanel.getGame().getCreditsScene().keyPressed(e);
			case LOADING -> {
				break;
			}
			default -> throw new IllegalStateException("Unexpected value: " + GameScene.scene);
		}
	}
}
