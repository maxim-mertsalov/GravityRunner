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
		switch (GameScene.scene){
			case PLAYING -> gamePanel.getGame().getPlayingScene().mouseDragged(e);
			case MENU -> gamePanel.getGame().getMenuScene().mouseDragged(e);
			case LOBBY -> gamePanel.getGame().getLobbyScene().mouseDragged(e);
			case SETTINGS -> gamePanel.getGame().getSettingsScene().mouseDragged(e);
			case CREDITS -> gamePanel.getGame().getCreditsScene().mouseDragged(e);
			case PANORAMA -> gamePanel.getGame().getPanoramaScene().mouseDragged(e);
			case TUTORIAL -> gamePanel.getGame().getTutorialScene().mouseDragged(e);
            case LOADING -> {
                break;
            }
            default -> throw new IllegalStateException("Unexpected value: " + GameScene.scene);
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
		switch (GameScene.scene){
			case PLAYING -> gamePanel.getGame().getPlayingScene().mouseMoved(e);
			case MENU -> gamePanel.getGame().getMenuScene().mouseMoved(e);
			case LOBBY -> gamePanel.getGame().getLobbyScene().mouseMoved(e);
			case SETTINGS -> gamePanel.getGame().getSettingsScene().mouseMoved(e);
			case CREDITS -> gamePanel.getGame().getCreditsScene().mouseMoved(e);
			case PANORAMA -> gamePanel.getGame().getPanoramaScene().mouseMoved(e);
			case TUTORIAL -> gamePanel.getGame().getTutorialScene().mouseMoved(e);
			case LOADING -> {
				break;
			}
			default -> throw new IllegalStateException("Unexpected value: " + GameScene.scene);
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
		switch (GameScene.scene){
			case PLAYING -> gamePanel.getGame().getPlayingScene().mouseClicked(e);
			case MENU -> gamePanel.getGame().getMenuScene().mouseClicked(e);
			case LOBBY -> gamePanel.getGame().getLobbyScene().mouseClicked(e);
			case SETTINGS -> gamePanel.getGame().getSettingsScene().mouseClicked(e);
			case CREDITS -> gamePanel.getGame().getCreditsScene().mouseClicked(e);
			case PANORAMA -> gamePanel.getGame().getPanoramaScene().mouseClicked(e);
			case TUTORIAL -> gamePanel.getGame().getTutorialScene().mouseClicked(e);
			case LOADING -> {
				break;
			}
			default -> throw new IllegalStateException("Unexpected value: " + GameScene.scene);
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
		switch (GameScene.scene){
			case PLAYING -> gamePanel.getGame().getPlayingScene().mousePressed(e);
			case MENU -> gamePanel.getGame().getMenuScene().mousePressed(e);
			case LOBBY -> gamePanel.getGame().getLobbyScene().mousePressed(e);
			case SETTINGS -> gamePanel.getGame().getSettingsScene().mousePressed(e);
			case CREDITS -> gamePanel.getGame().getCreditsScene().mousePressed(e);
			case PANORAMA -> gamePanel.getGame().getPanoramaScene().mousePressed(e);
			case TUTORIAL -> gamePanel.getGame().getTutorialScene().mousePressed(e);
			case LOADING -> {
				break;
			}
			default -> throw new IllegalStateException("Unexpected value: " + GameScene.scene);
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
		switch (GameScene.scene){
			case PLAYING -> gamePanel.getGame().getPlayingScene().mouseReleased(e);
			case MENU -> gamePanel.getGame().getMenuScene().mouseReleased(e);
			case LOBBY -> gamePanel.getGame().getLobbyScene().mouseReleased(e);
			case SETTINGS -> gamePanel.getGame().getSettingsScene().mouseReleased(e);
			case CREDITS -> gamePanel.getGame().getCreditsScene().mouseReleased(e);
			case PANORAMA -> gamePanel.getGame().getPanoramaScene().mouseReleased(e);
			case TUTORIAL -> gamePanel.getGame().getTutorialScene().mouseReleased(e);
			case LOADING -> {
				break;
			}
			default -> throw new IllegalStateException("Unexpected value: " + GameScene.scene);
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
