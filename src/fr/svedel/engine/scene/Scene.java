package fr.svedel.engine.scene;

import fr.svedel.engine.graph.*;
import fr.svedel.engine.scene.lights.SceneLights;

import java.util.*;

public class Scene {

	private Camera camera;
	//private Fog fog;
	private List<Model> modelList;
	private SceneLights sceneLights;
	private TextureCache textureCache;

	public Scene(int width, int height) {
		modelList = new ArrayList<>();
		textureCache = new TextureCache();
		camera = new Camera();
		sceneLights = new SceneLights();
		//fog = new Fog();
	}

	public void addEntity(Entity entity) {
		Model model = entity.getModel();
		if (modelList.indexOf(model) < 0) {
			//throw new RuntimeException("Could not find model [" + modelId + "]");
			//System.out.println("Model wasn't in the list, we are adding it");
			addModel(model);
		}
		model.getEntitiesList().add(entity);
	}

	public void addModel(Model model) {
		modelList.add(model);
	}

	public void cleanup() {
		modelList.forEach(Model::cleanup);
	}

	public Camera getCamera() {
		return camera;
	}

	/*public Fog getFog() {
		return fog;
		}*/

	public List<Model> getModelList() {
		return modelList;
	}
	
	public SceneLights getSceneLights() {
		return sceneLights;
	}

	public TextureCache getTextureCache() {
		return textureCache;
	}

	public void resize(int width, int height) {
		
	}

	/*public void setFog(Fog fog) {
		this.fog = fog;
		}*/

	/*public void setSceneLights(SceneLights sceneLights) {
		this.sceneLights = sceneLights;
		}*/
}
