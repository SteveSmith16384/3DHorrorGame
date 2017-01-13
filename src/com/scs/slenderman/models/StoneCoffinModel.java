package com.scs.slenderman.models;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class StoneCoffinModel extends Node {
	
	public StoneCoffinModel(AssetManager assetManager) {
		super("StoneCoffin");
		
		Spatial s = assetManager.loadModel("Models/Stone_coffin.blend");
		//s.rotate(0, 90 * FastMath.DEG_TO_RAD, 0);
	
		this.attachChild(s);
	}

}
