package fr.svedel.game;

import fr.svedel.engine.scene.Entity;

public abstract class Mob {
	protected float x;
	protected float y;
	protected float width;
	protected float height;
	
	protected Entity tity = new Entity(ModelInstances.BLANK.getModel());
	
	public Mob(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		setDepth(Room.MOB_DEPTH);
		updateEntity();
	}
	
	public Mob() {
		this(0, 0, 0, 0);
	}
	
	public float getX() {
		return x;
	}
	
	public void refreshEntityPos() {
		tity.setPosition(x, y);
		tity.updateModelMatrix();
	}
	
	public void refreshEntityScale() {
		tity.setScale(width, height);
		tity.updateModelMatrix();
	}
	
	public void setX(float x) {
		this.x = x;
		refreshEntityPos();
	}
	
	public float getY() {
		return y;
	}
	
	public void setY(float y) {
		this.y = y;
		refreshEntityPos();
	}
	
	public float getWidth() {
		return width;
	}
	
	public void setWidth(float width) {
		width = width;
		refreshEntityScale();
	}
	
	public float getHeight() {
		return height;
	}
	
	public void setHeight(float height) {
		height = height;
		refreshEntityScale();
	}
	
	public float getDepth() {
		return tity.getDepth();
	}
	
	public void setDepth(float depth) {
		tity.setDepth(depth);
	}
	
	public Entity getEntity() {
		return tity;
	}
	
	public void updateEntity() {
		tity.setScale(width, height);
		tity.setPosition(x, y);
		tity.updateModelMatrix();
	}
}
