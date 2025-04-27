import me.xmertsalov.Game;
import me.xmertsalov.GamePanel;
import me.xmertsalov.scenes.inGame.LoadingScene;
import me.xmertsalov.scenes.inGame.PlayingScene;
import me.xmertsalov.scenes.inGame.SettingsScene;
import me.xmertsalov.scenes.inGame.TutorialScene;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScenesTest {
    Game game;
    GamePanel gamePanel;

    @BeforeEach
    void setUp() {
        game = new Game(true, false);
        gamePanel = new GamePanel(game, new Dimension(800, 600));
    }

    @AfterEach
    void tearDown() {
        gamePanel = null;
        game = null;
    }

    @Test
    public void testLoadingSceneInitialization() {
        LoadingScene scene = new LoadingScene(game);
        assertNotNull(scene, "LoadingScene should be initialized");
        assertEquals(game, scene.getGame(), "LoadingScene should be associated with the correct game instance");
    }

    @Test
    public void testPlayingSceneInitialization() {
        PlayingScene scene = new PlayingScene(game);
        assertNotNull(scene, "PlayingScene should be initialized");
        assertEquals(game, scene.getGame(), "PlayingScene should be associated with the correct game instance");
    }

    @Test
    public void testSettingsSceneInitialization() {
        SettingsScene scene = new SettingsScene(game);
        assertNotNull(scene, "SettingsScene should be initialized");
        assertEquals(game, scene.getGame(), "SettingsScene should be associated with the correct game instance");
    }

    @Test
    public void testTutorialSceneInitialization() {
        TutorialScene scene = new TutorialScene(game);
        assertNotNull(scene, "TutorialScene should be initialized");
        assertEquals(game, scene.getGame(), "TutorialScene should be associated with the correct game instance");
    }
}
