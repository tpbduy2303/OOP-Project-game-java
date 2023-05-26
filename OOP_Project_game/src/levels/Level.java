package levels;

public class Level {

	private int[][] lvlData;

	public Level(int[][] lvlData) {
		this.lvlData = lvlData;
	}

	public int getSpriteIndex(int x, int y) {
		return lvlData[y][x];
	}
	public void setSpriteIndex(int value, int x, int y) {
		this.lvlData[y][x] = value;
	}

	public int[][] getLevelData(){
		return lvlData;
	}
}
