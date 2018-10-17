package com.scs.horrorgame.entities;

import java.io.IOException;
import java.util.Iterator;

import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.collision.UnsupportedCollisionException;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.Savable;
import com.jme3.math.Quaternion;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.scs.horrorgame.HorrorGame;
import com.scs.horrorgame.IProcessable;

public abstract class AbstractEntity implements IProcessable, Savable {
	
	private static final float TURN_SPEED = 1f;
	
	protected HorrorGame game;
	protected Node main_node, left_node, right_node;
	public String name;
	
	public AbstractEntity(HorrorGame _game, String _name) {
		super();
		
		this.game = _game;
		name = _name;

		main_node = new Node(name + "_MainNode");
		
		left_node = new Node("left_node");
		main_node.attachChild(left_node);
		left_node.setLocalTranslation(-3, 0, 0);
		
		right_node = new Node("right_node");
		main_node.attachChild(right_node);
		right_node.setLocalTranslation(3, 0, 0);
	}

	
	/**
	 * Since the root_node contains everything including the (invisible) left & right nodes, this method is for getting the actual visible Geometry.
	 * 
	 */
	//public abstract Spatial getGeometry();
	
	public abstract void remove();
	
	public void turnLeft(float tpf) {
		this.getMainNode().rotate(new Quaternion().fromAngleAxis(-1 * TURN_SPEED * tpf, Vector3f.UNIT_Y));
	}


	public void turnRight(float tpf) {
		this.getMainNode().rotate(new Quaternion().fromAngleAxis(1 * TURN_SPEED * tpf, Vector3f.UNIT_Y));
	}
	
	
	public Node getMainNode() {
		return main_node;
	}


	public float distance(AbstractEntity o) {
		return distance(o.getMainNode().getWorldTranslation());
	}


	public float distance(Vector3f pos) {
		float dist = this.getMainNode().getWorldTranslation().distance(pos);
		return dist;
	}


	public boolean canSee(AbstractEntity cansee) {
		Ray r = new Ray(this.getMainNode().getWorldTranslation(), cansee.getMainNode().getWorldTranslation().subtract(this.getMainNode().getWorldTranslation()).normalizeLocal());
		//synchronized (module.objects) {
		//if (go.collides) {
		CollisionResults results = new CollisionResults();
		Iterator<IProcessable> it = game.objects.iterator();
		while (it.hasNext()) {
			IProcessable o = it.next();
			if (o instanceof AbstractEntity && o != this) {
				AbstractEntity go = (AbstractEntity)o;
				// if (go.collides) {
					if (go.getMainNode().getWorldBound() != null) {
						results.clear();
						try {
							go.getMainNode().collideWith(r, results);
						} catch (UnsupportedCollisionException ex) {
							System.out.println("Spatial: " + go.getMainNode());
							ex.printStackTrace();
						}
						if (results.size() > 0) {
							float go_dist = this.distance(cansee)-1;
							/*Iterator<CollisionResult> it = results.iterator();
							while (it.hasNext()) {*/
							CollisionResult cr = results.getClosestCollision();
							if (cr.getDistance() < go_dist) {
								return false;
							}
						}
					}
				//}
			}
		}
		return true;
	}

	
	@Override
	public String toString() {
		return "Entity:" + name;
	}
	

	@Override
	public void write(JmeExporter ex) throws IOException {
		
	}


	@Override
	public void read(JmeImporter im) throws IOException {
		
	}

}
