package me.xmertsalov.components;

import me.xmertsalov.Game;
import me.xmertsalov.entities.Player;
import me.xmertsalov.gameObjects.powerUps.PowerUp;
import me.xmertsalov.gameObjects.powerUps.SpeedDown;
import me.xmertsalov.gameObjects.powerUps.SpeedUp;
import me.xmertsalov.gameObjects.saws.Saw;

import java.awt.*;
import java.awt.geom.Rectangle2D;


public class PhisicsControler {
    private Game game;
    private boolean isGroundedUp = false, isGroundedDown = false;

    private int ticks;

    public PhisicsControler(Game game) {
        this.game = game;
    }

    public void update(){
        detectFallingCollisions();
        detectWallsCollisions();
        resetPlayerVelocityX();
        detectGameObjects();
        ifPlayerFallingAway();
    }

    private void detectFallingCollisions() {
        isGroundedDown = false;
        isGroundedUp = false;

        game.getPlayingScene().getLevelsManager().getActiveLevels().forEach(level -> {
            level.getTiles().forEach(tile -> {
                if (tile.getCollider() != null) { // There are tiles with colliders:
                    Collider collider = tile.getCollider();
                    if (game.getPlayingScene().getPlayer().getCollider() instanceof BoxCollider boxCollider) {

                        double velocityY = game.getPlayingScene().getPlayer().getPhisicsComponent().getVelocityY();
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
                                game.getPlayingScene().getPlayer().getPhisicsComponent().setAbleToDown(false);
                                game.getPlayingScene().getPlayer().getPhisicsComponent().setVelocityY(0);

                                // Correct player's position to sit exactly on top of the platform
                                game.getPlayingScene().getPlayer().setPosY(tileBounds.getY() - boxCollider.getBounds().getHeight() * 1.1);
                            }

                            if (velocityY < 0 && tileBounds.intersects(playerNextBoundsDown)) { // Moving up
//                                System.out.println("Grounded up");
                                isGroundedUp = true;
                                game.getPlayingScene().getPlayer().getPhisicsComponent().setAbleToUp(false);
                                game.getPlayingScene().getPlayer().getPhisicsComponent().setVelocityY(0);

                                // Correct player's position to sit exactly below the platform
                                game.getPlayingScene().getPlayer().setPosY(tileBounds.getY() + boxCollider.getBounds().getHeight() * 0.55);
                            }
                        }
                    }
                }
            });
        });
    }

    private void detectWallsCollisions() {
        game.getPlayingScene().getLevelsManager().getActiveLevels().forEach(level -> {
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



                        if (game.getPlayingScene().getPlayer().getCollider() instanceof BoxCollider playerCollider) {
                            if (playerCollider.getBounds().intersects(adjustedTileBounds)) {
                                // Adjust player's position to prevent overlap
                                double overlap = playerCollider.getBounds().getMaxX() - adjustedTileBounds.getMinX();
                                game.getPlayingScene().getPlayer().setPosX(game.getPlayingScene().getPlayer().getPosX() - overlap);
                                game.getPlayingScene().getPlayer().getPhisicsComponent().setVelocityX(tile.getTilePhisicsComponents().getVelocity_x());
                            }
                            else{
                                game.getPlayingScene().getPlayer().getPhisicsComponent().setVelocityX(0);
                            }
                        }
                    }
                }
            });
        });
    }

    public void resetPlayerVelocityX(){
        if (game.getPlayingScene().getPlayer().isConsumedBonus()) {
            ticks++;
            if (ticks >= Game.UPS_LIMIT * 3) { // 3 seconds
                game.getPlayingScene().getPlayer().setConsumedBonus(false);
                game.getPlayingScene().getPlayer().getPhisicsComponent().setVelocityX(0);
                ticks = 0;
            }
        }
    }

    public void detectGameObjects(){
        game.getPlayingScene().getLevelsManager().getActiveLevels().forEach(level -> {
            level.getGameObjects().forEach(gameObject -> {

                BoxCollider player_collider = (BoxCollider) game.getPlayingScene().getPlayer().getCollider();



                if(gameObject instanceof PowerUp powerUp){
                    Player player = game.getPlayingScene().getPlayer();

                    if (powerUp.getBounds().intersects(player_collider.getBounds()) && !player.isConsumedBonus()) {
                        if (powerUp instanceof SpeedUp speedUp) {
//                            game.getPlayingScene().getPlayer().getPhisicsComponent().applyForce(speedUp.getVelocityXIncrement() * 10, 0);
                            game.getPlayingScene().getPlayer().getPhisicsComponent().setVelocityX(speedUp.getVelocityXIncrement() * 10);
                            game.getPlayingScene().getPlayer().setConsumedBonus(true);
                        }
                        else if (powerUp instanceof SpeedDown speedDown) {
//                            game.getPlayingScene().getPlayer().getPhisicsComponent().applyForce(speedDown.getVelocityXIncrement() * 10, 0);
                            game.getPlayingScene().getPlayer().getPhisicsComponent().setVelocityX(speedDown.getVelocityXIncrement() * 10);
                            game.getPlayingScene().getPlayer().setConsumedBonus(true);
                        }
                    }
                }
                if (gameObject instanceof Saw saw){
                    if (saw.getBounds().intersects(player_collider.getBounds())) {
                        if (!game.getPlayingScene().getPlayer().isGodMode()) {
                            playerDead(game.getPlayingScene().getPlayer());
                        }

                    }
                }

            });
        });
    }



    private void ifPlayerFallingAway(){
        if (game.getPlayingScene().getPlayer().getPosY() > Game.WINDOW_HEIGHT + Game.TILES_SIZE * 2) { // falling down
            playerDead(game.getPlayingScene().getPlayer());
        }
        else if(game.getPlayingScene().getPlayer().getPosY() < -Game.TILES_SIZE * 2){ // falling up
            playerDead(game.getPlayingScene().getPlayer());
        }
        else if (game.getPlayingScene().getPlayer().getPosX() < -Game.TILES_SIZE * 2){
            playerDead(game.getPlayingScene().getPlayer());
        }
    }

    private void playerDead(Player player){
        player.setDead(true);
        double xVelocity = game.getPlayingScene().getLevelsManager().getSpeed();
        game.getPlayingScene().getPlayer().getPhisicsComponent().setVelocityX(-xVelocity);
    }

    public void render(Graphics g){
    }
}

