package com.scs.horrorgame.models;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class Tree_arbol_seco_Model extends Node {
	
	public Tree_arbol_seco_Model(AssetManager assetManager) {
		super("Tree_arbol_seco");
		
		Spatial s = assetManager.loadModel("Models/arbol_seco.blend");
		s.scale(1.5f);
		this.attachChild(s);
	}

}
