package entities;

//import static utilz.Constants.Directions.DOWN;
//import static utilz.Constants.Directions.LEFT;
//import static utilz.Constants.Directions.RIGHT;
//import static utilz.Constants.Directions.UP;
import static utilz.Constants.PlayerConstants.ATTACK_1;
import static utilz.Constants.PlayerConstants.GetSpriteAmount;
import static utilz.Constants.PlayerConstants.RUNNING;

import static utilz.Constants.PlayerConstants.IDLE;

import static utilz.HelpMethods.*;
import static utilz.HelpMethods.GetEntityXPosNextToWall;

import java.awt.Graphics;
//import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
//import java.io.IOException;
//import java.io.InputStream;

// javax.imageio.ImageIO;

import Main.Game;
import utilz.LoadSave;

public class Player extends Entity{
	
    private BufferedImage[][] animations;
    private int aniTick, aniIndex, aniSpeed = 30;
    private int playerAction = IDLE;
	private boolean moving = false, attacking = false;
	private boolean left, up, right, down, jump;
	private float playerSpeed = 2.0f;
	private  int [][] lvlData;
	//reposition hitbox for main character
	private float xDrawOffset = 16 * Game.SCALE;
	private float yDrawOffset = 7 * Game.SCALE;
	// Jumping / gravity
	private float airSpeed = 1f;
	private float gravity = 0.04f * Game.SCALE;
	private float jumpSpeed = -2.25f * Game.SCALE;
	private float fallSpeedAfterCollition = 0.5f * Game.SCALE;
	private boolean inAir = false;

	public Player(float x, float y, int width, int height) {
		super(x, y, width, height);
		loadAnimation();
		//resize player hitbox
		iniHitbox(x,y,30*Game.SCALE,47*Game.SCALE);
	}
	
	public void update() {
	    updatePos();
		updateAnimationTick();
	    setAnimation();

	}
	public void render(Graphics g) {
		g.drawImage(animations[playerAction][aniIndex], (int) (hitbox.x - xDrawOffset), (int) (hitbox.y - yDrawOffset), (int)(64*Game.SCALE),(int)(64*Game.SCALE), null);
		drawHitbox(g);
	}
	
    private void updatePos() {
		moving = false;

		if (jump)
			jump();
		if(!left && !right && !inAir)
			return;

		float xSpeed = 0, ySpeed = 0;

		if (left)
			xSpeed -= playerSpeed;

		if (right)
			xSpeed += playerSpeed;
		if (!inAir)
			if(!IsEntityOnFloor(hitbox,lvlData)){
				inAir = true;
			}

		// falling check
		if (inAir) {
			if (CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width , hitbox.height, lvlData)) {
			hitbox.y += airSpeed;
			airSpeed += gravity;
			updateXPos(xSpeed);
		} else {
			hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox,airSpeed);
			if (airSpeed > 0)
				resetInAir();
			else
				airSpeed = fallSpeedAfterCollition;
			updateXPos(xSpeed);
		}

		}else
			updateXPos(xSpeed);

		moving = true;

//		if (up && !down) {
//			ySpeed = -playerSpeed;
//		} else if (down && !up) {
//			ySpeed = playerSpeed;
//		}
//		if (CanMoveHere(x+ xSpeed, y +ySpeed, width, height, lvlData)){
//			this.x += xSpeed;
//			this.y += ySpeed;
//			moving = true;
//		}
//		if (CanMoveHere(hitbox.x + xSpeed, hitbox.y +ySpeed, hitbox.width, hitbox.height, lvlData)){
//			hitbox.x += xSpeed;
//			hitbox.y += ySpeed;
//			moving = true;
//		}
	}

	private void jump() {
		if(inAir)
			return;
		inAir =true;
		airSpeed = jumpSpeed;
	}

	private void resetInAir() {
		inAir = false;
		airSpeed = 0;
	}

	private void updateXPos(float xSpeed) {
		if (CanMoveHere(hitbox.x + xSpeed, hitbox.y , hitbox.width, hitbox.height, lvlData)){
			hitbox.x += xSpeed;
		}else{
			hitbox.x = GetEntityXPosNextToWall(hitbox,xSpeed);
		}
	}



	private void setAnimation() {
		int startAni = playerAction;
		if (moving) 
			playerAction = RUNNING;
			else 
				playerAction = IDLE;
		if (attacking)
			playerAction = ATTACK_1;

		if (startAni != playerAction) {
			aniTick = 0;
			aniIndex = 0;
		}		
	}
	private void updateAnimationTick() {
    	aniTick ++;
    	if ( aniTick >= aniSpeed ) {
    		aniTick = 0;
    		aniIndex++;
    		if ( aniIndex >=   GetSpriteAmount(playerAction)) {
    			aniIndex = 0;
    			attacking = false;
    		}
    	}
	}
	public void loadLvlData(int[][] lvlData) {
		this.lvlData = lvlData;
		if (!IsEntityOnFloor(hitbox, lvlData))
			inAir = true;

	}
    private void loadAnimation() {
			BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
			animations = new BufferedImage[4][6];
			for (int j =0; j < animations.length;j++) {
				for (int i = 0; i < animations[j].length; i++) {
				 animations[j][i] = img.getSubimage(100*i, j*100,90,100);
				}
			}
    }

	public void loadvlData(int [][] lvlData){
		this.lvlData = lvlData;
	}
	public void resetDirBooleans() {
		left = false;
		right = false;
		up = false;
		down = false;
	}
	public void setAttacking(boolean attacking) {
		this.attacking = attacking;
	}
	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isUp() {
		return up;
	}

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
    public void setJump(boolean jump){
		this.jump = jump;
	}
    

}
