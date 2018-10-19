package com.scs.horrorgame.entities;

import com.jme3.audio.AudioNode;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera.FrustumIntersect;
import com.jme3.scene.Spatial;
import com.scs.horrorgame.HorrorGame;
import com.scs.horrorgame.JMEAngleFunctions;
import com.scs.horrorgame.Settings;
import com.scs.horrorgame.shapes.AbstractBillboard;

public class ChargingHarmlessMonster extends AbstractEntity {

	private static final float COLL_WIDTH = 1f;
	private static final float COLL_HEIGHT = 2f;

	private static final float SPEED = 20f;
	private static final float ATTACK_DIST = 12f;

	private Spatial geometry;
	private AudioNode audio_node;
	private boolean active = false;

	public ChargingHarmlessMonster(HorrorGame game, float x, float z) {
		super(game, "ChargingHarmlessMonster");

		if (Settings.USE_BENS_SOUND) {
			geometry = new AbstractBillboard(game.getAssetManager(), "Textures/ben_monster2.png", COLL_WIDTH, COLL_HEIGHT);
		} else {
			geometry = new AbstractBillboard(game.getAssetManager(), "Textures/skeleton-ghost.png", COLL_WIDTH, COLL_HEIGHT);
		}
		geometry.setLocalTranslation(-COLL_WIDTH/2, 0, 0); // Keep origin at bottom
		//main_node.attachChild(geometry);  NOT YET!

		main_node.setLocalTranslation(new Vector3f(x, 0, z));
		this.main_node.updateModelBound();

		this.geometry.setUserData(Settings.ENTITY, this);

		audio_node = new AudioNode(game.getAssetManager(), "Sound/benroar.ogg", false);
		audio_node.setLooping(true);
		audio_node.setPositional(true);
		this.getMainNode().attachChild(audio_node);

	}


	@Override
	public void process(float tpf) {
		if (game.started) {
			if (!active) {
				// Look away to hide
				//this.getMainNode().lookAt(super.game.player.getMainNode().getWorldTranslation(), Vector3f.UNIT_Y);
				//this.getMainNode().getLocalRotation().multLocal(-1);

				FrustumIntersect insideoutside = game.getInsideOutside(this);
				if (insideoutside == FrustumIntersect.Inside) { // Only move if we can't be seen
					float dist_to_player = this.getMainNode().getWorldTranslation().distance(game.player.getMainNode().getWorldTranslation());
					if (dist_to_player <= ATTACK_DIST) {
						try {
							this.audio_node.play();
						} catch (java.lang.IllegalStateException ex) {
							// Unable to play sounds - no audiocard/speakers?
						}

						active = true;
						main_node.attachChild(geometry);
					}
				}
			} else {
				this.getMainNode().lookAt(super.game.player.getMainNode().getWorldTranslation(), Vector3f.UNIT_Y);
				JMEAngleFunctions.moveForwards(this.getMainNode(), SPEED * tpf);
				float dist_to_player = this.getMainNode().getWorldTranslation().distance(game.player.getMainNode().getWorldTranslation());
				if (dist_to_player <= .5f) {
					this.remove();
				}			
			}
		}
	}


	@Override
	public void remove() {
		this.main_node.removeFromParent();

	}


}
