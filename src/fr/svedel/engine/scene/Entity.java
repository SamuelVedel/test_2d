package fr.svedel.engine.scene;

import org.joml.*;

import fr.svedel.engine.graph.Model;
import fr.svedel.engine.Utils;

public class Entity {

	private final Model model;
	private Matrix3f modelMatrix;

	private Vector2f position;
	private float rotationAngle;
	private Vector2f rotationPos;
	private Vector2f scale;
	private float depth;

	private String texturePath;
	private String normalMapPath;

	public Entity(Model model) {
		this.model = model;
		modelMatrix = new Matrix3f();
		position = new Vector2f();
		rotationAngle = 0;
		rotationPos = new Vector2f();
		scale = new Vector2f();
		scale.x = 1;
		scale.y = 1;
	}

	public Model getModel() {
		return model;
	}

	public Matrix3f getModelMatrix() {
		return modelMatrix;
	}

	public Vector2f getPosition() {
		return position;
	}

	public final void setPosition(float x, float y) {
		position.x = x;
		position.y = y;
	}

	public float getRotationAngle() {
		return rotationAngle;
	}

	public void setRotationAngle(float angle) {
		rotationAngle = angle;
	}

	public Vector2f getRotationPos() {
		return rotationPos;
	}

	public void setRotationPos(float x, float y) {
		rotationPos.x = x;
		rotationPos.y = y;
	}

	public Vector2f getScale() {
		return scale;
	}

	public void setScale(float w, float h) {
		scale.x = w;
		scale.y = h;
	}

	public float getDepth() {
		return depth;
	}

	public void setDepth(float depth) {
		this.depth = depth;
	}

	public boolean hasHisOwnTexture() {
		return texturePath != null;
	}

	public String getTexturePath() {
		return texturePath;
	}

	public void setTexturePath(String texturePath) {
		this.texturePath = texturePath;
	}

	public String getNormalMapPath() {
		return normalMapPath;
	}

	public void setNormalMapPath(String normalMapPath) {
		this.normalMapPath = normalMapPath;
	}

	public void updateModelMatrix() {
		Matrix3f transMat = Utils.getTranslationMatrix(position.x, position.y);
		Matrix3f rotTransMat = Utils.getTranslationMatrix(-rotationPos.x, -rotationPos.y);
		Matrix3f invRotTransMat = Utils.getTranslationMatrix(rotationPos.x, rotationPos.y);
		Matrix3f rotMat = Utils.getRotationMatrix(rotationAngle);
		Matrix3f scaleMat = Utils.getScaleMatrix(scale.x, scale.y);
		
		modelMatrix = transMat
			.mul(invRotTransMat)
			.mul(rotMat)
			.mul(rotTransMat)
			.mul(scaleMat);
	}
}
