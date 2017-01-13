package com.scs.slenderman.models;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class PillarModel extends Node {
	
	public PillarModel(AssetManager assetManager) {
		super("Pillar");
		
		Spatial s = assetManager.loadModel("Models/cemetery/grave4.obj");
		//s.rotate(0, 90 * FastMath.DEG_TO_RAD, 0);
	
		this.attachChild(s);
	}

}
