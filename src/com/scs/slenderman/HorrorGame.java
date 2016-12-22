package com.scs.slenderman;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.VideoRecorderAppState;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.font.BitmapFont;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.Light;
import com.jme3.light.LightList;
import com.jme3.light.SpotLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.FogFilter;
import com.jme3.renderer.Camera.FrustumIntersect;
import com.jme3.scene.Spatial;
import com.scs.slenderman.gameentities.Collectable;
import com.scs.slenderman.gameentities.Entity;
import com.scs.slenderman.gameentities.Monster;
import com.scs.slenderman.gameentities.Tree;
import com.scs.slenderman.hud.HUD;
import com.scs.slenderman.map.IMapInterface;
import com.scs.slenderman.map.RandomMap;
import com.scs.slenderman.shapes.CreateShapes;

/**
 * Todo:-
 * Don't have trees near spider
 * Spider's collision box is wrong size
 * Add fences
 * Add blue hue to gfx?
 * DONE Fog
 * Floor slowly gives way
 * 
 * Add models - Look for statue models
 * Gravestone
 * Gate
 * Metal grill fence with transp texture
 * Sfx - heavy breathing
 * Have sound that gets louder as enemy approaches
 * Move a direction light for nice effect
 * Lightbox with moon in sky?  No, fog
 * 
 * Brief spec:
 * The player must walk around a spooky landscape and collect something.
 * They are being followed by a monster who can't move when being looked at.
 * The player must identify the enemy and collect the pieces.
 * The monster takes on different forms.  What is the monster?
 * 
 */

public class HorrorGame extends SimpleApplication implements ActionListener, PhysicsCollisionListener {

	// Our movement speed
	private static final float speed = 6f;
	private static final float strafeSpeed = 4f;
	//private static final float headHeight = 3f; // Dist above playermodel centre to position  cam

	public BulletAppState bulletAppState;

	private Vector3f walkDirection = new Vector3f();
	private boolean left = false, right = false, up = false, down = false;
	public Player player;

	//Temporary vectors used on each frame.
	private Vector3f camDir = new Vector3f();
	private Vector3f camLeft = new Vector3f();

	private SpotLight spotlight;
	private Monster monster;
	private HUD hud;
	public int num_collected = 0;
	public boolean game_over = false;
	private VideoRecorderAppState video_recorder;
	private static final Random rnd = new Random();


	public List<IProcessable> objects = new ArrayList<IProcessable>();


	public static void main(String[] args) {
		HorrorGame app = new HorrorGame();
		app.start();
	}


	public void simpleInitApp() {
		assetManager.registerLocator("assets/", FileLocator.class); // default

		BitmapFont guiFont_small = assetManager.loadFont("Interface/Fonts/Console.fnt");

		cam.setFrustumPerspective(45f, (float) cam.getWidth() / cam.getHeight(), 0.01f, Settings.CAM_DIST);

		// Set up Physics
		bulletAppState = new BulletAppState();
		stateManager.attach(bulletAppState);
		//bulletAppState.getPhysicsSpace().enableDebug(assetManager);

		viewPort.setBackgroundColor(new ColorRGBA(0.1f, 0.1f, 0.1f, 1f));

		setUpKeys();
		setUpLight();

		FilterPostProcessor fpp = new FilterPostProcessor(assetManager);
		FogFilter fog = new FogFilter(ColorRGBA.Black, .5f, Settings.CAM_DIST/2);
		fpp.addFilter(fog);
		viewPort.addProcessor(fpp);

		player = new Player(this);
		rootNode.attachChild(player.getMainNode());
		this.objects.add(player);

		createMonster();
		IMapInterface map = new RandomMap();
		loadMap(map);
		addCollectables(map.getNumCollectables(), map.getWidth(), map.getDepth());

		bulletAppState.getPhysicsSpace().addCollisionListener(this);

		//CreateShapes.initFloor(assetManager, this.bulletAppState, this.rootNode, 30f, 30f);
		//CreateShapes.addBoxTL("Wall", assetManager, this.bulletAppState, this.rootNode, 4, 2f, .1f, 0, 2.1f, 4f, 0.1f);
		//CreateShapes.addCylinder_Top(assetManager, this.bulletAppState, this.rootNode, .5f, 4, 6, 4, 1, 0.1f);

		//cam.lookAt(this.monster.getGeometry().getWorldTranslation(), Vector3f.UNIT_Y);

		hud = new HUD(this, this.getAssetManager(), cam.getWidth(), cam.getHeight(), guiFont_small);
		this.guiNode.attachChild(hud);
		this.objects.add(hud);
	}


	@Override
	public void simpleUpdate(float tpf) {
		/*
		 * The direction of character is determined by the camera angle
		 * the Y direction is set to zero to keep our character from
		 * lifting of terrain. For free flying games simply ad speed 
		 * to Y axis
		 */
		if (!game_over) {
			camDir.set(cam.getDirection()).multLocal(speed, 0.0f, speed);
			camLeft.set(cam.getLeft()).multLocal(strafeSpeed);
			walkDirection.set(0, 0, 0);
			if (left) {
				walkDirection.addLocal(camLeft);
			}
			if (right) {
				walkDirection.addLocal(camLeft.negate());
			}
			if (up) {
				walkDirection.addLocal(camDir);
			}
			if (down) {
				walkDirection.addLocal(camDir.negate());
			}
			player.playerControl.setWalkDirection(walkDirection);
		}

		for(IProcessable ip : objects) {
			ip.process(tpf);
		}

		// HUD
		StringBuilder text = new StringBuilder();
		if (!game_over) {
			float dist = this.monster.getGeometry().getWorldTranslation().distance(this.player.getGeometry().getWorldTranslation());
			text.append("Distance: " + (int)dist + "\nBoxes collected: " + this.num_collected + "\n");
		} else {
			text.append("GaMe OvEr\n");
		}
		if (video_recorder != null) {
			text.append("Recording...");
		}
		this.hud.log_ta.setText(text.toString());

		/*
		 * By default the location of the box is on the bottom of the terrain
		 * we make a slight offset to adjust for head height.
		 */
		//player.getRootNode().updateGeometricState();
		Vector3f vec = player.getMainNode().getWorldTranslation();
		cam.setLocation(new Vector3f(vec.x, vec.y + Settings.PLAYER_HEIGHT, vec.z));

		if (spotlight != null) {
			this.spotlight.setPosition(cam.getLocation());
			this.spotlight.setDirection(cam.getDirection());
		}
	}


	private void loadMap(IMapInterface map) {
		// Floor first
		for (int z=0 ; z<map.getDepth() ; z+= Settings.FLOOR_SECTION_SIZE) {
			for (int x=0 ; x<map.getWidth() ; x+= Settings.FLOOR_SECTION_SIZE) {
				CreateShapes.initFloor(assetManager, bulletAppState, this.rootNode, x, z, Settings.FLOOR_SECTION_SIZE, Settings.FLOOR_SECTION_SIZE);
				//p("Creating floor at " + x + "," + z);
			}			
		}

		// Now add scenery
		for (int z=0 ; z<map.getDepth() ; z++) {
			for (int x=0 ; x<map.getWidth() ; x++) {
				int code = map.getCodeForSquare(x, z);
				switch (code) {
				case Settings.MAP_NOTHING:
					break;

				case Settings.MAP_PLAYER:
					player.playerControl.warp(new Vector3f(x, 6f, z));
					break;

				case Settings.MAP_MONSTER:
					this.monster.warp(x, z);
					break;

				case Settings.MAP_TREE:
					p("Adding tree to " + x + "," + z);
					Tree tree = new Tree(this, x, z);
					this.rootNode.attachChild(tree.getMainNode());
					break;

				default:
					throw new RuntimeException("Unknown type:" + code);
				}
			}
		}
	}


	private void setUpLight() {
		// Remove existing lights
		this.rootNode.getWorldLightList().clear(); //this.rootNode.getWorldLightList().size();
		LightList list = this.rootNode.getWorldLightList();
		for (Light it : list) {
			this.rootNode.removeLight(it);
		}

		// We add light so we see the scene
		{
			AmbientLight al = new AmbientLight();
			al.setColor(ColorRGBA.White.mult(1));
			rootNode.addLight(al);
		}

		{
			/*DirectionalLight dl = new DirectionalLight();
			dl.setColor(ColorRGBA.White);
			dl.setDirection(new Vector3f(2.8f, -2.8f, -2.8f).normalizeLocal());
			rootNode.addLight(dl);*/
		}

		this.spotlight = new SpotLight();
		spotlight.setColor(ColorRGBA.White.mult(1f));
		spotlight.setSpotRange(10f);
		spotlight.setSpotInnerAngle(FastMath.QUARTER_PI / 8);
		spotlight.setSpotOuterAngle(FastMath.QUARTER_PI / 2);
		rootNode.addLight(spotlight);
	}


	/** We over-write some navigational key mappings here, so we can
	 * add physics-controlled walking and jumping: */
	private void setUpKeys() {
		inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
		inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
		inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_W));
		inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_S));
		inputManager.addMapping("Jump", new KeyTrigger(KeyInput.KEY_SPACE));
		inputManager.addMapping(Settings.KEY_RECORD, new KeyTrigger(KeyInput.KEY_R));

		inputManager.addListener(this, "Left");
		inputManager.addListener(this, "Right");
		inputManager.addListener(this, "Up");
		inputManager.addListener(this, "Down");
		inputManager.addListener(this, "Jump");
		inputManager.addListener(this, Settings.KEY_RECORD);
	}


	/** These are our custom actions triggered by key presses.
	 * We do not walk yet, we just keep track of the direction the user pressed. */
	public void onAction(String binding, boolean isPressed, float tpf) {
		if (this.game_over == false) {
			if (binding.equals("Left")) {
				left = isPressed;
			} else if (binding.equals("Right")) {
				right= isPressed;
			} else if (binding.equals("Up")) {
				up = isPressed;
			} else if (binding.equals("Down")) {
				down = isPressed;
			} else if (binding.equals("Jump")) {
				if (isPressed) { 
					player.playerControl.jump(); 
				}
			} else if (binding.equals(Settings.KEY_RECORD)) {
				if (video_recorder == null) {
					//log("RECORDING VIDEO");
					video_recorder = new VideoRecorderAppState();
					stateManager.attach(video_recorder);
					/*if (Statics.MUTE) {
						log("Warning: sounds are muted");
					}*/
				} else {
					//log("STOPPED RECORDING");
					stateManager.detach(video_recorder);
					video_recorder = null;
				}		
			}
		}
	}


	private void createMonster() {
		monster = new Monster(this, this.assetManager, 7, 7);
		rootNode.attachChild(monster.getMainNode());
		this.objects.add(monster);
	}


	private void addCollectables(int num, int max_w, float max_d) {
		for (int i=0 ; i<num ; i++) {
			float x = rnd.nextFloat() * max_w;
			float z = rnd.nextFloat() * max_d;
			Collectable col = new Collectable(this, x, z);
			rootNode.attachChild(col.getMainNode());
		}
	}


	public FrustumIntersect getInsideOutside(Entity entity) {
		FrustumIntersect insideoutside = cam.contains(entity.getGeometry().getWorldBound());
		return insideoutside;
	}


	@Override
	public void collision(PhysicsCollisionEvent event) {
		//System.out.println(event.getObjectA().getUserObject().toString() + " collided with " + event.getObjectB().getUserObject().toString());

		Spatial ga = (Spatial)event.getObjectA().getUserObject(); 
		Entity a = ga.getUserData(Settings.ENTITY);
		/*if (a == null) {
			throw new RuntimeException("Geometry " + ga.getName() + " has no entity");
		}*/

		Spatial gb = (Spatial)event.getObjectB().getUserObject(); 
		Entity b = gb.getUserData(Settings.ENTITY);
		/*if (b == null) {
			throw new RuntimeException("Geometry " + gb.getName() + " has no entity");
		}*/

		if (a != null && b != null) {
			CollisionLogic.collision(this, a, b);
		}
	}


	public static void p(String s) {
		System.out.println(System.currentTimeMillis() + ": " + s);
	}

}
