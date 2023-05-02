package utilz;

public class Constants {
	public static class Directions {
		public static final int LEFT = 0;
		public static final int UP = 1;
		public static final int RIGHT = 2;
		public static final int DOWN = 3;
	}

	public static class PlayerConstants {
		public static final int IDLE = 3;
		public static final int RUNNING= 0;
		
		public static final int JUMP = 4;
		public static final int HIT = 1;
		public static final int ATTACK_1 = 2;

		public static int GetSpriteAmount(int player_action) {
			switch (player_action) {
			
				
			case IDLE:
				return 4;
			case HIT:
				return 5;
			case RUNNING:
			case JUMP:
			case ATTACK_1:
				return 6;
		
			default:
				return 1;
			}
		}
	}

}