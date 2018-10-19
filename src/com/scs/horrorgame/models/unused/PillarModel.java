package com.scs.horrorgame.models.unused;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.scs.horrorgame.JMEModelFunctions;

public class PillarModel extends Node {
	
	public PillarModel(AssetManager assetManager) {
		super("Pillar");
		
		Spatial s = assetManager.loadModel("Models/cemetery/grave4.obj");
		//s.rotate(0, 90 * FastMath.DEG_TO_RAD, 0);
		JMEModelFunctions.setTextureOnSpatial(assetManager, s, "Textures/road2.png");

		this.attachChild(s);
	}

}
