package com.scs.slenderman.models;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class LargeGravestoneModel extends Node {
	
	public LargeGravestoneModel(AssetManager assetManager) {
		super("LargeGravestone");
		
		Spatial s = assetManager.loadModel("Models/cemetery/grave3.obj");
		//s.rotate(0, 90 * FastMath.DEG_TO_RAD, 0);
	
		this.attachChild(s);
	}

}
