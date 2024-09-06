package fr.svedel.engine;

import org.joml.Matrix3f;

import java.io.IOException;
import java.nio.file.*;

public class Utils {

	private Utils() {
		// Utility class
	}

	public static String readFile(String filePath) {
		String str;
		try {
			str = new String(Files.readAllBytes(Paths.get(filePath)));
		} catch (IOException excp) {
			throw new RuntimeException("Error reading file [" + filePath + "]", excp);
		}
		return str;
	}

	public static Matrix3f getTranslationMatrix(float x, float y) {
		return new Matrix3f(1, 0, 0,
							0, 1, 0,
							x, y, 1);
	}

	public static Matrix3f getScaleMatrix(float w, float h) {
		return new Matrix3f(w, 0, 0,
							0, h, 0,
							0, 0, 1);
	}

	public static Matrix3f getRotationMatrix(float angle) {
		return new Matrix3f ((float) Math.cos(angle), (float) Math.sin(angle), 0,
							 (float) -Math.sin(angle), (float) Math.cos(angle), 0,
							 0, 0, 1);
	}
}
