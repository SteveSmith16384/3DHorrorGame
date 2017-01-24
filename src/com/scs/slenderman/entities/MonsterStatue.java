package com.scs.slenderman.entities;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera.FrustumIntersect;
import com.jme3.scene.Spatial;
import com.scs.slenderman.HorrorGame;
import com.scs.slenderman.Settings;
import com.scs.slenderman.models.MedievalStatueModel;

public class MonsterStatue extends AbstractMonster {

	public MonsterStatue(HorrorGame game, AssetManager assetManager, float x, float z) {
		super(game, assetManager, x, z);
	}


	@Override
	public void process(float tpf) {
		super.process(tpf);

		if (insideoutside == FrustumIntersect.Outside || Settings.MONSTER_ALWAYS_MOVES) { // Only move if we can't be seen
			if (dist_to_player <= 3) {
				this.getMainNode().lookAt(super.game.player.getMainNode().getWorldTranslation(), Vector3f.UNIT_Y);
			}
		}
	}


	@Override
	protected Spatial getModel() {
		return new MedievalStatueModel(game.getAssetManager());
	}

}
