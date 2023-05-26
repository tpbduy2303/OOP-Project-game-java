package entities;

//import static utilz.Constants.Directions.DOWN;
//import static utilz.Constants.Directions.LEFT;
//import static utilz.Constants.Directions.RIGHT;
//import static utilz.Constants.Directions.UP;
import static utilz.Constants.PlayerConstants.*;
import static utilz.HelpMethods.*;

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
	private boolean moving = false, attacking1 = false, attacking2 = false;
	private boolean left, up, right, down, jump;
	private float playerSpeed = 5f * Game.SCALE;
	private  int [][] lvlData;
	//reposition hitbox
	private float xDrawOffset = 19 * Game.SCALE;
	private float yDrawOffset = 17 * Game.SCALE;
	private float faceWall;
	// Jumping / gravity
	private float airSpeed = 0f;
	private float gravity = 0.04f * Game.SCALE;
	private float jumpSpeed = -2.75f * Game.SCALE;
	private float fallSpeedAfterCollition = 0.1f * Game.SCALE;
	private boolean inAir = false;

	public Player(float x, float y, int width, int height) {
		super(x, y, width, height);
		loadAnimation();
		//resize player hitbox
		iniHitbox(x,y,23*Game.SCALE,37*Game.SCALE);
	}
	
	public void update() {
	    updatePos();
		updateAnimationTick();
	    setAnimation();

	}
	public void render(Graphics g, int xLvlOffset) {
		if (hitbox.x < xLvlOffset) {
			hitbox.x = xLvlOffset;
		}
		g.drawImage(animations[playerAction][aniIndex], (int) (hitbox.x - xDrawOffset) - xLvlOffset, (int) (hitbox.y - yDrawOffset), (int)(64*Game.SCALE),(int)(64*Game.SCALE), null);
		drawHitbox(g, xLvlOffset);
	}
	
    private void updatePos() {
		moving = false;

		if (jump)
			jump();
		
		if(!IsEntityOnFloor(hitbox,lvlData))
			inAir = true;
		if (!inAir)
			if ((!left && !right) || (right && left))
				return;

		float xSpeed = 0;

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
	}
    
    public boolean standingStable() {
    	if (ifBlockStable(hitbox.x+faceWall, hitbox.y, hitbox.width , hitbox.height, lvlData)) 
    		return false;
    	return true;
    }

	private void jump() {
//		if(inAir)
//			return;
		inAir =true;
		airSpeed = jumpSpeed;
	}

	private void resetInAir() {
		inAir = false;
		airSpeed = 0;
	}

	private void updateXPos(float xSpeed) {
		faceWall = 0;
		if (xSpeed > 0)
			faceWall = 20f*Game.SCALE;
		if (xSpeed < 0)
			faceWall = -8f*Game.SCALE;
		if (CanMoveHere(hitbox.x + xSpeed + faceWall, hitbox.y , hitbox.width, hitbox.height, lvlData)){
			hitbox.x += xSpeed;
		}else{
			hitbox.x = GetEntityXPosNextToWall(hitbox,xSpeed);
		}
	}
	
	private void setAnimation() {
		int startAni = playerAction;
		if (moving) {
			if (left)
				playerAction = RUNNING2;
			else if (right)
				playerAction = RUNNING;
		}
			else 
				playerAction = IDLE;
		if (attacking1) {
			playerAction = ATTACK_1;
			aniSpeed = 15;
		}
		else if (attacking2) {
			playerAction = ATTACK_2;
			aniSpeed = 15;
		}

		if (startAni != playerAction) {
			aniTick = 0;
			aniIndex = 0;
			aniSpeed = 30;
		}		
	}
	private void updateAnimationTick() {
    	aniTick ++;
    	if ( aniTick >= aniSpeed ) {
    		aniTick = 0;
    		aniIndex++;
    		if ( aniIndex >=   GetSpriteAmount(playerAction)) {
    			aniIndex = 0;
    			attacking1 = false;
    			attacking2 = false;
    		}
    	}
	}

    private void loadAnimation() {
			BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
			animations = new BufferedImage[6][6];
			for (int j =0; j < animations.length;j++) {
				for (int i = 0; i < animations[j].length; i++) {
				 animations[j][i] = img.getSubimage(100*i, j*100,96,100);
				}
			}
    }

	public void loadLvlData(int[][] lvlData){
		this.lvlData = lvlData;
	}
	public void resetDirBooleans() {
		left = false;
		right = false;
		up = false;
		down = false;
	}
	public void setAttacking1(boolean attacking) {
		this.attacking1 = attacking;
	}
	public void setAttacking2(boolean attacking) {
		this.attacking2 = attacking;
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
