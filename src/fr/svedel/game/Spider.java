package fr.svedel.game;

public class Spider extends Mob {
	
	private static final float WIDTH = 20*Room.PIXEL_SIZE*2;
	private static final float HEIGHT = 9*Room.PIXEL_SIZE*2;
	
	private String texturePath = "resources/textures/spider.png";
	private String normalMapPath = "resources/textures/spider.norm.tiny.png";
	
	public Spider(float x, float y) {
		super(x, y, WIDTH, HEIGHT);
		tity.setTexturePath(texturePath);
		tity.setNormalMapPath(normalMapPath);
	}
	
	public Spider() {
		this(0, 0);
	}
	
}
