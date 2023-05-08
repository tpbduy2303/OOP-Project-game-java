package Main;

import java.awt.Graphics;

import entities.EnemyManager;
import entities.Fire_dude;
import entities.Player;
import levels.LevelManager;

public class Game implements Runnable {
    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;
    private final int FPS_SET = 120;
    private final int UPS_SET = 200;
    private Player player;
	private LevelManager levelManager;

	public final static int TILES_DEFAULT_SIZE = 32;
	public final static float SCALE = 1.5f;
	public final static int TILES_IN_WIDTH = 26;
	public final static int TILES_IN_HEIGHT = 14;
	public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
	public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
	public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;
    
    public Game(){
    	initClasses();
        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocus();
        startGameLoop();
    }
    private void startGameLoop(){
        gameThread = new Thread(this);
        gameThread.start();
    }
	private void update() {
		player.update();
		levelManager.update();
	}
	public void render(Graphics g) {
		levelManager.draw(g);
		player.render(g);
	}
	private void initClasses() {
        levelManager = new LevelManager(this);
		player = new Player(200, 200, 100, 100);
        player.loadvlData(levelManager.getCurrentLevel().getLvlData());

	}
    @Override
    public void run(){
        double timePerFrame = 1000000000.0/FPS_SET;
        double timePerUpdate = 1000000000.0/UPS_SET;
        long last = System.nanoTime();
        long now = System.nanoTime();
        
        int frames = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();
        double deltaF = 0, deltaU = 0;
        
        while(true){
        	
            now = System.nanoTime();
            
            deltaF += (now - last) / timePerFrame;
            deltaU += (now - last) / timePerUpdate;
            last = now;
            
            if(deltaF >= 1){
                gamePanel.repaint();
                deltaF--;
                frames++;
            }
            if(deltaU >= 1){
            	update();
            	deltaU--;
                updates++;
            }
            if(System.currentTimeMillis()-lastCheck >= 1000){
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: "+frames+"|| UPS: "+updates);
                frames = 0;
                updates = 0;
            }
        }
    }
	public void windowFocusLost() {
		player.resetDirBooleans();
	}
	public Player getPlayer() {
		return player;
	}
}