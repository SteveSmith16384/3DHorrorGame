package com.scs.slenderman;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.bounding.BoundingBox;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.light.Light;
import com.jme3.light.LightList;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Spatial;

public class ModelViewer extends SimpleApplication {

	public static void main(String[] args){
		ModelViewer app = new ModelViewer();
		app.showSettings = false;
		app.start();
	}


	@Override
	public void simpleInitApp() {
		assetManager.registerLocator("assets/", FileLocator.class); // default
		
		//cam.setFrustumPerspective(45, settings.getWidth() / settings.getHeight(), .1f, 1000);
		cam.setFrustumPerspective(60, settings.getWidth() / settings.getHeight(), .1f, 100);

		setupLight();
		
		//Spatial model = assetManager.loadModel("Models/big_wood_barrel.obj"); // todo - use this
		//model.scale(.01f);
		//Spatial model = assetManager.loadModel("Models/cemetery/grave1.obj"); // Cross
		//Spatial model = assetManager.loadModel("Models/cemetery/grave2.obj"); // Gravestone 
		//Spatial model = assetManager.loadModel("Models/cemetery/grave3.obj"); // Large Gravestone 
		//Spatial model = assetManager.loadModel("Models/cemetery/grave4.obj"); // Pillar
		//Spatial model = assetManager.loadModel("Models/cemetery/grave5.obj"); // Crypt
		//Spatial model = assetManager.loadModel("Models/cemetery/grave6.obj"); // Long grave
		//Spatial model = assetManager.loadModel("Models/arbol_seco.blend");
		//Spatial model = assetManager.loadModel("Models/arbol_seco1.blend"); // todo - use this?
		Spatial model = assetManager.loadModel("Models/Tree_Creature.blend");
		model.scale(0.1f);

		model.setModelBound(new BoundingBox());
		model.updateModelBound();

		//model.setLocalTranslation(0, 0, 5);
		rootNode.attachChild(model);

		this.rootNode.attachChild(JMEFunctions.GetGrid(assetManager, 10));
		
		this.flyCam.setMoveSpeed(6f);
		//cam.update();
		
		rootNode.updateGeometricState();

	}


	private void setupLight() {
		// Remove existing lights
		this.rootNode.getWorldLightList().clear();
		LightList list = this.rootNode.getWorldLightList();
		for (Light it : list) {
			this.rootNode.removeLight(it);
		}

		// We add light so we see the scene
		AmbientLight al = new AmbientLight();
		al.setColor(ColorRGBA.White.mult(3f));
		rootNode.addLight(al);

		DirectionalLight dirlight = new DirectionalLight(); // FSR need this for textures to show
		//dirlight.set
		rootNode.addLight(dirlight);

	}


	@Override
	public void simpleUpdate(float tpf) {
		//System.out.println("Pos: " + this.cam.getLocation());
		//this.rootNode.rotate(0,  tpf,  tpf);
	}

	
}