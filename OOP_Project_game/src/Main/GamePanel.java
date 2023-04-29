package Main;

import javax.swing.JPanel;

import Inputs.KeyboardInputs;
import Inputs.MouseInputs;

import java.awt.Graphics;
//import java.awt.event.KeyEvent;
//import java.awt.event.KeyListener;
public class GamePanel extends JPanel{
    private MouseInputs mouseInputs;
    private int xDel = 100, yDel = 100;
    private int frames;
    private long lastCheck = 0;
    private int xDir = 1, yDir = 1;
    public GamePanel(){
        mouseInputs = new MouseInputs(this);
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }
    public void changexDel(int value){
        this.xDel += value; 
        
    }
    public void changeyDel(int value){
        this.yDel += value; 
        
    }
    public void setRectPos(int x,int y){
        this.xDel = x;
        this.yDel = y;
        
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        updateRectangel();
        g.fillRect(xDel, yDel, 200, 50);
    }
    private void updateRectangel(){
        xDel+=xDir;
        yDel+=yDir;
    }
}