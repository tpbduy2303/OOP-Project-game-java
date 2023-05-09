package levels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Main.Game;
import utilz.LoadSave;

public class LevelManager {

	private Game game;
	private BufferedImage[] levelSprite;
	private Level levelOne;

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

	public void draw(Graphics g) {

		//In ra tất cả các ô trong cái file ô gạch
//		for (int j = 4; j < 16; j++)
//			for (int i = 0; i < 20; i++) {
//				g.drawImage(levelSprite[20*(j-4)+i], (32 + 10) * i, (32 + 10) * (j-4), 32, 32, null);
//			}

		for (int j = 0; j < Game.TILES_IN_HEIGHT; j++)
			for (int i = 0; i < Game.TILES_IN_WIDTH; i++) {
				int index = levelOne.getSpriteIndex(i, j);
				g.drawImage(levelSprite[index], Game.TILES_SIZE * i, Game.TILES_SIZE * j, Game.TILES_SIZE, Game.TILES_SIZE, null);
			}
	}

	public void update() {

	}
	public Level getCurrentLevel(){
		return levelOne;
	}
}

