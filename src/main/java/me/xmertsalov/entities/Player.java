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

	// Player attributes
	private boolean isGodMode = false;
	private boolean isDead = false;

	private boolean moving = false, attacking = false;

	// Player controls
	private boolean left, up, right, down, changedGravity;

	// Player speed
	private final float playerSpeed = 2.0f;

	// Player dimensions
	private final double playerWidth, playerHeight;
	private final static double incrementIndex = Game.SCALE * 0.4;

	// Player components
	private Collider collider;
	private PhisicsComponent phisicsComponent;
	private final Animator animator;

	// Other
	private final double mass = 0.5f;
	private boolean isConsumedBonus = false;

	// Player controls
	private final int changeGravityKey;


	public Player(float x, float y, int changeGravityKey) {
		super(x, y);
        this.changeGravityKey = changeGravityKey;

		playerStates = new HashMap<>();
		playerStates.put("IDLE", new ArrayList<>(Arrays.asList(0, 5)) );
		playerStates.put("RUNNING", new ArrayList<>(Arrays.asList(1, 6)) );
		playerStates.put("HIT", new ArrayList<>(Arrays.asList(5, 4)) );
		playerStates.put("ATTACK_1", new ArrayList<>(Arrays.asList(6, 3)) );
		playerStates.put("GROUND", new ArrayList<>(Arrays.asList(4, 2)) );
		playerStates.put("JUMP", new ArrayList<>(Arrays.asList(2, 3)) );
		playerStates.put("DEATH", new ArrayList<>(Arrays.asList(6, 8)) );

        animator = new Animator(BundleLoader.PIRATE_ATLAS,
				64,
				40,
				7,
				8,
				playerStates,
				playerState,
				-1);

		playerWidth = 64 * 4 * incrementIndex;
		playerHeight = 40 * 4 * incrementIndex;

		collider = new BoxCollider(x + 20 * 4 * incrementIndex,
				y + 4 * 4 * incrementIndex,
				24 * incrementIndex * 4,
				28 * incrementIndex * 4);

		phisicsComponent = new PhisicsComponent(this, mass);
	}

	public void update() {
		if (isDead) {
			// Handle player death logic here
			animator.setAnimationState("DEATH", true);
//			return;
		}
		phisicsComponent.setVelocityY(0);

		collider.updateBounds(
				x + 20 * 4 * incrementIndex,
				y + 4 * 4 * incrementIndex
		);
		animator.update();
		phisicsComponent.update();


//		setAnimationState();
	}

	public void render(Graphics g) {
//		g.drawImage(animations[playerState][aniIndex],
//				(int) x, (int) y,
//				256, 160, null);

		collider.draw(g);
		animator.draw(g, x, y, (int)(playerWidth), (int)(playerHeight));
	}


	private void setAnimationState() {
		if (moving)
			playerState = "RUNNING";
		else
			playerState = "IDLE";

		if (attacking)
			playerState = "ATTACK_1";

		animator.setAnimationState(playerState);
	}



	private void updatePos() {
		moving = false;

		if (left && !right) {
			x -= playerSpeed;
			moving = true;
		} else if (right && !left) {
			x += playerSpeed;
			moving = true;
		}

		if (up && !down) {
			phisicsComponent.setVelocityY(-playerSpeed);
			moving = true;
		} else if (down && !up) {
			phisicsComponent.setVelocityY(playerSpeed);
			moving = true;
		} else {
			// Reset vertical velocity when no movement keys are pressed
			phisicsComponent.setVelocityY(0);
		}
	}

	public void resetDirBooleans() {
		left = false;
		right = false;
		up = false;
		down = false;
	}

	public void changeGravity() {
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

	public boolean isGodMode(){ return isGodMode; }

	public boolean isDead(){ return isDead; }
	public void setDead(boolean dead) { this.isDead = dead; }

    public boolean isConsumedBonus() {
        return isConsumedBonus;
    }

    public void setConsumedBonus(boolean consumedBonus) {
        isConsumedBonus = consumedBonus;
    }
}

