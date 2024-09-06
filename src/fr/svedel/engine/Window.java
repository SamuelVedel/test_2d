package fr.svedel.engine;

import org.lwjgl.glfw.*;
import org.lwjgl.system.MemoryUtil;
//import org.tinylog.Logger;

import java.util.concurrent.Callable;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {

	private final long windowHandle;
	private int width;
	private int height;
	private int bufferWidth;
	private int bufferHeight;
	private MouseInput mouseInput;
	private Callable<Void> resizeFunc;
	private boolean isInFullScreen = false;

	public Window(String title, WindowOptions opts, Callable<Void> resizeFunc) {
		this.resizeFunc = resizeFunc;
		if (!glfwInit()) {
			throw new IllegalStateException("Unable to initialize GLFW");
		}

		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GL_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);

		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
		if (opts.compatibleProfile) {
			glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_COMPAT_PROFILE);
		} else {
			glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
			glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
		}

		if (opts.width > 0 && opts.height > 0) {
			this.width = opts.width;
			this.height = opts.height;
		} else {
			glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);
			GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
			width = vidMode.width();
			height = vidMode.height();
		}

		windowHandle = glfwCreateWindow(width, height, title, NULL, NULL);
		if (windowHandle == NULL) {
			throw new RuntimeException("Failed to create the GLFW window");
		}

		glfwSetFramebufferSizeCallback(windowHandle, (window, w, h) -> resized(w, h));

		glfwSetErrorCallback((int errorCode, long msgPtr) -> 
							 //Logger.error("Error code [{}], msg [{}]", errorCode, MemoryUtil.memUTF8(msgPtr))
							 System.out.println("Error code "+errorCode+", msg "+MemoryUtil.memUTF8(msgPtr))
		);

		glfwSetKeyCallback(windowHandle, (window, key, scancode, action, mods) -> {
			keyCallBack(key, action);
		});

		glfwMakeContextCurrent(windowHandle);

		if (opts.fps > 0) {
			glfwSwapInterval(0);
		} else {
			glfwSwapInterval(1);
		}

		glfwShowWindow(windowHandle);

		int[] arrWidth = new int[1];
		int[] arrHeight = new int[1];
		glfwGetFramebufferSize(windowHandle, arrWidth, arrHeight);
		bufferWidth = arrWidth[0];
		bufferHeight = arrHeight[0];

		mouseInput = new MouseInput(windowHandle);
	}
	
	public void enableFullScreen() {
		glfwSetWindowMonitor(windowHandle, glfwGetPrimaryMonitor(),
							 0, 0, width, height, GLFW_DONT_CARE);
		isInFullScreen = true;
	}
	
	public void disableFullScreen() {
		glfwSetWindowMonitor(windowHandle, NULL,
							 0, 0, width, height, GLFW_DONT_CARE);
		isInFullScreen = false;
	}
	
	public void toggleFullScreen() {
		if (isInFullScreen) {
			disableFullScreen();
		} else {
			enableFullScreen();
		}
	}
	
	public void cleanup() {
		glfwFreeCallbacks(windowHandle);
		glfwDestroyWindow(windowHandle);
		glfwTerminate();
		GLFWErrorCallback callback = glfwSetErrorCallback(null);
		if (callback != null) {
			callback.free();
		}
	}

	public int getBufferHeight() {
		return bufferHeight;
	}

	public MouseInput getMouseInput() {
		return mouseInput;
	}

	public int getBufferWidth() {
		return bufferWidth;
	}

	public long getWindowHandle() {
		return windowHandle;
	}

	public boolean isKeyPressed(int keyCode) {
		return glfwGetKey(windowHandle, keyCode) == GLFW_PRESS;
	}

	public void keyCallBack(int key, int action) {
		if (action == GLFW_RELEASE) {
			if (key == GLFW_KEY_ESCAPE) {
				glfwSetWindowShouldClose(windowHandle, true); // We will detect this in the rendering loop
			} else if (key == GLFW_KEY_F11) {
				toggleFullScreen();
			}
		}
	}

	public void pollEvents() {
		glfwPollEvents();
	}

	protected void resized(int bufferWidth, int bufferHeight) {
		this.bufferWidth = bufferWidth;
		this.bufferHeight = bufferHeight;
		try {
			resizeFunc.call();
		} catch (Exception excp) {
			//Logger.error("Error calling resize callback", excp);
			System.out.println("Error calling resize callback "+ excp);
		}
	}

	public void update() {
		glfwSwapBuffers(windowHandle);
	}

	public boolean windowShouldClose() {
		return glfwWindowShouldClose(windowHandle);
	}

	public static class WindowOptions {
		public boolean compatibleProfile;
		public int fps;
		public int height;
		public int ups = Engine.TARGET_UPS;
		public int width;
	}
}
