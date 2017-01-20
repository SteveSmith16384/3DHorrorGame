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
		
		//assetManager.registerLocator("assets/Models/", FileLocator.class);
		assetManager.registerLocator("assets/Textures/", FileLocator.class);
		/*assetManager.registerLocator("assets/Sound/", FileLocator.class);
		assetManager.registerLocator("assets/Shaders/", FileLocator.class);
		*/
		
		cam.setFrustumPerspective(60, settings.getWidth() / settings.getHeight(), .1f, 100);

		setupLight();
		
		//Spatial model = assetManager.loadModel("Models/big_wood_barrel.obj"); // todo - use this
		//model.scale(.01f);

		//Spatial model = assetManager.loadModel("Models/arbol_seco.blend");
		
		/*Spatial model = assetManager.loadModel("Models/Tree_Creature.blend");
		model.scale(0.1f);*/

		/*Spatial model = assetManager.loadModel("Models/InnansorraStatueUpload.blend");
		model.scale(1.3f);
		model.setLocalTranslation(0, 1f, 0);*/

		/*Spatial model = assetManager.loadModel("Models/Stone_coffin.obj");
		model.scale(0.1f);
		JMEFunctions.SetTextureOnSpatial(assetManager, model, "stonecoffin.tga");*/
		
		Spatial model = assetManager.loadModel("Models/skull2/skull/skull.obj");
		JMEFunctions.SetTextureOnSpatial(assetManager, model, "skull.tga");

		model.setModelBound(new BoundingBox());
		model.updateModelBound();

		rootNode.attachChild(model);

		this.rootNode.attachChild(JMEFunctions.GetGrid(assetManager, 10));
		
		this.flyCam.setMoveSpeed(12f);
		
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
		al.setColor(ColorRGBA.White.mult(4f));
		rootNode.addLight(al);

		DirectionalLight dirlight = new DirectionalLight(); // FSR need this for textures to show
		rootNode.addLight(dirlight);

	}


	@Override
	public void simpleUpdate(float tpf) {
		//System.out.println("Pos: " + this.cam.getLocation());
		//this.rootNode.rotate(0,  tpf,  tpf);
	}

	
}