package fr.svedel.game;

public class Spider extends Mob {
	
	private static final float WIDTH = 20*World.PIXEL_SIZE*2;
	private static final float HEIGHT = 9*World.PIXEL_SIZE*2;
	
	private String texturePath = "resources/textures/spider.png";
	private String normalMapPath = "resources/textures/spider.norm.tiny.png";
	
	public Spider(float x, float y) {
		super(x, y, WIDTH, HEIGHT);
		this.getEntity().setTexturePath(texturePath);
		this.getEntity().setNormalMapPath(normalMapPath);
	}
	
	public Spider() {
		this(0, 0);
	}
	
	@Override
	public void actions(float delta) {
		this.applieGravitiy(delta);
		if (this.getY() > 500) {
			this.setY(50);
			this.setVy(0);
		}
		this.updateEntity();
	}
	
}
