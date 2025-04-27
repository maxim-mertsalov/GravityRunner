import me.xmertsalov.components.PlayerAnimator;
import me.xmertsalov.entities.Player;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    Player player;
    PlayerAnimator playerAnimator;

    @BeforeEach
    void setUp() {
        // Use a mock PlayerAnimator to avoid loading large resources
        playerAnimator = null;
        player = new Player(0, 0, KeyEvent.VK_C, null, "TestPlayer");
    }

    @AfterEach
    void tearDown() {
        playerAnimator = null;
        player = null;
    }

    @Test
    public void testPlayer() {
        player = new Player(0, 0, KeyEvent.VK_C, null, "TestPlayer");
        assertEquals(player, new NullPointerException("PlayerAnimator is null"));
    }
}
