package me.xmertsalov.components.phisics.colliders;

import me.xmertsalov.Game;

import java.awt.*;

public class PolygonCollider extends Collider {
    private Polygon polygon;

    public PolygonCollider(int[] xPoints, int[] yPoints) {
        this.polygon = new Polygon(xPoints, yPoints, xPoints.length);
    }

    @Override
    public void draw(Graphics g) {
        if (!Game.DEBUG_COLLIDERS) return;
        g.setColor(Game.DEBUG_COLOR);
        g.fillPolygon(polygon);

        g.setColor(Game.DEBUG_COLOR_SECOND);
        g.drawPolygon(polygon);
//        g.draw(polygon);
    }

    public void updateBounds(double x, double y) {
        for (int i = 0; i < polygon.npoints; i++) {
            polygon.xpoints[i] = (int) (x + polygon.xpoints[i]);
            polygon.ypoints[i] = (int) (y + polygon.ypoints[i]);
        }
        polygon.invalidate();
    }

    public Polygon getBounds() {
        return polygon;
    }
}
