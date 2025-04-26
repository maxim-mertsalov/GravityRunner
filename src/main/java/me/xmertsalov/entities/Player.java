package me.xmertsalov.entities;

import me.xmertsalov.audio.AudioPlayer;
import me.xmertsalov.components.*;
import me.xmertsalov.Game;

import java.awt.Graphics;

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

	private double speed = 0;


	public Player(float x, float y, int changeGravityKey, PlayerAnimator playerAnimator, String playerSkin) {
		super(x, y);
        this.changeGravityKey = changeGravityKey;

		playerWidth = 48 * 2 * Game.SCALE;
		playerHeight = 48 * 2 * Game.SCALE;

		this.playerSkin = playerSkin;

		this.playerAnimator = playerAnimator;
		animator = playerAnimator.getAnimator(playerSkin);
		animator.setAnimationState("RUNNING");

		collider = new BoxCollider(x + playerWidth / 3,
				y + playerHeight / 3,
				playerWidth / 3,
				playerHeight / 3
		);

		phisicsComponent = new PhisicsComponent(this, mass);
	}

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

	public void render(Graphics g) {
		if (inActive) return;

		collider.draw(g);
		animator.draw(g, x, y + flipY, (int)(playerWidth), (int)(playerHeight * flipH));
	}

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

	private void resetTemporarilyDisabled(){
		if (!isTemporarilyDisabled) return;

		ticksBeforeTemporlyDisabled++;
		if (ticksBeforeTemporlyDisabled >= Game.UPS_LIMIT * 0.5){
			ticksBeforeTemporlyDisabled = 0;
			isTemporarilyDisabled = false;
		}
	}

	public void changeGravity() {
		if (disableControls || isTemporarilyDisabled) return;

//		AudioPlayer.playSfx(AudioPlayer.SFX_JUMP);

		phisicsComponent.setVelocityY(0);
		phisicsComponent.setGravityDirection(-phisicsComponent.getGravityDirection());
		phisicsComponent.setAbleToUp(true);
		phisicsComponent.setAbleToDown(true);
		isTemporarilyDisabled = true;
	}

	public Collider getCollider() {return collider;}
	public PhisicsComponent getPhisicsComponent() {return phisicsComponent;}

	public double getPosX() {return x;}
	public double getPosY() {return y;}
	public void setPosX(double x) {this.x = x;}
	public void setPosY(double y) {this.y = y;}

	public int getChangeGravityKey() {return changeGravityKey;}
	public void setChangeGravityKey(int changeGravityKey) {this.changeGravityKey = changeGravityKey;}

	public boolean isDead(){ return isDead; }
	public void setDead(boolean dead, double speed) {
		this.isDead = dead;
		this.speed = speed;
		if (!dead){
			animator.setAnimationState("RUNNING");
		}
		else{
			animator.setAnimationState("DEATH", true);
		}
	}

    public boolean isConsumedBonus() {return isConsumedBonus;}
    public void setConsumedBonus(boolean consumedBonus) {isConsumedBonus = consumedBonus;}

    public void setDisableControls(boolean disableControls) {this.disableControls = disableControls;}

    public boolean isDisableGravity() {return disableGravity;}

    public void setDisableGravity(boolean disableGravity) {this.disableGravity = disableGravity;}

	public PlayerAnimator getPlayerAnimator() {return playerAnimator;}

	public void setAnimator(PlayerAnimator playerAnimator, String playerSkin) {
		this.playerSkin = playerSkin;
		animator = playerAnimator.getAnimator(playerSkin);
		animator.setAnimationState("RUNNING");
	}
	public Animator getAnimator() {return animator;}

	public String getPlayerSkin() {return playerSkin;}

    public boolean isInActive() {return inActive;}
    public void setInActive(boolean inActive) {this.inActive = inActive;}

	public void setIsTemporarilyDisabled(boolean isTemporarylyDisabled) {this.isTemporarilyDisabled = isTemporarylyDisabled;}

	public void setFlipH(int flipH){this.flipH = flipH;}
	public void setFlipY(double flipY){this.flipY = flipY;}
}

