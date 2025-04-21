package me.xmertsalov;

import java.awt.*;

import me.xmertsalov.components.PhisicsControler;
import me.xmertsalov.entities.Player;
import me.xmertsalov.gameWorld.LevelsManager;

public class Game implements Runnable {
	// Game window and panel
	private GameWindow gameWindow;
	private GamePanel gamePanel;
	private Thread gameThread;
	private GraphicsDevice gd;

	// Game loop
	private final int FPS_LIMIT = 120;
	private final int UPS_LIMIT = 200;

	// Game dimensions
	public final static int TILES_DEFAULT_SIZE = 32;
	public static float SCALE = 1f;
	public final static int TILES_IN_WIDTH = 26;
	public final static int TILES_IN_HEIGHT = 14;
	public static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
	public static int GAME_WIDTH = TILES_DEFAULT_SIZE * TILES_IN_WIDTH;
	public static int GAME_HEIGHT = TILES_DEFAULT_SIZE * TILES_IN_HEIGHT;
	public final static int WINDOW_WIDTH = 1440; // 1440
	public final static int WINDOW_HEIGHT = 900; // 900

	public final static Color DEBUG_COLOR = new Color(238, 130, 238, 75);
	public final static Color DEBUG_COLOR_SECOND = new Color(255, 0, 0, 80);
	public final static boolean DEBUG_ENABLED = false;


	// Game objects
	private Player player;
	private LevelsManager levelsManager;
	private PhisicsControler phisicsControler;

	public Game() {
		setScale(WINDOW_WIDTH, WINDOW_HEIGHT);

		startGame();

		gamePanel = new GamePanel(this, new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		gameWindow = new GameWindow(gamePanel, this);
		gamePanel.requestFocus();

		startGameLoop();
	}

	// Change scale with resolution
	public void setScale(float width, float height) {
//		SCALE = width / GAME_WIDTH;
		SCALE = height / GAME_HEIGHT;
		TILES_SIZE = (int)Math.ceil(TILES_DEFAULT_SIZE * SCALE);

		System.out.println("GAME WIDTH: " + GAME_WIDTH);

		System.out.println(width + " x " + height + " | scale: " + SCALE);
	}

	// This method is called to start the game loop
	private void startGameLoop() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	// This method is called when the game starts one time
	private void startGame() {
		player = new Player((int)(WINDOW_WIDTH / 2) - TILES_SIZE, 200);
		levelsManager = new LevelsManager(this);
		phisicsControler = new PhisicsControler(this);
	}

	// This method is called every tick to update the game
	public void updateGame() {
		player.update();
		levelsManager.update();
		phisicsControler.update();
	}

	// This method is called every frame to render the game
	public void renderGame(Graphics g) {
		player.render(g);
		levelsManager.render(g);
		phisicsControler.render(g);
	}

	@Override
	public void run() {

		double timePerFrame = 1000000000.0 / FPS_LIMIT;
		double timePerUpdate = 1000000000.0 / UPS_LIMIT;

		long previousTime = System.nanoTime();

		int frameCount = 0;
		int updateCount = 0;
		long lastCheck = System.currentTimeMillis();

		double deltaUpdate = 0;
		double deltaFrame = 0;

		// Game loop
		while (true) {
			long currentTime = System.nanoTime();

			deltaUpdate += (currentTime - previousTime) / timePerUpdate;
			deltaFrame += (currentTime - previousTime) / timePerFrame;
			previousTime = currentTime;

			// Update the game
			if (deltaUpdate >= 1) {
				updateGame();
				updateCount++;
				deltaUpdate--;
			}

			// Render the game
			if (deltaFrame >= 1) {
				gamePanel.repaint();
				frameCount++;
				deltaFrame--;
			}

			// FPS and UPS counter
			if (System.currentTimeMillis() - lastCheck >= 1000) {
				lastCheck = System.currentTimeMillis();
//				System.out.println("FPS: " + frameCount + " | UPS: " + updateCount);
				frameCount = 0;
				updateCount = 0;

			}
		}

	}

	public void windowFocusLost() {
		player.resetDirBooleans();
	}

	public Player getPlayer() {
		return player;
	}

	public LevelsManager getLevelsManager() {
		return levelsManager;
	}
}
