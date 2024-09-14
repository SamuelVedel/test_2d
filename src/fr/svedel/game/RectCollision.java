package fr.svedel.game;

public class RectCollision {
	private float x;
	private float y;
	private float width;
	private float height;
	
	private float oldX;
	private float oldY;
	private float oldWidth;
	private float oldHeight;
	
	public static final int NO_COLLISION = 0;
	public static final int IN_CONTACT = 1;
	public static final int RIGHT_COLLISION = 1<<1;
	public static final int BOTTOM_COLLISION = 1<<2;
	public static final int LEFT_COLLISION = 1<<3;
	public static final int TOP_COLLISION = 1<<4;
	
	public RectCollision(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public RectCollision(int width, int height) {
		this(0, 0, width, height);
	}
	
	public RectCollision() {
		this(0, 0);
	}
	
	public void saveOldValues() {
		this.oldX = this.x;
		this.oldY = this.y;
		this.oldWidth = this.width;
		this.oldHeight = this.height;
	}
	
	public int hasCollide(RectCollision rc) {
		int collision = NO_COLLISION;
		// collision test
		if (this.x+this.width > rc.x && this.x < rc.x+rc.width
			&& this.y+this.height > rc.y && this.y < rc.y+rc.height) {
			collision += IN_CONTACT;
			
			// check witch side had collided
			if (this.oldX+this.oldWidth < rc.oldX)
				collision += RIGHT_COLLISION;
			if (this.oldY+this.oldHeight < rc.oldY)
				collision += BOTTOM_COLLISION;
			if (this.oldX > rc.oldX+rc.oldWidth)
				collision += LEFT_COLLISION;
			if (this.oldY > rc.oldY+rc.oldHeight)
				collision += TOP_COLLISION;
		}
		return collision;
	}
	
	public float getX() {
		return this.x;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public float getY() {
		return this.y;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public float getWidth() {
		return this.width;
	}
	
	public void setWidth(float width) {
		this.width = width;
	}
	
	public float getHeight() {
		return this.height;
	}
	
	public void setHeight(float height) {
		this.height = height;
	}
}
