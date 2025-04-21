package me.xmertsalov.entities;

import me.xmertsalov.components.BoxCollider;
import me.xmertsalov.components.Collider;
import me.xmertsalov.components.PhisicsComponent;
import me.xmertsalov.components.PlayerAnimator;
import me.xmertsalov.Game;

import static me.xmertsalov.components.PlayerState.*;

import java.awt.Graphics;

public class Player extends Entity {
	private int playerState = RUNNING;
	private boolean moving = false, attacking = false;
	private boolean left, up, right, down, changedGravity;
	private final float playerSpeed = 2.0f;

	private final double playerWidth, playerHeight;
	private final static double incrementIndex = Game.SCALE * 0.4;

	private Collider collider;
	private PhisicsComponent phisicsComponent;

	private final PlayerAnimator animator = new PlayerAnimator(this);

	private final double mass = 0.5f;

	public Player(float x, float y) {
		super(x, y);
		animator.loadAnimations();

		playerWidth = 64 * 4 * incrementIndex;
		playerHeight = 40 * 4 * incrementIndex;

		collider = new BoxCollider(x + 20 * 4 * incrementIndex,
				y + 4 * 4 * incrementIndex,
				24 * incrementIndex * 4,
				28 * incrementIndex * 4);

		phisicsComponent = new PhisicsComponent(this, mass);
	}

	public void update() {
//		updatePos();

		phisicsComponent.setVelocityY(0);

		collider.updateBounds(
				x + 20 * 4 * incrementIndex,
				y + 4 * 4 * incrementIndex
		);
		animator.updateAnimationTick(playerState);
		phisicsComponent.update();


//		setAnimationState();
	}

	public void render(Graphics g) {
//		g.drawImage(animations[playerState][aniIndex],
//				(int) x, (int) y,
//				256, 160, null);

		collider.draw(g);
		animator.render(g, x, y, (int)(playerWidth), (int)(playerHeight));
	}


	private void setAnimationState() {
		int startAni = playerState;

		if (moving)
			playerState = RUNNING;
		else
			playerState = IDLE;

		if (attacking)
			playerState = ATTACK_1;

		if (startAni != playerState)
			animator.resetAnimTick();
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

}

