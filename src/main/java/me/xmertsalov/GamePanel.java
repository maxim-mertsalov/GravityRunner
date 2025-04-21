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

	private void setPanelSize(Dimension dimension) {
//		Dimension size = new Dimension(1440, 900);
		setPreferredSize(dimension);
	}

	public void updateGame() {

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setColor(Color.white);
		for (int i = 0; i < 64; i++)
			for (int j = 0; j < 40; j++)
				g.fillRect(i * 20, j * 20, 20, 20);

		game.renderGame(g);

	}

	public Game getGame() {
		return game;
	}

}