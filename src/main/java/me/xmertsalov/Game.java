package me.xmertsalov;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import me.xmertsalov.audio.AudioPlayer;
import me.xmertsalov.background.BackgroundManager;
import me.xmertsalov.components.PlayerAnimator;
import me.xmertsalov.config.Config;
import me.xmertsalov.entities.Player;
import me.xmertsalov.scenes.GameScene;
import me.xmertsalov.scenes.inGame.*;
import me.xmertsalov.score.Score;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The {@code Game} class serves as the main entry point for the GravityRunner game.
 * It initializes the game, manages the game loop, and handles rendering and updates.
 * This class also manages global game settings, scenes, and modes.
 */
public class Game implements Runnable {
    private final GamePanel gamePanel;
	private Thread gameThread;
	private final GraphicsEnvironment graphics;
	private final GraphicsDevice device;

	// Game loop settings
	public final static int FPS_LIMIT = 120;
	public final static int UPS_LIMIT = 200;

	private int frameCount = 0;

	// Game dimensions
	public final static int TILES_DEFAULT_SIZE = 32;
	public static float SCALE = 1f;
	public final static int TILES_IN_WIDTH = 26;
	public final static int TILES_IN_HEIGHT = 14;
	public static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
	public static int GAME_WIDTH = TILES_DEFAULT_SIZE * TILES_IN_WIDTH;
	public static int GAME_HEIGHT = TILES_DEFAULT_SIZE * TILES_IN_HEIGHT;
	public static int WINDOW_WIDTH = 1440; // 1440
	public static int WINDOW_HEIGHT = 900; // 900

	// Debug
	public final static Color DEBUG_COLOR = new Color(238, 130, 238, 75);
	public final static Color DEBUG_COLOR_SECOND = new Color(255, 0, 0, 80);
	public static boolean DEBUG_COLLIDERS = false;
	private static boolean DEBUG_FPS = false;

	// All scenes
	private PlayingScene playingScene;
	private MenuScene menuScene;
	private LobbyScene lobbyScene;
	private SettingsScene settingsScene;
	private CreditsScene creditsScene;
	private PanoramaScene panoramaScene;
	private TutorialScene tutorialScene;
	private LoadingScene loadingScene;


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

	// Config
	private final Config config;

	// Music
	private AudioPlayer audioPlayer;

	// Logger
	public static final Logger logger = LogManager.getLogger(Game.class);

	/**
	 * Constructs a new {@code Game} instance with specified debug settings.
	 *
	 * @param showColliders whether to display collision boundaries for debugging
	 * @param showFPS       whether to display the current frames per second (FPS) for debugging
	 */
	public Game(boolean showColliders, boolean showFPS) {
		DEBUG_FPS = showFPS;
		DEBUG_COLLIDERS = showColliders;

		graphics = GraphicsEnvironment.getLocalGraphicsEnvironment();
		device = graphics.getDefaultScreenDevice();

		// Load config
		config = Config.loadConfig();
		WINDOW_WIDTH = config.getResolutionWidth();
		WINDOW_HEIGHT = config.getResolutionHeight();


		// Game window and panel
		gamePanel = new GamePanel(this, new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		GameWindow gameWindow = new GameWindow(gamePanel, this);

		// if fullscreen mode is enabled
		if (WINDOW_WIDTH == 0 && WINDOW_HEIGHT == 0){
			// if fullscreen mode supported by the device -> set fullscreen
			if (device.isFullScreenSupported()) {
				gameWindow.getJFrame().setUndecorated(true);
				device.setFullScreenWindow(gameWindow.getJFrame());
			}

			// else set maximum resolution
			else{
				gameWindow.getJFrame().setSize(device.getDisplayMode().getWidth(), device.getDisplayMode().getHeight());
			}

			// update window size
			WINDOW_WIDTH = device.getDisplayMode().getWidth();
			WINDOW_HEIGHT = device.getDisplayMode().getHeight();
			gamePanel.setPanelSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		}

		// set window visible
		gameWindow.getJFrame().pack();
		gameWindow.getJFrame().setVisible(true);

		// request focus for inputs
		gamePanel.requestFocus();

		setScale(WINDOW_WIDTH, WINDOW_HEIGHT);

		startGame();

		startGameLoop();
	}

	/**
	 * Adjusts the game's scale based on the given width and height.
	 * Updates the tile size and logs the new scale.
	 *
	 * @param width  the width of the game window
	 * @param height the height of the game window
	 */
	public void setScale(float width, float height) {
		SCALE = height / GAME_HEIGHT;
		TILES_SIZE = (int)Math.ceil(TILES_DEFAULT_SIZE * SCALE);

        logger.info("Game started with {}x{} resolution and scale: {}", (int)width, (int)height, SCALE);
	}

	/**
	 * Starts the game loop in a separate thread.
	 * This method initializes the game thread and begins execution.
	 */
	private void startGameLoop() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	/**
	 * Initializes the game by setting up background, audio, players, scenes, and score.
	 * This method is called once when the game starts.
	 */
	private void startGame() {
		backgroundManager = new BackgroundManager();
		audioPlayer = new AudioPlayer();

		audioPlayer.setMusicVolume((float) config.getMusicVolume() / 100);
		audioPlayer.setSfxVolume((float) config.getSfxVolume() / 100);

		players = new ArrayList<>();
		PlayerAnimator playerAnimator = new PlayerAnimator();

		players.add(new Player(0, 0, KeyEvent.VK_SHIFT, playerAnimator.clone(), "Adventure Boy A"));
		players.add(new Player(0, 0, KeyEvent.VK_C, playerAnimator.clone(), "Special Knight 1"));
		players.add(new Player(0, 0, KeyEvent.VK_N, playerAnimator.clone(), "Special Knight 2"));
		players.add(new Player(0, 0, KeyEvent.VK_L, playerAnimator.clone(), "Adventure Boy B"));

		playingScene = new PlayingScene(this);
		menuScene = new MenuScene(this);
		lobbyScene = new LobbyScene(this);
		creditsScene = new CreditsScene(this);
		settingsScene = new SettingsScene(this);
		tutorialScene = new TutorialScene(this);
		panoramaScene = new PanoramaScene(this);
		loadingScene = new LoadingScene(this);

		score = new Score(this);

	}

	/**
	 * Updates the game state on every tick.
	 * This includes updating the background, audio, and the current scene based on the game state.
	 */
	public void updateGame() {
		backgroundManager.update();
		audioPlayer.update();
		audioPlayer.autoGenerateMusic();
		switch (GameScene.scene){
			case PLAYING -> {
				playingScene.update();
				score.update();
            }
			case MENU -> menuScene.update();
			case LOBBY -> lobbyScene.update();
			case SETTINGS -> settingsScene.update();
			case CREDITS -> creditsScene.update();
			case EXIT -> System.exit(0);
			case PANORAMA -> panoramaScene.update();
			case TUTORIAL -> tutorialScene.update();
			case LOADING -> loadingScene.update();
			default -> throw new IllegalStateException("Unexpected value: " + GameScene.scene);
		}
	}

	/**
	 * Renders the game graphics on every frame.
	 * This includes drawing the background, the current scene, and optionally the FPS counter.
	 *
	 * @param g the {@link Graphics} object used for rendering
	 */
	public void renderGame(Graphics g) {
		if (menuScene == null || backgroundManager == null) return;

		backgroundManager.draw(g);

		switch (GameScene.scene) {
			case PLAYING -> playingScene.draw(g);
			case MENU -> menuScene.draw(g);
			case LOBBY -> lobbyScene.draw(g);
			case SETTINGS -> settingsScene.draw(g);
			case CREDITS -> creditsScene.draw(g);
			case PANORAMA -> panoramaScene.draw(g);
			case TUTORIAL -> tutorialScene.draw(g);
			case LOADING -> loadingScene.draw(g);
			default -> throw new IllegalStateException("Unexpected value: " + GameScene.scene);
		}

		if (DEBUG_FPS) {
			g.setColor(Color.GREEN);
			g.setFont(new Font("Arial", Font.BOLD, (int)(8 * Game.SCALE)));
			g.drawString("FPS: " + frameCount, (int)(Game.WINDOW_WIDTH - 42 * Game.SCALE), (int)(12 * Game.SCALE));
		}

	}

	/**
	 * The main game loop that handles updates and rendering.
	 * It maintains a consistent FPS and UPS (updates per second) rate.
	 */
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
				this.frameCount = frameCount;
				frameCount = 0;
				updateCount = 0;

			}
		}

	}

	/**
	 * Handles logic when the game window loses focus.
	 * This is used to reset certain states when the game is not in focus.
	 */
	public void windowFocusLost() {
		if (GameScene.scene == GameScene.PLAYING) {
			// logic for reset booleans when window is not focused
		}
	}

	// Game modes getters and setters
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

	// Getters Scenes
	public PlayingScene getPlayingScene() {return playingScene;}
	public MenuScene getMenuScene() {return menuScene;}
	public LobbyScene getLobbyScene() {return lobbyScene;}
	public SettingsScene getSettingsScene() {return settingsScene;}
	public PanoramaScene getPanoramaScene() {return panoramaScene;}
	public TutorialScene getTutorialScene() {return tutorialScene;}
	public LoadingScene getLoadingScene() {return loadingScene;}
	public CreditsScene getCreditsScene() {return creditsScene;}

	// Getters GameObjects
	public ArrayList<Player> getPlayers() {return players;}
	public Score getScore() {return score;}
	public Config getConfig() {return config;}
	public AudioPlayer getAudioPlayer() {return audioPlayer;}
}
