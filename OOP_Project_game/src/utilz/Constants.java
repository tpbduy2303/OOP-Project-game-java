package utilz;

import Main.Game;

public class Constants {
	public static class Directions {
		public static final int LEFT = 0;
		public static final int UP = 1;
		public static final int RIGHT = 2;
		public static final int DOWN = 3;
	}
	public static class UI {
		public static class Buttons {
			public static final int B_WIDTH_DEFAULT = 140;
			public static final int B_HEIGHT_DEFAULT = 56;
			public static final int B_WIDTH = (int) (B_WIDTH_DEFAULT * Game.SCALE);
			public static final int B_HEIGHT = (int) (B_HEIGHT_DEFAULT * Game.SCALE);
		}
		public static class URMButtons {
			public static final int URM_DEFAULT_SIZE = 56;
			public static final int URM_SIZE = (int) (URM_DEFAULT_SIZE * Game.SCALE);

		}
	}
	public static class EnemyConstants {
		public static final int FIRE_DUDE = 0;
		public static final int ICE_DUDE = 1;
		public static final int AKAZA = 2;
		public static final int TRAP = 3;


		public static final int IDLE = 0;
		public static final int ATTACK = 1;
		public static final int DEAD = 2;

		public static final int FIRE_DUDE_WIDTH_DEFAULT = 32;
		public static final int FIRE_DUDE_HEIGHT_DEFAULT = 16;
		public static final int FIRE_DUDE_WIDTH = (int) (FIRE_DUDE_WIDTH_DEFAULT * Game.SCALE);
		public static final int FIRE_DUDE_HEIGHT = (int) (FIRE_DUDE_HEIGHT_DEFAULT * Game.SCALE);
		public static final int ICE_DUDE_WIDTH_DEFAULT = 16;
		public static final int ICE_DUDE_HEIGHT_DEFAULT = 16;
		public static final int ICE_DUDE_WIDTH = (int) (ICE_DUDE_WIDTH_DEFAULT * Game.SCALE);
		public static final int ICE_DUDE_HEIGHT = (int) (ICE_DUDE_HEIGHT_DEFAULT * Game.SCALE);
		public static final int AKAZA_WIDTH_DEFAULT = 50;
		public static final int AKAZA_HEIGHT_DEFAULT = 50;
		public static final int AKAZA_WIDTH = (int) (AKAZA_WIDTH_DEFAULT * Game.SCALE);
		public static final int AKAZA_HEIGHT = (int) (AKAZA_HEIGHT_DEFAULT * Game.SCALE);
		public static final int FIRE_DUDE_DRAWOFFSET_X = (int)(10*Game.SCALE);

		public static final int ICE_DUDE_DRAWOFFSET_X = (int)(0*Game.SCALE);

		public static final int FIRE_DUDE_DRAWOFFSET_Y = (int)(0*Game.SCALE);
		public static final int ICE_DUDE_DRAWOFFSET_Y = (int)(0*Game.SCALE);
		public static int GetSpriteAmount(int enemy_type, int enemy_state) {
			switch (enemy_type) {
				case FIRE_DUDE:
					switch (enemy_state) {
						case IDLE, ATTACK:
							return 4;
						case DEAD:
							return 1;

					}
				case ICE_DUDE:
					switch (enemy_state) {
						case IDLE:
							return 6;
						case ATTACK:
							return 4;
						case DEAD:
							return 1;
					}
			}
			return 0;

		}
		public static int GetMaxHealth(int enemy_type) {
			switch (enemy_type) {
				case FIRE_DUDE:
					return 24;
				case ICE_DUDE:
					return 30;
				case AKAZA:
					return 60;
				case TRAP:
					return 100;
				default:
					return 1;
			}
		}

		public static int GetEnemyDmg(int enemy_type) {
			switch (enemy_type) {
				case FIRE_DUDE:
					return 15;
				case ICE_DUDE:
					return 3;
				case AKAZA:
					return 1;
				case TRAP:
					return 1000;
				default:
					return 0;
			}

		}

	}

		public static class PlayerConstants {
			public static final int IDLE = 3;
			public static final int RUNNING = 0;
			public static final int RUNNING2 = 5;

			public static final int ATTACK_2 = 4;
			public static final int HIT = 1;
			public static final int ATTACK_1 = 2;

			public static int GetSpriteAmount(int player_action) {
				switch (player_action) {
					case IDLE:
						return 4;
					case HIT:
						return 5;
					case RUNNING:					
					case RUNNING2:
					case ATTACK_2:
					case ATTACK_1:
						return 6;

					default:
						return 1;
				}
			}
		}

}