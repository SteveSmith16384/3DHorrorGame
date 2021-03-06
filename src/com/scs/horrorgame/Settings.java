package com.scs.horrorgame;

public class Settings {
	
	public static final String VERSION = "0.02";

	public static final boolean RECORD_VID = false;
	public static final boolean USE_BENS_SOUND = false;
	public static final boolean SHOW_DEBUG = false;
	public static final boolean DEBUG_LIGHT = false;
	public static final boolean SHOW_LOGO = false; // todo - need a logo!
	
	public static final float CAM_DIST = 30f;
	public static final int FLOOR_SECTION_SIZE=12;
	public static final boolean LIGHTING = true;
	public static final String NAME = "Sole Collector";
	
	// Player dimensions
	public static final float PLAYER_HEIGHT = 1.5f;
	public static final float PLAYER_RAD = .35f;
	
	// User Data
	public static final String ENTITY = "Entity";
	
	// Map codes
	public static final int MAP_NOTHING = 0;
	public static final int MAP_TREE = 1;
	public static final int MAP_PLAYER = 2;
	public static final int MAP_MONSTER_GHOST = 3; // 2D ghost, moves when not looked at.
	public static final int MAP_FENCE_LR = 4;
	public static final int MAP_FENCE_FB = 5;
	public static final int MAP_MEDIEVAL_STATUE = 6;
	public static final int MAP_SIMPLE_PILLAR = 7;
	public static final int MAP_MONSTER_STATUE = 8; // moves when NOT looked at.
	public static final int MAP_SIMPLE_CROSS = 9;
	public static final int MAP_SKULL = 10;
	public static final int MAP_SKULL2 = 11;
	public static final int MAP_STONE_COFFIN = 13;
	public static final int MAP_MONSTER_MOVING_STATUE = 14; // Moves fast when looked at
	public static final int MAP_CHARGING_GHOST = 15;
	
	// Key codes
	public static final String KEY_RECORD = "record";

}
