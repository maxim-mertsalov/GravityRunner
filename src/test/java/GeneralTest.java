
import me.xmertsalov.Game;
import me.xmertsalov.GamePanel;
import me.xmertsalov.inputSystem.KeyboardInputSystem;
import me.xmertsalov.inputSystem.MouseInputSystem;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class GeneralTest {
    MouseInputSystem mouseInputSystem;
    KeyboardInputSystem keyboardInputSystem;
    GamePanel gamePanel;
    Game game;

    @BeforeEach
    void setUp() {
        game = new Game(true, false);
        gamePanel = new GamePanel(game, new Dimension(800, 600));
        mouseInputSystem = new MouseInputSystem(gamePanel);
        keyboardInputSystem = new KeyboardInputSystem(gamePanel);
    }

    @AfterEach
    void tearDown() {
        mouseInputSystem = null;
        keyboardInputSystem = null;
        gamePanel = null;
        game = null;
    }

    @Test
    public void testMouseInputSystemInitialization() {
        assertNotNull(mouseInputSystem);
    }

    @Test
    public void testKeyboardInputSystemInitialization() {
        assertNotNull(keyboardInputSystem);
    }

    @Test
    public void testGamePanelInitialization() {
        assertNotNull(gamePanel);
    }

    @Test
    public void testGameInitialization() {
        assertNotNull(game);
    }

    @Test
    public void testGamePanelSize() {
        assertNotEquals(gamePanel.getWidth(), 800);
        assertNotEquals(gamePanel.getHeight(), 600);
    }

    @Test
    public void testDebugMode() {
        assertEquals(game.DEBUG_COLLIDERS, true);
        assertEquals(game.DEBUG_FPS, false);
    }
}
