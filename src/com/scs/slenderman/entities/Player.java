package com.scs.slenderman.entities;

import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial.CullHint;
import com.jme3.scene.shape.Box;
import com.scs.slenderman.HorrorGame;
import com.scs.slenderman.Settings;

public class Player extends Entity {
	
	private Geometry playerGeometry;
	public BetterCharacterControl playerControl;

	public Player(HorrorGame _game) {
		super(_game, "Player");
		
		/** Create a box to use as our player model */
		Box box1 = new Box(Settings.PLAYER_RAD, Settings.PLAYER_HEIGHT, Settings.PLAYER_RAD);
		playerGeometry = new Geometry("Player", box1);
		//Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");  // create a simple material
		//mat.setColor("Color", ColorRGBA.Blue);   // set color of material to blue
		//playerModel.setMaterial(mat);    
		playerGeometry.setLocalTranslation(new Vector3f(0,2,0));
		//playerModel.setLocalTranslation(new Vector3f(0,6,0));
		playerGeometry.setCullHint(CullHint.Always);
		this.getMainNode().attachChild(playerGeometry);

		// create character control parameters (Radius,Height,Weight)
		// Radius and Height determine the size of the collision bubble
		// Weight determines how much gravity effects the control
		playerControl = new BetterCharacterControl(Settings.PLAYER_RAD, Settings.PLAYER_HEIGHT, 1f);
		// set basic physical properties:
		playerControl.setJumpForce(new Vector3f(0, 5f, 0)); 
		playerControl.setGravity(new Vector3f(0, 1f, 0));
		//playerControl.warp(new Vector3f(0, 6, 0)); // So we drop
		this.getMainNode().addControl(playerControl);
		
		game.bulletAppState.getPhysicsSpace().add(playerControl);

		this.getMainNode().setUserData(Settings.ENTITY, this);

	}

	
	@Override
	public Geometry getGeometry() {
		return playerGeometry;
	}


	@Override
	public void process(float tpf) {
		// Do nothing
	}


	@Override
	public void remove() {
		this.main_node.removeFromParent();
		this.game.bulletAppState.getPhysicsSpace().remove(this.playerControl);
		
	}
}
