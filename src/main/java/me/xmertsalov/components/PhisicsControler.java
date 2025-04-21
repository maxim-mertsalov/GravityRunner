package me.xmertsalov.components;

import me.xmertsalov.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;


public class PhisicsControler {
    private Game game;
    private boolean isGroundedUp = false, isGroundedDown = false;

    public PhisicsControler(Game game) {
        this.game = game;
    }

    public void update(){
        detectFallingCollisions();
        detectWallsCollisions();
    }

    private void detectFallingCollisions() {
        isGroundedDown = false;
        isGroundedUp = false;

        game.getLevelsManager().getActiveLevels().forEach(level -> {
            level.getTiles().forEach(tile -> {
                if (tile.getCollider() != null) { // There are tiles with colliders:
                    Collider collider = tile.getCollider();
                    if (game.getPlayer().getCollider() instanceof BoxCollider boxCollider) {

                        double velocityY = game.getPlayer().getPhisicsComponent().getVelocityY();
                        double modVelocityY = Math.abs(velocityY);

                        // Predict future bounds based on velocity
                        Rectangle2D.Double playerNextBoundsUp = new Rectangle2D.Double(
                                boxCollider.getBounds().getX(),
                                boxCollider.getBounds().getY() + modVelocityY * 1.1 * Game.SCALE,
                                boxCollider.getBounds().getWidth(),
                                boxCollider.getBounds().getHeight()
                        );

                        Rectangle2D.Double playerNextBoundsDown = new Rectangle2D.Double(
                                boxCollider.getBounds().getX(),
                                boxCollider.getBounds().getY() - modVelocityY * 1.1 * Game.SCALE,
                                boxCollider.getBounds().getWidth(),
                                boxCollider.getBounds().getHeight()
                        );

                        if (collider instanceof BoxCollider tileBoxCollider) {
                            Rectangle2D tileBounds = tileBoxCollider.getBounds();

                            if (velocityY > 0 && tileBounds.intersects(playerNextBoundsUp)) { // Moving down
//                                System.out.println("Grounded down");
                                isGroundedDown = true;
                                game.getPlayer().getPhisicsComponent().setAbleToDown(false);
                                game.getPlayer().getPhisicsComponent().setVelocityY(0);

                                // Correct player's position to sit exactly on top of the platform
                                game.getPlayer().setPosY(tileBounds.getY() - boxCollider.getBounds().getHeight() * 1.1);
                            }

                            if (velocityY < 0 && tileBounds.intersects(playerNextBoundsDown)) { // Moving up
//                                System.out.println("Grounded up");
                                isGroundedUp = true;
                                game.getPlayer().getPhisicsComponent().setAbleToUp(false);
                                game.getPlayer().getPhisicsComponent().setVelocityY(0);

                                // Correct player's position to sit exactly below the platform
                                game.getPlayer().setPosY(tileBounds.getY() + boxCollider.getBounds().getHeight() * 0.55);
                            }
                        }
                    }
                }
            });
        });
    }

    private void detectWallsCollisions() {
        game.getLevelsManager().getActiveLevels().forEach(level -> {
            level.getTiles().forEach(tile -> {
                if (tile.getCollider() != null) { // There are tiles with colliders:
                    Collider collider = tile.getCollider();
                    if (collider instanceof BoxCollider boxCollider) {
                        // Adjust the future tile collider bounds
                        Rectangle2D.Double adjustedTileBounds = new Rectangle2D.Double(
                                boxCollider.getBounds().x + tile.getTilePhisicsComponents().getVelocity_x(),
                                boxCollider.getBounds().y + boxCollider.getBounds().getHeight() * 0.15,
                                boxCollider.getBounds().getWidth(),
                                boxCollider.getBounds().getHeight() * 0.7
                        );



                        if (game.getPlayer().getCollider() instanceof BoxCollider playerCollider) {
                            if (playerCollider.getBounds().intersects(adjustedTileBounds)) {
                                // Adjust player's position to prevent overlap
                                double overlap = playerCollider.getBounds().getMaxX() - adjustedTileBounds.getMinX();
                                game.getPlayer().setPosX(game.getPlayer().getPosX() - overlap);
                                game.getPlayer().getPhisicsComponent().setVelocityX(tile.getTilePhisicsComponents().getVelocity_x());
                            }
                            else{
                                game.getPlayer().getPhisicsComponent().setVelocityX(0);
                            }
                        }
                    }
                }
            });
        });
    }

    public void render(Graphics g){
//        BoxCollider boxCollider = (BoxCollider) game.getPlayer().getCollider();
//        double modVelocityY = Math.abs(game.getPlayer().getPhisicsComponent().getVelocityY());
//
//        Rectangle2D.Double playerNexBoundsDowner = new Rectangle2D.Double(
//                boxCollider.getBounds().getX(),
//                boxCollider.getBounds().getY() + modVelocityY,
//                boxCollider.getBounds().getWidth(),
//                boxCollider.getBounds().getHeight()
//        );
//
//        Rectangle2D.Double playerNexBoundsUpper = new Rectangle2D.Double(
//                boxCollider.getBounds().getX(),
//                boxCollider.getBounds().getY() - modVelocityY,
//                boxCollider.getBounds().getWidth(),
//                boxCollider.getBounds().getHeight()
//        );
//
//        g.setColor(Color.BLACK);
//        g.drawRect((int)playerNexBoundsUpper.x, (int)playerNexBoundsUpper.y, (int)playerNexBoundsUpper.width, (int)playerNexBoundsUpper.height);
//        g.drawRect((int)playerNexBoundsDowner.x, (int)playerNexBoundsDowner.y, (int)playerNexBoundsDowner.width, (int)playerNexBoundsDowner.height);
    }
}

