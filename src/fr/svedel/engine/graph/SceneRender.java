package fr.svedel.engine.graph;

import org.joml.*;
import fr.svedel.engine.scene.*;
import fr.svedel.engine.scene.lights.*;

import java.util.*;

import static org.lwjgl.opengl.GL30.*;

public class SceneRender {

	private static final int MAX_POINT_LIGHTS = 5;
	private static final int MAX_SPOT_LIGHTS = 5;

	private ShaderProgram shaderProgram;

	private UniformsMap uniformsMap;

	public SceneRender() {
		List<ShaderProgram.ShaderModuleData> shaderModuleDataList = new ArrayList<>();
		shaderModuleDataList.add(new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER));
		shaderModuleDataList.add(new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER));
		shaderProgram = new ShaderProgram(shaderModuleDataList);
		createUniforms();
	}

	public void cleanup() {
		shaderProgram.cleanup();
	}

	private void createUniforms() {
		uniformsMap = new UniformsMap(shaderProgram.getProgramId());
		
		// vert uniforms
		uniformsMap.createUniform("modelTm");
		uniformsMap.createUniform("viewTm");
		uniformsMap.createUniform("depth");
		
		// frag uniforms
		uniformsMap.createUniform("txtSampler");
		uniformsMap.createUniform("normalSampler");
		uniformsMap.createUniform("hasNormalMap");
		uniformsMap.createUniform("ambientLight.factor");
		uniformsMap.createUniform("ambientLight.color");
		
		for (int i = 0; i < MAX_POINT_LIGHTS; ++i) {
			uniformsMap.createUniform("pointLights["+i+"].position");
			uniformsMap.createUniform("pointLights["+i+"].color");
			uniformsMap.createUniform("pointLights["+i+"].intensity");
			uniformsMap.createUniform("pointLights["+i+"].att.constant");
			uniformsMap.createUniform("pointLights["+i+"].att.linear");
			uniformsMap.createUniform("pointLights["+i+"].att.exponent");
		}
	}

	public void render(Scene scene) {
		glEnable(GL_BLEND);
		glBlendEquation(GL_FUNC_ADD);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		shaderProgram.bind();
		
		uniformsMap.setUniform("viewTm", scene.getCamera().getViewMatrix());
		
		uniformsMap.setUniform("txtSampler", 0);
		uniformsMap.setUniform("normalSampler", 1);
		
		updateLights(scene);
		
		/*Fog fog = scene.getFog();
		uniformsMap.setUniform("fog.activeFog", fog.isActive() ? 1 : 0);
		uniformsMap.setUniform("fog.color", fog.getColor());
		uniformsMap.setUniform("fog.density", fog.getDensity());*/
		
		List<Model> models = scene.getModelList();
		TextureCache textureCache = scene.getTextureCache();
		for (Model model : models) {
			List<Entity> entities = model.getEntitiesList();

			for (Material material : model.getMaterialList()) {
				setBindedTexture(material.getTexturePath(), material.getNormalMapPath(),
								 textureCache);

				for (Mesh mesh : material.getMeshList()) {
					glBindVertexArray(mesh.getVaoId());
					for (Entity entity : entities) {
						if (entity.hasHisOwnTexture()) {
							setBindedTexture(entity.getTexturePath(), entity.getNormalMapPath(),
											 textureCache);
						}
						
						uniformsMap.setUniform("modelTm", entity.getModelMatrix());
						uniformsMap.setUniform("depth", entity.getDepth());
						glDrawElements(GL_TRIANGLES, mesh.getNumVertices(), GL_UNSIGNED_INT, 0);
						
						if (entity.hasHisOwnTexture()) {
							setBindedTexture(material.getTexturePath(),
											 material.getNormalMapPath(),textureCache);
						}
					}
				}
			}
		}

		glBindVertexArray(0);

		shaderProgram.unbind();
	}

	private void setBindedTexture(String texturePath, String normalMapPath,
								  TextureCache textureCache) {
		boolean hasNormalMapPath = normalMapPath != null;
		uniformsMap.setUniform("hasNormalMap", hasNormalMapPath ? 1 : 0);
		Texture texture = textureCache.getTexture(texturePath);
		glActiveTexture(GL_TEXTURE0);
		texture.bind();
		if (hasNormalMapPath) {
			Texture normalMapTexture = textureCache.getTexture(normalMapPath);
			glActiveTexture(GL_TEXTURE1);
			normalMapTexture.bind();
		}
	}

	private void updateLights(Scene scene) {
		Matrix3f viewMatrix = scene.getCamera().getViewMatrix();
		
		SceneLights sceneLights = scene.getSceneLights();
		AmbientLight ambientLight = sceneLights.getAmbientLight();
		uniformsMap.setUniform("ambientLight.factor", ambientLight.getIntensity());
		uniformsMap.setUniform("ambientLight.color", ambientLight.getColor());
		
		/*DirLight dirLight = sceneLights.getDirLight();
		Vector4f auxDir = new Vector4f(dirLight.getDirection(), 0);
		auxDir.mul(viewMatrix);
		Vector3f dir = new Vector3f(auxDir.x, auxDir.y, auxDir.z);
		uniformsMap.setUniform("dirLight.color", dirLight.getColor());
		uniformsMap.setUniform("dirLight.direction", dir);
		uniformsMap.setUniform("dirLight.intensity", dirLight.getIntensity());*/
		
		List<PointLight> pointLights = sceneLights.getPointLights();
		int numPointLights = pointLights.size();
		PointLight pointLight;
		for (int i = 0; i < MAX_POINT_LIGHTS; i++) {
			if (i < numPointLights) {
				pointLight = pointLights.get(i);
			} else {
				pointLight = null;
			}
			String name = "pointLights["+i+"]";
			updatePointLight(pointLight, name, viewMatrix);
		}
		
		/*List<SpotLight> spotLights = sceneLights.getSpotLights();
		int numSpotLights = spotLights.size();
		SpotLight spotLight;
		for (int i = 0; i < MAX_SPOT_LIGHTS; i++) {
			if (i < numSpotLights) {
				spotLight = spotLights.get(i);
			} else {
				spotLight = null;
			}
			String name = "spotLights[" + i + "]";
			updateSpotLight(spotLight, name, viewMatrix);
			}*/
	}

	private void updatePointLight(PointLight pointLight, String prefix, Matrix3f viewMatrix) {
		Vector3f aux = new Vector3f();
		Vector3f lightPosition = new Vector3f();
		Vector3f color = new Vector3f();
		float intensity = 0.0f;
		float constant = 1.0f;
		float linear = 0.0f;
		float exponent = 0.0f;
		if (pointLight != null) {
			//aux.set(pointLight.getPosition(), 1);
			//aux.mul(viewMatrix);
			//lightPosition.set(aux.x, aux.y);
			lightPosition.set(pointLight.getPosition());
			color.set(pointLight.getColor());
			intensity = pointLight.getIntensity();
			PointLight.Attenuation attenuation = pointLight.getAttenuation();
			constant = attenuation.getConstant();
			linear = attenuation.getLinear();
			exponent = attenuation.getExponent();
		}
		uniformsMap.setUniform(prefix+".position", lightPosition);
		uniformsMap.setUniform(prefix+".color", color);
		uniformsMap.setUniform(prefix+".intensity", intensity);
		uniformsMap.setUniform(prefix+".att.constant", constant);
		uniformsMap.setUniform(prefix+".att.linear", linear);
		uniformsMap.setUniform(prefix+".att.exponent", exponent);
	}

	/*private void updateSpotLight(SpotLight spotLight, String prefix, Matrix4f viewMatrix) {
		PointLight pointLight = null;
		Vector3f coneDirection = new Vector3f();
		float cutoff = 0.0f;
		if (spotLight != null) {
			coneDirection = spotLight.getConeDirection();
			cutoff = spotLight.getCutOff();
			pointLight = spotLight.getPointLight();
		}

		uniformsMap.setUniform(prefix + ".conedir", coneDirection);
		uniformsMap.setUniform(prefix + ".conedir", cutoff);
		updatePointLight(pointLight, prefix + ".pl", viewMatrix);
	}*/
}
