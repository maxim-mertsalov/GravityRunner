package me.xmertsalov.utils;

import me.xmertsalov.Game;
import me.xmertsalov.exceptions.BundleLoadException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * The {@code BundleLoader} class provides utility methods for loading various game resources,
 * such as sprite atlases, text files, and other assets required by the game.
 * It also defines constants for resource file paths used throughout the game.
 *
 * <p>Key functionalities include:
 * <ul>
 *   <li>Loading sprite atlas images as {@link BufferedImage} objects.</li>
 *   <li>Reading the content of text files as {@link String} objects.</li>
 * </ul>
 *
 * <p>All resource paths are defined as constants to ensure consistency and ease of access.
 * If a resource cannot be found or an I/O error occurs, a {@link BundleLoadException} is thrown.
 */
public class BundleLoader {
    // Player's skins
    public static final String PLAYER_BOY_ADVENTURE_A_ATLAS = "res/Players/Boy_Adventure A.png";
    public static final String PLAYER_BOY_ADVENTURE_B_ATLAS = "res/Players/Boy_Adventure B.png";
    public static final String PLAYER_BOY_ADVENTURE_C_ATLAS = "res/Players/Boy_Adventure C.png";
    public static final String PLAYER_BOY_ADVENTURE_D_ATLAS = "res/Players/Boy_Adventure D.png";

    public static final String PLAYER_KNIGHT_A_ATLAS = "res/Players/Knight_A.png";
    public static final String PLAYER_KNIGHT_B_ATLAS = "res/Players/Knight_B.png";
    public static final String PLAYER_KNIGHT_C_ATLAS = "res/Players/Knight_C.png";
    public static final String PLAYER_KNIGHT_D_ATLAS = "res/Players/Knight_D.png";
    public static final String PLAYER_KNIGHT_E_ATLAS = "res/Players/Knight_E.png";
    public static final String PLAYER_KNIGHT_F_ATLAS = "res/Players/Knight_F.png";
    public static final String PLAYER_KNIGHT_G_ATLAS = "res/Players/Knight_G.png";
    public static final String PLAYER_KNIGHT_H_ATLAS = "res/Players/Knight_H.png";

    public static final String PLAYER_SPECIAL_KNIGHT_1_ATLAS = "res/Players/Special Knight 1.png";
    public static final String PLAYER_SPECIAL_KNIGHT_2_ATLAS = "res/Players/Special Knight 2.png";
    public static final String PLAYER_SPECIAL_KNIGHT_3_ATLAS = "res/Players/Special Knight 3.png";
    public static final String PLAYER_SPECIAL_KNIGHT_4_ATLAS = "res/Players/Special Knight 4.png";

    public static final String PLAYER_WORKER_A_ATLAS = "res/Players/Worker_A.png";
    public static final String PLAYER_WORKER_B_ATLAS = "res/Players/Worker_B.png";
    public static final String PLAYER_WORKER_C_ATLAS = "res/Players/Worker_C.png";
    public static final String PLAYER_WORKER_D_ATLAS = "res/Players/Worker_D.png";


    // Game world
    public static final String TILESET_ATLAS = "res/tileset_sprites.png";
    public static final String WORLD_DATA = "res/world.level";

    // Background
    public static final String BACKGROUND_ATLAS = "res/Background/BG Image.png";

    public static final String BIG_CLOUDS_ATLAS = "res/Background/Big Clouds.png";
    public static final String SMALL_CLOUDS_1_ATLAS = "res/Background/Small Cloud 1.png";
    public static final String SMALL_CLOUDS_2_ATLAS = "res/Background/Small Cloud 2.png";
    public static final String SMALL_CLOUDS_3_ATLAS = "res/Background/Small Cloud 3.png";

    public static final String WATER_REFLECTS_ATLAS = "res/Background/Water Reflect Big atlas.png";

    // Game world objects
    public static final String POWER_UPS_ATLAS = "res/Objects/power-ups.png";
    public static final String SAW_ATLAS = "res/Objects/saw.png";

    // UI && MENU
    public static final String MENU_BACKGROUND = "res/UI/background.png";
    public static final String BIG_BUTTONS = "res/UI/btn main.png";
    public static final String SMALL_BUTTONS = "res/UI/btn small.png";
    public static final String ARROWS_BUTTONS = "res/UI/btn arrows.png";
    public static final String LOGO = "res/UI/logo.png";
    public static final String PLAYER_PLACEHOLDER = "res/UI/player_placeholder.png";
    public static final String MENU_BACKGROUND_LONG = "res/UI/background long.png";
    public static final String TEXT_BORDERLESS_MODE = "res/UI/text borderless mode.png";
    public static final String TEXT_GHOST_MODE = "res/UI/text ghost mode.png";
    public static final String TEXT_GOD_MODE = "res/UI/text god mode.png";
    public static final String TEXT_SLOW_MODE = "res/UI/text slow mode.png";
    public static final String TEXT_SPEED_MODE = "res/UI/text speed mode.png";
    public static final String TEXT_VIEWER_MODE = "res/UI/text viewer mode.png";
    public static final String EMPTY_BUTTON = "res/UI/btn sprite.png";
    public static final String SLIDER_BUTTON = "res/UI/btn slider.png";
    public static final String SETTINGS_BACKGROUND = "res/UI/background settings.png";

    // Score && Game Over
    public static final String TEXT_PLAYER_INDEXES = "res/UI/Score/players index.png";
    public static final String BACKGROUND_GAME_OVER = "res/UI/Score/background game over.png";
    public static final String TEXT_GAME_OVER = "res/UI/Score/text game over.png";

    // Credits
    public static final String CREDITS_BACKGROUND = "res/UI/credits.png";

    // Count Down
    public static final String COUNT_DOWN_ATLAS = "res/UI/count down.png";

    // Tutorial
    public static final String TUTORIAL_BACKGROUND = "res/UI/Tutorial/background tutorial.png";
    public static final String TUTORIAL_TEXTS = "res/UI/Tutorial/texts tutorial.png";
    public static final String TUTORIAL_IMAGES = "res/UI/Tutorial/images tutorial.png";

    // Palms
    public static final String PALM_BACK_1_ATLAS = "res/Palms/Back palm1.png";
    public static final String PALM_BACK_2_ATLAS = "res/Palms/Back palm2.png";
    public static final String PALM_BACK_3_ATLAS = "res/Palms/Back palm3.png";

    public static final String PALM_FRONT_1_ATLAS = "res/Palms/Palm1.png";
    public static final String PALM_FRONT_2_ATLAS = "res/Palms/Palm2.png";
    public static final String PALM_FRONT_3_ATLAS = "res/Palms/Palm3.png";


    /**
     * Loads a sprite atlas image from the specified file path.
     *
     * @param fileName the path to the sprite atlas resource.
     * @return the loaded {@link BufferedImage}.
     * @throws BundleLoadException if the resource cannot be found or an I/O error occurs.
     */
    public static BufferedImage getSpriteAtlas(String fileName) throws BundleLoadException {
        BufferedImage img;

        try {
            InputStream inputStream = BundleLoader.class.getClassLoader().getResourceAsStream(fileName);
            if (inputStream != null) {
                img = ImageIO.read(inputStream);
                inputStream.close();
            } else {
                throw new BundleLoadException("Resource not found", fileName);
            }
        } catch (IOException e) {
            throw new BundleLoadException(e, fileName);
        }
        return img;
    }

    /**
     * Reads the content of a text file from the specified file path.
     *
     * @param fileName the path to the text file resource.
     * @return the content of the file as a {@link String}.
     * @throws BundleLoadException if the resource cannot be found or an I/O error occurs.
     */
    public static String getFileContent(String fileName) throws BundleLoadException {

        StringBuilder content = new StringBuilder();
        try {
            InputStream inputStream = BundleLoader.class.getClassLoader().getResourceAsStream(fileName);

            if (inputStream != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
            } else {
                throw new BundleLoadException("Resource not found", fileName);
            }
        } catch (IOException e) {
            throw new BundleLoadException(e, fileName);
        }
        return content.toString();
    }

}
