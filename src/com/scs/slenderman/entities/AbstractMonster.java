package com.scs.slenderman.entities;

import java.util.ArrayList;
import java.util.List;

import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera.FrustumIntersect;
import com.jme3.scene.Spatial;
import com.scs.slenderman.HorrorGame;
import com.scs.slenderman.IProcessable;
import com.scs.slenderman.JMEFunctions;
import com.scs.slenderman.Settings;
import com.scs.slenderman.models.TreeCreatureModel;
import com.scs.slenderman.shapes.AbstractBillboard;

public abstract class AbstractMonster extends AbstractEntity implements IProcessable {

	private static final float SPEED = 1f;

	private Spatial geometry;
	private RigidBodyControl floor_phy;

	private AudioNode audio_node_i_see_you;
	private List<AudioNode> audio_node_moans = new ArrayList<>();
	private float next_scary_sound = 10;

	public AbstractMonster(HorrorGame game, AssetManager assetManager, float x, float z) {
		super(game, "Monster");

		/*Box box1 = new Box(COLL_RAD, COLL_HEIGHT/2, COLL_RAD); // Don't use box, just use model.  No!  Use box for physics!
		geometry = new Geometry("Monster_Box", box1);
		geometry.setCullHint(CullHint.Always);
		geometry.setModelBound(new BoundingBox());
		this.main_node.attachChild(geometry);

		floor_phy = new RigidBodyControl(0.9f);
		geometry.addControl(floor_phy);
		floor_phy.setKinematic(true);
		game.bulletAppState.getPhysicsSpace().add(floor_phy);*/

		// MONSTERS
		//main_node.attachChild(new MedievalStatue(assetManager));
		
		//geometry = new TreeCreatureModel(assetManager);

		// 2D ghost
		/*this.geometry = new AbstractBillboard(assetManager, "Textures/skeleton-ghost.png", COLL_WIDTH, COLL_HEIGHT);
		//this.geometry = new AbstractBillboard(assetManager, "Textures/mud.png", COLL_WIDTH, COLL_HEIGHT);
		geometry.setLocalTranslation(-COLL_WIDTH/2, 0, 0); // Keep origin at bottom
		*/
		
		geometry = this.getModel();
		
		main_node.attachChild(geometry);

		//main_node.setLocalTranslation(new Vector3f(x, COLL_HEIGHT/2, z));
		main_node.setLocalTranslation(new Vector3f(x, 0, z));

		this.main_node.updateModelBound();

		this.geometry.setUserData(Settings.ENTITY, this);

		audio_node_i_see_you = new AudioNode(assetManager, "Sound/i_see_you_voice.ogg", true);
		audio_node_i_see_you.setPositional(true);
		this.getMainNode().attachChild(audio_node_i_see_you);

		for (int i=1 ; i<=5 ; i++) {
			AudioNode an = new AudioNode(assetManager, "Sound/qubodup-GhostMoans/wav/qubodup-GhostMoan0" + i + ".ogg", true);
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
		next_scary_sound -= tpf;
		if (next_scary_sound <= 0) {
			int i = HorrorGame.rnd.nextInt(audio_node_moans.size() + 1);
			switch (i) {
			case 0:
				this.audio_node_i_see_you.play();
				break;

			default:
				AudioNode an = this.audio_node_moans.get(i-1);
				an.play();
			}
			next_scary_sound = 20 + HorrorGame.rnd.nextInt(10);
		}

		this.getMainNode().lookAt(super.game.player.getMainNode().getWorldTranslation(), Vector3f.UNIT_Y);

		FrustumIntersect insideoutside = game.getInsideOutside(this); //this.getGeometry().getWorldTranslation();
		if (insideoutside == FrustumIntersect.Outside || Settings.MONSTER_ALWAYS_MOVES) { // Only move if we can't be seen
			/*Vector3f player_pos = game.player.getGeometry().getWorldTranslation(); 
			float left_dist = this.left_node.getWorldTranslation().distance(player_pos);
			float right_dist = this.right_node.getWorldTranslation().distance(player_pos);

			if (left_dist > right_dist) {
				this.turnRight(tpf);
			} else {
				this.turnLeft(tpf);
			}*/

			JMEFunctions.MoveForwards(this.getMainNode(), SPEED * tpf);
		}
	}


	/*@Override
	public Spatial getGeometry() {
		return geometry;
	}*/


	@Override
	public void remove() {
		this.main_node.removeFromParent();
		if (floor_phy != null) {
			this.game.bulletAppState.getPhysicsSpace().remove(this.floor_phy);
		}

	}
}