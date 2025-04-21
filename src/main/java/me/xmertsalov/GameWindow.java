package me.xmertsalov;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.*;

public class GameWindow {
	private JFrame jFrame;
	private GraphicsEnvironment graphics;
	private GraphicsDevice device;
	private Game game;

	public GameWindow(GamePanel gamePanel, Game game) {
		this.game = game;

		jFrame = new JFrame();

		graphics = GraphicsEnvironment.getLocalGraphicsEnvironment();
		device = graphics.getDefaultScreenDevice();

		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setTitle(" Gravity Runner ");
		jFrame.add(gamePanel);
		jFrame.setLocationRelativeTo(null);

		jFrame.setResizable(false);
//		jFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);

//		jFrame.setJMenuBar(new JMenuBar());

		jFrame.pack();
		jFrame.setVisible(true);

//		if (device.isFullScreenSupported()) {
//			device.setFullScreenWindow(jFrame);
//			jFrame.setUndecorated(true);
//		}
//		else{
//			jFrame.setSize(device.getDisplayMode().getWidth(), device.getDisplayMode().getHeight());
//		}

//		game.setScale(device.getDisplayMode().getWidth(), device.getDisplayMode().getHeight());

		jFrame.addWindowFocusListener(new WindowFocusListener() {
			@Override
			public void windowLostFocus(WindowEvent e) {
				gamePanel.getGame().windowFocusLost();
			}

			@Override
			public void windowGainedFocus(WindowEvent e) {}
		});

	}

}
