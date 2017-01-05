package com.scs.slenderman.entities;

import com.jme3.asset.TextureKey;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;
import com.scs.slenderman.HorrorGame;
import com.scs.slenderman.Settings;

public class Gravestone extends Entity {

	private static final float WIDTH = 1f;
	private static final float HEIGHT = 1f;

	private Geometry geometry;
	private RigidBodyControl floor_phy;
	
	public Gravestone(HorrorGame _game, float x, float z, float rot) {
		super(_game, "Gravestone");

		Box box1 = new Box(WIDTH, HEIGHT, .1f);
		geometry = new Geometry("Gravestone", box1);
		TextureKey key3 = new TextureKey("Textures/Terrain/Pond/Pond.jpg");
		key3.setGenerateMips(true);
		Texture tex3 = game.getAssetManager().loadTexture(key3);
		tex3.setWrap(WrapMode.Repeat);

		Material floor_mat = null;
		if (Settings.LIGHTING) {
			floor_mat = new Material(game.getAssetManager(),"Common/MatDefs/Light/Lighting.j3md");  // create a simple material
			floor_mat.setTexture("DiffuseMap", tex3);
		} else {
			floor_mat = new Material(game.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
			floor_mat.setTexture("ColorMap", tex3);
		}
		geometry.setMaterial(floor_mat);

		this.main_node.attachChild(geometry);
		main_node.setLocalTranslation(x+(WIDTH/2), HEIGHT, z+0.5f);
		main_node.rotate(0, rot/180, 0);

		floor_phy = new RigidBodyControl(0.9f);
		geometry.addControl(floor_phy);

		game.bulletAppState.getPhysicsSpace().add(floor_phy);
		
		this.geometry.setUserData(Settings.ENTITY, this);

	}


	@Override
	public void process(float tpf) {
		// Do nothing
	}


	@Override
	public Geometry getGeometry() {
		return geometry;
	}


	@Override
	public void remove() {
		this.main_node.removeFromParent();
		this.game.bulletAppState.getPhysicsSpace().remove(this.floor_phy);
		
	}


}
