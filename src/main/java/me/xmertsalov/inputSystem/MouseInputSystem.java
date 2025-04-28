package me.xmertsalov.inputSystem;

import java.awt.event.*;

import me.xmertsalov.GamePanel;
import me.xmertsalov.scenes.GameScene;

/**
 * The {@code MouseInputSystem} class handles mouse input events for the game.
 * It implements {@link MouseListener}, {@link MouseMotionListener}, and {@link MouseWheelListener}
 * to capture mouse clicks, movements, drags, and wheel scrolling.
 * Depending on the current game scene, it delegates the input handling to the appropriate scene.
 */
public class MouseInputSystem implements MouseListener, MouseMotionListener, MouseWheelListener {

	private GamePanel gamePanel;

	/**
	 * Constructs a {@code MouseInputSystem} with the specified {@link GamePanel}.
	 *
	 * @param gamePanel the game panel that this input system interacts with
	 */
	public MouseInputSystem(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}

	/**
	 * This method is triggered when the mouse is dragged.
	 * It delegates the mouse drag event to the appropriate scene based on the current game state.
	 *
	 * @param e the {@link MouseEvent} associated with the mouse drag
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
		switch (GameScene.scene) {
			case PLAYING:
				gamePanel.getGame().getPlayingScene().mouseDragged(e);
				break;
			case MENU:
				gamePanel.getGame().getMenuScene().mouseDragged(e);
				break;
			case LOBBY:
				gamePanel.getGame().getLobbyScene().mouseDragged(e);
				break;
			case SETTINGS:
				gamePanel.getGame().getSettingsScene().mouseDragged(e);
				break;
			case CREDITS:
				gamePanel.getGame().getCreditsScene().mouseDragged(e);
				break;
			case PANORAMA:
				gamePanel.getGame().getPanoramaScene().mouseDragged(e);
				break;
			case TUTORIAL:
				gamePanel.getGame().getTutorialScene().mouseDragged(e);
				break;
			case LOADING:
				break;
			default:
				throw new IllegalStateException("Unexpected value: " + GameScene.scene);
		}
	}

	/**
	 * This method is triggered when the mouse is moved.
	 * It delegates the mouse move event to the appropriate scene based on the current game state.
	 *
	 * @param e the {@link MouseEvent} associated with the mouse movement
	 */
	@Override
	public void mouseMoved(MouseEvent e) {
		switch (GameScene.scene) {
			case PLAYING:
				gamePanel.getGame().getPlayingScene().mouseMoved(e);
				break;
			case MENU:
				gamePanel.getGame().getMenuScene().mouseMoved(e);
				break;
			case LOBBY:
				gamePanel.getGame().getLobbyScene().mouseMoved(e);
				break;
			case SETTINGS:
				gamePanel.getGame().getSettingsScene().mouseMoved(e);
				break;
			case CREDITS:
				gamePanel.getGame().getCreditsScene().mouseMoved(e);
				break;
			case PANORAMA:
				gamePanel.getGame().getPanoramaScene().mouseMoved(e);
				break;
			case TUTORIAL:
				gamePanel.getGame().getTutorialScene().mouseMoved(e);
				break;
			case LOADING:
				break;
			default:
				throw new IllegalStateException("Unexpected value: " + GameScene.scene);
		}
	}

	/**
	 * This method is triggered when the mouse is clicked.
	 * It delegates the mouse click event to the appropriate scene based on the current game state.
	 *
	 * @param e the {@link MouseEvent} associated with the mouse click
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		switch (GameScene.scene) {
			case PLAYING:
				gamePanel.getGame().getPlayingScene().mouseClicked(e);
				break;
			case MENU:
				gamePanel.getGame().getMenuScene().mouseClicked(e);
				break;
			case LOBBY:
				gamePanel.getGame().getLobbyScene().mouseClicked(e);
				break;
			case SETTINGS:
				gamePanel.getGame().getSettingsScene().mouseClicked(e);
				break;
			case CREDITS:
				gamePanel.getGame().getCreditsScene().mouseClicked(e);
				break;
			case PANORAMA:
				gamePanel.getGame().getPanoramaScene().mouseClicked(e);
				break;
			case TUTORIAL:
				gamePanel.getGame().getTutorialScene().mouseClicked(e);
				break;
			case LOADING:
				break;
			default:
				throw new IllegalStateException("Unexpected value: " + GameScene.scene);
		}
	}

	/**
	 * This method is triggered when a mouse button is pressed.
	 * It delegates the mouse press event to the appropriate scene based on the current game state.
	 *
	 * @param e the {@link MouseEvent} associated with the mouse press
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		switch (GameScene.scene) {
			case PLAYING:
				gamePanel.getGame().getPlayingScene().mousePressed(e);
				break;
			case MENU:
				gamePanel.getGame().getMenuScene().mousePressed(e);
				break;
			case LOBBY:
				gamePanel.getGame().getLobbyScene().mousePressed(e);
				break;
			case SETTINGS:
				gamePanel.getGame().getSettingsScene().mousePressed(e);
				break;
			case CREDITS:
				gamePanel.getGame().getCreditsScene().mousePressed(e);
				break;
			case PANORAMA:
				gamePanel.getGame().getPanoramaScene().mousePressed(e);
				break;
			case TUTORIAL:
				gamePanel.getGame().getTutorialScene().mousePressed(e);
				break;
			case LOADING:
				break;
			default:
				throw new IllegalStateException("Unexpected value: " + GameScene.scene);
		}
	}

	/**
	 * This method is triggered when a mouse button is released.
	 * It delegates the mouse release event to the appropriate scene based on the current game state.
	 *
	 * @param e the {@link MouseEvent} associated with the mouse release
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		switch (GameScene.scene) {
			case PLAYING:
				gamePanel.getGame().getPlayingScene().mouseReleased(e);
				break;
			case MENU:
				gamePanel.getGame().getMenuScene().mouseReleased(e);
				break;
			case LOBBY:
				gamePanel.getGame().getLobbyScene().mouseReleased(e);
				break;
			case SETTINGS:
				gamePanel.getGame().getSettingsScene().mouseReleased(e);
				break;
			case CREDITS:
				gamePanel.getGame().getCreditsScene().mouseReleased(e);
				break;
			case PANORAMA:
				gamePanel.getGame().getPanoramaScene().mouseReleased(e);
				break;
			case TUTORIAL:
				gamePanel.getGame().getTutorialScene().mouseReleased(e);
				break;
			case LOADING:
				break;
			default:
				throw new IllegalStateException("Unexpected value: " + GameScene.scene);
		}
	}

	/**
	 * This method is triggered when the mouse enters a component.
	 * Currently, it is not implemented and does nothing.
	 *
	 * @param e the {@link MouseEvent} associated with the mouse entering the component
	 */
	@Override
	public void mouseEntered(MouseEvent e) {}

	/**
	 * This method is triggered when the mouse exits a component.
	 * Currently, it is not implemented and does nothing.
	 *
	 * @param e the {@link MouseEvent} associated with the mouse exiting the component
	 */
	@Override
	public void mouseExited(MouseEvent e) {}

	/**
	 * This method is triggered when the mouse wheel is moved.
	 * Currently, it is not implemented and does nothing.
	 *
	 * @param e the {@link MouseWheelEvent} associated with the mouse wheel movement
	 */
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {}
}
