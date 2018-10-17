package com.scs.horrorgame.shapes;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Quad;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;

public class AbstractBillboard extends Geometry {

	public AbstractBillboard(AssetManager assetManager, String tex, float w, float h) {
		super("Billboard", new Quad(w, h));
		
		Material mat = new Material(assetManager,"Common/MatDefs/Light/Lighting.j3md");  // create a simple material
		mat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);

		Texture t = assetManager.loadTexture(tex);
		t.setWrap(WrapMode.Repeat);
		mat.setTexture("DiffuseMap", t);

		this.setMaterial(mat);

		this.setQueueBucket(Bucket.Transparent);

	}

}