package entities;

imort static utilz.HelpMethods.CanMoveHere;
import static utilz.Constants.Directions.DOWN;
import static utilz.Constants.Directions.LEFT;
import static utilz.Constants.Directions.RIGHT;
import static utilz.Constants.Directions.UP;
import static utilz.Constants.PlayerConstants.ATTACK_1;
import static utilz.Constants.PlayerConstants.GetSpriteAmount;
import static utilz.Constants.PlayerConstants.IDLE;
import static utilz.Constants.PlayerConstants.RUNNING;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import Main.Game;
import utilz.LoadSave;

public class Player extends Entity{
	
    private BufferedImage[][] animations;
    private int aniTick, aniIndex, aniSpeed = 30;
    private int playerAction = IDLE;
	private boolean moving = false, attacking = false;
	private boolean left, up, right, down, jump;
	private float playerSpeed = 2.0f;

	public Player(float x, float y, int width, int height) {
		super(x, y, width, height);
		loadAnimation();
	}
	
	public void update() {
	    updatePos();
		updateAnimationTick();
	    setAnimation();
	}
	public void render(Graphics g) {
		g.drawImage(animations[playerAction][aniIndex], (int) (hitbox.x - xDrawOffset), (int) (hitbox.y - yDrawOffset), width, height, null);
	}
	
    private void updatePos() {
		moving = false;
		
	    	if(!left && !right && !up &&!down)
			return;
	    
	    	float xSpeed = 0, yspeed = 0;
			
		if (left && !right) 
			xSpeed = - playerSpeed;

		else if (right && !left) 
			xSpeed = playerSpeed;

		if (up && !down) 
			yspeed = -playerSpeed;
		
		else if (down && !up) 
			yspeed = -playerSpeed;
		if(CanMoveHere(x+xSpeed, y+ySpeed, width, height, lvlData)){
			this.x +=xSpeed;
			this.y +=ySpeed;
			moving = true;
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
	
    private void loadAnimation() {
			BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
			animations = new BufferedImage[4][6];
			for (int j =0; j < animations.length;j++) {
				for (int i = 0; i < animations[j].length; i++) {
				 animations[j][i] = img.getSubimage(100*i, j*100,90,100);
				}
			}
    }
	public void loadLvlData(int[][] lvlData) {
		this.lvlData = 	lvlData;
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
    
    

}
