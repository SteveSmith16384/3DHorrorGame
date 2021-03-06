package com.scs.horrorgame.models;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class MedievalStatueModel extends Node {
	
	public MedievalStatueModel(AssetManager assetManager) {
		super("MedeivalStatue");
		
		Spatial s = assetManager.loadModel("Models/InnansorraStatueUpload.blend");
		//s.rotate(0, 90 * FastMath.DEG_TO_RAD, 0);
		s.scale(1.3f);
		s.setLocalTranslation(0, 1f, 0);

		this.attachChild(s);
	}

}
