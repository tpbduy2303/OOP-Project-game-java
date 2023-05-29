package utilz;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Main.Game;
import entities.Akaza;
import entities.Fire_dude;
import entities.Ice_dude;
import entities.Trap;

public class LoadSave {

	public static final String PLAYER_ATLAS = "player_spirite.png";
	public static final String LEVEL_ONE_DATA = "level_one_data.png";
	public static final String LEVEL = "Element_Zone_Castle_Tileset.png";
	public static final String MENU_BUTTONS = "button_atlas.png";
	public static final String URM_BUTTONS = "urm_buttons.png";
	public static final String GAME_COMPLETED = "game_completed.png";
	public static final String MENU_BACKGROUND = "menu_background.png";
	public static final String MENU_BACKGROUND_IMG = "background.png";
	public static final String GAME_BACKGROUND_IMG1 = "backgroundgame1.png";
	public static final String GAME_BACKGROUND_IMG2 = "backgroundgame2.jpg";
	public static final String GAME_BACKGROUND_IMG3 = "backgroundgame3.jpeg";
	public static final String GAME_BACKGROUND_IMG4 = "backgroundgame4.jpeg";


	public static final String FIRE_DUDE = "Fire_Dude.png";
	public static final String ICE_DUDE = "Ice_Dude.png";
	public static final String STATUS_BAR = "health_power_bar.png";

	public static final String AKAZA = "Akaza.png";
	public static final String TRAP = "trap_atlas.png";
	public static final String DEATH_SCREEN = "death_screen.png";

	public static BufferedImage GetSpriteAtlas(String fileName) {
		BufferedImage img = null;
		InputStream is = LoadSave.class.getResourceAsStream("/" + fileName);
		try {
			img = ImageIO.read(is);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return img;
	}

	public static ArrayList<Fire_dude> Get_Fire_dude(){
		BufferedImage img = GetSpriteAtlas(LEVEL_ONE_DATA);
		ArrayList<Fire_dude> list = new ArrayList<>();
		for (int j = 0; j < img.getHeight(); j++)
			for (int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getGreen();
				if (value == Constants.EnemyConstants.FIRE_DUDE)
				list.add(new Fire_dude(i*Game.TILES_SIZE, j*Game.TILES_SIZE));
			}
		return list;
	}
	public static ArrayList<Ice_dude> Get_Ice_dude(){
		BufferedImage img = GetSpriteAtlas(LEVEL_ONE_DATA);
		ArrayList<Ice_dude> list = new ArrayList<>();
		for (int j = 0; j < img.getHeight(); j++)
			for (int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getGreen();
				if (value == Constants.EnemyConstants.ICE_DUDE)
					list.add(new Ice_dude(i*Game.TILES_SIZE, j*Game.TILES_SIZE));
			}
		return list;
	}

	public static int[][] GetLevelData() {
		BufferedImage img = GetSpriteAtlas(LEVEL_ONE_DATA);
		int[][] lvlData = new int[img.getHeight()][img.getWidth()];

		for (int j = 0; j < img.getHeight(); j++)
			for (int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getRed();
				lvlData[j][i] = value;
			}
		return lvlData;

	}

	public static ArrayList<Akaza> Get_Akaza() {
		BufferedImage img = GetSpriteAtlas(LEVEL_ONE_DATA);
		ArrayList<Akaza> list = new ArrayList<>();
		for (int j = 0; j < img.getHeight(); j++)
			for (int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getGreen();
				if (value == Constants.EnemyConstants.AKAZA)
					list.add(new Akaza(i*Game.TILES_SIZE, j*Game.TILES_SIZE));
			}
		return list;
	}

	public static ArrayList<Trap> Get_Trap() {
		BufferedImage img = GetSpriteAtlas(LEVEL_ONE_DATA);
		ArrayList<Trap> list = new ArrayList<>();
		for (int j = 0; j < img.getHeight(); j++)
			for (int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getGreen();
				if (value == Constants.EnemyConstants.TRAP)
					list.add(new Trap(i*Game.TILES_SIZE, j*Game.TILES_SIZE));
			}
		return list;
	}
}
