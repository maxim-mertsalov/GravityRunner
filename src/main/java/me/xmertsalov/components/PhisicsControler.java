package me.xmertsalov.components;

import me.xmertsalov.Game;
import me.xmertsalov.entities.Player;
import me.xmertsalov.gameObjects.powerUps.PowerUp;
import me.xmertsalov.gameObjects.powerUps.SpeedDown;
import me.xmertsalov.gameObjects.powerUps.SpeedUp;
import me.xmertsalov.gameObjects.saws.Saw;
import me.xmertsalov.scenes.inGame.PlayingScene;

import java.awt.geom.Rectangle2D;


public class PhisicsControler {
    private final PlayingScene playingScene;

    private int ticks;

    public PhisicsControler(PlayingScene playingScene) {
        this.playingScene = playingScene;
    }

    public void update(){
        detectFallingCollisions();
        detectWallsCollisions();
        resetPlayerVelocityX();
        detectGameObjects();
        ifPlayerFallingAway();

        // Check if the player is in ghost mode
         if (playingScene.isGhostMode()) playersInteractions();
    }

    private void detectFallingCollisions() {

        playingScene.getLevelsManager().getActiveLevels().forEach(level -> level.getTiles().forEach(tile -> {
            if (tile.getCollider() != null) { // There are tiles with colliders:
                Collider collider = tile.getCollider();
                for (Player player : playingScene.getPlayers()) {
                    if (player.getCollider() instanceof BoxCollider boxCollider) {
                        double velocityY = player.getPhisicsComponent().getVelocityY();
                        double modVelocityY = Math.abs(velocityY);

                        double k = 1;

                        // Predict future bounds based on velocity
                        Rectangle2D.Double playerNextBoundsUp = new Rectangle2D.Double(
                                boxCollider.getBounds().getX(),
                                boxCollider.getBounds().getY() + modVelocityY * k * Game.SCALE,
                                boxCollider.getBounds().getWidth(),
                                boxCollider.getBounds().getHeight()
                        );

                        Rectangle2D.Double playerNextBoundsDown = new Rectangle2D.Double(
                                boxCollider.getBounds().getX(),
                                boxCollider.getBounds().getY() - modVelocityY * k * Game.SCALE,
                                boxCollider.getBounds().getWidth(),
                                boxCollider.getBounds().getHeight()
                        );

                        if (collider instanceof BoxCollider tileBoxCollider) {
                            Rectangle2D tileBounds = tileBoxCollider.getBounds();

                            if (velocityY > 0 && tileBounds.intersects(playerNextBoundsUp)) { // Moving down
                                player.getPhisicsComponent().setAbleToDown(false);
                                player.getPhisicsComponent().setVelocityY(0);

                                player.setIsTemporarilyDisabled(false);

                                // Correct player's position to sit exactly on top of the platform
                                player.setPosY(tileBounds.getY() - tileBounds.getHeight() - boxCollider.getBounds().getHeight());
                            }

                            if (velocityY < 0 && tileBounds.intersects(playerNextBoundsDown)) { // Moving up
                                player.getPhisicsComponent().setAbleToUp(false);
                                player.getPhisicsComponent().setVelocityY(0);

                                player.setIsTemporarilyDisabled(false);

                                // Correct player's position to sit exactly below the platform
                                player.setPosY(tileBounds.getY() - tileBounds.getHeight() + boxCollider.getBounds().getHeight());
                            }
                        }
                    }
                }
            }
        }));
    }

    private void detectWallsCollisions() {
        playingScene.getLevelsManager().getActiveLevels().forEach(level -> level.getTiles().forEach(tile -> {
            if (tile.getCollider() != null) { // There are tiles with colliders:
                Collider collider = tile.getCollider();
                if (collider instanceof BoxCollider boxCollider) {
                    // Adjust the future tile collider bounds
                    Rectangle2D.Double adjustedTileBounds = new Rectangle2D.Double(
                            boxCollider.getBounds().x + tile.getTilePhisicsComponents().getVelocity_x(),
                            boxCollider.getBounds().y + boxCollider.getBounds().getHeight() * 0.1,
                            boxCollider.getBounds().getWidth(),
                            boxCollider.getBounds().getHeight() * 0.8
                    );


                    for (Player player : playingScene.getPlayers()) {
                        if (player.getCollider() instanceof BoxCollider playerCollider){
                            if (playerCollider.getBounds().intersects(adjustedTileBounds)) {
                                double overlap = playerCollider.getBounds().getMaxX() - adjustedTileBounds.getMinX();
                                player.setPosX(player.getPosX() - overlap);
                                player.getPhisicsComponent().setVelocityX(tile.getTilePhisicsComponents().getVelocity_x());
                            }
                            else {
                                player.getPhisicsComponent().setVelocityX(0);
                            }
                        }
                    }
                }
            }
        }));
    }

    public void resetPlayerVelocityX(){
        for (Player player : playingScene.getPlayers()) {
            if (player.isConsumedBonus()) {
                ticks++;
                if (ticks >= Game.UPS_LIMIT * 3) { // 3 seconds
                    player.setConsumedBonus(false);
                    player.getPhisicsComponent().setVelocityX(0);
                    ticks = 0;
                }
            }
        }
    }

    public void detectGameObjects(){
        playingScene.getLevelsManager().getActiveLevels().forEach(level -> level.getGameObjects().forEach(gameObject -> {

            for (Player player : playingScene.getPlayers()) {
                BoxCollider player_collider = (BoxCollider) player.getCollider();

                if(gameObject instanceof PowerUp powerUp){
                    if (powerUp.getBounds().intersects(player_collider.getBounds()) && !player.isConsumedBonus()) {
                        if (powerUp instanceof SpeedUp speedUp) {
                            player.getPhisicsComponent().setVelocityX(speedUp.getVelocityXIncrement() * 10);
                            player.setConsumedBonus(true);
                        }
                        else if (powerUp instanceof SpeedDown speedDown) {
                            player.getPhisicsComponent().setVelocityX(speedDown.getVelocityXIncrement() * 10);
                            player.setConsumedBonus(true);
                        }
                    }
                }

                if (gameObject instanceof Saw saw){
                    if (saw.getBounds().intersects(player_collider.getBounds())) {
                        if (!playingScene.isGodMode()) {
                            playerDead(player);
                        }

                    }
                }

            }
        }));
    }

    private void ifPlayerFallingAway(){
        for (Player player : playingScene.getPlayers()) {
            if (playingScene.isBorderlessMode()){
                if (player.getPosY() >= Game.WINDOW_HEIGHT - Game.TILES_SIZE) { // falling down
                    player.setPosY(Game.WINDOW_HEIGHT - Game.TILES_SIZE);
                }
                else if(player.getPosY() <= -Game.TILES_SIZE){ // falling up
                    player.setPosY(-Game.TILES_SIZE);
                }
                else if (player.getPosX() < -Game.TILES_SIZE * 2){
                    playerDead(player);
                }
            }
            else{
                if (player.getPosY() > Game.WINDOW_HEIGHT + Game.TILES_SIZE * 2.5) { // falling down
                    playerDead(player);
                }
                else if(player.getPosY() < -Game.TILES_SIZE * 2.5){ // falling up
                    playerDead(player);
                }
                else if (player.getPosX() < -Game.TILES_SIZE * 2){
                    playerDead(player);
                }
            }
        }
    }

    private void playerDead(Player player){
        double xVelocity = playingScene.getLevelsManager().getSpeed();

        player.setDead(true, xVelocity);
        player.getPhisicsComponent().setVelocityX(-xVelocity);
    }

    private void playersInteractions(){
        if (playingScene.isGhostMode()) return;

        for(Player player : playingScene.getPlayers()) {
            for (Player otherPlayer : playingScene.getPlayers()) {
                if (player != otherPlayer) {
                    if(player.getCollider() instanceof BoxCollider boxCPlayer &&
                            otherPlayer.getCollider() instanceof BoxCollider boxCOtherPlayer) {

                        Rectangle2D.Double detectXBoundPlayer = new Rectangle2D.Double(
                                boxCPlayer.getBounds().getX(),
                                boxCPlayer.getBounds().getY() + boxCPlayer.getBounds().getHeight() * 0.15,
                                boxCPlayer.getBounds().getWidth(),
                                boxCPlayer.getBounds().getHeight() * 0.7
                        );

                        Rectangle2D.Double detectYBoundPlayer = new Rectangle2D.Double(
                                boxCPlayer.getBounds().getX() + boxCPlayer.getBounds().getWidth() * 0.1,
                                boxCPlayer.getBounds().getY() + player.getPhisicsComponent().getVelocityY(),
                                boxCPlayer.getBounds().getWidth() * 0.8,
                                boxCPlayer.getBounds().getHeight()
                        );

                        // Check if the players are colliding in the Y axis
                        if (detectYBoundPlayer.intersects(boxCOtherPlayer.getBounds())) {
//                            System.out.println("detectYBoundPlayer");

                            // if both players is falling down
                            if (player.getPhisicsComponent().getGravityDirection() > 0 &&
                                    otherPlayer.getPhisicsComponent().getGravityDirection() > 0) {
                                if (player.getPosY() < otherPlayer.getPosY()) {
                                    // upper player
                                    player.setPosY(otherPlayer.getPosY() - boxCOtherPlayer.getBounds().getHeight());
                                    player.getPhisicsComponent().setAbleToUp(true);
                                    player.getPhisicsComponent().setAbleToDown(false);
                                    // downer other
                                    otherPlayer.setDisableControls(true);
                                    otherPlayer.getPhisicsComponent().setAbleToUp(false);
                                    otherPlayer.getPhisicsComponent().setAbleToDown(true);
                                }
                            }
                            // if both players is falling up
                            else if (player.getPhisicsComponent().getGravityDirection() < 0 &&
                                    otherPlayer.getPhisicsComponent().getGravityDirection() < 0) {
                                if (player.getPosY() > otherPlayer.getPosY()) {
                                    // downer player
                                    player.setPosY(otherPlayer.getPosY() + boxCOtherPlayer.getBounds().getHeight());
                                    player.getPhisicsComponent().setAbleToUp(false);
                                    player.getPhisicsComponent().setAbleToDown(true);
                                    // upper other
                                    otherPlayer.setDisableControls(true);
                                    otherPlayer.getPhisicsComponent().setAbleToUp(true);
                                    otherPlayer.getPhisicsComponent().setAbleToDown(false);
                                }
                            }
                            // if first and second has different velocity
                            else if (player.getPhisicsComponent().getGravityDirection() < 0 &&
                                    otherPlayer.getPhisicsComponent().getGravityDirection() > 0) {
                                player.setPosY(player.getPosY() + 1);
                                player.getPhisicsComponent().setVelocityY(0);
                                player.getPhisicsComponent().setAbleToUp(false);
                                player.getPhisicsComponent().setAbleToDown(true);

                                otherPlayer.setPosY(otherPlayer.getPosY() - 1);
                                player.getPhisicsComponent().setVelocityY(0);
                                otherPlayer.getPhisicsComponent().setAbleToUp(true);
                                otherPlayer.getPhisicsComponent().setAbleToDown(false);
                            }
                        }
                        else{
                            player.setDisableControls(false);
                            otherPlayer.setDisableControls(false);
                        }

                        // Check if the players are colliding in the X axis
                        if (detectXBoundPlayer.intersects(boxCOtherPlayer.getBounds())) {
//                            System.out.println("detectXBoundPlayer");
                            if(player.getPosX() <= otherPlayer.getPosX()) {
                                player.setPosX(otherPlayer.getPosX() - boxCPlayer.getBounds().getWidth() - 1);
                            }
                        }

                    }
                }
            }
        }
    }
}

