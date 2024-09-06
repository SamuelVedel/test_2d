package fr.svedel.engine.scene.lights;

import org.joml.Vector2f;
import org.joml.Vector3f;

public class PointLight {
	
	private Attenuation attenuation;
	private Vector3f color;
	private float intensity;
	private Vector3f position;
	private float depth;

	public PointLight(Vector3f color, Vector3f position,
					  float intensity, Attenuation attenuation) {
		this.color = color;
		this.position = position;
		this.intensity = intensity;
		this.attenuation = attenuation;
	}
	
	public PointLight(Vector3f color, Vector3f position, float intensity) {
		this(color, position, intensity, new Attenuation(0, 0.001f, 0.00001f));
	}
	
	public PointLight() {
		this(new Vector3f(new float[] {1, 1, 1}),
			 new Vector3f(new float[] {0, 0, 0}), 1);
	}

	public Attenuation getAttenuation() {
		return attenuation;
	}

	public Vector3f getColor() {
		return color;
	}

	public float getIntensity() {
		return intensity;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setAttenuation(Attenuation attenuation) {
		this.attenuation = attenuation;
	}

	public void setColor(Vector3f color) {
		this.color = color;
	}

	public void setColor(float r, float g, float b) {
		color.set(r, g, b);
	}

	public void setIntensity(float intensity) {
		this.intensity = intensity;
	}

	public void setPosition(float x, float y) {
		position.x = x;
		position.y = y;
	}

	public void setPosition(float x, float y, float z) {
		position.set(x, y, z);
	}

	public void setDepth(float z) {
		position.z = z;
	}

	public static class Attenuation {

		private float constant;
		private float exponent;
		private float linear;

		public Attenuation(float constant, float linear, float exponent) {
			this.constant = constant;
			this.linear = linear;
			this.exponent = exponent;
		}

		public float getConstant() {
			return constant;
		}

		public float getExponent() {
			return exponent;
		}

		public float getLinear() {
			return linear;
		}

		public void setConstant(float constant) {
			this.constant = constant;
		}

		public void setExponent(float exponent) {
			this.exponent = exponent;
		}

		public void setLinear(float linear) {
			this.linear = linear;
		}
	}
}
