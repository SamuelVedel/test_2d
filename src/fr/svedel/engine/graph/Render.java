package fr.svedel.engine.graph;

import org.lwjgl.opengl.GL;

import static org.lwjgl.opengl.GL11.*;

import fr.svedel.engine.Window;
import fr.svedel.engine.scene.Scene;

public class Render {

	private SceneRender sceneRender;

	public Render(Window window) {
		GL.createCapabilities();
		glEnable(GL_DEPTH_TEST);
		sceneRender = new SceneRender();
	}

	public void cleanup() {
		sceneRender.cleanup();
	}

	public void render(Window window, Scene scene) {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glViewport(0, 0, window.getBufferWidth(), window.getBufferHeight());

		sceneRender.render(scene);
	}

	public void resize(int width, int height) {
		
	}
}
