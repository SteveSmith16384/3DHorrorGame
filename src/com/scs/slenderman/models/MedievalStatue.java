package com.scs.slenderman.models;

import com.jme3.asset.AssetManager;
import com.jme3.math.FastMath;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class MedievalStatue extends Node {
	
	public MedievalStatue(AssetManager assetManager) {
		super("MedeivalStatue");
		
		Spatial s = assetManager.loadModel("Models/InnansorraStatueUpload.blend");
		//s.rotate(0, 90 * FastMath.DEG_TO_RAD, 0);
	
		this.attachChild(s);
	}

}
