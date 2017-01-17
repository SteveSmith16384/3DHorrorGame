package com.scs.slenderman.models;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.scs.slenderman.JMEFunctions;

public class GravestoneModel extends Node {
	
	public GravestoneModel(AssetManager assetManager) {
		super("Gravestone");
		
		Spatial s = assetManager.loadModel("Models/cemetery/grave2.obj");
		//s.rotate(0, 90 * FastMath.DEG_TO_RAD, 0);
		JMEFunctions.SetTextureOnSpatial(assetManager, s, "Textures/rubble.jpg");

		this.attachChild(s);
	}

}
