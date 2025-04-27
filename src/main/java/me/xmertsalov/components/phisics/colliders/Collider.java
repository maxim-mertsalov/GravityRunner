package me.xmertsalov.components.phisics.colliders;

import java.awt.*;

public abstract class Collider {
    public abstract void draw(Graphics g);
    public abstract void updateBounds(double x, double y);
}
