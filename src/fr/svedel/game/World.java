package fr.svedel.game;

import java.util.ArrayList;
import java.util.List;

import fr.svedel.engine.scene.Entity;
import fr.svedel.engine.scene.lights.AmbientLight;
import fr.svedel.engine.scene.lights.PointLight;
import fr.svedel.engine.scene.lights.PointLight.Attenuation;
import fr.svedel.engine.scene.lights.SceneLights;
import fr.svedel.engine.scene.Scene;

public class World {
	
	public static final float PIXEL_SIZE = 1;
	public static final float DEFAULT_LIGHT_DEPTH = 2*Cube.DEFAULT_WIDTH;
	
	public static final float SCENE_DEPTH = 0;
	public static final float MOB_DEPTH = -0.1f;
	
	public static final float GRAVITY_ACC = .1f*PIXEL_SIZE;
	
	public static final Attenuation DEFAULT_ATTENUATION
		= new Attenuation(0.01f, PIXEL_SIZE/10000, PIXEL_SIZE/300000);
	
	private Cube[][] cubes;
	
	private Spider spid;
	
	private AmbientLight ambientLight;
	private PointLight cursorLight;
	
	private List<Entity> addedEntities = new ArrayList<>();
	private List<PointLight> addedPointLights = new ArrayList<>();
	
	public World() {
		initSpid();
		initCubes();
		initAmbientLight();
		initCursorLight();
	}
	
	public void actions(float delta) {
		this.spid.actions(delta, this);
	}
	
	private void initCubes() {
		cubes = new Cube[20][20];
		for (int iy = 0; iy < cubes.length; ++iy) {
			for (int ix = 0; ix < cubes[iy].length; ++ix) {
				if (iy > 8 && iy < 12) cubes[iy][ix] = null;
				else {
					cubes[iy][ix] = new Border(ix*Cube.DEFAULT_WIDTH, iy*Cube.DEFAULT_HEIGHT,
											   RectCollision.TOP_COLLISION,
											   ModelInstances.BRIK.getModel());
					addedEntities.add(cubes[iy][ix].getEntity());
				}
			}
		}
	}
	
	private void initSpid() {
		spid = new Spider(150, 150);
		addedEntities.add(spid.getEntity());
	}
	
	private void initAmbientLight() {
		ambientLight = new AmbientLight();
		ambientLight.setIntensity(0.2f);
	}
	
	private void initCursorLight() {
		cursorLight = new PointLight();
		cursorLight.setAttenuation(DEFAULT_ATTENUATION);
		cursorLight.setPosition(100, 100);
		cursorLight.setDepth(DEFAULT_LIGHT_DEPTH);
		//cursorLight.setColor(0, 1, 1);
		cursorLight.setIntensity(0.1f);
		addedPointLights.add(cursorLight);
	}
	
	public boolean isEmpty(int ix, int iy) {
		return cubes[iy][ix] == null;
	}
	
	public boolean isVoid(int ix, int iy) {
		return ix < 0 || iy < 0 || iy >= cubes.length || ix >= cubes[iy].length;
	}
	
	public Cube get(int ix, int iy) {
		if (isVoid(ix, iy))
			return Cube.VOID;
		return cubes[iy][ix];
	}
	
	public void moveCursorLight(float x, float y) {
		cursorLight.setPosition(x, y);
	}
	
	public void addEntitiesToScene(Scene scene) {
		for (int i = addedEntities.size()-1; i >= 0; --i) {
			scene.addEntity(addedEntities.get(i));
			addedEntities.remove(i);
		}
	}
	
	public void updateSceneLights(Scene scene) {
		SceneLights sl = scene.getSceneLights();
		sl.setAmbientLight(ambientLight);
		for (int i = addedPointLights.size()-1; i >= 0; --i) {
			sl.getPointLights().add(addedPointLights.get(i));
			addedPointLights.remove(i);
		}
	}
}
