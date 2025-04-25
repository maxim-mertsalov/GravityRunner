package me.xmertsalov.entities;

import me.xmertsalov.components.*;
import me.xmertsalov.Game;
import me.xmertsalov.utils.BundleLoader;

import static me.xmertsalov.components.PlayerState.*;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Player extends Entity {

	// Player states
	private HashMap<String, List<Integer>> playerStates;
	private String playerState = "RUNNING";

	private String playerSkin = "Adventure Boy A";

	// Player attributes
	private boolean isGodMode = false;
	private boolean isDead = false;
	private boolean inActive = false;

	private boolean moving = false, attacking = false;

	// Player controls
	private boolean left, up, right, down, changedGravity;

	// Player speed
	private final float playerSpeed = 2.0f;

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

	// Player controls
	private int changeGravityKey;
	private boolean disableControls = false;
	private boolean disableGravity = false;

	// Player flip sprite
	private double flipY = 0;
	private double flipH = 1;


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
//				24 * incrementIndex * 4,
//				28 * incrementIndex * 4);

		phisicsComponent = new PhisicsComponent(this, mass);
	}

	public void update() {
		if (isDead) {
			// Handle player death logic here
			animator.setAnimationState("DEATH", true);
			return;
		}
		if (inActive) return;

		phisicsComponent.setVelocityY(0);

		collider.updateBounds(
				x + playerWidth / 3,
				y + playerHeight / 3
		);
		animator.update();
		phisicsComponent.update();

		flipPlayer();


//		setAnimationState();
	}

	public void render(Graphics g) {
//		g.drawImage(animations[playerState][aniIndex],
//				(int) x, (int) y,
//				256, 160, null);
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

	public void setAnimator(PlayerAnimator playerAnimator, String playerSkin) {
        this.playerSkin = playerSkin;
		animator = playerAnimator.getAnimator(playerSkin);
		animator.setAnimationState("RUNNING");
	}

	public void resetDirBooleans() {
		left = false;
		right = false;
		up = false;
		down = false;
	}

	public void changeGravity() {
		if (disableControls) return;

		phisicsComponent.setVelocityY(0);
		phisicsComponent.setGravityDirection(-phisicsComponent.getGravityDirection());
		phisicsComponent.setAbleToUp(true);
		phisicsComponent.setAbleToDown(true);

//		// Adjust player position after gravity flip
//		if (phisicsComponent.getGravityDirection() > 0) {
//			// Gravity is now downwards
//			setPosY(getPosY() - playerHeight); // Move player up by playerHeight
//		} else {
//			// Gravity is now upwards
//			setPosY(getPosY() + playerHeight); // Move player down by playerHeight
//		}
	}

	public void setAttacking(boolean attacking) {
		this.attacking = attacking;
	}

	public boolean isLeft() {return left;}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isUp() {return up;}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public void setChangedGravity(boolean changedGravity) {
		this.changedGravity = changedGravity;
	}

	public boolean isChangedGravity() {
		return changedGravity;
	}


	public Collider getCollider() {
		return collider;
	}
	public PhisicsComponent getPhisicsComponent() {
		return phisicsComponent;
	}

	public double getPosX() {
		return x;
	}
	public double getPosY() {
		return y;
	}
	public void setPosX(double x) {
		this.x = x;
	}
	public void setPosY(double y) {
		this.y = y;
	}

	public double getSpeed() {
		return playerSpeed;
	}

	public int getChangeGravityKey() {
		return changeGravityKey;
	}

	public void setChangeGravityKey(int changeGravityKey) {
		this.changeGravityKey = changeGravityKey;
	}

	public boolean isGodMode(){ return isGodMode; }

	public boolean isDead(){ return isDead; }
	public void setDead(boolean dead) { this.isDead = dead; }

    public boolean isConsumedBonus() {
        return isConsumedBonus;
    }

    public void setConsumedBonus(boolean consumedBonus) {
        isConsumedBonus = consumedBonus;
    }

    public boolean isDisableControls() {
        return disableControls;
    }

    public void setDisableControls(boolean disableControls) {
        this.disableControls = disableControls;
    }

    public boolean isDisableGravity() {
        return disableGravity;
    }

    public void setDisableGravity(boolean disableGravity) {
        this.disableGravity = disableGravity;
    }

	public PlayerAnimator getPlayerAnimator() {
		return playerAnimator;
	}

	public String getPlayerSkin() {
		return playerSkin;
	}

    public boolean isInActive() {
        return inActive;
    }

    public void setInActive(boolean inActive) {
        this.inActive = inActive;
    }
}

