package me.xmertsalov.scenes;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public interface IScene {
    // start function is private and calls by the constructor
    public void update(); // update function
    public void draw(Graphics g); // render function

    public void mouseClicked(MouseEvent e);
    public void mousePressed(MouseEvent e);
    public void mouseReleased(MouseEvent e);
    public void mouseMoved(MouseEvent e);
    public void mouseDragged(MouseEvent e);

    public void keyPressed(KeyEvent e);
    public void keyReleased(KeyEvent e);
}
