package fr.svedel.game;

import java.util.ArrayList;
import java.util.List;

import fr.svedel.engine.scene.Entity;

public abstract class Mob {
	private float x;
	private float y;
	private float width;
	private float height;
	
	private float vx;
	private float vy;
	
	private List<RectCollision> rectCollisions = new ArrayList<>();
	
	private Entity tity = new Entity(ModelInstances.BLANK.getModel());
	
	public Mob(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		setDepth(World.MOB_DEPTH);
		updateEntity();
	}
	
	public Mob() {
		this(0, 0, 0, 0);
	}
	
	public abstract void actions(float delta);
	
	public void applieGravitiy(float delta) {
		this.vy += World.GRAVITY_ACC;
		this.y += this.vy;
	}
	
	public void updateEntityPos() {
		tity.setPosition(x, y);
		tity.updateModelMatrix();
	}
	
	public void updateEntityScale() {
		tity.setScale(width, height);
		tity.updateModelMatrix();
	}
	
	public void updateEntity() {
		tity.setScale(width, height);
		tity.setPosition(x, y);
		tity.updateModelMatrix();
	}
	
	public float getX() {
		return x;
	}
	
	public void setX(float x) {
		this.x = x;
		//updateEntityPos();
	}
	
	public float getY() {
		return y;
	}
	
	public void setY(float y) {
		this.y = y;
		//updateEntityPos();
	}
	
	public float getWidth() {
		return width;
	}
	
	public void setWidth(float width) {
		width = width;
		//updateEntityScale();
	}
	
	public float getHeight() {
		return height;
	}
	
	public void setHeight(float height) {
		height = height;
		//updateEntityScale();
	}
	
	public float getVx() {
		return this.vx;
	}
	
	public void setVx(float vx) {
		this.vx = vx;
	}
	
	public float getVy() {
		return this.vy;
	}
	
	public void setVy(float vy) {
		this.vy = vy;
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
}
