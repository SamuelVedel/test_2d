package fr.svedel.game;

import fr.svedel.engine.graph.Model;
import fr.svedel.engine.scene.Entity;

public class Cube {
	public static final float DEFAULT_WIDTH = 16*Room.PIXEL_SIZE;
	public static final float DEFAULT_HEIGHT = DEFAULT_WIDTH;
	
	public static final Cube VOID = new Cube(0, 0, 0, 0, null);
	
	private float x;
	private float y;
	private float width;
	private float height;
	private Entity tity;
	
	public Cube(float x, float y, float width, float height, Model model) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		initTity(model);
	}
	
	public Cube(float x, float y, Model model) {
		this(x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT, model);
	}
	
	private void initTity(Model model) {
		tity = new Entity(model);
		tity.setScale(width, height);
		tity.setPosition(x, y);
		tity.updateModelMatrix();
	}
	
	public Entity getEntity() {
		return tity;
	}
	
	public float getX() {
		return x;
	}
	
	public void setX(float x) {
		this.x = x;
		tity.setPosition(x, y);
		tity.updateModelMatrix();
	}
	
	public float getY() {
		return y;
	}
	
	public void setY(float y) {
		this.y = y;
		tity.setPosition(x, y);
		tity.updateModelMatrix();
	}
	
	public float getWidth() {
		return width;
	}
	
	public void setWidth(float width) {
		width = width;
		tity.setScale(width, height);
		tity.updateModelMatrix();
	}
	
	public float getHeight() {
		return height;
	}
	
	public void setHeight(float height) {
		height = height;
		tity.setScale(width, height);
		tity.updateModelMatrix();
	}
}
