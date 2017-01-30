package com.scs.slenderman.entities;

import com.jme3.audio.AudioNode;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera.FrustumIntersect;
import com.jme3.scene.Spatial;
import com.scs.slenderman.HorrorGame;
import com.scs.slenderman.JMEFunctions;
import com.scs.slenderman.Settings;
import com.scs.slenderman.shapes.AbstractBillboard;

public class ChargingHarmlessMonster extends AbstractEntity {

	private static final float COLL_WIDTH = 1f;
	private static final float COLL_HEIGHT = 2f;

	private static final float SPEED = 2f;
	private static final float ATTACK_DIST = 5f;

	private Spatial geometry;
	private AudioNode audio_node;
	private boolean active = false;

	public ChargingHarmlessMonster(HorrorGame game, float x, float z) {
		super(game, "ChargingHarmlessMonster");

		geometry = new AbstractBillboard(game.getAssetManager(), "Textures/ben_scary.png", COLL_WIDTH, COLL_HEIGHT); // todo
		geometry.setLocalTranslation(-COLL_WIDTH/2, 0, 0); // Keep origin at bottom
		main_node.attachChild(geometry);

		main_node.setLocalTranslation(new Vector3f(x, 0, z));
		this.main_node.updateModelBound();

		this.geometry.setUserData(Settings.ENTITY, this);

		audio_node = new AudioNode(game.getAssetManager(), "Sound/i_see_you_voice.ogg", false); // todo
		audio_node.setLooping(true);
		audio_node.setPositional(true);
		this.getMainNode().attachChild(audio_node);

	}


	@Override
	public void process(float tpf) {
		if (!active) {
			// Look away to hide
			this.getMainNode().lookAt(super.game.player.getMainNode().getWorldTranslation(), Vector3f.UNIT_Y);
			this.getMainNode().getLocalRotation().multLocal(-1);

			FrustumIntersect insideoutside = game.getInsideOutside(this);
			if (insideoutside == FrustumIntersect.Inside) { // Only move if we can't be seen
				float dist_to_player = this.getMainNode().getWorldTranslation().distance(game.player.getMainNode().getWorldTranslation());
				if (dist_to_player <= ATTACK_DIST) {
					this.audio_node.play();
					active = true;
				}
			}
		} else {
			this.getMainNode().lookAt(super.game.player.getMainNode().getWorldTranslation(), Vector3f.UNIT_Y);
			JMEFunctions.MoveForwards(this.getMainNode(), SPEED * tpf);
			float dist_to_player = this.getMainNode().getWorldTranslation().distance(game.player.getMainNode().getWorldTranslation());
			if (dist_to_player <= .5f) {
				this.remove();
			}			
		}
	}


	@Override
	public void remove() {
		this.main_node.removeFromParent();

	}


}
