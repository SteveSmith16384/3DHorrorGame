package com.scs.slenderman.shapes;

import com.jme3.asset.AssetManager;
import com.jme3.bounding.BoundingBox;
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Cylinder;
import com.jme3.texture.Texture;

public class MyCylinder extends Geometry {

	private Cylinder cyl;
	
	// radialSamples - 4 = square
	public MyCylinder(AssetManager assetManager, Vector3f start, Vector3f end, int axisSamples, int radialSamples, float radius, String tex) {
		super("MyCylinder");

		cyl = new Cylinder(axisSamples, radialSamples, radius, start.distance(end), false);
		cyl.setBound(new BoundingBox());
		cyl.updateBound();
		//cyl.scaleTextureCoordinates(new Vector2f(1, 6));
		this.setMesh(cyl);

		setLocalTranslation(FastMath.interpolateLinear(.5f, start, end));
		lookAt(end, Vector3f.UNIT_Y);

		Material mat = new Material(assetManager,"Common/MatDefs/Light/Lighting.j3md");  // create a simple material
		Texture t = assetManager.loadTexture(tex);
		mat.setTexture("DiffuseMap", t);
		this.setMaterial(mat);

	}

}