package entities;

import Main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static utilz.Constants.Directions.RIGHT;
import static utilz.Constants.EnemyConstants.*;

public class Ice_dude extends Enemy {
    private Rectangle2D.Float attackBox;
    private int attackBoxOffsetX;
    public Ice_dude(float x, float y) {
        super(x, y, ICE_DUDE_WIDTH, ICE_DUDE_HEIGHT, ICE_DUDE);
        iniHitbox(x, y, (int) 30 * Game.SCALE, (int) 32 * Game.SCALE);
        initAttackBox();

    }
    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x, y, (int) (40 * Game.SCALE), (int) (32 * Game.SCALE));
        attackBoxOffsetX = (int) (Game.SCALE * 5);
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
    // enemy move logic
    private void updateMove(int[][] lvlData, Player player){
        if (firstUpdate){
            firstUpdate(lvlData);
        }
        if (inAir){
            undateInAir(lvlData);
        }
        else {
            switch(enemyState){
                // case IDLE:
                //    enemyState = RUNNING
                case IDLE: //RUNNING
                    if (canSeePlayer(lvlData, player))
                        turnTowardsPlayer(player);
                    if (isPlayerCloseForAttack(player))
                        newState(ATTACK);
                    move(lvlData);
                    break;
                case ATTACK:
                    if (aniIndex == 0)
                        attackChecked = false;

                    // Changed the name for checkEnemyHit to checkPlayerHit
                    if (aniIndex == 2 || !attackChecked)
                        checkPlayerHit(attackBox, player);

                    break;


            }
        }
    }
    public void drawAttackBox(Graphics g, int xLvlOffset) {
        g.setColor(Color.red);
        g.drawRect((int) (attackBox.x - xLvlOffset), (int) attackBox.y, (int) attackBox.width, (int) attackBox.height);
    }
    public int flipX() {
        if (walkDir == RIGHT)
            return width;
        else
            return 0;
    }

    public int flipW() {
        if (walkDir == RIGHT)
            return -1;
        else
            return 1;

    }
}