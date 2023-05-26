package entities;

import Main.Game;
import utilz.LoadSave;
import static utilz.Constants.EnemyConstants.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class EnemyManager {
    private Game game;
    private BufferedImage[] firedudeArr;

    private ArrayList<Fire_dude> Fire_Dudes = new ArrayList<>();
    public EnemyManager(Game game){
        this.game = game;
        loadEnemyImgs();
        addEnermies();
    }

    private void addEnermies() {
        Fire_Dudes = LoadSave.Get_Fire_dude();
    }

    public void udpate(int[][] lvlData){
        for (Fire_dude c : Fire_Dudes){
            c.update(lvlData);
        }
    }
    public void draw(Graphics g){
        drawFire(g);

    }
    private void drawFire(Graphics g){
        for (Fire_dude c : Fire_Dudes) {
            //Enemy size
            g.drawImage(firedudeArr[c.getAniIndex()],(int)c.getHitbox().x,(int)c.getHitbox().y,FIRE_DUDE_WIDTH*2,FIRE_DUDE_HEIGHT*2,null);
        }
    }

    private void loadEnemyImgs() {
        firedudeArr = new BufferedImage[8];
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.FIRE_DUDE);
        for (int i = 0; i < firedudeArr.length; i++){
            //firedudeArr[i] = temp.getSubimage(i*FIRE_DUDE_WIDTH_DEFAULT, FIRE_DUDE_HEIGHT_DEFAULT , FIRE_DUDE_WIDTH_DEFAULT, FIRE_DUDE_HEIGHT_DEFAULT);
            firedudeArr[i] = temp.getSubimage(i*FIRE_DUDE_WIDTH_DEFAULT,0*FIRE_DUDE_HEIGHT_DEFAULT  , FIRE_DUDE_WIDTH_DEFAULT, FIRE_DUDE_HEIGHT_DEFAULT);
        }


    }


}
