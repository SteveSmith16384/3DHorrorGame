package com.scs.slenderman.effects;

import com.scs.slenderman.HorrorGame;
import com.scs.slenderman.IProcessable;
import com.scs.slenderman.entities.Collectable;

public class DistanceToClosestCollectable implements IProcessable {

	private static final float INTERVAL = 2;
	
	private float nextCalc = 0;
	public float closestDistance;
	private HorrorGame game;
	
	public DistanceToClosestCollectable(HorrorGame _game) {
		super();
		
		game = _game;
	}
	
	
	@Override
	public void process(float tpf) {
		nextCalc -= tpf;
		//closestDistance = nextCalc;
		if (nextCalc <= 0) {
			calcDistance();
			nextCalc = INTERVAL;
		}
	}
	
	
	private void calcDistance() {
		closestDistance = 9999;
		for (Collectable c : game.coll_remaining) {
			float dist = c.distance(game.player.getGeometry().getWorldTranslation());
			if (dist < closestDistance) {
				closestDistance = dist;
			}
		}
	}

}