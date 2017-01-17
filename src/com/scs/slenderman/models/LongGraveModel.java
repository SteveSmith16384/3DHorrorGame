package com.scs.slenderman.models;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.scs.slenderman.JMEFunctions;

public class LongGraveModel extends Node {
	
	public LongGraveModel(AssetManager assetManager) {
		super("LongGrave");
		
		Spatial s = assetManager.loadModel("Models/cemetery/grave6.obj");
		//s.rotate(0, 90 * FastMath.DEG_TO_RAD, 0);
		JMEFunctions.SetTextureOnSpatial(assetManager, s, "Textures/road2.png");

		this.attachChild(s);
	}

}
