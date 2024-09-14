package fr.svedel.game;

import java.util.ArrayList;
import java.util.List;

import fr.svedel.engine.scene.Entity;

public abstract class Mob extends Rectangle {
	private float vx;
	private float vy;
	
	private List<RectCollision> rectCollisions = new ArrayList<>();
	
	private Entity tity = new Entity(ModelInstances.BLANK.getModel());
	
	public Mob(float x, float y, float width, float height) {
		super(x, y, width, height);
		this.setDepth(World.MOB_DEPTH);
		this.updateEntity();
	}
	
	public Mob() {
		this(0, 0, 0, 0);
	}
	
	public abstract void actions(float delta);
	
	public void applieGravitiy(float delta) {
		this.vy += World.GRAVITY_ACC;
		this.setY(this.getY()+this.vy);
	}
	
	public void updateEntityPos() {
		this.tity.setPosition(this.getX(), this.getY());
		this.tity.updateModelMatrix();
	}
	
	public void updateEntityScale() {
		this.tity.setScale(this.getWidth(), this.getHeight());
		this.tity.updateModelMatrix();
	}
	
	public void updateEntity() {
		this.tity.setScale(this.getWidth(), this.getHeight());
		this.tity.setPosition(this.getX(), this.getY());
		this.tity.updateModelMatrix();
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
		return this.tity.getDepth();
	}
	
	public void setDepth(float depth) {
		this.tity.setDepth(depth);
	}
	
	public Entity getEntity() {
		return this.tity;
	}
}
