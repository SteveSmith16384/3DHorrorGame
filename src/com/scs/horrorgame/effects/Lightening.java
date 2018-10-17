package com.scs.horrorgame.effects;

import com.jme3.math.ColorRGBA;
import com.scs.horrorgame.HorrorGame;
import com.scs.horrorgame.IProcessable;

public class Lightening implements IProcessable {

	private float next_flash = 10;
	private HorrorGame game;
	private boolean flash_on = false;

	public Lightening(HorrorGame _game) {
		super();

		game = _game;
	}
	

	@Override
	public void process(float tpf) {
		next_flash -= tpf;

		if (next_flash <= 0) {
			if (flash_on) {
				// Restore
				game.getViewPort().setBackgroundColor(new ColorRGBA(0.1f, 0.1f, 0.1f, 1f));
				flash_on = false;
				next_flash = 5 + (HorrorGame.rnd.nextFloat() * 5);
			} else {
				next_flash = .1f; //Duration of flash
				game.getViewPort().setBackgroundColor(new ColorRGBA(1f, 1f, 1f, 1f));
				flash_on = true;
				game.thunderclap_sound_node.playInstance();
				HorrorGame.p("Lightening!");
			}
		}
	}

}

