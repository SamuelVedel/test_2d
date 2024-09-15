package fr.svedel.game;

public class RectCollision extends Rectangle {
	
	private float anchorX;
	private float anchorY;
	
	private Rectangle oldRect;
	
	public static final int NO_COLLISION = 0;
	public static final int IN_CONTACT = 1;
	public static final int RIGHT_COLLISION = 1<<1;
	public static final int BOTTOM_COLLISION = 1<<2;
	public static final int LEFT_COLLISION = 1<<3;
	public static final int TOP_COLLISION = 1<<4;
	
	public RectCollision(float x, float y, float anchorX, float anchorY,
						 float width, float height) {
		super(x+anchorX, y+anchorY, width, height);
		this.anchorX = anchorX;
		this.anchorY = anchorY;
		this.oldRect = new Rectangle(x+anchorX, y+anchorY, width, height);
	}
	
	public RectCollision(float anchorX, float anchorY, float width, float height) {
		this(0, 0, anchorX, anchorY, width, height);
	}
	
	public RectCollision(int width, int height) {
		this(0, 0, width, height);
	}
	
	public RectCollision() {
		this(0, 0);
	}
	
	public void saveOldValues() {
		this.oldRect.setValues(this);
	}
	
	public int hasCollide(RectCollision rc) {
		int collision = NO_COLLISION;
		// collision test
		if (this.x+this.width > rc.x && this.x < rc.x+rc.width
			&& this.y+this.height > rc.y && this.y < rc.y+rc.height) {
			collision |= IN_CONTACT;
			
			// check witch side had collided
			if (this.oldRect.getX()+this.oldRect.getWidth() < rc.oldRect.getX())
				collision |= RIGHT_COLLISION;
			if (this.oldRect.getY()+this.oldRect.getHeight() < rc.oldRect.getY())
				collision |= BOTTOM_COLLISION;
			if (this.oldRect.getX() > rc.oldRect.getX()+rc.oldRect.getWidth())
				collision |= LEFT_COLLISION;
			if (this.oldRect.getY() > rc.oldRect.getY()+rc.oldRect.getHeight())
				collision |= TOP_COLLISION;
		}
		
		System.out.println("a: "+collision);
		return collision;
	}
	
	public void updatePosition(float x, float y) {
		this.x = x+this.anchorX;
		this.y = y+this.anchorY;
	}
	
	public float getAnchorX() {
		return this.anchorX;
	}
	
	public void setAnchorX(float anchorX) {
		this.anchorX = anchorX;
	}
	
	public float getAnchorY() {
		return this.anchorY;
	}
	
	public void setAnchorY(float anchorY) {
		this.anchorY = anchorY;
	}
}
