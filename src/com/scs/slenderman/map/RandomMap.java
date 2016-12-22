package com.scs.slenderman.map;

import java.util.Random;

import com.scs.slenderman.Settings;

public class RandomMap implements IMapInterface {
	
	private static final int WIDTH = 48;//12;
	private static final int DEPTH = 48;//12;
	
	private static final int PLAYER_X = WIDTH/2;
	private static final int PLAYER_Z = DEPTH/2;
	
	private static final int MONSTER_X = 0;
	private static final int MONSTER_Z = 0;
	
	
	private static final Random rnd = new Random();

	@Override
	public int getWidth() {
		return WIDTH;
	}

	@Override
	public int getDepth() {
		return DEPTH;
	}

	@Override
	public int getCodeForSquare(int x, int z) {
		if (x == PLAYER_X && z == PLAYER_Z) {
			return Settings.MAP_PLAYER;
		}

		if (x == MONSTER_X && z == MONSTER_Z) {
			return Settings.MAP_MONSTER;
		}

		int num = rnd.nextInt(25)-23;
		if (num < 0) {
			num = 0;
		}
		return num;
	}

	@Override
	public int getNumCollectables() {
		return (WIDTH * DEPTH) / 36;
	}

}
