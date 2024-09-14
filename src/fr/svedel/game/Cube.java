package fr.svedel.game;

import fr.svedel.engine.graph.Model;
import fr.svedel.engine.scene.Entity;

public class Cube extends Rectangle {
	public static final float DEFAULT_WIDTH = 16*World.PIXEL_SIZE;
	public static final float DEFAULT_HEIGHT = DEFAULT_WIDTH;
	
	public static final Cube VOID = new Cube(0, 0, 0, 0, null);
	
	private Entity tity;
	
	public Cube(float x, float y, float width, float height, Model model) {
		super(x, y, width, height);
		
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
	
	@Override
	public void setX(float x) {
		super.setX(x);
		tity.setPosition(this.getX(), this.getY());
		tity.updateModelMatrix();
	}
	
	@Override
	public void setY(float y) {
		super.setY(y);
		tity.setPosition(getX(), getY());
		tity.updateModelMatrix();
	}
	
	@Override
	public void setWidth(float width) {
		super.setWidth(width);
		tity.setScale(getWidth(), getHeight());
		tity.updateModelMatrix();
	}
	
	@Override
	public void setHeight(float height) {
		super.setHeight(height);
		tity.setScale(getWidth(), getHeight());
		tity.updateModelMatrix();
	}
}
