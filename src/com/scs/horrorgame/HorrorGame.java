package com.scs.horrorgame;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.prefs.BackingStoreException;

import com.jme3.app.SimpleApplication;
import com.jme3.app.StatsAppState;
import com.jme3.app.state.VideoRecorderAppState;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.audio.AudioNode;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
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
import com.jme3.system.AppSettings;
import com.scs.horrorgame.effects.DistanceToClosestCollectable;
import com.scs.horrorgame.effects.Lightening;
import com.scs.horrorgame.entities.AbstractEntity;
import com.scs.horrorgame.entities.ChargingHarmlessMonster;
import com.scs.horrorgame.entities.Collectable;
import com.scs.horrorgame.entities.Fence;
import com.scs.horrorgame.entities.Player;
import com.scs.horrorgame.entities.SimpleCross;
import com.scs.horrorgame.entities.SimplePillar;
import com.scs.horrorgame.entities.Skull;
import com.scs.horrorgame.entities.Skull2;
import com.scs.horrorgame.entities.StoneCoffin;
import com.scs.horrorgame.entities.Tree;
import com.scs.horrorgame.entities.monsters.AbstractMonster;
import com.scs.horrorgame.entities.monsters.Monster2DGhost;
import com.scs.horrorgame.hud.HUD;
import com.scs.horrorgame.map.CSVMap;
import com.scs.horrorgame.map.IMapInterface;
import com.scs.horrorgame.shapes.CreateShapes;

public class HorrorGame extends SimpleApplication implements ActionListener, PhysicsCollisionListener {

	// Our movement speed
	private static final float speed = 3f;
	private static final float strafeSpeed = 3f;

	public BulletAppState bulletAppState;

	private Vector3f walkDirection = new Vector3f();
	private boolean left = false, right = false, up = false, down = false;
	public Player player;

	//Temporary vectors used on each frame.
	private Vector3f camDir = new Vector3f();
	private Vector3f camLeft = new Vector3f();

	private SpotLight spotlight;
	public AbstractMonster monster_that_killed_player;
	private HUD hud;
	public List<Collectable> coll_remaining = new ArrayList<>();
	private boolean game_over = false;
	private boolean player_won = false;
	private VideoRecorderAppState video_recorder;
	public static final Random rnd = new Random();
	private DistanceToClosestCollectable closest;
	public boolean started = false;

	private AudioNode ambient_node;
	private AudioNode game_over_sound_node;
	public AudioNode thunderclap_sound_node;
	private AudioNode scary_sound1, scary_sound2, bens_sfx;
	private float next_scary_sound = 10;

	public List<IProcessable> objects = new ArrayList<IProcessable>();


	public static void main(String[] args) {
		try {
			AppSettings settings = new AppSettings(true);
			try {
				settings.load(Settings.NAME);
			} catch (BackingStoreException e) {
				e.printStackTrace();
			}
			settings.setTitle(Settings.NAME + " (v" + Settings.VERSION + ")");
			if (Settings.SHOW_LOGO) {
				settings.setSettingsDialogImage("/ad_logo.png");
			} else {
				settings.setSettingsDialogImage(null);
			}

			HorrorGame app = new HorrorGame();
			app.setSettings(settings);
			app.setPauseOnLostFocus(true);

			File video, audio;
			/*if (Settings.RECORD_VID) {
				app.setTimer(new IsoTimer(60));
				video = File.createTempFile("JME-water-video", ".avi");
				audio = File.createTempFile("JME-water-audio", ".wav");
				Capture.captureVideo(app, video);
				Capture.captureAudio(app, audio);
			}*/

			app.start();

			/*if (Settings.RECORD_VID) {
				System.out.println("Video saved at " + video.getCanonicalPath());
				System.out.println("Audio saved at " + audio.getCanonicalPath());
			}*/

		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	public void simpleInitApp() {
		assetManager.registerLocator("assets/", FileLocator.class); // default
		assetManager.registerLocator("assets/Textures/", FileLocator.class);

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
		if (Settings.DEBUG_LIGHT == false) {
			FogFilter fog = new FogFilter(ColorRGBA.Red, 1f, 2f);//Settings.CAM_DIST/2);
			fog.setFogDistance(2f);
			//fpp.addFilter(fog);
		}
		viewPort.addProcessor(fpp);

		player = new Player(this);
		rootNode.attachChild(player.getMainNode());
		this.objects.add(player);

		IMapInterface map;
		try {
			map = new CSVMap("./maps/map1.csv");
		} catch (Exception e) {
			e.printStackTrace();
			try {
				map = new CSVMap("./map1.csv");
			} catch (Exception e2) {
				e2.printStackTrace();
				try {
					map = new CSVMap("/map1.csv");
				} catch (Exception e3) {
					e3.printStackTrace();
					throw new RuntimeException("Unable to load map");
				}
			}
		}
		//map = new ArrayMap();
		loadMap(map);
		addCollectables((map.getWidth() * map.getDepth())/500, map.getWidth(), map.getDepth());
		addHarmlessMonsters((map.getWidth() * map.getDepth())/500, map.getWidth(), map.getDepth());
		bulletAppState.getPhysicsSpace().addCollisionListener(this);

		hud = new HUD(this, this.getAssetManager(), cam.getWidth(), cam.getHeight(), guiFont_small);
		this.guiNode.attachChild(hud);
		this.objects.add(hud);

		this.objects.add(new Lightening(this));
		closest = new DistanceToClosestCollectable(this);
		this.objects.add(closest);

		// Audio nodes
		ambient_node = new AudioNode(assetManager, "Sound/horror ambient.ogg", true);
		ambient_node.setPositional(false);
		ambient_node.setVolume(0.3f);
		ambient_node.setLooping(true);
		this.rootNode.attachChild(ambient_node);
		try {
			ambient_node.play();
		} catch (java.lang.IllegalStateException ex) {
			// Unable to play sounds - no audiocard/speakers?
		}

		scary_sound1 = new AudioNode(assetManager, "Sound/ghost_1.ogg", false);
		scary_sound1.setVolume(.1f);
		scary_sound1.setPositional(false);
		this.rootNode.attachChild(scary_sound1);

		scary_sound2 = new AudioNode(assetManager, "Sound/churchbell.ogg", false);
		scary_sound2.setPositional(false);
		this.rootNode.attachChild(scary_sound2);

		bens_sfx = new AudioNode(assetManager, "Sound/benscarypoo.ogg", false);
		bens_sfx.setPositional(false);
		bens_sfx.setVolume(3);
		this.rootNode.attachChild(bens_sfx);

		game_over_sound_node = new AudioNode(assetManager, "Sound/excited horror sound.ogg", false);
		game_over_sound_node.setPositional(false);
		this.rootNode.attachChild(game_over_sound_node);

		thunderclap_sound_node = new AudioNode(assetManager, "Sound/rock_breaking.ogg", false);
		thunderclap_sound_node.setPositional(false);
		this.rootNode.attachChild(thunderclap_sound_node);

		stateManager.getState(StatsAppState.class).toggleStats(); // Turn off stats

	}


	@Override
	public void simpleUpdate(float tpf_secs) {
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
			player.walking = up || down || left || right;
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

			next_scary_sound -= tpf_secs;
			if (next_scary_sound <= 0) {
				playRandomScarySound();
				next_scary_sound = 5 + rnd.nextInt(10);
			}
		}

		for(IProcessable ip : objects) {
			ip.process(tpf_secs);
		}

		// HUD
		StringBuilder text = new StringBuilder();
		if (!game_over) {
			/*if (Settings.SHOW_DEBUG) {
				float dist_to_monster = 0;
				if (monster != null) {
					dist_to_monster = this.monster.getMainNode().getWorldTranslation().distance(this.player.getMainNode().getWorldTranslation());
					if (dist_to_monster <= 1) {
						//todo - re-add 
						this.gameOver(false);
					}
				}
				text.append("Distance: " + (int)dist_to_monster + "\n");
			}*/
			text.append("There are " + this.coll_remaining.size() + " boxes remaining\nThe closest box is " + this.closest.getClosestDistance() + "m away\n");
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
		Vector3f vec = player.getMainNode().getWorldTranslation();
		cam.setLocation(new Vector3f(vec.x, vec.y + Settings.PLAYER_HEIGHT, vec.z));

		if (this.isGameOver()) {
			if (this.monster_that_killed_player != null) {
				cam.lookAt(this.monster_that_killed_player.getMainNode().getWorldTranslation(), Vector3f.UNIT_Y);
			}
		}

		if (spotlight != null) {
			this.spotlight.setPosition(cam.getLocation());
			this.spotlight.setDirection(cam.getDirection());
		}

		started = true;
	}


	private void loadMap(IMapInterface map) {
		// Floor first
		for (int z=0 ; z<map.getDepth() ; z+= Settings.FLOOR_SECTION_SIZE) {
			for (int x=0 ; x<map.getWidth() ; x+= Settings.FLOOR_SECTION_SIZE) {
				//p("Creating floor at " + x + "," + z);
				CreateShapes.CreateFloorTL(assetManager, bulletAppState, this.rootNode, x, 0f, z, Settings.FLOOR_SECTION_SIZE, 0.1f, Settings.FLOOR_SECTION_SIZE, "Textures/DirtWithWeeds_S.jpg");
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
					player.playerControl.warp(new Vector3f(x, 2f, z));
					break;

				case Settings.MAP_MONSTER_GHOST:
					this.addRandomMonster(x, z);
					//AbstractEntity monster2 = new Monster2DGhost(this, x, z);
					//rootNode.attachChild(monster2.getMainNode());
					//this.objects.add(monster2);
					break;

				case Settings.MAP_MONSTER_STATUE:
					this.addRandomMonster(x, z); 

					//AbstractEntity monster = new StaticMonsterStatue(this, x, z);
					//rootNode.attachChild(monster.getMainNode());
					//this.objects.add(monster);
					break;

				case Settings.MAP_MONSTER_MOVING_STATUE:
					this.addRandomMonster(x, z);
					//AbstractEntity monster3 = new MovingMonsterStatue(this, x, z);
					//rootNode.attachChild(monster3.getMainNode());
					//this.objects.add(monster3);
					break;

				case Settings.MAP_TREE:
					AbstractEntity tree = new Tree(this, x, z); // todo - need to add these to a list so we can remove them at the end
					this.rootNode.attachChild(tree.getMainNode());
					break;

				case Settings.MAP_FENCE_LR:
					AbstractEntity fence1 = new Fence(this, x, z, 0);
					this.rootNode.attachChild(fence1.getMainNode());
					break;

				case Settings.MAP_FENCE_FB:
					AbstractEntity fence2 = new Fence(this, x, z, 90);
					this.rootNode.attachChild(fence2.getMainNode());
					break;

				case Settings.MAP_MEDIEVAL_STATUE:
					/*AbstractEntity ms = new MedievalStatue(this, x, z);
					this.rootNode.attachChild(ms.getMainNode());*/
					break;

				case Settings.MAP_SIMPLE_PILLAR:
					AbstractEntity lg = new SimplePillar(this, x, z);
					this.rootNode.attachChild(lg.getMainNode());
					break;

				case Settings.MAP_STONE_COFFIN:
					AbstractEntity gs = new StoneCoffin(this, x, z);
					this.rootNode.attachChild(gs.getMainNode());
					break;

				case Settings.MAP_SIMPLE_CROSS:
					AbstractEntity cross = new SimpleCross(this, x, z);
					this.rootNode.attachChild(cross.getMainNode());
					break;

				case Settings.MAP_SKULL:
					AbstractEntity skull = new Skull(this, x, z);
					this.rootNode.attachChild(skull.getMainNode());
					break;

				case Settings.MAP_SKULL2:
					AbstractEntity skull2 = new Skull2(this, x, z);
					this.rootNode.attachChild(skull2.getMainNode());
					break;

				case Settings.MAP_CHARGING_GHOST:
					//AbstractEntity ch = new ChargingHarmlessMonster(this, x, z);
					//this.rootNode.attachChild(ch.getMainNode());
					//this.objects.add(ch);
					break;

				default:
					p("Ignoring map code " + code);
					//throw new RuntimeException("Unknown type:" + code);
				}
			}
		}

		// Sections are general 4 wide and long and 2 high
		/*Stairs stairs = new Stairs(this, 10f, 0, 10f, 4f, 2f, 4f);
		this.rootNode.attachChild(stairs.getMainNode());

		Corridor corr = new Corridor(this, 10f, 0, 10f, 4f, 2f, 4f, false, false);
		this.rootNode.attachChild(corr.getMainNode());

		Corridor corr2 = new Corridor(this, 10f, 2f, 14f, 4f, 2f, 4f, true, false);
		this.rootNode.attachChild(corr2.getMainNode());*/

	}


	private void addRandomMonster(float x, float z) {
		/*int i = rnd.nextInt(3);
		switch (i) {
		case 0:*/
		AbstractEntity monster2 = new Monster2DGhost(this, x, z);
		rootNode.attachChild(monster2.getMainNode());
		this.objects.add(monster2);
		/*break;

		case 1:
			AbstractEntity monster = new StaticMonsterStatue(this, x, z);
			rootNode.attachChild(monster.getMainNode());
			this.objects.add(monster);
			break;

		case 2:
			AbstractEntity monster3 = new MovingMonsterStatue(this, x, z);
			rootNode.attachChild(monster3.getMainNode());
			this.objects.add(monster3);
		break;
	}
	*/
}


private void setUpLight() {
	// Remove existing lights
	this.rootNode.getWorldLightList().clear(); //this.rootNode.getWorldLightList().size();
	LightList list = this.rootNode.getWorldLightList();
	for (Light it : list) {
		this.rootNode.removeLight(it);
	}

	if (Settings.DEBUG_LIGHT == false) {
		AmbientLight al = new AmbientLight();
		al.setColor(ColorRGBA.White.mult(.5f));
		rootNode.addLight(al);

		this.spotlight = new SpotLight();
		spotlight.setColor(ColorRGBA.White.mult(3f));
		spotlight.setSpotRange(10f);
		spotlight.setSpotInnerAngle(FastMath.QUARTER_PI / 8);
		spotlight.setSpotOuterAngle(FastMath.QUARTER_PI / 2);
		rootNode.addLight(spotlight);
	} else {
		AmbientLight al = new AmbientLight();
		al.setColor(ColorRGBA.White.mult(3));
		rootNode.addLight(al);
	}
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
			right = isPressed;
		} else if (binding.equals("Up")) {
			up = isPressed;
			//p("player: " + this.player.getGeometry().getWorldTranslation());
		} else if (binding.equals("Down")) {
			down = isPressed;
		} else if (binding.equals("Jump")) {
			if (isPressed) { 
				player.playerControl.jump(); 
			}
		} else if (binding.equals(Settings.KEY_RECORD)) {
			if (isPressed) {
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
}


private void addCollectables(int num, int max_w, float max_d) {
	int INSETS = 4;
	for (int i=0 ; i<num ; i++) {
		float x = rnd.nextFloat() * (max_w-INSETS-INSETS);
		float z = rnd.nextFloat() * (max_d-INSETS-INSETS);
		Collectable col = new Collectable(this, x+INSETS, z+INSETS);
		rootNode.attachChild(col.getMainNode());
		coll_remaining.add(col);
	}
}


private void addHarmlessMonsters(int num, int max_w, float max_d) {
	int INSETS = 4;
	for (int i=0 ; i<num ; i++) {
		float x = rnd.nextFloat() * (max_w-INSETS-INSETS);
		float z = rnd.nextFloat() * (max_d-INSETS-INSETS);

		AbstractEntity ch = new ChargingHarmlessMonster(this, x, z);
		this.rootNode.attachChild(ch.getMainNode());
		this.objects.add(ch);
	}
}


public FrustumIntersect getInsideOutside(AbstractEntity entity) {
	FrustumIntersect insideoutside = cam.contains(entity.getMainNode().getWorldBound());
	return insideoutside;
}


@Override
public void collision(PhysicsCollisionEvent event) {
	//System.out.println(event.getObjectA().getUserObject().toString() + " collided with " + event.getObjectB().getUserObject().toString());
	// SkullModel (SkullModel) collided with Player_MainNode (Node)

	Spatial ga = (Spatial)event.getObjectA().getUserObject(); 
	AbstractEntity a = ga.getUserData(Settings.ENTITY);
	/*if (a == null) {
			throw new RuntimeException("Geometry " + ga.getName() + " has no entity");
		}*/

	Spatial gb = (Spatial)event.getObjectB().getUserObject(); 
	AbstractEntity b = gb.getUserData(Settings.ENTITY);
	/*if (b == null) {
			throw new RuntimeException("Geometry " + gb.getName() + " has no entity");
		}*/

	if (a != null && b != null) {
		CollisionLogic.collision(this, a, b);
	}
}


public void gameOver(boolean _player_won, AbstractMonster monster) {
	if (this.game_over == false) {
		this.monster_that_killed_player = monster;
		p("GAME OVER!");
		this.game_over = true;
		player_won =_player_won;
		game_over_sound_node.playInstance();
		player.playerControl.setGravity(new Vector3f(0f, 1f, 0f)); // float upwards
		if (!player_won) {
			hud.showDamageBox();
		}
	}
}


public boolean isGameOver() {
	return this.game_over;
}


public boolean hasPlayerWon() {
	return this.player_won;
}


private void playRandomScarySound() {
	if (Settings.USE_BENS_SOUND) {
		this.bens_sfx.playInstance();
	} else {
		int i = rnd.nextInt(2);
		switch (i) {
		case 0:
			this.scary_sound1.playInstance();
			break;
		case 1:
			this.scary_sound2.playInstance();
			break;
		}
	}
}


public static void p(String s) {
	System.out.println(System.currentTimeMillis() + ": " + s);
}


public static void pe(String s) {
	System.err.println(System.currentTimeMillis() + ": " + s);
}


public BulletAppState getBulletAppState() {
	return bulletAppState;
}


}
