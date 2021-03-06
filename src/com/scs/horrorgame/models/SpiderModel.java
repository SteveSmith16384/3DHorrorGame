package com.scs.horrorgame.models;

import com.jme3.asset.AssetManager;
import com.jme3.math.FastMath;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class SpiderModel extends Node {
	
	public SpiderModel(AssetManager assetManager) {
		super("Spider");
		
		Spatial s = assetManager.loadModel("Models/spider.obj");
		s.rotate(0, 90 * FastMath.DEG_TO_RAD, 0);
	
		this.attachChild(s);
	}

}
