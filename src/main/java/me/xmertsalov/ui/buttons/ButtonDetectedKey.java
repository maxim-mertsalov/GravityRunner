package me.xmertsalov.ui.buttons;

import me.xmertsalov.utils.BundleLoader;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class ButtonDetectedKey implements IButton{
    private Rectangle2D.Float rectangle;
    private BufferedImage[][] images;
    private int variant = 0;
    private int state = 0;
    private Runnable onClickListener;

    private boolean listening = false;
    private int keyCode = 0;

    public ButtonDetectedKey(int x, int y, int width, int height, int variant) {
        this.rectangle = new Rectangle2D.Float(x, y, width, height);
        this.variant = variant;

        loadImage();
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(images[variant][state], (int) rectangle.x, (int) rectangle.y, (int) rectangle.width, (int) rectangle.height, null);
        g.setColor(Color.BLACK);
        g.drawString(KeyEvent.getKeyText(keyCode), (int) (rectangle.x + rectangle.getWidth() / 2 - 5), (int) (rectangle.y + rectangle.getHeight() / 2));
    }

    @Override
    public void update() {
        if (listening) {
            state = 2;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (rectangle.contains(e.getX(), e.getY()) && !listening) {
            listening = true;
            state = 2;
        }
        else if (listening) {
            listening = false;
            state = 0;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (listening) return;

        if (rectangle.contains(e.getX(), e.getY())) {
            mouseEntered(e);
        }
        else {
            mouseExited(e);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        state = 1;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        state = 0;
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (listening) {
            int newCode = e.getKeyCode();
            if (newCode != this.keyCode) {
                this.keyCode = newCode;
            }
//            System.out.println("keyCode: " + KeyEvent.getKeyText(keyCode));
            state = 0;
            listening = false;
        }
    }

    @Override
    public void setState(int state) {
        this.state = state;
    }

    @Override
    public void setVariant(int variant) {
        this.variant = variant;
    }

    @Override
    public void setOnClickListener(Runnable listener) {
        this.onClickListener = listener;
    }

    @Override
    public void loadImage() {
        BufferedImage image = BundleLoader.getSpriteAtlas(BundleLoader.EMPTY_BUTTON);

        int rows = 1;
        int cols = 3;
        int width = 56;
        int height = 14;

        images = new BufferedImage[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                images[i][j] = image.getSubimage(j * width, i * height, width, height);
            }
        }
    }

    public int getKeyCode() {
        return keyCode; // Placeholder for the key code
    }
    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
    }

    @Override
    public Rectangle2D getRectangle() {
        return rectangle;
    }
}
