package com.scs.slenderman.entities;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Spatial;
import com.scs.slenderman.HorrorGame;
import com.scs.slenderman.models.MedievalStatueModel;

public class MonsterStatue extends AbstractMonster {

	public MonsterStatue(HorrorGame game, AssetManager assetManager, float x, float z) {
		super(game, assetManager, x, z);
	}

	
	@Override
	protected Spatial getModel() {
		return new MedievalStatueModel(game.getAssetManager());
	}

}
