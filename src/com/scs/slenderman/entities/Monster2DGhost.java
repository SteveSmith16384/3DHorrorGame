package com.scs.slenderman.entities;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Spatial;
import com.scs.slenderman.HorrorGame;
import com.scs.slenderman.shapes.AbstractBillboard;

public class Monster2DGhost extends AbstractMonster {

	private static final float COLL_WIDTH = 1f;
	private static final float COLL_HEIGHT = 2f;

	public Monster2DGhost(HorrorGame game, AssetManager assetManager, float x, float z) {
		super(game, assetManager, x, z);
	}

	
	@Override
	protected Spatial getModel() {
		Spatial geometry = new AbstractBillboard(game.getAssetManager(), "Textures/skeleton-ghost.png", COLL_WIDTH, COLL_HEIGHT);
		//this.geometry = new AbstractBillboard(assetManager, "Textures/mud.png", COLL_WIDTH, COLL_HEIGHT);
		geometry.setLocalTranslation(-COLL_WIDTH/2, 0, 0); // Keep origin at bottom
		return geometry;
	}


}
