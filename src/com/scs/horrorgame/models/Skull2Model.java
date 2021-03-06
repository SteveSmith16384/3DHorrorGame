package com.scs.horrorgame.models;

import com.jme3.asset.AssetManager;
import com.jme3.math.FastMath;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.scs.horrorgame.JMEModelFunctions;

public class Skull2Model extends Node {
	
	public Skull2Model(AssetManager assetManager) {
		super("Skull2Model");
		
		Spatial s = assetManager.loadModel("Models/skull2/skull/skull.obj");
		JMEModelFunctions.setTextureOnSpatial(assetManager, s, "skull.tga");
		s.scale(0.1f);
		s.rotate(0, 90 * FastMath.DEG_TO_RAD, 0);
	
		this.attachChild(s);
	}

}
