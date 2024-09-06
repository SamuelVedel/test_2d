package fr.svedel.engine.graph;

import fr.svedel.engine.scene.Entity;

import java.util.*;

public class Model {

	private List<Entity> entitiesList;
	private List<Material> materialList;

	public Model(List<Material> materialList) {
		entitiesList = new ArrayList<>();
		this.materialList = materialList;
	}

	public void cleanup() {
		materialList.forEach(Material::cleanup);
	}

	public List<Entity> getEntitiesList() {
		return entitiesList;
	}

	public List<Material> getMaterialList() {
		return materialList;
	}
}
