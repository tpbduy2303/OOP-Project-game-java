package entities;

import Main.*;

import java.awt.*;

import static utilz.Constants.EnemyConstants.*;

public class Fire_dude extends Enemy{

    public Fire_dude(float x, float y) {
        super(x, y, FIRE_DUDE_WIDTH,FIRE_DUDE_HEIGHT , FIRE_DUDE);
        //iniHitbox(x,y,(int)(22*Game.SCALE),(int)(19*Game.SCALE));

    }

}
