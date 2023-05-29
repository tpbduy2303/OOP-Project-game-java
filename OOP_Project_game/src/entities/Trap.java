package entities;

import Main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static utilz.Constants.EnemyConstants.*;

public class Trap extends Enemy{
    private Rectangle2D.Float attackBox;
    private int attackBoxOffsetX;

    public Trap(float x, float y) {
        super(x, y, (int) (28* Game.SCALE), (int) (28* Game.SCALE), AKAZA);
        iniHitbox( x,  y,(int) 28* Game.SCALE,(int) 28*Game.SCALE);
        initAttackBox();
    }
    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x, y, (int) (28 * Game.SCALE), (int) (28 * Game.SCALE));
        attackBoxOffsetX = (int) (0*Game.SCALE);
    }
    public void update(int[][] lvlData, Player player){
        updateAnimationTick();
        updateMove(lvlData, player);
        updateAttackBox();
    }
    private void updateAttackBox() {
        attackBox.x = hitbox.x - attackBoxOffsetX;
        attackBox.y = hitbox.y;

    }
    private void updateMove(int[][] lvlData, Player player){
        if (firstUpdate){
            firstUpdate(lvlData);
        }
        if (inAir){
            undateInAir(lvlData);
        }
        else {
//            switch(enemyState){
//                // case IDLE:
//                //    enemyState = RUNNING
//                case IDLE: //RUNNING
//                    if (canSeePlayer(lvlData, player)) {
//                        turnTowardsPlayer(player);
//                        if (isPlayerCloseForAttack(player))
//                            newState(ATTACK);
//                    }
//                    move(lvlData);
//                    break;
//                case ATTACK:
//                    if (aniIndex == 0)
//                        attackChecked = false;
//
//                    // Changed the name for checkEnemyHit to checkPlayerHit
//                    if (aniIndex == 3 && !attackChecked)
            checkPlayerHit(attackBox, player);

//                    break;
        }
    }

    public void drawAttackBox(Graphics g, int xLvlOffset) {
        g.setColor(Color.red);
        g.drawRect((int) (attackBox.x - xLvlOffset), (int) attackBox.y, (int) attackBox.width, (int) attackBox.height);
    }

}

