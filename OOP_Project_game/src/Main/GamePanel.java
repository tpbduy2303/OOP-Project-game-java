package Main;

import static utilz.Constants.PlayerConstants.*;
import static utilz.Constants.Directions.*;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import inputs.KeyboardInputs;
import inputs.MouseInputs;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
public class GamePanel extends JPanel{
    private MouseInputs mouseInputs;
    private int xDel = 100, yDel = 100;
    private int frames;
    private long lastCheck = 0;
    private int xDir = 1, yDir = 1;
    private BufferedImage img, subImg;
    private BufferedImage[][] animations;
    private int aniTick, aniIndex, aniSpeed = 30;
    private int playerAction = RUNNING;
    private int playerDir = -1;
    private boolean moving = false;
    
    public GamePanel(){
    	setPanelSize();
    	importImg();
    	loadAnimation();
        mouseInputs = new MouseInputs(this);
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }
    private void loadAnimation() {
		animations = new BufferedImage[4][6];
		for (int j =0; j < animations.length;j++) {
		 for (int i = 0; i < animations[j].length; i++) {
			 animations[j][i] = img.getSubimage(100*i, j*100,100,100);
		 }
		}
    }
	private void importImg() {
    	InputStream is = getClass().getResourceAsStream("/player_spirite.png");
		try {
			img = ImageIO.read(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void setPanelSize() {
		Dimension size = new Dimension();
		setPreferredSize(size);
		setMaximumSize(size);
	}
	public void setDirection(int direction) {
		this.playerDir = direction;
		moving =true;
	}
	public void setMoving (boolean moving) {
		this.moving = moving;
	}
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        updateAnimationTick();
        setAnimation();
        updatePos();
        
       g.drawImage(animations[playerAction][aniIndex], (int) xDel, (int) yDel,200,200, null);
    }
    private void updatePos() {
    	if (moving) {
			switch (playerDir) {
			case LEFT:
				xDel -= 5;
				break;
			case UP:
				yDel -= 5;
				break;
			case RIGHT:
				xDel += 5;
				break;
			case DOWN:
				yDel += 5;
				break;
			}
		}
	}
	private void setAnimation() {
		if (moving) 
			playerAction = RUNNING;
			else 
				playerAction = IDLE;
		
		
	}
	private void updateAnimationTick() {
    	aniTick ++;
    	if ( aniTick >= aniSpeed ) {
    		aniTick = 0;
    		aniIndex++;
    		if ( aniIndex >=   GetSpriteAmount(playerAction)) {
    			aniIndex = 0;
    		}
    	}
	}
	private void updateRectangel(){
        xDel+=xDir;
        yDel+=yDir;
    }
}