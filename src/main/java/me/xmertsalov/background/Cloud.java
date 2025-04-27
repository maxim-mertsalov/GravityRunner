package me.xmertsalov.background;

import me.xmertsalov.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Cloud {
    // Position && Size
    private double x, y;
    private final double width;
    private final double height;

    // Image
    private BufferedImage image;

    // States
    private double velocity_x;


    public Cloud(double x, double y, double velocity_x, BufferedImage image) {
        this.x = x;
        this.y = y;
        this.velocity_x = velocity_x;
        this.image = image;
        this.width = image.getWidth() * Game.SCALE * 2;
        this.height = image.getHeight() * Game.SCALE * 2;
    }

    public Cloud clone() {
        return new Cloud(x, y, velocity_x, image);
    }


    public void update() {
        x += velocity_x;
    }

    public void draw(Graphics2D g) {
        g.drawImage(image, (int) x, (int) y, (int) (width), (int) (height), null);
    }

    public double getX() {return x;}
    public double getY() {return y;}

    public void setX(double x) {this.x = x;}
    public void setY(double y) {this.y = y;}

    public double getWidth() {return width;}
    public double getHeight() {return height;}

    public void setVelocity_x(double velocity_x) {
        this.velocity_x = velocity_x;
    }
}
