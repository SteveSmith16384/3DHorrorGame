package com.scs.horrorgame.entities;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.scene.Spatial;
import com.scs.horrorgame.HorrorGame;
import com.scs.horrorgame.models.SkullModel;

public class Skull extends AbstractEntity {
	
	private Spatial floor_geo;
	private RigidBodyControl floor_phy;
	
	public Skull(HorrorGame _game, float x, float z) {
		super(_game, "Skull");
		
		floor_geo = new SkullModel(game.getAssetManager());
		floor_geo.setLocalTranslation(x, 0, z);
		floor_geo.rotate(0, (float)(HorrorGame.rnd.nextFloat() * Math.PI * .1f), 0); // rotate random amount, and maybe scale slightly

		this.main_node.attachChild(floor_geo);
		this.main_node.setLocalTranslation(0, 1f, 0); // Drop from sky

		floor_phy = new RigidBodyControl(1f);
		floor_geo.addControl(floor_phy);
		game.bulletAppState.getPhysicsSpace().add(floor_phy);
		floor_phy.setFriction(1f);
	}

	
	@Override
	public void process(float tpf) {
		// Do nothing
		
	}

	
	@Override
	public void remove() {
		this.main_node.removeFromParent();
		this.game.bulletAppState.getPhysicsSpace().remove(this.floor_phy);
		
	}
}
