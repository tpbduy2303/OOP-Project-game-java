package Inputs;
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

    }
    @Override
    public void keyPressed(KeyEvent e){
        switch(e.getKeyCode()){
            case KeyEvent.VK_A:
                gamePanel.changexDel(-5);
                break;
            case KeyEvent.VK_S:
                gamePanel.changeyDel(5);
                break;
            case KeyEvent.VK_D:
                gamePanel.changexDel(5);
                break;
            case KeyEvent.VK_W:
                gamePanel.changeyDel(-5);
                break;
        }
    }
}
