package fr.svedel.game;

import java.util.ArrayList;
import java.util.List;

import fr.svedel.engine.graph.Material;
import fr.svedel.engine.graph.Mesh;
import fr.svedel.engine.graph.Model;

enum ModelInstances {
	
	BLANK(MeshInstances.RECT, null),
	
	BRIK(MeshInstances.RECT, "resources/textures/brik");
	
	private MeshInstances meshi;
	private String texturePath;
	private Model model;
	
	private ModelInstances(MeshInstances meshIntance, String texturePath) {
		this.meshi = meshIntance;
		this .texturePath = texturePath;
	}
	
	public static void initialize() {
		for (ModelInstances modeli: values()) {
			modeli.init();
		}
	}
	
	private void init() {
		Material mater = new Material();
		mater.getMeshList().add(meshi.getMesh());
		if (texturePath != null) {
			mater.setTexturePath(texturePath+".png");
			mater.setNormalMapPath(texturePath+".norm.png");
		}
		List<Material> materialList = new ArrayList<>();
		materialList.add(mater);
		model = new Model(materialList);
		
		meshi = null;
		texturePath = null;
	}
	
	public Model getModel() {
		return model;
	}
}
