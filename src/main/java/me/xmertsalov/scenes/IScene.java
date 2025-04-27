package me.xmertsalov.scenes;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public interface IScene {
    void update(); // update function
    void draw(Graphics g); // render function

    void mouseClicked(MouseEvent e);
    void mousePressed(MouseEvent e);
    void mouseReleased(MouseEvent e);
    void mouseMoved(MouseEvent e);
    void mouseDragged(MouseEvent e);

    void keyPressed(KeyEvent e);
    void keyReleased(KeyEvent e);
}
