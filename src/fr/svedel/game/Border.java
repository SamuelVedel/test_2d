package fr.svedel.game;

import java.util.List;

import fr.svedel.engine.graph.Model;

public class Border extends Cube {
	private RectCollision rectCollision;
	
	/**
	 * Collision type.
	 * this int is composed by the
	 * constants in RectCollision
	 */
	private int collisionType;
	
	public Border(float x, float y, float width, float height, int collisionType, Model model) {
		super(x, y, width, height, model);
		this.collisionType = collisionType;
		this.type = BORDER_TYPE;
		this.rectCollision = new RectCollision(x, y, 0, 0, width, height);
	}
	
	public Border(float x, float y, int collisionType, Model model) {
		this(x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT, collisionType, model);
	}
	
	public int hasCollide(RectCollision rc) {
		int collision = this.rectCollision.hasCollide(rc);
		return collision&(this.collisionType|RectCollision.IN_CONTACT);
	}
	
	public void collide(Mob mob, RectCollision rc) {
		int collision = hasCollide(rc);
		if ((collision&RectCollision.RIGHT_COLLISION) != 0) {
			mob.setX(this.getX()+this.getWidth()-rc.getAnchorX());
		}
		if ((collision&RectCollision.BOTTOM_COLLISION) != 0) {
			mob.setY(this.getY()+this.getHeight()-rc.getAnchorY());
		}
		if ((collision&RectCollision.LEFT_COLLISION) != 0) {
			mob.setY(this.getY()-rc.getWidth()-rc.getAnchorX());
		}
		if ((collision&RectCollision.TOP_COLLISION) != 0) {
			mob.setY(this.getY()-rc.getHeight()-rc.getAnchorY());
			mob.resetFall();
		}
	}
}
