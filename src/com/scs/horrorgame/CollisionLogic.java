package com.scs.horrorgame;

import com.scs.horrorgame.entities.AbstractEntity;
import com.scs.horrorgame.entities.Collectable;
import com.scs.horrorgame.entities.Player;
import com.scs.horrorgame.entities.monsters.AbstractMonster;

public class CollisionLogic {

	public static void collision(HorrorGame game, AbstractEntity a, AbstractEntity b) {
		if (a instanceof Player && b instanceof Collectable) {
			Player_Collectable(game, (Player)a, (Collectable)b);
		}
		if (a instanceof Collectable && b instanceof Player) {
			Player_Collectable(game, (Player)b, (Collectable)a);
		}
		if (a instanceof Player && b instanceof AbstractMonster) {
			Player_Monster(game, (Player)a, (AbstractMonster)b);
		}
		if (a instanceof AbstractMonster && b instanceof Player) {
			Player_Monster(game, (Player)b, (AbstractMonster)a);
		}
	}
	
	
	private static void Player_Collectable(HorrorGame game, Player player, Collectable col) {
		col.remove();
		game.coll_remaining.remove(col);
		if (game.coll_remaining.isEmpty()) {
			game.gameOver(true, null);
		}
	}
	

	private static void Player_Monster(HorrorGame game, Player player, AbstractMonster col) {
		// Do nothing - we do this by checking the distance
		//game.gameOver(false);
	}
	
}
