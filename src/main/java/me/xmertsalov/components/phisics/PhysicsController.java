package me.xmertsalov.components.phisics;

import me.xmertsalov.Game;
import me.xmertsalov.components.phisics.colliders.BoxCollider;
import me.xmertsalov.components.phisics.colliders.Collider;
import me.xmertsalov.entities.Player;
import me.xmertsalov.gameObjects.powerUps.PowerUp;
import me.xmertsalov.gameObjects.powerUps.SpeedDown;
import me.xmertsalov.gameObjects.powerUps.SpeedUp;
import me.xmertsalov.gameObjects.saws.Saw;
import me.xmertsalov.scenes.inGame.PlayingScene;

import java.awt.geom.Rectangle2D;

/**
 * The {@code PhysicsController} class manages the physics interactions and collisions
 * in the game. It handles player interactions with tiles, walls, game objects, and other players.
 * Additionally, it manages player states such as falling, velocity resets, and death conditions.
 */
public class PhysicsController {
    private final PlayingScene playingScene;
    private int ticks;

    /**
     * Constructs a {@code PhysicsController} for the specified {@link PlayingScene}.
     *
     * @param playingScene the playing scene associated with this physics controller
     */
    public PhysicsController(PlayingScene playingScene) {
        this.playingScene = playingScene;
    }

    /**
     * Updates the physics state by detecting collisions, resetting velocities,
     * and handling player interactions.
     */
    public void update() {
        detectFallingCollisions();
        detectWallsCollisions();
        resetPlayerVelocityX();
        detectGameObjects();
        ifPlayerFallingAway();

        // Check if the player is in ghost mode
        if (!playingScene.isGhostMode()) playersInteractions();
    }

    /**
     * Detects collisions between players and tiles when falling and adjusts their positions
     * and velocities accordingly.
     */
    private void detectFallingCollisions() {

        playingScene.getLevelsManager().getActiveLevels().forEach(level -> level.getTiles().forEach(tile -> {
            if (tile.getCollider() != null) { // There are tiles with colliders:
                Collider collider = tile.getCollider();
                for (Player player : playingScene.getPlayers()) {

                    if (player.getCollider() instanceof BoxCollider && !player.isInActive()) {
                        BoxCollider boxCollider = (BoxCollider) player.getCollider();

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

                        if (collider instanceof BoxCollider) {
                            BoxCollider tileBoxCollider = (BoxCollider) collider;

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

    /**
     * Detects collisions between players and walls, adjusting their positions and velocities
     * to prevent overlapping.
     */
    private void detectWallsCollisions() {
        playingScene.getLevelsManager().getActiveLevels().forEach(level -> level.getTiles().forEach(tile -> {
            if (tile.getCollider() != null) { // There are tiles with colliders:
                Collider collider = tile.getCollider();
                if (collider instanceof BoxCollider) {

                    BoxCollider boxCollider = (BoxCollider) collider;

                    // Adjust the future tile collider bounds
                    Rectangle2D.Double adjustedTileBounds = new Rectangle2D.Double(
                            boxCollider.getBounds().x + tile.getTilePhisicsComponents().getVelocity_x(),
                            boxCollider.getBounds().y + boxCollider.getBounds().getHeight() * 0.1,
                            boxCollider.getBounds().getWidth(),
                            boxCollider.getBounds().getHeight() * 0.8
                    );


                    for (Player player : playingScene.getPlayers()) {

                        if (player.getCollider() instanceof BoxCollider && !player.isInActive()) {
                            BoxCollider playerCollider = (BoxCollider) player.getCollider();

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

    /**
     * Resets the horizontal velocity of players after a bonus effect expires.
     * The reset occurs after a fixed duration.
     */
    public void resetPlayerVelocityX(){
        for (Player player : playingScene.getPlayers()) {
            if (player.isConsumedBonus() && !player.isInActive()) {
                ticks++;
                if (ticks >= Game.UPS_LIMIT * Game.TIME_BEFORE_POWER_UP_RESET) { // 3 seconds
                    player.setConsumedBonus(false);
                    player.getPhisicsComponent().setVelocityX(0);
                    ticks = 0;
                }
            }
        }
    }

    /**
     * Detects interactions between players and game objects such as power-ups and saws.
     * Applies effects or triggers events based on the type of object.
     */
    public void detectGameObjects(){
        playingScene.getLevelsManager().getActiveLevels().forEach(level -> level.getGameObjects().forEach(gameObject -> {

            for (Player player : playingScene.getPlayers()) {

                if (player.getCollider() instanceof BoxCollider && !player.isInActive()){
                    BoxCollider player_collider = (BoxCollider) player.getCollider();

                    if(gameObject instanceof PowerUp){
                        PowerUp powerUp = (PowerUp) gameObject;

                        if (powerUp.getBounds().intersects(player_collider.getBounds()) && !player.isConsumedBonus()) {
                            if (powerUp instanceof SpeedUp) {
                                SpeedUp speedUp = (SpeedUp) powerUp;

                                player.getPhisicsComponent().setVelocityX(speedUp.getVelocityXIncrement() * 10);
                                player.setConsumedBonus(true);
                            }
                            else if (powerUp instanceof SpeedDown) {
                                SpeedDown speedDown = (SpeedDown) powerUp;

                                player.getPhisicsComponent().setVelocityX(speedDown.getVelocityXIncrement() * 10);
                                player.setConsumedBonus(true);
                            }
                        }
                    }

                    if (gameObject instanceof Saw){
                        Saw saw = (Saw) gameObject;

                        if (saw.getBounds().intersects(player_collider.getBounds())) {
                            if (!playingScene.isGodMode()) {
                                playerDead(player);
                            }

                        }
                    }
                }
            }
        }));
    }

    /**
     * Checks if players are falling out of bounds and handles their positions or triggers
     * death conditions based on the game mode.
     */
    private void ifPlayerFallingAway(){
        for (Player player : playingScene.getPlayers()) {
            if (!player.isInActive()){
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
    }

    /**
     * Marks a player as dead and adjusts their velocity based on the level's speed.
     *
     * @param player the player to mark as dead
     */
    private void playerDead(Player player){
        double xVelocity = playingScene.getLevelsManager().getSpeed();

        player.setDead(true, xVelocity);
        player.getPhisicsComponent().setVelocityX(-xVelocity);
    }

    /**
     * Handles interactions between players, such as collisions and adjustments
     * to their positions and velocities.
     */
    private void playersInteractions(){
        if (playingScene.isGhostMode()) return;

        for(Player player : playingScene.getPlayers()) {
            for (Player otherPlayer : playingScene.getPlayers()) {
                if (player != otherPlayer) {
                    if(player.getCollider() instanceof BoxCollider &&
                            otherPlayer.getCollider() instanceof BoxCollider) {

                        // Do not check collisions if one of the players is not playing
                        if (player.isInActive() || otherPlayer.isInActive()) continue;

                        BoxCollider boxCPlayer = (BoxCollider) player.getCollider();
                        BoxCollider boxCOtherPlayer = (BoxCollider) otherPlayer.getCollider();

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

