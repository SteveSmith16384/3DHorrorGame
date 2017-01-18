package com.scs.slenderman.models;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.scs.slenderman.JMEFunctions;

public class StoneCoffinModel extends Node {
	
	public StoneCoffinModel(AssetManager assetManager) {
		super("StoneCoffin");
		
		Spatial s = assetManager.loadModel("Models/Stone_coffin.obj");
		//s.rotate(0, 90 * FastMath.DEG_TO_RAD, 0);
		s.scale(0.025f);
		JMEFunctions.SetTextureOnSpatial(assetManager, s, "stonecoffin.tga");
	
		this.attachChild(s);
	}

}
