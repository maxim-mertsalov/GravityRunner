package me.xmertsalov.ui;

import me.xmertsalov.Game;
import me.xmertsalov.exeptions.BundleLoadException;
import me.xmertsalov.scenes.GameScene;
import me.xmertsalov.ui.buttons.BigButtonFactory;
import me.xmertsalov.ui.buttons.IButton;
import me.xmertsalov.utils.BundleLoader;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class TutorialManager implements UIManager{

    // Dependencies
    private BigButtonFactory bigButtonFactory;
    private Game game;

    // Storage
    private ArrayList<IButton> buttons;

    // Images
    private BufferedImage background;
    private BufferedImage[] texts;
    private BufferedImage[] images;

    // Constants
    private final int countCases = 14;

    // States
    private int currentState = 0;
    // 9 - next, 10 - prev, 11 - home, 0 - play
    private int leftBtnVariant = 11;
    private int rightBtnVariant = 9;

    // UI Settings
    private final double btn_scale = 1.5;

    int backgroundInitWidth = 320;
    int backgroundInitHeight = 322;

    int textInitWidth = 192;
    int textInitHeight = 96;

    int imageInitWidth = 1710;
    int imageInitHeight = 1112;

    double backgorundOffsetX = (Game.WINDOW_WIDTH / 2.15);

    double backgroundWidth = (Game.WINDOW_WIDTH - backgorundOffsetX);
    double backgroundHeight = (backgroundWidth / backgroundInitWidth * backgroundInitHeight);

    int backgroundX = (int)(Game.WINDOW_WIDTH / 2 - backgroundWidth / 2);
    int backgorundY = (int)(6 * Game.SCALE);

    double textWidth = (int)(backgroundWidth / 1.6);
    double textHeight = (int)(textWidth / textInitWidth * textInitHeight);

    int textX = (int)(backgroundX);
    int textY = (int)(backgorundY + backgroundHeight - textHeight + 2 * Game.SCALE);

    double imageXOffset = (Game.SCALE * 16);

    double imageWidth = (int)(backgroundWidth  - imageXOffset * 2);
    double imageHeight = (int)(imageWidth / imageInitWidth * imageInitHeight);



    public TutorialManager(Game game) {
        this.game = game;

        bigButtonFactory = new BigButtonFactory();

        // Load images
        loadImages();

        // Create buttons
        createButtons();
    }



    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(background, backgroundX, backgorundY, (int)backgroundWidth, (int)backgroundHeight, null);
        g.drawImage(texts[currentState], textX, textY, (int)textWidth, (int)textHeight, null);
        g.drawImage(images[currentState], (int)(backgroundX + imageXOffset), (int)(backgorundY + 20 * Game.SCALE), (int)imageWidth, (int)imageHeight, null);
        for (IButton button : buttons) {
            button.draw(g);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        for (IButton button : buttons) {
            button.mouseClicked(e);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (IButton button : buttons) {
            button.mousePressed(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (IButton button : buttons) {
            button.mouseReleased(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for (IButton button : buttons) {
            button.mouseMoved(e);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        for (IButton button : buttons) {
            button.keyPressed(e);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        for (IButton button : buttons) {
            button.keyReleased(e);
        }
    }

    private void createButtons() {
        buttons = new ArrayList<>();

        double marginTop = 40 * Game.SCALE;

        // left button (bottom)
        buttons.add(bigButtonFactory.createButton(
                (int)(textX + textWidth - 14 * Game.SCALE),
                (int)(textY + marginTop + 14 * btn_scale * Game.SCALE + 4 * Game.SCALE),
                (int)(56 * btn_scale * Game.SCALE),
                (int)(14 * btn_scale * Game.SCALE),
                leftBtnVariant, game
        ));
        buttons.get(0).setOnClickListener(() -> {
            currentState--;


            if (currentState < 0) {
                leftBtnVariant = 11;
                currentState = 0;

                GameScene.scene = GameScene.MENU;
            }
            else if (currentState == 0) {
                leftBtnVariant = 11;
            }
            else {
                leftBtnVariant = 10;
                rightBtnVariant = 9;
            }
            buttons.get(0).setVariant(leftBtnVariant);
            buttons.get(1).setVariant(rightBtnVariant);
        });

        // right button (top)
        buttons.add(bigButtonFactory.createButton(
                (int)(textX + textWidth + 56 * Game.SCALE),
                (int)(textY + marginTop),
                (int)(56 * btn_scale * Game.SCALE),
                (int)(14 * btn_scale * Game.SCALE),
                rightBtnVariant, game
        ));
        buttons.get(1).setOnClickListener(() -> {
            currentState++;

            if (currentState > countCases - 1) {
                rightBtnVariant = 9;
                leftBtnVariant = 11;
                currentState = 0;

                GameScene.scene = GameScene.LOBBY;
            }
            else if (currentState == countCases - 1) {
                rightBtnVariant = 0;
            }
            else {
                leftBtnVariant = 10;
                rightBtnVariant = 9;
            }
            buttons.get(0).setVariant(leftBtnVariant);
            buttons.get(1).setVariant(rightBtnVariant);
        });

    }

    private void loadImages(){
        texts = new BufferedImage[countCases];
        images = new BufferedImage[countCases];

        BufferedImage bigTextsImg, bigImagesImg;

        try {
            background = BundleLoader.getSpriteAtlas(BundleLoader.TUTORIAL_BACKGROUND);

            bigTextsImg = BundleLoader.getSpriteAtlas(BundleLoader.TUTORIAL_TEXTS);
            bigImagesImg = BundleLoader.getSpriteAtlas(BundleLoader.TUTORIAL_IMAGES);

        } catch (BundleLoadException e) {
            Game.logger.error(e.getMessage());
            throw new RuntimeException(e);
        }

        int imgWidth = 1710;
        int imgHeight = 1112;

        int textWidth = 192;
        int textHeight = 96;


        for (int i = 0; i < countCases; i++) {
            texts[i] = bigTextsImg.getSubimage(0, i * textHeight, textWidth, textHeight);
            images[i] = bigImagesImg.getSubimage(0, i * imgHeight, imgWidth, imgHeight);
        }
    }
}
