package me.xmertsalov.components;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class Collider {
    public abstract void draw(Graphics g);
    public abstract void updateBounds(double x, double y);
}
