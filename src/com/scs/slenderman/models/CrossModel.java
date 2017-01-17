package com.scs.slenderman.models;

import com.jme3.asset.AssetManager;
import com.jme3.asset.TextureKey;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;
import com.scs.slenderman.JMEFunctions;
import com.scs.slenderman.Settings;

public class CrossModel extends Node {
	
	public CrossModel(AssetManager assetManager) {
		super("Cross");
		
		TextureKey key3 = new TextureKey("Textures/rubble.jpg");//road2.png");
		key3.setGenerateMips(true);
		Texture tex3 = assetManager.loadTexture(key3);
		tex3.setWrap(WrapMode.EdgeClamp);

		Material floor_mat = null;
		if (Settings.LIGHTING) {
			floor_mat = new Material(assetManager,"Common/MatDefs/Light/Lighting.j3md");  // create a simple material
			floor_mat.setTexture("DiffuseMap", tex3);
		} else {
			floor_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
			floor_mat.setTexture("ColorMap", tex3);
		}

		Spatial s = assetManager.loadModel("Models/cemetery/grave1.obj");
		s.setLocalTranslation(0, 1, 0);
		JMEFunctions.SetMaterialOnSpatial(s, floor_mat);
		//s.rotate(0, 90 * FastMath.DEG_TO_RAD, 0);
		
		this.attachChild(s);
	}

}
