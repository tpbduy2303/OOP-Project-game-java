package entities;

import Main.*;
import Main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;


import static utilz.Constants.EnemyConstants.*;

import static utilz.Constants.Directions.*;

public class Fire_dude extends Enemy{
    private Rectangle2D.Float attackBox;
    private int attackBoxOffsetX;
    private int healthBarWidth = (int) (50 * Game.SCALE);
    private int healthBarHeight = (int) (4 * Game.SCALE);
    private int healthWidth = healthBarWidth;
    public Fire_dude(float x, float y) {
        super(x, y, FIRE_DUDE_WIDTH,FIRE_DUDE_HEIGHT , FIRE_DUDE);
        iniHitbox( x,  y,(int) 45* Game.SCALE,(int) 32*Game.SCALE);
        initAttackBox();

    }
    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x, y, (int) (82 * Game.SCALE), (int) (25 * Game.SCALE));
        attackBoxOffsetX = (int) (Game.SCALE * 20);
    }
    public void update(int[][] lvlData, Player player){
        updateAnimationTick();
        updateMove(lvlData, player);
        updateAttackBox();
        updateHPBar();
    }
    private void updateHPBar() {
        healthWidth = (int) ((currentHealth / (float) maxHealth) * healthBarWidth);
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
                    if (canSeePlayer(lvlData, player)) {
                        turnTowardsPlayer(player);
                        if (isPlayerCloseForAttack(player))
                            newState(ATTACK);
                    }
                    move(lvlData);
                    break;
                case ATTACK:
                    if (aniIndex == 0)
                        attackChecked = false;

                    // Changed the name for checkEnemyHit to checkPlayerHit
                    if (aniIndex == 3 && !attackChecked)
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
    public void drawHP(Graphics g, int xLvlOffset) {
        g.setColor(Color.red);
        g.fillRect((int) hitbox.x- xLvlOffset, (int) hitbox.y, healthWidth, healthBarHeight);
    }
}
