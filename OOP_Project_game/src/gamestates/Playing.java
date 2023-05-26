package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import entities.EnemyManager;
import entities.Player;
import levels.LevelManager;
import utilz.LoadSave;
import Main.Game;
public class Playing extends State implements Statemethods {
    private Player player;
    private LevelManager levelManager;
    private EnemyManager enemyManager;

	private int xLvlOffset;
	private	int newStageOffset = 0;
	private final int Stage1 = 186*Game.TILES_DEFAULT_SIZE*(int)Game.SCALE;
	private	final int Stage2 = 331*Game.TILES_DEFAULT_SIZE*(int)Game.SCALE;
	private	final int Stage3 = 528*Game.TILES_DEFAULT_SIZE*(int)Game.SCALE;
	private int leftBorder = (int) (0.35 * Game.GAME_WIDTH);
	private int rightBorder = (int) (0.45 * Game.GAME_WIDTH);
	private int lvlTilesWide = LoadSave.GetLevelData()[0].length;
	private int maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
	private int maxLvlOffsetX = maxTilesOffset * Game.TILES_SIZE;
	
	private BufferedImage backgroundgame1;
	private BufferedImage backgroundgame2;
	private BufferedImage backgroundgame3;
	private BufferedImage backgroundgame4;
    
    public Playing(Game game) {
        super(game);
        initClasses();
    }
    private void initClasses() {
        levelManager = new LevelManager(game);
        enemyManager = new EnemyManager(this.getGame());
        player = new Player(200, 200, 100, 100);
        player.loadLvlData(levelManager.getCurrentLevel().getLevelData());
        backgroundgame1 = LoadSave.GetSpriteAtlas(LoadSave.GAME_BACKGROUND_IMG1);
        backgroundgame2 = LoadSave.GetSpriteAtlas(LoadSave.GAME_BACKGROUND_IMG2);
        backgroundgame3 = LoadSave.GetSpriteAtlas(LoadSave.GAME_BACKGROUND_IMG3);
        backgroundgame4 = LoadSave.GetSpriteAtlas(LoadSave.GAME_BACKGROUND_IMG4);

    }
    @Override
    public void update() {
        levelManager.update();
        player.update();
      enemyManager.udpate(levelManager.getCurrentLevel().getLevelData());

		checkCloseToBorder();
		if (player.standingStable()) {
			levelManager.setStable(false);
		}
    }

	private void checkCloseToBorder() {
		int playerX = (int) player.getHitbox().x;
		int diff = playerX - xLvlOffset;

		if (diff > rightBorder)
			xLvlOffset += diff - rightBorder;
		else if (diff < leftBorder)
			xLvlOffset += diff - leftBorder;
		
		changeStage();
		
		if (xLvlOffset > maxLvlOffsetX)
			xLvlOffset = maxLvlOffsetX;
		else if (xLvlOffset < newStageOffset)
			xLvlOffset = newStageOffset;
		
	}
	private void changeStage() {
		
		if (xLvlOffset > Stage3)
			newStageOffset = Stage3;
		if (newStageOffset == Stage3) 
			return;
		
		if (xLvlOffset > Stage2) 
			newStageOffset = Stage2;
		if (newStageOffset == Stage2) 
			return;
		
		if (xLvlOffset > Stage1)
			newStageOffset = Stage1;
	
	}
	
	@Override
    public void draw(Graphics g) {

		if (xLvlOffset > Stage3 - 1)
			g.drawImage(backgroundgame4, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
		else if (xLvlOffset > Stage2 - 1)
			g.drawImage(backgroundgame3, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
		else if (xLvlOffset > Stage1 - 1)
			g.drawImage(backgroundgame2, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
		else
			g.drawImage(backgroundgame1, -350, 0, (int)(Game.GAME_WIDTH*1.5), (int)(Game.GAME_HEIGHT*1.5), null);
		
        levelManager.draw(g, xLvlOffset);
        player.render(g, xLvlOffset);

        enemyManager.draw(g);

    }

    @Override
    public void mouseClicked(MouseEvent e) {
//        if (e.getButton() == MouseEvent.BUTTON1)
//            player.setAttacking(true);
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                player.setUp(true);
                break;
            case KeyEvent.VK_A:
                player.setLeft(true);
                break;
            case KeyEvent.VK_S:
                player.setDown(true);
                break;
            case KeyEvent.VK_D:
                player.setRight(true);
                break;
            case KeyEvent.VK_J:
                player.setAttacking1(true);
                break;
            case KeyEvent.VK_K:
                player.setAttacking2(true);
                break;
            case KeyEvent.VK_SPACE:
                player.setJump(true);
                break;
//            case KeyEvent.VK_BACK_SPACE:
//                Gamestate.state = Gamestate.MENU;
//                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                player.setLeft(false);
                break;
            case KeyEvent.VK_D:
                player.setRight(false);
                break;
            case KeyEvent.VK_SPACE:
                player.setJump(false);
                break;


        }
    }
    public void windowFocusLost() {
        player.resetDirBooleans();
    }
    public Player getPlayer() {
        return player;
    }
}
