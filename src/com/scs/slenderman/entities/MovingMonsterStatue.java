package com.scs.slenderman.entities;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera.FrustumIntersect;
import com.jme3.scene.Spatial;
import com.scs.slenderman.HorrorGame;
import com.scs.slenderman.JMEFunctions;
import com.scs.slenderman.models.MedievalStatueModel;

public class MovingMonsterStatue extends AbstractMonster {

	private static final float ACTIVATE_DURATION = 1;

	private float time_until_move = ACTIVATE_DURATION;

	public MovingMonsterStatue(HorrorGame game, AssetManager assetManager, float x, float z) {
		super(game, assetManager, x, z);
	}


	@Override
	public void process(float tpf) {
		super.process(tpf);

		if (insideoutside != FrustumIntersect.Outside) { // Only move if we can't be seen - todo - only do if close
			time_until_move -= tpf;
			if (time_until_move < 0) {
				Vector3f player_pos = game.player.getMainNode().getWorldTranslation(); 
				float left_dist = this.left_node.getWorldTranslation().distance(player_pos);
				float right_dist = this.right_node.getWorldTranslation().distance(player_pos);

				if (left_dist > right_dist) {
					this.turnRight(tpf * 0.5f);
				} else {
					this.turnLeft(tpf * 0.5f);
				}
				if (Math.abs(left_dist - right_dist) < 0.1f) {
					JMEFunctions.MoveForwards(this.getMainNode(), SPEED * tpf * 20);
				}
			}
		} else {
			time_until_move = ACTIVATE_DURATION;
		}
	}


	@Override
	protected Spatial getModel() {
		return new MedievalStatueModel(game.getAssetManager());
	}

}
