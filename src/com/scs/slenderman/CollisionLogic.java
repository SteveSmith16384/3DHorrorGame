package com.scs.slenderman;

import com.scs.slenderman.entities.Collectable;
import com.scs.slenderman.entities.Entity;
import com.scs.slenderman.entities.Monster;
import com.scs.slenderman.entities.Player;

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
		col.remove();
		game.coll_remaining.remove(col);
		if (game.coll_remaining.isEmpty()) {
			game.gameOver(true);
		}
	}
	

	private static void Player_Monster(HorrorGame game, Player player, Monster col) {
		//game.p("Hit monster!");
		game.gameOver(false);
	}
	
}
