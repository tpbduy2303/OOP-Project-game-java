package entities;

import Main.Game;
import gamestates.Playing;
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

    private BufferedImage akazaArr;
    private BufferedImage trapArr;

    private Playing playing;

    private ArrayList<Fire_dude> Fire_Dudes = new ArrayList<>();
    private ArrayList<Ice_dude> Ice_Dudes = new ArrayList<>();
    private ArrayList<Akaza> akazas= new ArrayList<>();
    private ArrayList<Trap> traps= new ArrayList<>();


    public EnemyManager(Game game,Playing playing){
        this.game = game;
        this.playing = playing;
        loadEnemyImgs();
        addEnermies();
    }

    private void addEnermies() {
        Fire_Dudes = LoadSave.Get_Fire_dude();
        Ice_Dudes = LoadSave.Get_Ice_dude();
        akazas = LoadSave.Get_Akaza();
        traps = LoadSave.Get_Trap();
    }

    public void udpate(int[][] lvlData, Player player){
        for (Fire_dude f : Fire_Dudes){
            if (f.isActive()) {
                f.update(lvlData, player);
            }
        }
        for (Ice_dude i : Ice_Dudes){
            if (i.isActive())
                i.update(lvlData, player);
        }
        for (Akaza a : akazas) {
            if (a.isActive())
                a.update(lvlData, player);
        }
        for (Trap t : traps) {
            if (t.isActive())
                t.update(lvlData, player);
        }


    }
    public void draw(Graphics g, int xLvlOffset){
        drawFire(g,xLvlOffset);
        drawIce(g,xLvlOffset);
        drawAkaza(g,xLvlOffset);
        drawTrap(g,xLvlOffset);
    }
    private void drawTrap(Graphics g, int xLvlOffset) {
        for (Trap a : traps) {
            g.drawImage(trapArr,(int)a.getHitbox().x - xLvlOffset ,(int)a.getHitbox().y ,28*2,28*2,null);
//            a.drawHitbox(g,xLvlOffset);
//            a.drawAttackBox(g,xLvlOffset);
        }
    }
    private void drawAkaza(Graphics g, int xLvlOffset) {
        for (Akaza a : akazas) {
            g.drawImage(akazaArr,(int)a.getHitbox().x - xLvlOffset + 2*a.flipX(),(int)a.getHitbox().y ,AKAZA_WIDTH*2*a.flipW(),AKAZA_HEIGHT*2,null);
//            a.drawHitbox(g,xLvlOffset);
//            a.drawAttackBox(g,xLvlOffset);
            a.drawHP(g,xLvlOffset);
        }
    }

    private void drawFire(Graphics g, int xLvlOffset){
        for (Fire_dude c : Fire_Dudes) {
            //Enemy size
            g.drawImage(firedudeArr[c.getEnemyState()][c.getAniIndex()],(int)c.getHitbox().x - xLvlOffset- FIRE_DUDE_DRAWOFFSET_X+ 2*c.flipX(),(int)c.getHitbox().y -FIRE_DUDE_DRAWOFFSET_Y,FIRE_DUDE_WIDTH*2* c.flipW(),FIRE_DUDE_HEIGHT*2,null);
//            c.drawHitbox(g,xLvlOffset);
//            c.drawAttackBox(g,xLvlOffset);
            c.drawHP(g,xLvlOffset);

        }
    }


        private void drawIce(Graphics g, int xLvlOffset){
        for (Ice_dude c : Ice_Dudes) {
            //Enemy size
            g.drawImage(icedudeArr[c.getEnemyState()][c.getAniIndex()],(int)c.getHitbox().x - xLvlOffset- ICE_DUDE_DRAWOFFSET_X+ 2*c.flipX(),(int)c.getHitbox().y - ICE_DUDE_DRAWOFFSET_Y,ICE_DUDE_WIDTH*2* c.flipW(),ICE_DUDE_HEIGHT*2,null);
//            c.drawHitbox(g,xLvlOffset);
//            c.drawAttackBox(g,xLvlOffset);
            c.drawHP(g,xLvlOffset);


        }
    }
    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        checkFireHit(attackBox);
        checkIceHit(attackBox);
        checkAkazaHit(attackBox);
        checkTrapHit(attackBox);
    }

    private void checkTrapHit(Rectangle2D.Float attackBox) {
        for (Trap a : traps) {if (a.isActive())
            if (attackBox.intersects(a.getHitbox())) {
                a.hurt(0);
                return;
            }}
    }
        //playerdame
    private void checkAkazaHit(Rectangle2D.Float attackBox) {
        for (Akaza a : akazas) {
            if (a.isActive()) {
                if (attackBox.intersects(a.getHitbox())) {
                    a.hurt(25);
                    return;
                }
            }else playing.setGameCompleted(true);

        }
    }

    private void checkFireHit(Rectangle2D.Float attackBox){
        for (Fire_dude f: Fire_Dudes)
            if (f.isActive())
                if (attackBox.intersects(f.getHitbox())) {
                    f.hurt(25);
                    return;
                }
    }
    private void checkIceHit(Rectangle2D.Float attackBox){
        for (Ice_dude c : Ice_Dudes)
            if (c.isActive())
                if (attackBox.intersects(c.getHitbox())) {
                    c.hurt(25);
                    return;
                }
    }

    private void loadEnemyImgs() {
        akazaArr = LoadSave.GetSpriteAtlas(LoadSave.AKAZA);
        trapArr = LoadSave.GetSpriteAtlas(LoadSave.TRAP);
        firedudeArr = new BufferedImage[3][4];
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.FIRE_DUDE);
        for (int j = 0; j < firedudeArr.length; j++){
            for (int i = 0; i < firedudeArr[j].length; i++ ){
            firedudeArr[j][i] = temp.getSubimage(i*FIRE_DUDE_WIDTH_DEFAULT,j*FIRE_DUDE_HEIGHT_DEFAULT  , FIRE_DUDE_WIDTH_DEFAULT, FIRE_DUDE_HEIGHT_DEFAULT);
        }}
        icedudeArr = new BufferedImage[3][6];
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
        for (Akaza a : akazas) {
            a.resetEnemy();
        for (Trap t : traps){
            t.resetEnemy();
        }

    }
}   }
