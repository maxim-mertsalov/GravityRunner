import me.xmertsalov.exceptions.BundleLoadException;
import me.xmertsalov.utils.BundleLoader;

import java.awt.image.BufferedImage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class BundleLoaderTest {
    @Test
    public void testBundleLoaderException() {
        // Initialize the BundleLoader and check if it's not null
        BufferedImage image = null;
        try {
            image = BundleLoader.getSpriteAtlas("not exist path");
        } catch (BundleLoadException e) {
            assertEquals(e .getMessage(), "Resource not foundnot exist path");
        }
    }


    @Test
    public void testBundleLoaderInitialization() {
        // Initialize the BundleLoader and check if it's not null
        BufferedImage image = null;
        try {
            image = BundleLoader.getSpriteAtlas(BundleLoader.MENU_BACKGROUND);
        } catch (BundleLoadException e) {
        }

        assertNotNull(image);

    }
}
