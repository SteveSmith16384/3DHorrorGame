package com.scs.slenderman;

import com.scs.slenderman.gameentities.Collectable;
import com.scs.slenderman.gameentities.Entity;
import com.scs.slenderman.gameentities.Monster;

public class CollisionLogic {

	public static void collision(HorrorGame game, Entity a, Entity b) {
		if (a instanceof Player && b instanceof Collectable) {
			Player_Collectable(game, (Player)a, (Collectable)b);
		}
		if (a instanceof Collectable && b instanceof Player) {
			Player_Collectable(game, (Player)b, (Collectable)a);
		}
		if (a instanceof Player && b instanceof Monster) {
			Player_Monster(game, (Player)a, (Monster)b);
		}
		if (a instanceof Monster && b instanceof Player) {
			Player_Monster(game, (Player)b, (Monster)a);
		}
	}
	
	
	private static void Player_Collectable(HorrorGame game, Player player, Collectable col) {
		col.remove();//getMainNode().removeFromParent();
		//game.bulletAppState.getPhysicsSpace().remove(col.);
		game.num_collected++;
	}
	

	private static void Player_Monster(HorrorGame game, Player player, Monster col) {
		//game.game_over = true;
		game.p("Hit monster!");
	}
	
}
