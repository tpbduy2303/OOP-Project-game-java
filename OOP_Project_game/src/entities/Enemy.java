package entities;


import Main.Game;
import utilz.Constants;

import static utilz.Constants.Directions.*;
import static utilz.Constants.EnemyConstants.*;
import static utilz.HelpMethods.*;

public abstract class Enemy extends Entity{
    private int aniIndex, enemyState = 0, enemyType;
    private int aniTick, aniSpeed = 25;
    private boolean firstUpdate = true;
    private boolean inAir = false;
    private float fallSpeed;
    private float gravity = 0.04f * Game.SCALE;
    private float walkSpeed = 1.0f * Game.SCALE;
    private int walkDir = LEFT;

    public Enemy(float x, float y, int width, int height, int enemyType) {
        super(x, y, width, height);
        this.enemyType = enemyType;
        iniHitbox( x,  y,45* Game.SCALE,29*Game.SCALE);

    }

    private void updateAnimationTick(){
        aniTick ++;
        if (aniTick >= aniSpeed){
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= Constants.EnemyConstants.GetSpriteAmount(enemyType,enemyState)){
                aniIndex = 0;
            }
        }
    }
    public void update(int[][] lvlData){
        updateAnimationTick();
        updateMove(lvlData);

    }

    private void updateMove(int[][] lvlData){
        if (firstUpdate){
            if(!IsEntityOnFloor(hitbox,lvlData)){
                inAir = true;
                firstUpdate = false;
            }}
        if (inAir){
            if(CanMoveHere(hitbox.x, hitbox.y, hitbox.width, hitbox.height, lvlData)){
                hitbox.y += fallSpeed;
                fallSpeed += gravity;
            }else {
                inAir = false;
                hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox,fallSpeed);
            }
        }else {
                switch(enemyState){
                   // case IDLE:
                   //    enemyState = RUNNING
                    case IDLE: //RUNNING
                        float xSpeed = 0.5f;

                        if(walkDir == LEFT)
                            xSpeed -= walkSpeed;
                        else
                            xSpeed += walkSpeed;


                        if (CanMoveHere(hitbox.x, hitbox.y, hitbox.width, hitbox.height, lvlData))
                            if (IsFloor(hitbox,xSpeed,lvlData)){
                                hitbox.x += xSpeed;
                                return;
                            }

                        changeWalkDir();
                        break;
                }
            }
        }


    private void changeWalkDir() {
        if(walkDir == LEFT){
            walkDir = RIGHT;
        }else
            walkDir = LEFT;
    }

    public int getAniIndex(){
        return aniIndex;
    }
    public int getEnemyState(){
        return enemyState;
    }
}
