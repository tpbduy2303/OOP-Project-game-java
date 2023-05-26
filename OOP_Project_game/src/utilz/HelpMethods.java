//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package utilz;

import java.awt.geom.Rectangle2D;

public class HelpMethods {
    public HelpMethods() {
    }

    public static boolean CanMoveHere(float x, float y, float width, float height, int[][] lvlData) {
        return !IsSolid(x, y, lvlData) && !IsSolid(x + width, y + height, lvlData) && !IsSolid(x + width, y, lvlData) && !IsSolid(x, y + height, lvlData) && !IsSolid(x, y + height / 2.0F, lvlData) && !IsSolid(x + width, y + height / 2.0F, lvlData);
    }

    public static boolean ifBlockStable(float x, float y, float width, float height, int[][] lvlData) {
        if (checkChangeBlock(x - 9.0F, y + 1.0F, lvlData)) {
            return false;
        } else if (checkChangeBlock(x + width + 20.0F, y + height + 1.0F, lvlData)) {
            return false;
        } else if (checkChangeBlock(x + width + 1.0F, y + 1.0F, lvlData)) {
            return false;
        } else if (checkChangeBlock(x, y + height + 1.0F, lvlData)) {
            return false;
        } else if (checkChangeBlock(x - 1.0F, y + height / 2.0F, lvlData)) {
            return false;
        } else {
            return !checkChangeBlock(x + width + 1.0F, y + height / 2.0F, lvlData);
        }
    }

    private static boolean IsSolid(float x, float y, int[][] lvlData) {
        int maxWidth = lvlData[0].length * 56;
        if (!(x < 0.0F) && !(x >= (float)maxWidth)) {
            if (!(y < 0.0F) && !(y >= 672.0F)) {
                float xIndex = x / 56.0F;
                float yIndex = y / 56.0F;
                int value = lvlData[(int)yIndex][(int)xIndex];
                return value != 24 && value != 214 && value != 158 && value != 213 && value != 193 && value != 194 && value != 137 && value != 138 && value != 157 && value != 148 && value != 149 && value != 168 && value != 169 && value != 154 && value != 133 && value != 134 && value != 153 && value != 96 && value != 97 && value != 36 && value != 37 && value != 175;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    private static boolean checkChangeBlock(float x, float y, int[][] lvlData) {
        int maxWidth = lvlData[0].length * 56;
        if (!(x < 0.0F) && !(x >= (float)maxWidth)) {
            if (!(y < 0.0F) && !(y >= 672.0F)) {
                float xIndex = x / 56.0F;
                float yIndex = y / 56.0F;
                int value = lvlData[(int)yIndex][(int)xIndex];
                return value == 176;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static float GetEntityXPosNextToWall(Rectangle2D.Float hitbox, float xSpeed) {
        int currentLeftTile = (int)(hitbox.x / 56.0F);
        int currentRightTile = (int)((hitbox.x + hitbox.width) / 56.0F);
        if (xSpeed > 0.0F) {
            int tilesXPos = currentRightTile * 56;
            int xOffset = (int)(56.0F - hitbox.width);
            return (float)(tilesXPos + xOffset - 20);
        } else {
            return (float)(currentLeftTile * 56 + 8);
        }
    }

    public static float GetEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, float airSpeed) {
        int currentTopTile = (int)(hitbox.y / 56.0F);
        int currentBottomTile = (int)((hitbox.y + hitbox.height) / 56.0F);
        if (airSpeed > 0.0F) {
            int tileYPos = currentBottomTile * 56;
            int yOffset = (int)(56.0F - hitbox.height);
            return (float)(tileYPos + yOffset - 1);
        } else {
            return (float)(currentTopTile * 56);
        }
    }

    public static boolean IsEntityOnFloor(Rectangle2D.Float hitbox, int[][] lvlData) {
        return IsSolid(hitbox.x, hitbox.y + hitbox.height + 1.0F, lvlData) || IsSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1.0F, lvlData);
    }

    public static boolean IsFloor(Rectangle2D.Float hitbox, float xSpeed, int[][] lvlData) {
        return IsSolid(hitbox.x + xSpeed, hitbox.y + hitbox.height + 1.0F, lvlData);
    }
}
