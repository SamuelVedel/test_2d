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
	
	public abstract void actions(float delta, World world);
	
	public void applieGravitiy(float delta) {
		this.vy += World.GRAVITY_ACC;
		this.setY(this.getY()+this.vy);
	}
	
	public void applieCollision(World world) {
		updateCollisions();
		for (int i = rectCollisions.size()-1; i >= 0; --i) {
			RectCollision rc = rectCollisions.get(i);
			float x = rc.getX();
			float y = rc.getY();
			float width = rc.getWidth();
			float height = rc.getHeight();
			
			int startx = (int)(x/Cube.DEFAULT_WIDTH)-1;
			int endx = (int)((x+width)/Cube.DEFAULT_WIDTH)+1;
			int starty = (int)(y/Cube.DEFAULT_HEIGHT)-1;
			int endy = (int)((x+height)/Cube.DEFAULT_HEIGHT)+1;
			
			for (int iy = starty; iy <= endy; ++iy) {
				for (int ix = startx; ix <= endx; ++ix) {
					if (!world.isVoid(ix, iy) && !world.isEmpty(ix, iy)) {
						Cube cube = world.get(ix, iy);
						if (cube.getType() == Cube.BORDER_TYPE) {
							Border border = (Border) cube;
							border.collide(this);
						}
					}
				}
			}
		}
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
	
	public void saveOldCollisions() {
		for (int i = rectCollisions.size()-1; i >= 0; --i) {
			rectCollisions.get(i).saveOldValues();
		}
	}
	
	public void updateCollisions() {
		for (int i = rectCollisions.size()-1; i >= 0; --i) {
			rectCollisions.get(i).updatePosition(this.getX(), this.getY());
		}
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
	
	public List<RectCollision> getRectCollisions() {
		return this.rectCollisions;
	}
}
