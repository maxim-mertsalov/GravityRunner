package me.xmertsalov.inputSystem;

import java.awt.event.*;

import me.xmertsalov.GamePanel;

public class MouseInputSystem implements MouseListener, MouseMotionListener, MouseWheelListener {

	private GamePanel gamePanel;

	public MouseInputSystem(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}

	@Override
	public void mouseDragged(MouseEvent e) {}

	@Override
	public void mouseMoved(MouseEvent e) {}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1)
			gamePanel.getGame().getPlayer().setAttacking(true);
	}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {}
}
