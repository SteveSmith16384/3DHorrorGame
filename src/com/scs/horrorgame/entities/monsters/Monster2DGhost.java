package com.scs.horrorgame.entities.monsters;

import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera.FrustumIntersect;
import com.jme3.scene.Spatial;
import com.scs.horrorgame.HorrorGame;
import com.scs.horrorgame.JMEAngleFunctions;
import com.scs.horrorgame.Settings;
import com.scs.horrorgame.shapes.AbstractBillboard;

/*
 * 2D ghost that moves when not looked at.
 */
public class Monster2DGhost extends AbstractMonster {

	private static final float COLL_WIDTH = 1f;
	private static final float COLL_HEIGHT = 2f;

	public Monster2DGhost(HorrorGame game, float x, float z) {
		super(game, x, z);
	}


	@Override
	public void process(float tpf) {
		super.process(tpf);

		this.getMainNode().lookAt(super.game.player.getMainNode().getWorldTranslation(), Vector3f.UNIT_Y);

		if (insideoutside == FrustumIntersect.Outside) { // Only move if we can't be seen
			JMEAngleFunctions.moveForwards(this.getMainNode(), SPEED * tpf);
		}
	}


	@Override
	protected Spatial getModel() {
		Spatial geometry;
		if (Settings.USE_BENS_SOUND) {
			geometry = new AbstractBillboard(game.getAssetManager(), "Textures/ben_scary.png", COLL_WIDTH, COLL_HEIGHT);
		} else {
			geometry = new AbstractBillboard(game.getAssetManager(), "Textures/skeleton-ghost.png", COLL_WIDTH, COLL_HEIGHT);
		}
		//this.geometry = new AbstractBillboard(assetManager, "Textures/mud.png", COLL_WIDTH, COLL_HEIGHT);
		geometry.setLocalTranslation(-COLL_WIDTH/2, 0, 0); // Keep origin at bottom
		return geometry;
	}


}
