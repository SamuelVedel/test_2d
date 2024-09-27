package fr.svedel.game;

import org.lwjgl.glfw.GLFW;

import fr.svedel.engine.Window;

public class Spider extends Mob {
	
	private static final float WIDTH = 20*World.PIXEL_SIZE*2;
	private static final float HEIGHT = 9*World.PIXEL_SIZE*2;
	
	private static final float DEFAULT_VX = 5f*Cube.DEFAULT_WIDTH;
	private static final float DEFAULT_VJUMP = -5f*Cube.DEFAULT_WIDTH;
	
	private boolean rightPressed = false;
	private boolean leftPressed = false;
	private boolean jumpPressed = false;
	
	private String texturePath = "resources/textures/spider.png";
	private String normalMapPath = "resources/textures/spider.norm.tiny.png";
	
	public Spider(float x, float y) {
		super(x, y, WIDTH, HEIGHT);
		this.getEntity().setTexturePath(texturePath);
		this.getEntity().setNormalMapPath(normalMapPath);
		this.getRectCollisions().add(new RectCollision(x, y, 0, 0, WIDTH, HEIGHT));
		this.initAttributes();
	}
	
	public Spider() {
		this(0, 0);
	}
	
	private void initAttributes() {
		this.setVx(DEFAULT_VX);
		this.setVJump(DEFAULT_VJUMP);
	}
	
	public void input(Window window) {
		this.rightPressed = window.isKeyPressed(GLFW.GLFW_KEY_D);
		this.leftPressed = window.isKeyPressed(GLFW.GLFW_KEY_A);
		this.jumpPressed = window.isKeyPressed(GLFW.GLFW_KEY_SPACE);
	}
	
	@Override
	public void actions(float delta, World world) {
		this.updateCollisions();
		this.saveOldCollisions();
		
		applieInputs(delta);
		
		this.applieGravitiy(delta);
		if (this.getY() > 500) {
			this.setY(50);
			this.setVy(0);
		}
		
		this.applieCollision(world);
		
		this.updateEntity();
	}
	
	private void applieInputs(float delta) {
		if (this.rightPressed) {
			this.addX(this.getVx()*delta);
		}
		if (this.leftPressed) {
			this.addX(-this.getVx()*delta);
		}
		if (this.jumpPressed && this.canJump()) {
			this.setVy(this.getVJump());
			this.setCanJump(false);
		}
	}
	
}
