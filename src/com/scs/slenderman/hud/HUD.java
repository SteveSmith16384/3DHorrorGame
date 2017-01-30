package com.scs.slenderman.hud;

import com.jme3.asset.AssetManager;
import com.jme3.bounding.BoundingBox;
import com.jme3.font.BitmapFont;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Quad;
import com.scs.slenderman.HorrorGame;
import com.scs.slenderman.IProcessable;
import com.scs.slenderman.gui.TextArea;

/*
 * Positioning text = the co-ords of BitmapText are for the top-left of the first line of text, and they go down from there.
 * 
 */
public class HUD extends Node implements IProcessable {

	public TextArea log_ta, options;
	private int screen_width, screen_height;
	protected HorrorGame module;

	private Geometry damage_box;
	private ColorRGBA dam_box_col = new ColorRGBA(1, 0, 0, 0.0f);
	private boolean process_damage_box;

	public HUD(HorrorGame _module, AssetManager assetManager, int w, int h, BitmapFont font_small) {
		super("HUD");

		module = _module;
		screen_width = w;
		screen_height = h;

		log_ta = new TextArea("log", font_small, 6, "");
		log_ta.setLocalTranslation(0, screen_height, 0);
		this.attachChild(log_ta);

		// Damage box
		Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		mat.setColor("Color", this.dam_box_col);
		mat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
		damage_box = new Geometry("damagebox", new Quad(w, h));
		damage_box.move(0, 0, 0);
		damage_box.setMaterial(mat);
		this.attachChild(damage_box);

		this.updateGeometricState();

		this.setModelBound(new BoundingBox());
		this.updateModelBound();

	}


	@Override
	public void process(float tpf) {
		if (process_damage_box) {
			this.dam_box_col.a -= (tpf/2);
			if (dam_box_col.a < 0) {
				dam_box_col.a = 0;
				process_damage_box = false;
			}
		}

	}


	public void updateAllText() {
	}


	public void log(String s) {
		this.log_ta.addLine(s);
	}


	public void showDamageBox() {
		//process_damage_box = true;
		this.dam_box_col.a = .5f;
		this.dam_box_col.r = 1f;
		this.dam_box_col.g = 0f;
		this.dam_box_col.b = 0f;
	}


}
