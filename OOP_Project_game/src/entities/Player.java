package entities;

//import static utilz.Constants.Directions.DOWN;
//import static utilz.Constants.Directions.LEFT;
//import static utilz.Constants.Directions.RIGHT;
//import static utilz.Constants.Directions.UP;
import static utilz.Constants.PlayerConstants.*;
import static utilz.HelpMethods.*;

import java.awt.*;
//import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
//import java.io.IOException;
//import java.io.InputStream;

// javax.imageio.ImageIO;

import Main.Game;
import gamestates.Playing;
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
	private float xDrawOffset = 12 * Game.SCALE;
	private float yDrawOffset = 5 * Game.SCALE;
	private float faceWall;

	// Jumping / gravity
	private float airSpeed = 0f;
	private float gravity = 0.04f * Game.SCALE;
	private float jumpSpeed = -2.75f * Game.SCALE;
	private float fallSpeedAfterCollition = 0.1f * Game.SCALE;
	private boolean inAir = false;

	// StatusBarUI
	private BufferedImage statusBarImg;

	private int statusBarWidth = (int) (192 * Game.SCALE);
	private int statusBarHeight = (int) (58 * Game.SCALE);
	private int statusBarX = (int) (10 * Game.SCALE);
	private int statusBarY = (int) (10 * Game.SCALE);

	private int healthBarWidth = (int) (150 * Game.SCALE);
	private int healthBarHeight = (int) (4 * Game.SCALE);
	private int healthBarXStart = (int) (34 * Game.SCALE);
	private int healthBarYStart = (int) (14 * Game.SCALE);

	private int maxHealth = 1000;
	private int currentHealth = maxHealth;
	private int healthWidth = healthBarWidth;

	// AttackBox
	private Rectangle2D.Float attackBox;

	private int flipX = 0;
	private int flipW = 1;

	private boolean attackChecked;
	private Playing playing;


	public Player(float x, float y, int width, int height, Playing playing ) {
		super(x, y, width, height);
		this.playing = playing;

		loadAnimation();
		//resize player hitbox
		iniHitbox(x,y,(int)23*Game.SCALE,(int)37*Game.SCALE);
		initAttackBox();
	}
	private void initAttackBox() {
		attackBox = new Rectangle2D.Float(x, y, (int) (20 * Game.SCALE), (int) (25 * Game.SCALE));
	}
	
	public void update() {
		updateHealthBar();

		if (currentHealth <= 0) {
			playing.setGameOver(true);
			return;
		}

		updateAttackBox();

		updatePos();
		if (attacking1)
			checkAttack();
		updateAnimationTick();
	    setAnimation();

	}
	private void checkAttack() {
		if (attackChecked || aniIndex != 1)
			return;
		attackChecked = true;
		playing.checkEnemyHit(attackBox);

	}

	private void updateAttackBox() {
		if (right)
			attackBox.x = hitbox.x + hitbox.width + (int) (Game.SCALE * 5);
		else if (left)
			attackBox.x = hitbox.x - attackBox.width - (int) (Game.SCALE * 5);

		attackBox.y = hitbox.y + (Game.SCALE * 10);
	}

	private void updateHealthBar() {
		healthWidth = (int) ((currentHealth / (float) maxHealth) * healthBarWidth);
	}
	public void render(Graphics g, int xLvlOffset) {
		if (hitbox.x < xLvlOffset) {
			hitbox.x = xLvlOffset;
		}
		g.drawImage(animations[playerAction][aniIndex], (int) (hitbox.x - xDrawOffset) - xLvlOffset + flipX, (int) (hitbox.y - yDrawOffset), width * flipW, height, null);
//		drawHitbox(g, xLvlOffset);
//		drawAttackBox(g, xLvlOffset);
		drawUI(g);

	}
	private void drawAttackBox(Graphics g, int lvlOffsetX) {
		g.setColor(Color.red);
		g.drawRect((int) attackBox.x - lvlOffsetX, (int) attackBox.y, (int) attackBox.width, (int) attackBox.height);

	}

	private void drawUI(Graphics g) {
		g.drawImage(statusBarImg, statusBarX, statusBarY, statusBarWidth, statusBarHeight, null);
		g.setColor(Color.red);
		g.fillRect(healthBarXStart + statusBarX, healthBarYStart + statusBarY, healthWidth, healthBarHeight);
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

		if (left) {
			xSpeed -= playerSpeed;
			flipX = width;
			flipW = -1;
		}
		if (right){
			xSpeed += playerSpeed;
			flipX = 0;
			flipW = 1;
		}
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

//			if (left)
//				playerAction = RUNNING2;
//			else if (right)

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
			resetAniTick();
		}		
	}
	private void resetAniTick() {
		aniTick = 0;
		aniIndex = 0;
		aniSpeed = 30;
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
				attackChecked = false;

			}
    	}
	}
	public void changeHealth(int value) {
		currentHealth += value;

		if (currentHealth <= 0)
			currentHealth = 0;
		else if (currentHealth >= maxHealth)
			currentHealth = maxHealth;
	}
    private void loadAnimation() {
			BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
			animations = new BufferedImage[6][6];
			for (int j =0; j < animations.length;j++) {
				for (int i = 0; i < animations[j].length; i++) {
				 animations[j][i] = img.getSubimage(100*i, j*100,96,100);
				}
			}
		statusBarImg = LoadSave.GetSpriteAtlas(LoadSave.STATUS_BAR);

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

	public void resetAll() {
		resetDirBooleans();
		inAir = false;
		attacking1 = false;
		attacking2 = false;
		moving = false;
		playerAction = IDLE;
		currentHealth = maxHealth;

		hitbox.x = x;
		hitbox.y = y;

		if (!IsEntityOnFloor(hitbox, lvlData))
			inAir = true;
	}
}
