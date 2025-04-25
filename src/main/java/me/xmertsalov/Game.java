package me.xmertsalov;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import me.xmertsalov.background.BackgroundManager;
import me.xmertsalov.components.PlayerAnimator;
import me.xmertsalov.entities.Player;
import me.xmertsalov.scenes.GameScene;
import me.xmertsalov.scenes.inGame.*;
import me.xmertsalov.score.Score;

public class Game implements Runnable {
    private final GamePanel gamePanel;
	private Thread gameThread;
	private GraphicsDevice gd;

	// Game loop settings
	public final static int FPS_LIMIT = 120;
	public final static int UPS_LIMIT = 200;

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

	// Debug collisions
	public final static Color DEBUG_COLOR = new Color(238, 130, 238, 75);
	public final static Color DEBUG_COLOR_SECOND = new Color(255, 0, 0, 80);
	public final static boolean DEBUG_ENABLED = false;

	// All scenes
	private PlayingScene playingScene;
	private MenuScene menuScene;
	private LobbyScene lobbyScene;
	private SettingsScene settingsScene;
	private CreditsScene creditsScene;


	// Global game objects
	private ArrayList<Player> players;
	private BackgroundManager backgroundManager;
	private Score score;

	// Game Modes
	private boolean increasedGameSpeedMode = true;
	private boolean ghostMode = false;
	private boolean godMode = false;
	private boolean slowMode = false;
	private boolean borderlessMode = false;
	private boolean viewerMode = false;

	public Game() {
		setScale(WINDOW_WIDTH, WINDOW_HEIGHT);

		startGame();

		gamePanel = new GamePanel(this, new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        // Game window and panel
        GameWindow gameWindow = new GameWindow(gamePanel, this);
		gamePanel.requestFocus();

		startGameLoop();
	}

	// Change scale with resolution
	public void setScale(float width, float height) {
		SCALE = height / GAME_HEIGHT;
		TILES_SIZE = (int)Math.ceil(TILES_DEFAULT_SIZE * SCALE);

		System.out.println(width + " x " + height + " | scale: " + SCALE);
	}

	// This method is called to start the game loop
	private void startGameLoop() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	// This method is called when the game starts one time
	private void startGame() {
		players = new ArrayList<>();
		PlayerAnimator playerAnimator = new PlayerAnimator();

		players.add(new Player(0, 0, KeyEvent.VK_SHIFT, playerAnimator.clone(), "Adventure Boy A"));
		players.add(new Player(0, 0, KeyEvent.VK_C, playerAnimator.clone(), "Special Knight 1"));
		players.add(new Player(0, 0, KeyEvent.VK_N, playerAnimator.clone(), "Special Knight 2"));
		players.add(new Player(0, 0, KeyEvent.VK_L, playerAnimator.clone(), "Adventure Boy B"));

		playingScene = new PlayingScene(this);
		menuScene = new MenuScene(this);
		lobbyScene = new LobbyScene(this);

		score = new Score(this);
		backgroundManager = new BackgroundManager();
	}

	// This method is called every tick to update the game
	public void updateGame() {
		backgroundManager.update();
		switch (GameScene.scene){
			case PLAYING -> {
				playingScene.update();
				score.update();
            }
			case MENU -> menuScene.update();
			case LOBBY -> lobbyScene.update();
			case EXIT -> System.exit(0);
			default -> throw new IllegalStateException("Unexpected value: " + GameScene.scene);
		}
	}

	// This method is called every frame to render the game
	public void renderGame(Graphics g) {
		backgroundManager.draw(g);
		switch (GameScene.scene) {
			case PLAYING -> playingScene.draw(g);
			case MENU -> menuScene.draw(g);
			case LOBBY -> lobbyScene.draw(g);
			default -> throw new IllegalStateException("Unexpected value: " + GameScene.scene);
		}

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
		if (GameScene.scene == GameScene.PLAYING) {
			// logic for reset booleans when window is not focused
		}
	}

	public boolean isBorderlessMode() {return borderlessMode;}
	public boolean isSlowMode() {return slowMode;}
	public boolean isGodMode() {return godMode;}
	public boolean isGhostMode() {return ghostMode;}
	public boolean isIncreasedGameSpeedMode() {return increasedGameSpeedMode;}
	public boolean isViewerMode() {return viewerMode;}
	public void setGodMode(boolean godMode) {this.godMode = godMode;}
	public void setViewerMode(boolean viewerMode) {this.viewerMode = viewerMode;}
	public void setBorderlessMode(boolean borderlessMode) {this.borderlessMode = borderlessMode;}
	public void setSlowMode(boolean slowMode) {this.slowMode = slowMode;}
	public void setGhostMode(boolean ghostMode) {this.ghostMode = ghostMode;}
	public void setIncreasedGameSpeedMode(boolean increasedGameSpeedMode) {this.increasedGameSpeedMode = increasedGameSpeedMode;}

	public PlayingScene getPlayingScene() {return playingScene;}

	public MenuScene getMenuScene() {return menuScene;}

	public LobbyScene getLobbyScene() {return lobbyScene;}

	public ArrayList<Player> getPlayers() {return players;}

	public Score getScore() {return score;}
}
