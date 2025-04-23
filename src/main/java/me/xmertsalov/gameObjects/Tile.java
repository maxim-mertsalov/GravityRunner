package me.xmertsalov.gameObjects;

import me.xmertsalov.components.BoxCollider;
import me.xmertsalov.components.Collider;
import me.xmertsalov.components.PolygonCollider;
import me.xmertsalov.components.TilePhisicsComponents;
import me.xmertsalov.Game;

import java.awt.*;

public class Tile extends GameObject {
    private int tileIndex;
    private String tileType;

    private java.awt.Image img;

    private Collider collider;
    private TilePhisicsComponents tilePhisicsComponents;

    public Tile(double x, double y, int tileIndex, String tileType) {
        super(x, y);
        this.tileIndex = tileIndex;
        this.tileType = tileType;
        this.collider = setCollider(tileType);
        this.tilePhisicsComponents = new TilePhisicsComponents(this);
    }

    private Collider setCollider(String tileType) {
        switch (tileType) {
            case "grass": // grass can't have colliders
                return null;

            case "spices":
                return null;

            case "platform":
                return new BoxCollider(x, y, Game.TILES_SIZE / 2.0, Game.TILES_SIZE / 2.0);

            case "slide1-1": // platform 1-grid slide
                return new PolygonCollider(
                    new int[]{(int)(x + Game.TILES_SIZE), (int)(x + Game.TILES_SIZE), (int)x},
                    new int[]{(int)(y + Game.TILES_SIZE), (int)y, (int)(y + Game.TILES_SIZE)});

            case "slide2-1": // platform 2-grid slide
                return new PolygonCollider(
                    new int[]{(int)(x + 2 * Game.TILES_SIZE), (int)(x + 2 * Game.TILES_SIZE), (int)x},
                    new int[]{(int)(y + Game.TILES_SIZE), (int)y, (int)(y + Game.TILES_SIZE)});
            case "slide2-2":
                return null;

            case "slide3-1": // platform 3-grid slide
                return new PolygonCollider(
                    new int[]{(int)(x + 3 * Game.TILES_SIZE), (int)(x + 3 * Game.TILES_SIZE), (int)x},
                    new int[]{(int)(y + Game.TILES_SIZE), (int)y, (int)(y + Game.TILES_SIZE)});
            case "slide3-2":
            case "slide3-3":
                return null;

            case "slide4-1": // platform 1-grid slide
                return new PolygonCollider(
                    new int[]{(int)(x + Game.TILES_SIZE), (int)(x + Game.TILES_SIZE), (int)x},
                    new int[]{(int)(y + Game.TILES_SIZE), (int)y, (int)y});

            case "slide5-1": // platform 2-grid slide
                return new PolygonCollider(
                    new int[]{(int)(x + 2 * Game.TILES_SIZE), (int)(x + 2 * Game.TILES_SIZE), (int)x},
                    new int[]{(int)(y + Game.TILES_SIZE), (int)y, (int)y});
            case "slide5-2":
                return null;

            case "slide6-1": // platform 3-grid slide
                return new PolygonCollider(
                    new int[]{(int)(x + 3 * Game.TILES_SIZE), (int)(x + 3 * Game.TILES_SIZE), (int)x},
                    new int[]{(int)(y + Game.TILES_SIZE), (int)y, (int)y});
            case "slide6-2":
            case "slide6-3":
                return null;

            case "slide9-1": // platform 1-grid slide
                return new PolygonCollider(
                    new int[]{(int)(x + Game.TILES_SIZE), (int)(x + Game.TILES_SIZE), (int)x},
                    new int[]{(int)(y + Game.TILES_SIZE), (int)y, (int)y});

            case "slide8-1": // platform 2-grid slide
                return new PolygonCollider(
                    new int[]{(int)(x + 2 * Game.TILES_SIZE), (int)x, (int)x},
                    new int[]{(int)(y + Game.TILES_SIZE), (int)(y + Game.TILES_SIZE), (int)y});
            case "slide8-2":
                return null;

            case "slide7-1": // platform 3-grid slide
                return new PolygonCollider(
                    new int[]{(int)(x + 3 * Game.TILES_SIZE), (int)x, (int)x},
                    new int[]{(int)(y + Game.TILES_SIZE), (int)(y + Game.TILES_SIZE), (int)y});
            case "slide7-2":
            case "slide7-3":
                return null;

            case "slide12-1": // platform 1-grid slide
                return new PolygonCollider(
                    new int[]{(int)(x + Game.TILES_SIZE), (int)(x + Game.TILES_SIZE), (int)x},
                    new int[]{(int)y, (int)y, (int)y});

            case "slide11-1": // platform 2-grid slide
                return new PolygonCollider(
                    new int[]{(int)(x + 2 * Game.TILES_SIZE), (int)x, (int)x},
                    new int[]{(int)y, (int)(y + Game.TILES_SIZE), (int)y});
            case "slide11-2":
                return null;

            case "slide10-1": // platform 3-grid slide
                return new PolygonCollider(
                    new int[]{(int)(x + 3 * Game.TILES_SIZE), (int)x, (int)x},
                    new int[]{(int)y, (int)(y + Game.TILES_SIZE), (int)y});
            case "slide10-2":
            case "slide10-3":
                return null;

            default: // for all other tiles
                return new BoxCollider(x, y, Game.TILES_SIZE, Game.TILES_SIZE);
        }
    }

    public void update() {
        tilePhisicsComponents.update();
    }

    public void updatePos(double x, double y) {
        this.x = x;
        this.y = y;
        if (collider != null) {
            collider.updateBounds(x, y);
        }
    }

    // main renderer method
    public void render(Graphics g, java.awt.Image img) {
        this.img = img;
        draw(g);
    }

    public Collider getCollider() {
        return collider;
    }

    public TilePhisicsComponents getTilePhisicsComponents() {
        return tilePhisicsComponents;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(img, (int) this.getX(), (int) this.getY(), Game.TILES_SIZE, Game.TILES_SIZE, null);

        // For debugging colliders
        if (this.getCollider() != null) this.getCollider().draw(g);
    }

    public int getTileIndex() {
        return tileIndex;
    }

    public String getTileType() {
        return tileType;
    }

    public Tile clone() {
        return new Tile(this.x, this.y, this.tileIndex, this.tileType);
    }

    @Override
    public void setX(double x) {
        super.setX(x);
        collider.updateBounds(x, y);
    }
    @Override
    public void setY(double y) {
        super.setY(y);
        collider.updateBounds(x, y);
    }
}
