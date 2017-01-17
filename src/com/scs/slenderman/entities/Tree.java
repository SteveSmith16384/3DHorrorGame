package com.scs.slenderman.entities;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.scene.Spatial;
import com.scs.slenderman.HorrorGame;
import com.scs.slenderman.models.Tree_arbol_seco_Model;

public class Tree extends Entity {
	
	private static final float HEIGHT = 15;
	//private static final float RAD = .45f;
	
	private Spatial floor_geo;
	private RigidBodyControl floor_phy;
	
	public Tree(HorrorGame _game, float x, float z) {
		super(_game, "Tree");
		
		//float RAD = .3f + (HorrorGame.rnd.nextFloat() / 10f);
		//Geometry floor_geo = new MyCylinder(game.getAssetManager(), new Vector3f(x, HEIGHT, z), new Vector3f(x, 0, z), 2, 10, RAD, "Textures/scarybark.jpg");
		floor_geo = new Tree_arbol_seco_Model(game.getAssetManager());
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
	public Spatial getGeometry() {
		return floor_geo;
	}

	
	@Override
	public void remove() {
		this.main_node.removeFromParent();
		this.game.bulletAppState.getPhysicsSpace().remove(this.floor_phy);
		
	}
}
