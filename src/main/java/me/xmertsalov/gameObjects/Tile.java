package me.xmertsalov.gameObjects;

import me.xmertsalov.components.phisics.colliders.BoxCollider;
import me.xmertsalov.components.phisics.colliders.Collider;
import me.xmertsalov.components.phisics.colliders.PolygonCollider;
import me.xmertsalov.components.phisics.TilePhisicsComponents;
import me.xmertsalov.Game;

import java.awt.*;

/**
 * The {@code Tile} class represents a single tile in the game world.
 * Each tile has a specific type, index, position, and optional physics components.
 * It can also have a collider for collision detection and rendering capabilities.
 */
public class Tile extends GameObject {
    private final int tileIndex;
    private final String tileType;

    private java.awt.Image img;

    private Collider collider;
    private TilePhisicsComponents tilePhisicsComponents;

    /**
     * Constructs a new {@code Tile} object with the specified position, index, and type.
     *
     * @param x         The x-coordinate of the tile.
     * @param y         The y-coordinate of the tile.
     * @param tileIndex The index of the tile, used for identification.
     * @param tileType  The type of the tile, which determines its behavior and collider.
     */
    public Tile(double x, double y, int tileIndex, String tileType) {
        super(x, y);
        this.tileIndex = tileIndex;
        this.tileType = tileType;
        this.collider = setCollider(tileType);
        this.tilePhisicsComponents = new TilePhisicsComponents(this);
    }

    /**
     * Sets the collider for the tile based on its type.
     *
     * @param tileType The type of the tile, which determines the collider to be used.
     * @return A {@code Collider} object specific to the tile type, or {@code null} if no collider is needed.
     */
    private Collider setCollider(String tileType) {
        // broken tiles (tileIndex == 0) don't have colliders
        if (tileIndex == 0) {
            return null;
        }

        switch (tileType) {
            case "grass": // grass can't have colliders
            case "spices":
                return null;

            case "platform": {
                return new BoxCollider(x, y, Game.TILES_SIZE / 2.0, Game.TILES_SIZE / 2.0);
            }

            default: // for all other tiles
                return new BoxCollider(x, y, Game.TILES_SIZE, Game.TILES_SIZE);
        }

    }

    /**
     * Updates the tile's physics components.
     */
    public void update() {
        tilePhisicsComponents.update();
    }

    /**
     * Updates the position of the tile and its collider.
     *
     * @param x The new x-coordinate of the tile.
     * @param y The new y-coordinate of the tile.
     */
    public void updatePos(double x, double y) {
        this.x = x;
        this.y = y;
        if (collider != null) {
            collider.updateBounds(x, y);
        }
    }

    /**
     * Draws the tile and its collider (if present) using the specified graphics context.
     *
     * @param g The graphics context used for drawing.
     */
    @Override
    public void draw(Graphics g) {
        g.drawImage(img, (int) this.getX(), (int) this.getY(), Game.TILES_SIZE, Game.TILES_SIZE, null);

        // For debugging colliders
        if (this.getCollider() != null) this.getCollider().draw(g);
    }

    /**
     * Retrieves the collider associated with the tile.
     *
     * @return The collider of the tile, or {@code null} if no collider is present.
     */
    public Collider getCollider() {
        return collider;
    }

    /**
     * Retrieves the physics components associated with the tile.
     *
     * @return The {@code TilePhisicsComponents} of the tile.
     */
    public TilePhisicsComponents getTilePhisicsComponents() {
        return tilePhisicsComponents;
    }

    /**
     * Retrieves the index of the tile.
     *
     * @return The tile's index.
     */
    public int getTileIndex() {
        return tileIndex;
    }

    /**
     * Retrieves the type of the tile.
     *
     * @return The tile's type as a string.
     */
    public String getTileType() {
        return tileType;
    }

    /**
     * Creates a copy of the current tile.
     *
     * @return A new {@code Tile} object with the same properties as the current tile.
     */
    public Tile clone() {
        return new Tile(this.x, this.y, this.tileIndex, this.tileType);
    }

    /**
     * Sets the image to be displayed for this tile.
     *
     * @param img The {@code Image} object to be used as the tile's visual representation.
     */
    public void setImage(Image img) {
        this.img = img;
    }

    /**
     * Sets the x-coordinate of the tile and updates the collider's bounds.
     *
     * @param x The new x-coordinate of the tile.
     */
    @Override
    public void setX(double x) {
        super.setX(x);
        collider.updateBounds(x, y);
    }

    /**
     * Sets the y-coordinate of the tile and updates the collider's bounds.
     *
     * @param y The new y-coordinate of the tile.
     */
    @Override
    public void setY(double y) {
        super.setY(y);
        collider.updateBounds(x, y);
    }
}