package com.scs.slenderman.entities;

import com.jme3.asset.TextureKey;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.Vector2f;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;
import com.scs.slenderman.HorrorGame;
import com.scs.slenderman.Settings;

public class SimpleCross extends AbstractEntity { // todo - finish this

	private static final float THICKNESS = .2f;
	private static final float WIDTH = .7f;
	private static final float HEIGHT = 1f;
	private static final String TEX = "Textures/bricktex.jpg";

	//private Geometry geometry;
	private RigidBodyControl floor_phy;

	public SimpleCross(HorrorGame _game, float x, float z) {
		super(_game, "SimpleCross");

		TextureKey key3 = new TextureKey(TEX);
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

		{
			Box vert = new Box(THICKNESS/2, HEIGHT/2, THICKNESS/2);
			vert.scaleTextureCoordinates(new Vector2f(WIDTH, HEIGHT));
			Geometry geometry = new Geometry("vert", vert);
			geometry.setMaterial(floor_mat);
			this.main_node.attachChild(geometry);
		}

		{
			Box horiz = new Box(WIDTH/2, THICKNESS/2, THICKNESS/3);
			horiz.scaleTextureCoordinates(new Vector2f(WIDTH, HEIGHT));
			Geometry geometry = new Geometry("horiz", horiz);
			geometry.setMaterial(floor_mat);
			geometry.setLocalTranslation(0, .1f, z);
			this.main_node.attachChild(geometry);
		}

		main_node.setLocalTranslation(x+(WIDTH/2), HEIGHT/2, z+0.5f);

		floor_phy = new RigidBodyControl(0);
		main_node.addControl(floor_phy);

		game.bulletAppState.getPhysicsSpace().add(floor_phy);

		this.main_node.setUserData(Settings.ENTITY, this);

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
