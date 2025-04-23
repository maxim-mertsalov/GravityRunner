package me.xmertsalov.gameObjects;

import java.awt.*;

public abstract class GameObject {
    protected double x, y;

    public GameObject(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }
    public void setY(double y) {
        this.y = y;
    }

    public abstract void update();

    public abstract void draw(Graphics g);

    public abstract void updatePos(double x, double y);

    public abstract GameObject clone();
}
