package me.xmertsalov;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.*;

/**
 * The {@code GameWindow} class is responsible for creating and managing the main game window.
 * It initializes a {@link JFrame} to display the game and handles window focus events.
 */
public class GameWindow {
	private JFrame jFrame;
	private GraphicsEnvironment graphics;
	private GraphicsDevice device;
	private Game game;

	/**
	 * Constructs a {@code GameWindow} object.
	 *
	 * @param gamePanel The {@link GamePanel} to be added to the game window. It represents the main game rendering surface.
	 * @param game The {@link Game} instance that contains the game logic and state.
	 */
	public GameWindow(GamePanel gamePanel, Game game) {
		this.game = game;

		jFrame = new JFrame();

		// Initialize graphics environment and device for potential future use.
		graphics = GraphicsEnvironment.getLocalGraphicsEnvironment();
		device = graphics.getDefaultScreenDevice();

		// Configure the JFrame properties.
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setTitle(" Gravity Runner ");
		jFrame.add(gamePanel);
		jFrame.setLocationRelativeTo(null);
		jFrame.setResizable(false);

		// Add a window focus listener to handle focus changes.
		jFrame.addWindowFocusListener(new WindowFocusListener() {
			@Override
			public void windowLostFocus(WindowEvent e) {
				// Notify the game when the window loses focus.
				gamePanel.getGame().windowFocusLost();
			}

			@Override
			public void windowGainedFocus(WindowEvent e) {
				// No specific action is taken when the window gains focus.
			}
		});
	}

	/**
	 * Returns the {@link JFrame} instance used by this game window.
	 *
	 * @return The {@link JFrame} instance.
	 */
	public JFrame getJFrame() {
		return jFrame;
	}

}
