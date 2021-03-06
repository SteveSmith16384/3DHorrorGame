package com.scs.horrorgame.entities.monsters;

import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera.FrustumIntersect;
import com.jme3.scene.Spatial;
import com.scs.horrorgame.HorrorGame;
import com.scs.horrorgame.JMEAngleFunctions;
import com.scs.horrorgame.models.MedievalStatueModel;

/*
 * Statue that moves when NOT looked at.
 */
public class StaticMonsterStatue extends AbstractMonster {

	public StaticMonsterStatue(HorrorGame game, float x, float z) {
		super(game, x, z);
	}


	@Override
	public void process(float tpf) {
		super.process(tpf);

		if (insideoutside == FrustumIntersect.Outside) { // Only move if we can't be seen
			if (dist_to_player <= 3) {
				this.getMainNode().lookAt(super.game.player.getMainNode().getWorldTranslation(), Vector3f.UNIT_Y);
			}
			JMEAngleFunctions.moveForwards(this.getMainNode(), SPEED * tpf);
		}
	}


	@Override
	protected Spatial getModel() {
		return new MedievalStatueModel(game.getAssetManager());
	}

}
