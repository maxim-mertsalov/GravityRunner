package me.xmertsalov.ui.buttons;

import me.xmertsalov.audio.AudioPlayer;
import me.xmertsalov.utils.BundleLoader;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class BigButton implements IButton {
    private Rectangle2D.Float rectangle;
    private BufferedImage[][] images;
    private int state = 0;
    private int variant = 0;
    private Runnable onClickListener;

    public BigButton(int x, int y, int width, int height, int variant) {
        this.rectangle = new Rectangle2D.Float(x, y, width, height);
        this.variant = variant;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(images[variant][state], (int) rectangle.x, (int) rectangle.y, (int) rectangle.width, (int) rectangle.height, null);
    }

    @Override
    public void update() {
        // Implementation
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (rectangle.contains(e.getX(), e.getY()) && onClickListener != null) {
            onClickListener.run();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (rectangle.contains(e.getX(), e.getY())) {
            state = 2;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
       if (rectangle.contains(e.getX(), e.getY())) {
            state = 1;
//           AudioPlayer.playSfx(AudioPlayer.SFX_CLICK);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (rectangle.contains(e.getX(), e.getY())) {
            mouseEntered(e);
        }
        else{
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
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

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
        BufferedImage image = BundleLoader.getSpriteAtlas(BundleLoader.BIG_BUTTONS);

        int rows = 12;
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

    @Override
    public Rectangle2D getRectangle() {
        return rectangle;
    }

    @Override
    public int getData() {
        return 0;
    }

    @Override
    public void setData(int data) {}
}
