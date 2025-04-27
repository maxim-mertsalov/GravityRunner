package me.xmertsalov;

import java.awt.*;

import javax.swing.JPanel;

import me.xmertsalov.inputSystem.KeyboardInputSystem;
import me.xmertsalov.inputSystem.MouseInputSystem;

/**
 * The {@code GamePanel} class is a custom JPanel that serves as the main rendering
 * and input handling panel for the game. It integrates mouse and keyboard input systems
 * and provides a rendering surface for the game.
 */
public class GamePanel extends JPanel {

	private MouseInputSystem mouseInputSystem;
	private KeyboardInputSystem keyboardInputSystem;
	private Game game;

	/**
	 * Constructs a {@code GamePanel} with the specified game instance and panel dimensions.
	 * Initializes input systems and sets up the panel size and listeners.
	 *
	 * @param game      the {@code Game} instance to be rendered and interacted with
	 * @param dimension the {@code Dimension} specifying the size of the panel
	 */
	public GamePanel(Game game, Dimension dimension) {
		mouseInputSystem = new MouseInputSystem(this);
		keyboardInputSystem = new KeyboardInputSystem(this);
		this.game = game;

		setPanelSize(dimension);
		addKeyListener(keyboardInputSystem);
		addMouseListener(mouseInputSystem);
		addMouseMotionListener(mouseInputSystem);

	}

	/**
	 * Sets the preferred size of the panel.
	 *
	 * @param dimension the {@code Dimension} specifying the desired size of the panel
	 */
	public void setPanelSize(Dimension dimension) {
		setPreferredSize(dimension);
	}

	/**
	 * Paints the game components onto the panel. This method is called automatically
	 * by the Swing framework whenever the panel needs to be repainted.
	 *
	 * @param g the {@code Graphics} object used for drawing
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		game.renderGame(g);

	}

	/**
	 * Returns the {@code Game} instance associated with this panel.
	 *
	 * @return the {@code Game} instance
	 */
	public Game getGame() {
		return game;
	}

}