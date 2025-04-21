package me.xmertsalov.components;

import me.xmertsalov.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class BoxCollider extends Collider {
    private Rectangle2D.Double bounds;

    public BoxCollider(double x, double y, double width, double height) {
        bounds = new Rectangle2D.Double(x, y, width, height);
    }

    public Rectangle2D.Double getBounds() {
        return bounds;
    }

    public void updateBounds(double x, double y) {
        bounds.setRect(x, y, bounds.getWidth(), bounds.getHeight());
    }

    public void draw(Graphics g) {
        if (!Game.DEBUG_ENABLED) return;

        g.setColor(Game.DEBUG_COLOR);
        g.fillRect((int) bounds.getX(), (int) bounds.getY(), (int) bounds.getWidth(), (int) bounds.getHeight());

        g.setColor(Game.DEBUG_COLOR_SECOND);
        g.drawRect((int) bounds.getX(), (int) bounds.getY(), (int) bounds.getWidth(), (int) bounds.getHeight());
    }
}
