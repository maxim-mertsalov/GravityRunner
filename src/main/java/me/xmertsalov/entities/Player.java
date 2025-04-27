package me.xmertsalov.entities;

import me.xmertsalov.components.*;
import me.xmertsalov.Game;
import me.xmertsalov.components.Animator.Animator;
import me.xmertsalov.components.Animator.LoopingAnimationStrategy;
import me.xmertsalov.components.Animator.OneWayAnimationStrategy;
import me.xmertsalov.components.phisics.colliders.BoxCollider;
import me.xmertsalov.components.phisics.colliders.Collider;
import me.xmertsalov.components.phisics.PhisicsComponent;

import java.awt.Graphics;

/**
 * The {@code Player} class represents the player entity in the game.
 * It extends the {@code Entity} class and includes additional attributes and behaviors
 * specific to the player, such as animations, physics, controls, and collision handling.
 */
public class Player extends Entity {
	// Player attributes
	private boolean isDead = false;
	private boolean inActive = false;
	private boolean isTemporarilyDisabled = false;
	private String playerSkin;

	// Player dimensions
	private final double playerWidth, playerHeight;

	// Player components
	private Collider collider;
	private PhisicsComponent phisicsComponent;
	private Animator animator;
	PlayerAnimator playerAnimator;

	// Other
	private final double mass = 0.5f;
	private boolean isConsumedBonus = false;
	private int ticksBeforeTemporlyDisabled = 0;

	// Player controls
	private int changeGravityKey;
	private boolean disableControls = false;
	private boolean disableGravity = false;

	// Player flip sprite
	private double flipY = 0;
	private int flipH = 1;

	// States
	private double speed = 0;

	// Constants
	private final double TIME_BEFORE_JUMP = 0.6f;

	/**
	 * Constructs a {@code Player} with the specified position, control key, animator, and skin.
	 *
	 * @param x               the initial x-coordinate of the player
	 * @param y               the initial y-coordinate of the player
	 * @param changeGravityKey the key used to change the player's gravity
	 * @param playerAnimator  the animator responsible for the player's animations
	 * @param playerSkin      the skin used for the player's appearance
	 */
	public Player(float x, float y, int changeGravityKey, PlayerAnimator playerAnimator, String playerSkin) {
		super(x, y);
        this.changeGravityKey = changeGravityKey;

		playerWidth = 48 * 2 * Game.SCALE;
		playerHeight = 48 * 2 * Game.SCALE;

		this.playerSkin = playerSkin;

		this.playerAnimator = playerAnimator;
		if (playerAnimator == null) {
			throw new NullPointerException("PlayerAnimator is null");
		}
		animator = playerAnimator.getAnimator(playerSkin);
		animator.setAnimationState("RUNNING");

		collider = new BoxCollider(x + playerWidth / 3,
				y + playerHeight / 3,
				playerWidth / 3,
				playerHeight / 3
		);

		phisicsComponent = new PhisicsComponent(this, mass);
	}

	/**
	 * Updates the player's state, including physics, animations, and collision bounds.
	 * This method is called on every game update cycle.
	 */
	public void update() {
		if (isDead) {
			setPosX(getPosX() - speed);
			return;
		}
		if (inActive) return;

		resetTemporarilyDisabled();

		phisicsComponent.setVelocityY(0);

		collider.updateBounds(
				x + playerWidth / 3,
				y + playerHeight / 3
		);
		animator.update();
		phisicsComponent.update();

		flipPlayer();
	}

	/**
	 * Renders the player on the screen using the specified {@code Graphics} object.
	 *
	 * @param g the {@code Graphics} object used for rendering
	 */
	public void render(Graphics g) {
		if (inActive) return;

		collider.draw(g);
		animator.draw(g, x, y + flipY, (int)(playerWidth), (int)(playerHeight * flipH));
	}

	/**
	 * Flips the player's sprite vertically based on the current gravity direction.
	 */
	public void flipPlayer() {
		if(getPhisicsComponent().getGravityDirection() < 0){
			flipY = playerHeight;
			flipH = -1;
		}
		else {
			flipY = 0;
			flipH = 1;
		}
	}

	/**
	 * Resets the player's temporarily disabled state after a certain number of ticks.
	 */
	private void resetTemporarilyDisabled(){
		if (!isTemporarilyDisabled) return;

		ticksBeforeTemporlyDisabled++;
		if (ticksBeforeTemporlyDisabled >= Game.UPS_LIMIT * TIME_BEFORE_JUMP){
			ticksBeforeTemporlyDisabled = 0;
			isTemporarilyDisabled = false;
		}
	}

	/**
	 * Changes the player's gravity direction and temporarily disables controls.
	 */
	public void changeGravity() {
		if (disableControls || isTemporarilyDisabled) return;

		phisicsComponent.setVelocityY(0);
		phisicsComponent.setGravityDirection(-phisicsComponent.getGravityDirection());
		phisicsComponent.setAbleToUp(true);
		phisicsComponent.setAbleToDown(true);
		isTemporarilyDisabled = true;
	}

	/**
	 * Gets the player's collider component.
	 *
	 * @return the {@code Collider} associated with the player
	 */
	public Collider getCollider() {return collider;}

	/**
	 * Gets the player's physics component.
	 *
	 * @return the {@code PhisicsComponent} associated with the player
	 */
	public PhisicsComponent getPhisicsComponent() {return phisicsComponent;}

	/**
	 * Gets the player's current x-coordinate.
	 *
	 * @return the x-coordinate of the player
	 */
	public double getPosX() {return x;}

	/**
	 * Gets the player's current y-coordinate.
	 *
	 * @return the y-coordinate of the player
	 */
	public double getPosY() {return y;}

	/**
	 * Sets the player's x-coordinate.
	 *
	 * @param x the new x-coordinate of the player
	 */
	public void setPosX(double x) {this.x = x;}

	/**
	 * Sets the player's y-coordinate.
	 *
	 * @param y the new y-coordinate of the player
	 */
	public void setPosY(double y) {this.y = y;}

	/**
	 * Gets the key used to change the player's gravity.
	 *
	 * @return the key used to change gravity
	 */
	public int getChangeGravityKey() {return changeGravityKey;}

	/**
	 * Sets the key used to change the player's gravity.
	 *
	 * @param changeGravityKey the new key for changing gravity
	 */
	public void setChangeGravityKey(int changeGravityKey) {this.changeGravityKey = changeGravityKey;}

	/**
	 * Checks if the player is dead.
	 *
	 * @return {@code true} if the player is dead, {@code false} otherwise
	 */
	public boolean isDead(){ return isDead; }

	/**
	 * Sets the player's death state and updates the animation accordingly.
	 *
	 * @param dead  whether the player is dead
	 * @param speed the speed at which the player moves when dead
	 */
	public void setDead(boolean dead, double speed) {
		this.isDead = dead;
		this.speed = speed;
		if (!dead){
			animator.setAnimationState("RUNNING");
			animator.setAnimationStrategy(new LoopingAnimationStrategy());
		}
		else{
			animator.setAnimationState("DEATH");
			animator.setAnimationStrategy(new OneWayAnimationStrategy());
		}
	}

	/**
	 * Checks if the player has consumed a bonus.
	 *
	 * @return {@code true} if the player has consumed a bonus, {@code false} otherwise
	 */
    public boolean isConsumedBonus() {return isConsumedBonus;}

	/**
	 * Sets whether the player has consumed a bonus.
	 *
	 * @param consumedBonus {@code true} if the player has consumed a bonus, {@code false} otherwise
	 */
    public void setConsumedBonus(boolean consumedBonus) {isConsumedBonus = consumedBonus;}

	/**
	 * Disables or enables the player's controls.
	 *
	 * @param disableControls {@code true} to disable controls, {@code false} to enable them
	 */
    public void setDisableControls(boolean disableControls) {this.disableControls = disableControls;}

	/**
	 * Checks if gravity is disabled for the player.
	 *
	 * @return {@code true} if gravity is disabled, {@code false} otherwise
	 */
    public boolean isDisableGravity() {return disableGravity;}

	/**
	 * Sets whether gravity is disabled for the player.
	 *
	 * @param disableGravity {@code true} to disable gravity, {@code false} to enable it
	 */
    public void setDisableGravity(boolean disableGravity) {this.disableGravity = disableGravity;}

	/**
	 * Gets the player's animator.
	 *
	 * @return the {@code PlayerAnimator} associated with the player
	 */
	public PlayerAnimator getPlayerAnimator() {return playerAnimator;}

	/**
	 * Sets the player's animator and updates the animation state.
	 *
	 * @param playerAnimator the new animator for the player
	 * @param playerSkin     the skin used for the player's appearance
	 */
	public void setAnimator(PlayerAnimator playerAnimator, String playerSkin) {
		this.playerSkin = playerSkin;
		animator = playerAnimator.getAnimator(playerSkin);
		animator.setAnimationState("RUNNING");
	}

	/**
	 * Gets the player's animator.
	 *
	 * @return the {@code Animator} associated with the player
	 */
	public Animator getAnimator() {return animator;}

	/**
	 * Gets the player's skin.
	 *
	 * @return the skin used for the player's appearance
	 */
	public String getPlayerSkin() {return playerSkin;}

	/**
	 * Checks if the player is inactive.
	 *
	 * @return {@code true} if the player is inactive, {@code false} otherwise
	 */
    public boolean isInActive() {return inActive;}

	/**
	 * Sets whether the player is inactive.
	 *
	 * @param inActive {@code true} to make the player inactive, {@code false} otherwise
	 */
    public void setInActive(boolean inActive) {this.inActive = inActive;}

	/**
	 * Sets whether the player is temporarily disabled.
	 *
	 * @param isTemporarylyDisabled {@code true} to temporarily disable the player, {@code false} otherwise
	 */
	public void setIsTemporarilyDisabled(boolean isTemporarylyDisabled) {this.isTemporarilyDisabled = isTemporarylyDisabled;}

	/**
	 * Sets the horizontal flip state of the player's sprite.
	 *
	 * @param flipH the horizontal flip value (1 for normal, -1 for flipped)
	 */
	public void setFlipH(int flipH){this.flipH = flipH;}

	/**
	 * Sets the vertical flip offset of the player's sprite.
	 *
	 * @param flipY the vertical flip offset
	 */
	public void setFlipY(double flipY){this.flipY = flipY;}
}
