package me.xmertsalov.gameObjects.saws;

import me.xmertsalov.Game;

import java.awt.*;

public class MovableSaw extends Saw {
    private double speed = 1.5;

    private double velocityX;
    private double velocityY;

    private double velocityXOffset = 0;

    private double baseVelocityX;
    private double baseVelocityY;

    private double lengthBetweenStartX = 0;
    private double lengthBetweenStartY = 0;

    private boolean direction = true; // true = right, false = left

    private double dx, dy;
    private double sx, sy;

    private double lengthX, lengthY;

    public MovableSaw(double x, double y, double dx, double dy) {
        super(x, y);

        this.dx = dx;
        this.dy = dy;
        this.sx = x;
        this.sy = y;

        this.lengthX = dx - sx;
        this.lengthY = dy - sy;

        double length = Math.sqrt(lengthX * lengthX + lengthY * lengthY);
        this.baseVelocityX = (lengthX / length) * speed;
        this.baseVelocityY = (lengthY / length) * speed;

        this.velocityX = baseVelocityX;
        this.velocityY = baseVelocityY;
    }

    @Override
    public void update() {
        checkPosition();

        x += velocityX;
        lengthBetweenStartX += velocityX;
        y += velocityY;
        lengthBetweenStartY += velocityY;

        bounds.x = x;
        bounds.y = y;

        animator.update();
    }

    private void checkPosition() {
        if (direction) {
            // Moving toward (dx, dy)
            if ((baseVelocityX > 0 && x >= dx) || (baseVelocityX < 0 && x <= dx)
                    || (baseVelocityY > 0 && y >= dy) || (baseVelocityY < 0 && y <= dy)) {
                direction = false;
                velocityX = -baseVelocityX - Math.abs(velocityXOffset);
                velocityY = -baseVelocityY;
            }
        } else {
            // Moving back to (sx, sy)
            if ((velocityX < 0 && x <= sx) || (velocityX > 0 && x >= sx)
                    || (velocityY < 0 && y <= sy) || (velocityY > 0 && y >= sy)) {
                direction = true;
                velocityX = baseVelocityX;
                velocityY = baseVelocityY;
            }
        }
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        g.setColor(Game.DEBUG_COLOR);
        g.drawLine((int) sx, (int) sy, (int) dx, (int) dy);
    }

    @Override
    public void updatePos(double x, double y) {
        super.updatePos(x, y);
        this.sx = x - lengthBetweenStartX;
        this.sy = y - lengthBetweenStartY;
        this.dx = x - lengthBetweenStartX + lengthX;
        this.dy = y -lengthBetweenStartY + lengthY;
    }

    public void setVelocity(double velocityX, double velocityY) {
        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }

    public double getVelocityX() {
        return velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public MovableSaw clone() {
        return new MovableSaw(x, y, dx, dy);
    }

    public void setVelocityOffset(double offsetX) {
        this.velocityXOffset = offsetX;
    }
}

