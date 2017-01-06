package com.scs.slenderman.entities;

import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.bounding.BoundingBox;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera.FrustumIntersect;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial.CullHint;
import com.jme3.scene.shape.Box;
import com.scs.slenderman.HorrorGame;
import com.scs.slenderman.IProcessable;
import com.scs.slenderman.JMEFunctions;
import com.scs.slenderman.Settings;
import com.scs.slenderman.models.SpiderModel;

public class Monster extends Entity implements IProcessable {

	private static final float COLL_HEIGHT = 2f;
	private static final float COLL_RAD = 1f;
	private static final float SPEED = 1f;

	private Geometry geometry;
	private RigidBodyControl floor_phy;
	
	private AudioNode audio_node;
	private float next_scary_sound = 10;

	public Monster(HorrorGame game, AssetManager assetManager, float x, float z) {
		super(game, "Monster");

		Box box1 = new Box(COLL_RAD, COLL_HEIGHT/2, COLL_RAD); // Don't use box, just use model.  No!  Use box for physics!
		geometry = new Geometry("Monster_Box", box1);
		geometry.setCullHint(CullHint.Always);
		geometry.setModelBound(new BoundingBox());
		this.main_node.attachChild(geometry);
		
		floor_phy = new RigidBodyControl(0.9f);
		geometry.addControl(floor_phy);
		floor_phy.setKinematic(true);
		game.bulletAppState.getPhysicsSpace().add(floor_phy);

		main_node.attachChild(new SpiderModel(assetManager));

		main_node.setLocalTranslation(new Vector3f(x, COLL_HEIGHT/2, z));

		this.main_node.updateModelBound();

		this.geometry.setUserData(Settings.ENTITY, this);
		
		audio_node = new AudioNode(assetManager, "Sound/i_see_you_voice.ogg", true); // todo - make mono
		audio_node.setPositional(true);
		this.getMainNode().attachChild(audio_node);
		//ambient_node.play(); todo

	}


	public void warp(float x, float z) {
		this.floor_phy.setPhysicsLocation(new Vector3f(x, 0, z));
		this.getMainNode().setLocalTranslation(x, 0, z);
	}
	
	
	@Override
	public void process(float tpf) {
		next_scary_sound -= tpf;
		if (next_scary_sound <= 0) {
			this.audio_node.play();
			next_scary_sound = 20 + HorrorGame.rnd.nextInt(10);
		}

		FrustumIntersect insideoutside = game.getInsideOutside(this);
		if (insideoutside == FrustumIntersect.Outside || Settings.MONSTER_ALWAYS_MOVES) { // Only move if we can't be seen
			Vector3f player_pos = game.player.getGeometry().getWorldTranslation(); 
			float left_dist = this.left_node.getWorldTranslation().distance(player_pos);
			float right_dist = this.right_node.getWorldTranslation().distance(player_pos);

			if (left_dist > right_dist) {
				this.turnRight(tpf);
			} else {
				this.turnLeft(tpf);
			}
			JMEFunctions.MoveForwards(this.getMainNode(), SPEED * tpf);
		}
	}


	@Override
	public Geometry getGeometry() {
		return geometry;
	}


	@Override
	public void remove() {
		this.main_node.removeFromParent();
		this.game.bulletAppState.getPhysicsSpace().remove(this.floor_phy);
		
	}
}
