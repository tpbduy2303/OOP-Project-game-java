package Main;

import javax.swing.JPanel;
import inputs.KeyboardInputs;
import inputs.MouseInputs;
import Main.Game;

import java.awt.Dimension;
import java.awt.Graphics;
public class GamePanel extends JPanel{
    private MouseInputs mouseInputs;
    private Game game;
    
    public GamePanel(Game game){
    	this.game = game;
    	setPanelSize();
        mouseInputs = new MouseInputs(this);
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }
	private void setPanelSize() {
		Dimension size = new Dimension(Game.GAME_WIDTH, Game.GAME_HEIGHT);
		setPreferredSize(size);
	}
	public void updateGame() {
		
	}
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        game.render(g);


    }
	public Game getGame() {
		return game;
	}

}