package com.scs.horrorgame.entities;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.scene.Spatial;
import com.scs.horrorgame.HorrorGame;
import com.scs.horrorgame.JMEFunctions;
import com.scs.horrorgame.models.Tree_arbol_seco_Model;

public class Tree extends AbstractEntity {
	
	private Spatial floor_geo;
	private RigidBodyControl floor_phy;
	
	public Tree(HorrorGame _game, float x, float z) {
		super(_game, "Tree");
		
		floor_geo = new Tree_arbol_seco_Model(game.getAssetManager());
		JMEFunctions.SetTextureOnSpatial(game.getAssetManager(), floor_geo, "Textures/scarybark.jpg");
		floor_geo.setLocalTranslation(x, 0, z);
		floor_geo.scale(1f + (HorrorGame.rnd.nextFloat()));
		floor_geo.rotate(0, (float)(HorrorGame.rnd.nextFloat() * Math.PI), 0); // rotate random amount, and maybe scale slightly

		this.main_node.attachChild(floor_geo);

		floor_phy = new RigidBodyControl(0f);
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
