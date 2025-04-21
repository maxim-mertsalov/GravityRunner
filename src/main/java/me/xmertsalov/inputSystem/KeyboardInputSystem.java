package me.xmertsalov.inputSystem;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import me.xmertsalov.GamePanel;

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
		switch (e.getKeyCode()) {
		case KeyEvent.VK_W:
			gamePanel.getGame().getPlayer().setUp(false);
			break;
		case KeyEvent.VK_A:
//			gamePanel.getGame().getPlayer().setLeft(false);
			gamePanel.getGame().getLevelsManager().setLeft(false);
			break;
		case KeyEvent.VK_S:
			gamePanel.getGame().getPlayer().setDown(false);
			break;
		case KeyEvent.VK_D:
//			gamePanel.getGame().getPlayer().setRight(false);
			gamePanel.getGame().getLevelsManager().setRight(false);
			break;
		case KeyEvent.VK_SHIFT:
//			gamePanel.getGame().getPlayer().setChangedGravity(false);
			gamePanel.getGame().getPlayer().changeGravity();
			break;
		}
	}

	// Key down
	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_W:
			gamePanel.getGame().getPlayer().setUp(true);
			break;
		case KeyEvent.VK_A:
//			gamePanel.getGame().getPlayer().setLeft(true);
			gamePanel.getGame().getLevelsManager().setLeft(true);
			break;
		case KeyEvent.VK_S:
			gamePanel.getGame().getPlayer().setDown(true);
			break;
		case KeyEvent.VK_D:
//			gamePanel.getGame().getPlayer().setRight(true);
			gamePanel.getGame().getLevelsManager().setRight(true);
			break;
		case KeyEvent.VK_SHIFT:
//			gamePanel.getGame().getPlayer().setChangedGravity(true);
			break;
		}
	}
}
