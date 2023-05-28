//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package utilz;

import java.awt.geom.Rectangle2D;

import Main.Game;

import java.awt.geom.Rectangle2D;

public class HelpMethods {
    public static boolean CanMoveHere(float x, float y, float width, float height, int[][] lvlData){
        if (!IsSolid(x,y,lvlData))
            if(!IsSolid(x+width,y+height,lvlData))
                if(!IsSolid(x+width,y,lvlData))
                    if (!IsSolid(x,y+height,lvlData))
                        if (!IsSolid(x,y+(height/2),lvlData))
                            if (!IsSolid(x+width,y+(height/2),lvlData))
                                return true;
        return false;
    }
    public static boolean ifBlockStable(float x, float y, float width, float height, int[][] lvlData) {
        if (checkChangeBlock(x-9,y+1,lvlData))
            return false;
        if (checkChangeBlock(x+width+20,y+height+1,lvlData))
            return false;
        if (checkChangeBlock(x+width+1,y+1,lvlData))
            return false;
        if (checkChangeBlock(x,y+height+1,lvlData))
            return false;
        if (checkChangeBlock(x-1,y+(height/2),lvlData))
            return false;
        if (checkChangeBlock(x+width+1,y+(height/2),lvlData))
            return false;
        return true;
    }
    private static boolean IsSolid(float x, float y,int[][] lvlData){
        int maxWidth = lvlData[0].length * Game.TILES_SIZE;
        if (x < 0 || x >= maxWidth) {
            return true;
        }
        if (y < 0 || y >= Game.GAME_HEIGHT){
            return true;
        }
        float xIndex = x / Game.TILES_SIZE;
        float yIndex = y / Game.TILES_SIZE;

        return IsTileSolid((int) xIndex, (int) yIndex, lvlData);
    }
    public static boolean IsTileSolid(int xTile, int yTile, int[][] lvlData) {
        int value = lvlData[yTile][xTile];

        if (value == 24 || value == 214 || value == 158 || value == 213 || value == 193 ||
                value == 194|| value == 137 || value == 138 || value == 157 || value == 148 ||
                value == 149|| value == 168 || value == 169 || value == 154 || value == 133 ||
                value == 134|| value == 153 || value == 96  || value == 97  || value == 36  ||
                value == 37 || value == 175)
            return false;
        return true;
    }
    private static boolean checkChangeBlock(float x, float y, int[][] lvlData) {
        int maxWidth = lvlData[0].length * Game.TILES_SIZE;
        if (x < 0 || x >= maxWidth) {
            return false;
        }
        if (y < 0 || y >= Game.GAME_HEIGHT){
            return false;
        }
        float xIndex = x / Game.TILES_SIZE;
        float yIndex = y / Game.TILES_SIZE;

        int value = lvlData[(int)yIndex][(int)xIndex];

        if (value == 176)
            return true;
        return false;
    }

    public static float GetEntityXPosNextToWall(Rectangle2D.Float hitbox, float xSpeed) {
        int currentLeftTile = (int) (hitbox.x / Game.TILES_SIZE);
        int currentRightTile = (int) ((hitbox.x + hitbox.width) / Game.TILES_SIZE);
        if (xSpeed > 0){
            //Right
            int tilesXPos = currentRightTile * Game.TILES_SIZE;
            int xOffset = (int)(Game.TILES_SIZE - hitbox.width);
            return tilesXPos + xOffset - 20;
        }else {
            //Left
            return currentLeftTile * Game.TILES_SIZE + 8;
        }
    }
    public static float GetEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, float airSpeed) {
        int currentTopTile = (int) (hitbox.y / Game.TILES_SIZE);
        int currentBottomTile = (int) ((hitbox.y + hitbox.height) / Game.TILES_SIZE);
        if (airSpeed > 0) {
            // Falling - touching floor
            int tileYPos = currentBottomTile * Game.TILES_SIZE;
            int yOffset = (int) (Game.TILES_SIZE - hitbox.height);
            return tileYPos + yOffset - 1;
        } else
            // Jumping
            return currentTopTile * Game.TILES_SIZE;
    }
    public static boolean IsEntityOnFloor(Rectangle2D.Float hitbox,int[][]lvlData){
        //Check the picxel below bettomleft and bottomright
        if (!IsSolid(hitbox.x, hitbox.y + hitbox.height + 1,lvlData))
            if(!IsSolid(hitbox.x + hitbox.width, hitbox.y+ hitbox.height + 1  ,lvlData))
                return false;
        return true;
    }
    public static boolean IsFloor(Rectangle2D.Float hitbox, float xSpeed, int[][] lvlData) {
        return IsSolid(hitbox.x + xSpeed, hitbox.y + hitbox.height + 1.0F, lvlData);
    }
    public static boolean IsAllTilesWalkable(int xStart, int xEnd, int y, int[][] lvlData) {
        for (int i = 0; i < xEnd - xStart; i++) {
            if (IsTileSolid(xStart + i, y, lvlData))
                return false;
            if (!IsTileSolid(xStart + i, y + 1, lvlData))
                return false;
        }

        return true;
    }

    public static boolean IsSightClear(int[][] lvlData, Rectangle2D.Float firstHitbox, Rectangle2D.Float secondHitbox, int yTile) {
        int firstXTile = (int) (firstHitbox.x / Game.TILES_SIZE);
        int secondXTile = (int) (secondHitbox.x / Game.TILES_SIZE);

        if (firstXTile > secondXTile)
            return IsAllTilesWalkable(secondXTile, firstXTile, yTile, lvlData);
        else
            return IsAllTilesWalkable(firstXTile, secondXTile, yTile, lvlData);

    }
}
