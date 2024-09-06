package fr.svedel.engine;

import fr.svedel.engine.graph.Render;
import fr.svedel.engine.scene.Scene;

public interface IAppLogic {

	public void cleanup();

	public void init(Window window, Scene scene, Render render);

	public void input(Window window, Scene scene, long diffTimeMillis, boolean inputConsumed);

	public void update(Window window, Scene scene, long diffTimeMillis);
}
