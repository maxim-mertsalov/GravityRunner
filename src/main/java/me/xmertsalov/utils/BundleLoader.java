package me.xmertsalov.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class BundleLoader {
    // Player's skins
    public static final String PIRATE_ATLAS = "/res/pirate_sprites.png";
    //Others
    public static final String PLAYER_BOY_ADVENTURE_A_ATLAS = "/res/Players/Boy_Adventure A.png";
    public static final String PLAYER_BOY_ADVENTURE_B_ATLAS = "/res/Players/Boy_Adventure B.png";
    public static final String PLAYER_BOY_ADVENTURE_C_ATLAS = "/res/Players/Boy_Adventure C.png";
    public static final String PLAYER_BOY_ADVENTURE_D_ATLAS = "/res/Players/Boy_Adventure D.png";

    public static final String PLAYER_KNIGHT_A_ATLAS = "/res/Players/Knight_A.png";
    public static final String PLAYER_KNIGHT_B_ATLAS = "/res/Players/Knight_B.png";
    public static final String PLAYER_KNIGHT_C_ATLAS = "/res/Players/Knight_C.png";
    public static final String PLAYER_KNIGHT_D_ATLAS = "/res/Players/Knight_D.png";
    public static final String PLAYER_KNIGHT_E_ATLAS = "/res/Players/Knight_E.png";
    public static final String PLAYER_KNIGHT_F_ATLAS = "/res/Players/Knight_F.png";
    public static final String PLAYER_KNIGHT_G_ATLAS = "/res/Players/Knight_G.png";
    public static final String PLAYER_KNIGHT_H_ATLAS = "/res/Players/Knight_H.png";

    public static final String PLAYER_SPECIAL_KNIGHT_1_ATLAS = "/res/Players/Special Knight 1.png";
    public static final String PLAYER_SPECIAL_KNIGHT_2_ATLAS = "/res/Players/Special Knight 2.png";
    public static final String PLAYER_SPECIAL_KNIGHT_3_ATLAS = "/res/Players/Special Knight 3.png";
    public static final String PLAYER_SPECIAL_KNIGHT_4_ATLAS = "/res/Players/Special Knight 4.png";

    public static final String PLAYER_WORKER_A_ATLAS = "/res/Players/Worker_A.png";
    public static final String PLAYER_WORKER_B_ATLAS = "/res/Players/Worker_B.png";
    public static final String PLAYER_WORKER_C_ATLAS = "/res/Players/Worker_C.png";
    public static final String PLAYER_WORKER_D_ATLAS = "/res/Players/Worker_D.png";


    // Game world
    public static final String TILESET_ATLAS = "/res/tileset_sprites.png";
    public static final String WORLD_DATA = "/res/world.level";

    // Background
    public static final String BACKGROUND_ATLAS = "/res/background/BG Image.png";

    public static final String BIG_CLOUDS_ATLAS = "/res/background/Big Clouds.png";
    public static final String SMALL_CLOUDS_1_ATLAS = "/res/background/Small Cloud 1.png";
    public static final String SMALL_CLOUDS_2_ATLAS = "/res/background/Small Cloud 2.png";
    public static final String SMALL_CLOUDS_3_ATLAS = "/res/background/Small Cloud 3.png";

    public static final String WATER_REFLECTS_ATLAS = "/res/background/Water Reflect Big atlas.png";

    // Game world objects
    public static final String POWER_UPS_ATLAS = "/res/Objects/power-ups.png";
    public static final String SAW_ATLAS = "/res/Objects/saw.png";


    public static BufferedImage getSpriteAtlas(String fileName) {
        BufferedImage img = null;
        try {
            InputStream inputStream = BundleLoader.class.getResourceAsStream(fileName);
            if (inputStream != null) {
                img = ImageIO.read(inputStream);
                inputStream.close();
            } else {
                throw new IOException("Resource not found: " + fileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }

    public static String getFileContent(String fileName) {
        StringBuilder content = new StringBuilder();
        try {
            InputStream inputStream = BundleLoader.class.getResourceAsStream(fileName);

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            if (inputStream != null) {
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
            } else {
                throw new IOException("Resource not found: " + fileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }

}
