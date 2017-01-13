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

public class Wall extends Entity {

	private static final float WIDTH = 2f;
	private static final float HEIGHT = 1.5f;

	private Geometry geometry;
	private RigidBodyControl floor_phy;
	
	public Wall(HorrorGame _game, float x, float z, float rot) {
		super(_game, "Wall");

		Box box1 = new Box(WIDTH/2, HEIGHT/2, .1f);
		box1.scaleTextureCoordinates(new Vector2f(WIDTH, HEIGHT));
		geometry = new Geometry("Fence", box1);
		//TextureKey key3 = new TextureKey("Textures/Terrain/Pond/Pond.jpg");
		TextureKey key3 = new TextureKey("Textures/fence.png"); // todo - scale tex
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
		floor_mat.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
		geometry.setMaterial(floor_mat);
		geometry.setQueueBucket(Bucket.Transparent);
		
		this.main_node.attachChild(geometry);
		float rads = (float)Math.toRadians(rot);
		main_node.rotate(0, rads, 0);
		main_node.setLocalTranslation(x+(WIDTH/2), HEIGHT/2, z+0.5f);

		floor_phy = new RigidBodyControl(0);
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
