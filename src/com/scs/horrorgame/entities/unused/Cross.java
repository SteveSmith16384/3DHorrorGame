package com.scs.horrorgame.entities.unused;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.scene.Spatial;
import com.scs.horrorgame.HorrorGame;
import com.scs.horrorgame.entities.AbstractEntity;
import com.scs.horrorgame.models.unused.CrossModel;

public class Cross extends AbstractEntity {
	
	private Spatial floor_geo;
	private RigidBodyControl floor_phy;
	
	public Cross(HorrorGame _game, float x, float z) {
		super(_game, "Cross");
		
		floor_geo = new CrossModel(game.getAssetManager());
		floor_geo.setLocalTranslation(x, 0, z);
		this.main_node.attachChild(floor_geo);

		floor_geo.scale(1f + (HorrorGame.rnd.nextFloat()));
		floor_geo.rotate(0, (float)(HorrorGame.rnd.nextFloat() * Math.PI), 0); // rotate random amount, and maybe scale slightly

		floor_phy = new RigidBodyControl(0f);
		floor_geo.addControl(floor_phy);
		game.bulletAppState.getPhysicsSpace().add(floor_phy);
		floor_phy.setFriction(1f);
	}

	
	@Override
	public void process(float tpf) {
		// Do nothing
	}

	
	/*@Override
	public Spatial getGeometry() {
		return floor_geo;
	}*/

	
	@Override
	public void remove() {
		this.main_node.removeFromParent();
		this.game.bulletAppState.getPhysicsSpace().remove(this.floor_phy);
		
	}
}
