package com.scs.slenderman.shapes;

import com.jme3.asset.AssetManager;
import com.jme3.asset.TextureKey;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;
import com.scs.slenderman.Settings;

public class CreateShapes {

	public static Geometry CreateFloorTL(AssetManager assetManager, BulletAppState bulletAppState, Node rootNode, float x, float z, float w, float d, String tex) {
		Box floor = new Box(w/2, 0.1f, d/2);
		floor.scaleTextureCoordinates(new Vector2f(w, d));

		TextureKey key3 = new TextureKey(tex);
		key3.setGenerateMips(true);
		Texture tex3 = assetManager.loadTexture(key3);
		tex3.setWrap(WrapMode.Repeat);

		Material floor_mat = null;
		if (Settings.LIGHTING) {
			floor_mat = new Material(assetManager,"Common/MatDefs/Light/Lighting.j3md");  // create a simple material
			floor_mat.setTexture("DiffuseMap", tex3);
		} else {
			floor_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
			floor_mat.setTexture("ColorMap", tex3);
		}

		Geometry floor_geo = new Geometry("Floor", floor);
		floor_geo.setMaterial(floor_mat);
		floor_geo.setLocalTranslation(x+(w/2), -0.1f, z+(d/2)); // Move it into position
		rootNode.attachChild(floor_geo);

		/* Make the floor physical with mass 0.0f! */
		RigidBodyControl floor_phy = new RigidBodyControl(0.0f);
		floor_geo.addControl(floor_phy);
		bulletAppState.getPhysicsSpace().add(floor_phy);
		floor_phy.setFriction(1f);
		return floor_geo;
	}


	private static void addBox(String name, AssetManager assetManager, BulletAppState bulletAppState, Node rootNode, float w, float h, float x, float z) {
		Box floor = new Box(w/2, h/2, w/2);
		floor.scaleTextureCoordinates(new Vector2f(3, 6));

		Material floor_mat = null;
		if (Settings.LIGHTING) {
			floor_mat = new Material(assetManager,"Common/MatDefs/Light/Lighting.j3md");  // create a simple material
		} else {
			floor_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		}
		TextureKey key3 = new TextureKey("Textures/Terrain/Pond/Pond.jpg");
		key3.setGenerateMips(true);
		Texture tex3 = assetManager.loadTexture(key3);
		tex3.setWrap(WrapMode.Repeat);
		floor_mat.setTexture("ColorMap", tex3);

		Geometry floor_geo = new Geometry(name, floor);
		floor_geo.setMaterial(floor_mat);
		floor_geo.setLocalTranslation(x, h*2, z);
		rootNode.attachChild(floor_geo);
		/* Make the floor physical with mass 0.0f! */
		RigidBodyControl floor_phy = new RigidBodyControl(0.5f);
		floor_geo.addControl(floor_phy);
		bulletAppState.getPhysicsSpace().add(floor_phy);
		floor_phy.setFriction(1f);
	}


	private static void addBoxTL(String name, AssetManager assetManager, BulletAppState bulletAppState, Node rootNode, float w, float h, float d, float x, float y, float z, float weight) {
		Box floor = new Box(w/2, h/2, d/2);
		floor.scaleTextureCoordinates(new Vector2f(3, 6));

		TextureKey key3 = new TextureKey("Textures/Terrain/Pond/Pond.jpg");
		key3.setGenerateMips(true);
		Texture tex3 = assetManager.loadTexture(key3);
		tex3.setWrap(WrapMode.Repeat);

		Material floor_mat = null;
		if (Settings.LIGHTING) {
			floor_mat = new Material(assetManager,"Common/MatDefs/Light/Lighting.j3md");  // create a simple material
			floor_mat.setTexture("DiffuseMap", tex3);
		} else {
			floor_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
			floor_mat.setTexture("ColorMap", tex3);
		}

		Geometry floor_geo = new Geometry(name, floor);
		floor_geo.setMaterial(floor_mat);
		floor_geo.setLocalTranslation(x, y, z);
		rootNode.attachChild(floor_geo);

		RigidBodyControl floor_phy = new RigidBodyControl(weight);
		floor_geo.addControl(floor_phy);
		bulletAppState.getPhysicsSpace().add(floor_phy);
		floor_phy.setFriction(1f);
	}


	private static Geometry addCylinder_Top(AssetManager assetManager, BulletAppState bulletAppState, Node rootNode, 
			float rad, float h, float x, float y, float z, float weight) {
		Geometry floor_geo = new MyCylinder(assetManager, new Vector3f(x, y, z), new Vector3f(x, y-h, z), 2, 10, .4f, "Textures/Terrain/Pond/Pond.jpg");
		rootNode.attachChild(floor_geo);

		RigidBodyControl floor_phy = new RigidBodyControl(weight);
		floor_geo.addControl(floor_phy);
		bulletAppState.getPhysicsSpace().add(floor_phy);
		floor_phy.setFriction(1f);
		return floor_geo;
	}



}
