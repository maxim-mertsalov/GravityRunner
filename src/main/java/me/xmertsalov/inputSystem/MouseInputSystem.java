package me.xmertsalov.inputSystem;

import java.awt.event.*;

import me.xmertsalov.GamePanel;
import me.xmertsalov.scenes.GameScene;

public class MouseInputSystem implements MouseListener, MouseMotionListener, MouseWheelListener {

	private GamePanel gamePanel;

	public MouseInputSystem(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}

	@Override
	public void mouseDragged(MouseEvent e) {}

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
			default -> throw new IllegalStateException("Unexpected value: " + GameScene.scene);
		}
	}

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
			default -> throw new IllegalStateException("Unexpected value: " + GameScene.scene);
		}
	}

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
			default -> throw new IllegalStateException("Unexpected value: " + GameScene.scene);
		}
	}

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
			default -> throw new IllegalStateException("Unexpected value: " + GameScene.scene);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {}
}
