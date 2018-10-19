package com.scs.horrorgame;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.bounding.BoundingBox;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.light.Light;
import com.jme3.light.LightList;
import com.jme3.math.ColorRGBA;
import com.jme3.post.FilterPostProcessor;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class ModelViewer extends SimpleApplication implements AnimEventListener {

	private AnimControl control;
	private FilterPostProcessor fpp;

	public static void main(String[] args) {
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

		super.getViewPort().setBackgroundColor(ColorRGBA.Black);

		setupLight();


		Spatial model = assetManager.loadModel("Models/InnansorraStatueUpload.blend");
		String animNode = "Low Poly Characte.001 (Node)";
		String animToUse = "ArmatureAction.001";
		
		model.scale(5);

		if (model instanceof Node) {
			HorrorGame.p("Listing anims:");
			JMEModelFunctions.listAllAnimations((Node)model);
			HorrorGame.p("Finished listing anims");
			
			control = JMEModelFunctions.getNodeWithControls(animNode, (Node)model);
			if (control != null) {
				control.addListener(this);
				//Globals.p("Control Animations: " + control.getAnimationNames());
				AnimChannel channel = control.createChannel();
				try {
					channel.setAnim(animToUse);
					HorrorGame.p("Runnign anim " + animToUse);
				} catch (IllegalArgumentException ex) {
					HorrorGame.pe("Try running the right anim code!");
				}
			} else {
				HorrorGame.p("No animation control on selected node '" + animNode + "'");
			}
		}

		rootNode.attachChild(model);

		this.rootNode.attachChild(JMEModelFunctions.getGrid(assetManager, 10));

		rootNode.updateGeometricState();

		model.updateModelBound();
		BoundingBox bb = (BoundingBox)model.getWorldBound();
		//Globals.p("Model w/h/d: " + (bb.getXExtent()*2) + "/" + (bb.getYExtent()*2) + "/" + (bb.getZExtent()*2));

		this.flyCam.setMoveSpeed(12f);

		fpp = new FilterPostProcessor(assetManager);
		viewPort.addProcessor(fpp);	}


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


	@Override
	public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {

	}


	@Override
	public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {

	}


}