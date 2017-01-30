package com.scs.slenderman.entities.monsters;

import java.util.ArrayList;
import java.util.List;

import com.jme3.audio.AudioNode;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera.FrustumIntersect;
import com.jme3.scene.Spatial;
import com.scs.slenderman.HorrorGame;
import com.scs.slenderman.IProcessable;
import com.scs.slenderman.Settings;
import com.scs.slenderman.entities.AbstractEntity;

public abstract class AbstractMonster extends AbstractEntity implements IProcessable {

	protected static final float SPEED = .4f;

	private Spatial geometry;
	private RigidBodyControl floor_phy;

	private AudioNode audio_node_i_see_you;
	private AudioNode bens_sfx;
	private List<AudioNode> audio_node_moans = new ArrayList<>();
	private float next_scary_sound = 10;
	protected float dist_to_player;
	protected FrustumIntersect insideoutside;
	
	public AbstractMonster(HorrorGame game, float x, float z) {
		super(game, "Monster");

		geometry = this.getModel();
		
		main_node.attachChild(geometry);

		//main_node.setLocalTranslation(new Vector3f(x, COLL_HEIGHT/2, z));
		main_node.setLocalTranslation(new Vector3f(x, 0, z));

		this.main_node.updateModelBound();

		this.geometry.setUserData(Settings.ENTITY, this);

		audio_node_i_see_you = new AudioNode(game.getAssetManager(), "Sound/i_see_you_voice.ogg", false);
		audio_node_i_see_you.setPositional(true);
		this.getMainNode().attachChild(audio_node_i_see_you);

		for (int i=1 ; i<=5 ; i++) {
			AudioNode an = new AudioNode(game.getAssetManager(), "Sound/qubodup-GhostMoans/wav/qubodup-GhostMoan0" + i + ".ogg", false);
			an.setPositional(true);
			this.getMainNode().attachChild(an);
			this.audio_node_moans.add(an);
		}

	}


	protected abstract Spatial getModel();
	
	public void warp(float x, float z) {
		if (floor_phy != null) {
			this.floor_phy.setPhysicsLocation(new Vector3f(x, 0, z));
		}
		this.getMainNode().setLocalTranslation(x, 0, z);
	}


	@Override
	public void process(float tpf) {
		dist_to_player = this.getMainNode().getWorldTranslation().distance(game.player.getMainNode().getWorldTranslation());
		if (dist_to_player <= 1) {
			game.gameOver(false, this);
		}

		next_scary_sound -= tpf;
		if (next_scary_sound <= 0) {
			int i = HorrorGame.rnd.nextInt(audio_node_moans.size() + 1);
			switch (i) {
			case 0:
				this.audio_node_i_see_you.playInstance();
				break;

			default:
				AudioNode an = this.audio_node_moans.get(i-1);
				an.playInstance();
			}
			HorrorGame.p("Monster sound!");
			next_scary_sound = 10 + HorrorGame.rnd.nextInt(5);
		}

		insideoutside = game.getInsideOutside(this);
		//if (insideoutside == FrustumIntersect.Outside) { // Only move if we can't be seen
			/*Vector3f player_pos = game.player.getGeometry().getWorldTranslation(); 
			float left_dist = this.left_node.getWorldTranslation().distance(player_pos);
			float right_dist = this.right_node.getWorldTranslation().distance(player_pos);

			if (left_dist > right_dist) {
				this.turnRight(tpf);
			} else {
				this.turnLeft(tpf);
			}*/

		//	JMEFunctions.MoveForwards(this.getMainNode(), SPEED * tpf);
		//}
	}


	@Override
	public void remove() {
		this.main_node.removeFromParent();
		if (floor_phy != null) {
			this.game.bulletAppState.getPhysicsSpace().remove(this.floor_phy);
		}

	}
}
