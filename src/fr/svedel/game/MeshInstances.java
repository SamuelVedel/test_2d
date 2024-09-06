package fr.svedel.game;

import fr.svedel.engine.graph.Mesh;

public enum MeshInstances {
	
	RECT(
		 new float[] {
			 0, 0, // 0 top left corner |-
			 1, 0, // 1 top right corner -|
			 0, 1, // 2 bottom left corner |_
			 1, 1  // 3 bottom right corner _|
		 },
		 new float[] {
			 0, 0,
			 1, 0,
			 0, 1,
			 1, 1
		 },
		 new int[] {
			 1, 0, 2,
			 1, 2, 3
		 }
		 );
	
	private float[] positions;
	private float[] textCoords;
	private int[] indices;
	private Mesh mesh;
	
	private MeshInstances(float[] positions, float[] textCoords, int[] indices) {
		this.positions = positions;
		this.textCoords =textCoords;
		this.indices = indices;
	}
	
	public static void initialize() {
		for (MeshInstances mi: values()) {
			mi.init();
		}
	}
	
	public static void cleanup() {
		for (MeshInstances mi: values()) {
			mi.getMesh().cleanup();
		}
	}
	
	private void init() {
		mesh = new Mesh(positions, textCoords, indices);
		positions = null;
		textCoords = null;
		indices = null;
	}
	
	public Mesh getMesh() {
		return mesh;
	}
}
