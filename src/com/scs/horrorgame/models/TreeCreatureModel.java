package com.scs.horrorgame.models;

import com.jme3.asset.AssetManager;
import com.jme3.math.FastMath;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class TreeCreatureModel extends Node {
	
	public TreeCreatureModel(AssetManager assetManager) {
		super("TreeCreature");
		
		Spatial s = assetManager.loadModel("Models/Tree_Creature.blend");
		s.rotate(0, 90 * FastMath.DEG_TO_RAD, 0);
		s.scale(0.3f);
		//s.setlocaltr

		this.attachChild(s);
	}

}
