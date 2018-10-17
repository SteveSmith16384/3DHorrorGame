package com.scs.horrorgame.entities.unused;

import com.scs.horrorgame.HorrorGame;
import com.scs.horrorgame.entities.AbstractEntity;
import com.scs.horrorgame.shapes.CreateShapes;

public class Stairs extends AbstractEntity {
	
	//private static final float STAIRS_WIDTH = 2f;
	//private static final float STAIR_DEPTH = .4f;
	private static final float STAIR_HEIGHT = .2f;
	
	//private static final int NUM_STAIRS = 10;
	
	public Stairs(HorrorGame _game, float x, float y, float z, float STAIRS_WIDTH, float height, float section_depth) {
		super(_game, "Stairs");
		
		int NUM_STAIRS = (int)(height / STAIR_HEIGHT);
		float STAIR_DEPTH = section_depth / NUM_STAIRS;
		for (int i=1 ; i<NUM_STAIRS ; i++) {
			float actx = x;
			float acty = y + (i*STAIR_HEIGHT);
			float actz = z + (i*STAIR_DEPTH);
			CreateShapes.CreateFloorTL(game.getAssetManager(), game.getBulletAppState(), this.main_node, actx, acty, actz, 
					STAIRS_WIDTH, STAIR_HEIGHT, STAIR_DEPTH, "Textures/scarybark.jpg");
		}

	}
	

	@Override
	public void process(float tpf) {
		// Do nothing
		
	}

	
	/*@Override
	public Spatial getGeometry() {
		return this.main_node;
	}*/

	
	@Override
	public void remove() {
		this.main_node.removeFromParent();
		// this.game.bulletAppState.getPhysicsSpace().remove(this.floor_phy); todo - how to remove each step?
		
	}
	
}
