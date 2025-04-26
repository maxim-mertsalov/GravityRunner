package me.xmertsalov;

import java.awt.*;

import javax.swing.JPanel;

import me.xmertsalov.inputSystem.KeyboardInputSystem;
import me.xmertsalov.inputSystem.MouseInputSystem;

public class GamePanel extends JPanel {

	private MouseInputSystem mouseInputSystem;
	private KeyboardInputSystem keyboardInputSystem;
	private Game game;


	public GamePanel(Game game, Dimension dimension) {
		mouseInputSystem = new MouseInputSystem(this);
		keyboardInputSystem = new KeyboardInputSystem(this);
		this.game = game;

		setPanelSize(dimension);
		addKeyListener(keyboardInputSystem);
		addMouseListener(mouseInputSystem);
		addMouseMotionListener(mouseInputSystem);

	}

	public void setPanelSize(Dimension dimension) {
		setPreferredSize(dimension);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		game.renderGame(g);

	}

	public Game getGame() {
		return game;
	}

}