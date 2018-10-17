package com.scs.horrorgame.models.unused;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.scs.horrorgame.JMEFunctions;

public class LargeGravestoneModel extends Node {
	
	public LargeGravestoneModel(AssetManager assetManager) {
		super("LargeGravestone");
		
		Spatial s = assetManager.loadModel("Models/cemetery/grave3.obj");
		//s.rotate(0, 90 * FastMath.DEG_TO_RAD, 0);
		JMEFunctions.SetTextureOnSpatial(assetManager, s, "Textures/road2.png");

		this.attachChild(s);
	}

}