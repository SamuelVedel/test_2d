package fr.svedel.engine.graph;

import org.joml.Vector4f;

import java.util.*;

public class Material {

	private List<Mesh> meshList;
	private String normalMapPath;
	private String texturePath;

	public Material() {
		meshList = new ArrayList<>();
	}

	public void cleanup() {
		meshList.forEach(Mesh::cleanup);
	}

	public List<Mesh> getMeshList() {
		return meshList;
	}

	public String getNormalMapPath() {
		return normalMapPath;
	}

	public String getTexturePath() {
		return texturePath;
	}

	public void setNormalMapPath(String normalMapPath) {
		this.normalMapPath = normalMapPath;
	}

	public void setTexturePath(String texturePath) {
		this.texturePath = texturePath;
	}
}
