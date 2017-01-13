package com.scs.slenderman.entities;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.scene.Spatial;
import com.scs.slenderman.HorrorGame;
import com.scs.slenderman.models.LargeGravestoneModel;

public class LargeGravestone extends Entity {
	
	private Spatial floor_geo;
	private RigidBodyControl floor_phy;
	
	public LargeGravestone(HorrorGame _game, float x, float z) {
		super(_game, "LargeGravestone");
		
		floor_geo = new LargeGravestoneModel(game.getAssetManager());
		floor_geo.setLocalTranslation(x, 0, z);
		this.main_node.attachChild(floor_geo);
		// todo - rotate random amount, and maybe scale slightly

		floor_phy = new RigidBodyControl(0f);
		floor_geo.addControl(floor_phy);
		game.bulletAppState.getPhysicsSpace().add(floor_phy);
		floor_phy.setFriction(.5f);
	}

	
	@Override
	public void process(float tpf) {
		// Do nothing
	}

	
	@Override
	public Spatial getGeometry() {
		return floor_geo;
	}

	
	@Override
	public void remove() {
		this.main_node.removeFromParent();
		this.game.bulletAppState.getPhysicsSpace().remove(this.floor_phy);
	}
	
}
