package com.scs.slenderman.entities.unused;

import com.jme3.scene.Spatial;
import com.scs.slenderman.HorrorGame;
import com.scs.slenderman.entities.AbstractEntity;
import com.scs.slenderman.shapes.CreateShapes;

public class Corridor extends AbstractEntity {

	//private static final float CORRIDOR_WIDTH = 2;
	private static final float WALL_THICKNESS = .1f;
	//private static final float WALL_DEPTH = 2f;
	//private static final float WALL_HEIGHT = 2f;

	public Corridor(HorrorGame game, float x, float y, float z, float CORRIDOR_WIDTH, float WALL_HEIGHT, float WALL_DEPTH, boolean floor, boolean ceiling, boolean front, boolean back) {
		super(game, "Corridor");

		// Left wall
		CreateShapes.CreateFloorTL(game.getAssetManager(), game.getBulletAppState(), this.main_node, x-WALL_THICKNESS, y, z, 
				WALL_THICKNESS, WALL_HEIGHT, WALL_DEPTH, "Textures/scarybark.jpg");

		// Right wall
		CreateShapes.CreateFloorTL(game.getAssetManager(), game.getBulletAppState(), this.main_node, x+CORRIDOR_WIDTH, y, z, 
				WALL_THICKNESS, WALL_HEIGHT, WALL_DEPTH, "Textures/scarybark.jpg");

		// Floor
		if (floor) {
			CreateShapes.CreateFloorTL(game.getAssetManager(), game.getBulletAppState(), this.main_node, x, y, z, 
					CORRIDOR_WIDTH, WALL_THICKNESS, WALL_DEPTH, "Textures/scarybark.jpg");
		}

		if (ceiling) {
			CreateShapes.CreateFloorTL(game.getAssetManager(), game.getBulletAppState(), this.main_node, x, y+WALL_HEIGHT, z, 
					CORRIDOR_WIDTH, WALL_THICKNESS, WALL_DEPTH, "Textures/scarybark.jpg");
		}
		
		if (front) { // Closest
			CreateShapes.CreateFloorTL(game.getAssetManager(), game.getBulletAppState(), this.main_node, x, y, z, 
					WALL_THICKNESS, WALL_HEIGHT, WALL_DEPTH, "Textures/scarybark.jpg");

		}
	}


	@Override
	public void process(float tpf) {

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
