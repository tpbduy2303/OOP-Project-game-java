package levels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Main.Game;
import utilz.LoadSave;

public class LevelManager {

	private Game game;
	private BufferedImage[] levelSprite;
	private Level levelOne;
	
	final private static int Changetime = 200*3;
	private static long timer1 = 0, timer2 = 0;
	private static boolean stable = true, oldstable;

	public LevelManager(Game game) {
		this.game = game;
		importOutsideSprites();
		levelOne = new Level(LoadSave.GetLevelData());
	}

	private void importOutsideSprites() {
		BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.LEVEL);
		levelSprite = new BufferedImage[300];
		
		for (int j = 4; j < 16; j++)
			for (int i = 0; i < 20; i++) {
				int index = (j - 4) * 20 + i;
				levelSprite[index] = img.getSubimage(i * 8, j * 8, 8, 8);
			}
	}

	public void draw(Graphics g, int xLvlOffset) {

		//In ra tất cả các ô trong cái file ô gạch
//		for (int j = 4; j < 16; j++)
//			for (int i = 0; i < 20; i++) {
//				g.drawImage(levelSprite[20*(j-4)+i], (32 + 10) * i, (32 + 10) * (j-4), 32, 32, null);
//			}

		for (int j = 0; j < Game.TILES_IN_HEIGHT; j++)
			for (int i = 0; i < levelOne.getLevelData()[0].length; i++) {
				int index = levelOne.getSpriteIndex(i, j);
				g.drawImage(levelSprite[index], Game.TILES_SIZE * i - xLvlOffset, Game.TILES_SIZE * j, Game.TILES_SIZE, Game.TILES_SIZE, null);
			}
	}

	public void update() {
		changeBlock();
        if (!stable) {
        	
        	if (oldstable != stable)
        		timer2 = 0;
        	changeStableBlock();
        }
        else {
        	changeUnstableBlock();
        }
		oldstable = stable;
	}
	
	private void changeUnstableBlock() {
        if(timer2 == Changetime){
        	timer2 = 0;
    		for (int j = 0; j < Game.TILES_IN_HEIGHT; j++)
    			for (int i = 0; i < levelOne.getLevelData()[0].length; i++) {
    				int index = levelOne.getSpriteIndex(i, j);
    				changeLvlIndex(175, 176, i, j, index);
    			}
        }
        timer2++;
		
	}
	
	private void changeStableBlock() {
        if(timer2 == Changetime-200d){
        	timer2 = 0;
        	stable = true;
    		for (int j = 0; j < Game.TILES_IN_HEIGHT; j++)
    			for (int i = 0; i < levelOne.getLevelData()[0].length; i++) {
    				int index = levelOne.getSpriteIndex(i, j);
    				changeLvlIndex(176, 175, i, j, index);
    			}
        }
        timer2++;
		
	}

	private void changeBlock() {
        if(timer1 == Changetime){
        	timer1 = 0;
    		for (int j = 0; j < Game.TILES_IN_HEIGHT; j++)
    			for (int i = 0; i < levelOne.getLevelData()[0].length; i++) {
    				int index = levelOne.getSpriteIndex(i, j);
    				changeLvlIndex(96, 76, i, j, index);
    				changeLvlIndex(97, 77, i, j, index);
    				changeLvlIndex(36, 16, i, j, index);
    				changeLvlIndex(37, 17, i, j, index);
    				changeLvlIndex(74, 96, i, j, index);
    				changeLvlIndex(75, 97, i, j, index);
    				changeLvlIndex(14, 36, i, j, index);
    				changeLvlIndex(15, 37, i, j, index);
    			}
        }
        else if(timer1 == Changetime-200) {
    		for (int j = 0; j < Game.TILES_IN_HEIGHT; j++)
    			for (int i = 0; i < levelOne.getLevelData()[0].length; i++) {
    				int index = levelOne.getSpriteIndex(i, j);
    				changeLvlIndex(76, 74, i, j, index);
    				changeLvlIndex(77, 75, i, j, index);
    				changeLvlIndex(16, 14, i, j, index);
    				changeLvlIndex(17, 15, i, j, index);
    			}
    		timer1++;
        }
        else {
        	timer1++;
        }
	}
	
	public void setStable(boolean stable) {
		LevelManager.stable = stable;
	}
	
	public void changeLvlIndex(int a, int b, int i, int j, int index){
		if (index == a) 
			levelOne.setSpriteIndex(b, i, j);
	}

	public Level getCurrentLevel(){
		return levelOne;
	}

}
