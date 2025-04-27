import me.xmertsalov.background.BackgroundManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class BackgroundTest {
    BackgroundManager backgroundManager;

    @BeforeEach
    void setUp() {
        backgroundManager = new BackgroundManager();
    }

    @AfterEach
    void tearDown() {
        backgroundManager = null;
    }

    @Test
    public void testBackgroundManagerInitialization() {
        assertNotNull(backgroundManager);
    }
}
