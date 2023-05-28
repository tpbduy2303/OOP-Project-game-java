package entities;

import Main.Game;
import utilz.LoadSave;
import static utilz.Constants.EnemyConstants.*;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class EnemyManager {
    private Game game;
    private BufferedImage[][] firedudeArr;
    private BufferedImage[][] icedudeArr;

    private ArrayList<Fire_dude> Fire_Dudes = new ArrayList<>();
    private ArrayList<Ice_dude> Ice_Dudes = new ArrayList<>();

    public EnemyManager(Game game){
        this.game = game;
        loadEnemyImgs();
        addEnermies();
    }

    private void addEnermies() {
        Fire_Dudes = LoadSave.Get_Fire_dude();
        Ice_Dudes = LoadSave.Get_Ice_dude();
    }

    public void udpate(int[][] lvlData, Player player){
        for (Fire_dude f : Fire_Dudes){
            if (f.isActive())
                    f.update(lvlData, player);
        }
        for (Ice_dude i : Ice_Dudes){
            if (i.isActive())
                i.update(lvlData, player);
        }


    }
    public void draw(Graphics g, int xLvlOffset){
        drawFire(g,xLvlOffset);
        drawIce(g,xLvlOffset);
    }
    private void drawFire(Graphics g, int xLvlOffset){
        for (Fire_dude c : Fire_Dudes) {
            //Enemy size
            g.drawImage(firedudeArr[c.getEnemyState()][c.getAniIndex()],(int)c.getHitbox().x - xLvlOffset- FIRE_DUDE_DRAWOFFSET_X+ 2*c.flipX(),(int)c.getHitbox().y -FIRE_DUDE_DRAWOFFSET_Y,FIRE_DUDE_WIDTH*2* c.flipW(),FIRE_DUDE_HEIGHT*2,null);
            c.drawHitbox(g,xLvlOffset);
            c.drawAttackBox(g,xLvlOffset);
        }
    }
    private void drawIce(Graphics g, int xLvlOffset){
        for (Ice_dude c : Ice_Dudes) {
            //Enemy size
            g.drawImage(icedudeArr[c.getEnemyState()][c.getAniIndex()],(int)c.getHitbox().x - xLvlOffset- ICE_DUDE_DRAWOFFSET_X+ 2*c.flipX(),(int)c.getHitbox().y - ICE_DUDE_DRAWOFFSET_Y,ICE_DUDE_WIDTH*2* c.flipW(),ICE_DUDE_HEIGHT*2,null);
            c.drawHitbox(g,xLvlOffset);
            c.drawAttackBox(g,xLvlOffset);

        }
    }
    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        checkFireHit(attackBox);
        checkIceHit(attackBox);
    }
    private void checkFireHit(Rectangle2D.Float attackBox){
        for (Fire_dude f: Fire_Dudes)
            if (f.isActive())
                if (attackBox.intersects(f.getHitbox())) {
                    f.hurt(10);
                    return;
                }
    }
    private void checkIceHit(Rectangle2D.Float attackBox){
        for (Ice_dude c : Ice_Dudes)
            if (c.isActive())
                if (attackBox.intersects(c.getHitbox())) {
                    c.hurt(15);
                    return;
                }
    }

    private void loadEnemyImgs() {
        firedudeArr = new BufferedImage[2][4];
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.FIRE_DUDE);
        for (int j = 0; j < firedudeArr.length; j++){
            for (int i = 0; i < firedudeArr[j].length; i++ ){
            firedudeArr[j][i] = temp.getSubimage(i*FIRE_DUDE_WIDTH_DEFAULT,j*FIRE_DUDE_HEIGHT_DEFAULT  , FIRE_DUDE_WIDTH_DEFAULT, FIRE_DUDE_HEIGHT_DEFAULT);
        }}
        icedudeArr = new BufferedImage[2][6];
        BufferedImage temp1 = LoadSave.GetSpriteAtlas(LoadSave.ICE_DUDE);
        for (int j = 0; j < icedudeArr.length; j++){
            for (int i = 0; i < icedudeArr[j].length; i++ ){
            icedudeArr[j][i] = temp1.getSubimage(i*ICE_DUDE_WIDTH_DEFAULT,j*ICE_DUDE_HEIGHT_DEFAULT  , ICE_DUDE_WIDTH_DEFAULT, ICE_DUDE_HEIGHT_DEFAULT);
        }}
    }
    public void resetAllEnemies() {
        for (Fire_dude c : Fire_Dudes)
            c.resetEnemy();
        for (Ice_dude i : Ice_Dudes)
            i.resetEnemy();
    }
}
