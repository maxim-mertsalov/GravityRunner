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

    // Game world
    public static final String TILESET_ATLAS = "/res/tileset_sprites.png";
    public static final String WORLD_DATA = "/res/world.level";

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
