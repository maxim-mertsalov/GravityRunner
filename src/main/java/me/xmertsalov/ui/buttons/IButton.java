package me.xmertsalov.ui.buttons;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

public interface IButton {
    void draw(Graphics g);
    void update();

    void mouseClicked(MouseEvent e);
    void mousePressed(MouseEvent e);
    void mouseReleased(MouseEvent e);
    void mouseMoved(MouseEvent e);
    void mouseEntered(MouseEvent e);
    void mouseExited(MouseEvent e);

    void keyPressed(KeyEvent e);
    void keyReleased(KeyEvent e);

    void setState(int state);
    void setVariant(int variant);

    void setOnClickListener(Runnable listener);

    void loadImage();

    Rectangle2D getRectangle();
}
