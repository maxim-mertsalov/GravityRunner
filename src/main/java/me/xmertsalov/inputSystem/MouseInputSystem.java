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
			default -> throw new IllegalStateException("Unexpected value: " + GameScene.scene);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		switch (GameScene.scene){
			case PLAYING -> gamePanel.getGame().getPlayingScene().mouseClicked(e);
			case MENU -> gamePanel.getGame().getMenuScene().mouseClicked(e);
			default -> throw new IllegalStateException("Unexpected value: " + GameScene.scene);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		switch (GameScene.scene){
			case PLAYING -> gamePanel.getGame().getPlayingScene().mousePressed(e);
			case MENU -> gamePanel.getGame().getMenuScene().mousePressed(e);
			default -> throw new IllegalStateException("Unexpected value: " + GameScene.scene);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		switch (GameScene.scene){
			case PLAYING -> gamePanel.getGame().getPlayingScene().mouseReleased(e);
			case MENU -> gamePanel.getGame().getMenuScene().mouseReleased(e);
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
