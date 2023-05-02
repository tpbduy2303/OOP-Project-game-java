package inputs;
import static utilz.Constants.Directions.*;
import java.awt.event.KeyListener;

import Main.GamePanel;

import java.awt.event.KeyEvent;
public class KeyboardInputs implements KeyListener {
    private GamePanel gamePanel;
    public KeyboardInputs(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }
    @Override
    public void keyTyped(KeyEvent e){

    }
    @Override
    public void keyReleased(KeyEvent e){
    	switch(e.getKeyCode()){
        case KeyEvent.VK_A:        
        case KeyEvent.VK_S:      
        case KeyEvent.VK_D: 
        case KeyEvent.VK_W:
        	gamePanel.setMoving(false);
        	break;
        case KeyEvent.VK_SPACE:
        	
        	break;
    }
}
    
    @Override
    public void keyPressed(KeyEvent e){
        switch(e.getKeyCode()){
            case KeyEvent.VK_A:
                gamePanel.setDirection(LEFT);
                break;
            case KeyEvent.VK_S:
                gamePanel.setDirection(DOWN);
                break;
            case KeyEvent.VK_D:
                gamePanel.setDirection(RIGHT);
                break;
            case KeyEvent.VK_W:
                gamePanel.setDirection(UP);
                break;
        }
    }
}
