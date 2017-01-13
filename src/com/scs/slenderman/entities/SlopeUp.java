package com.scs.slenderman.entities;

import com.jme3.scene.Spatial;
import com.scs.slenderman.HorrorGame;
import com.scs.slenderman.shapes.CreateShapes;

public class SlopeUp extends Entity { // todo

	//private static final float CORRIDOR_WIDTH = 2;
	private static final float WALL_THICKNESS = .1f;
	//private static final float WALL_DEPTH = 2f;
	//private static final float WALL_HEIGHT = 2f;

	public SlopeUp(HorrorGame game, float x, float y, float z, float CORRIDOR_WIDTH, float WALL_HEIGHT, float WALL_DEPTH) {
		super(game, "Slope");

		CreateShapes.CreateFloorTL(game.getAssetManager(), game.getBulletAppState(), this.main_node, x, y, z, 
				CORRIDOR_WIDTH, WALL_THICKNESS, WALL_DEPTH, "Textures/scarybark.jpg");
	}


	@Override
	public void process(float tpf) {

	}

	@Override
	public Spatial getGeometry() {
		return this.main_node;
	}

	@Override
	public void remove() {
		this.main_node.removeFromParent();
		// this.game.bulletAppState.getPhysicsSpace().remove(this.floor_phy); todo - how to remove each step?

	}

}
