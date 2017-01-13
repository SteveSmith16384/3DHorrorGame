package com.scs.slenderman.models;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class TreeCreatureModel extends Node {
	
	public TreeCreatureModel(AssetManager assetManager) {
		super("TreeCreature");
		
		Spatial s = assetManager.loadModel("Models/Tree_Creature.blend");
		//s.rotate(0, 90 * FastMath.DEG_TO_RAD, 0);
		s.scale(0.1f);

		this.attachChild(s);
	}

}
