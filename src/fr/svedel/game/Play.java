package fr.svedel.game;

import fr.svedel.engine.Engine;
import fr.svedel.engine.graph.Render;
import fr.svedel.engine.IAppLogic;
import fr.svedel.engine.MouseInput;
import fr.svedel.engine.scene.Camera;
import fr.svedel.engine.scene.Entity;
import fr.svedel.engine.scene.lights.PointLight;
import fr.svedel.engine.scene.Scene;
import fr.svedel.engine.Window;

public class Play implements IAppLogic {
	
	/*Window window;
	Scene scene;
	Render render;*/
	
	World world;
	float scale = 3;
	PointLight pointl;
	
	public Play() {
		Window.WindowOptions winOpt = new Window.WindowOptions();
		winOpt.width = 800;
		winOpt.height = 600;
		new Engine("ha", winOpt, this).start();
	}
	
	@Override
	public void cleanup() {
		MeshInstances.cleanup();
	}
	
	@Override
	public void init(Window window, Scene scene, Render render) {
		/*this.window = window;
		this.scene = scene;
		this.render = render;*/
		window.enableFullScreen();
		MeshInstances.initialize();
		ModelInstances.initialize();
		
		world = new World();
		world.addEntitiesToScene(scene);
		world.updateSceneLights(scene);
	}
	
	@Override
	public void input(Window window, Scene scene, long diffTimeMillis, boolean inputConsumed) {
		float delta = diffTimeMillis/1000;
		MouseInput mouseInput = window.getMouseInput();
		float x = mouseInput.getMouseX()/scale;
		float y = mouseInput.getMouseY()/scale;
		world.moveCursorLight(x, y);
		
		world.actions(delta);
	}
	
	@Override
	public void update(Window window, Scene scene, long diffTimeMillis) {
		scene.getCamera().setScale(scale*2f/window.getBufferWidth(),
								   scale*-2f/window.getBufferHeight());
		scene.getCamera().updateViewMatrix();
	}
}
