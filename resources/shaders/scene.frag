#version 330

const int MAX_POINT_LIGHTS = 5;
const int MAX_SPOT_LIGHTS = 5;
const float SPECULAR_POWER = 10;

in vec2 outPosition;
in vec2 outTextCoord;

out vec4 fragColor;

struct Attenuation {
	float constant;
	float linear;
	float exponent;
};

struct AmbientLight {
	float factor;
	vec3 color;
};

struct PointLight {
	vec3 position;
	vec3 color;
	float intensity;
	Attenuation att;
};

struct SpotLight {
	PointLight pl;
	vec2 conedir;
	float cutoff;
};

struct DirLight {
	vec3 color;
	vec2 direction;
	float intensity;
};

struct NormalData {
	vec3 normal;
	//float factor;
};

/*
struct Fog {
	int activeFog;
	vec3 color;
	float density;
};
*/

uniform sampler2D txtSampler;
uniform sampler2D normalSampler;
uniform int hasNormalMap;
uniform AmbientLight ambientLight;
uniform PointLight pointLights[MAX_POINT_LIGHTS];
//uniform SpotLight spotLights[MAX_SPOT_LIGHTS];
//uniform DirLight dirLight;
//uniform Fog fog;

NormalData normDataInit(vec3 normalColor) {
	NormalData normData;
	vec3 normComp = normalColor-vec3(0.5, 0.5, 0.5);
	if (sqrt(dot(normComp, normComp)) < 0.1) {
		normData.normal = vec3(0, 0, 0);
	} else {
		normData.normal = normalize(normComp);
		//normData.normal.z *= -1;
	}
	//normData.factor = normalColor.z;
	return normData;
}

vec3 normDataGetNormal(NormalData normData) {
	return normData.normal;
}

/*float normDataGetFactor(NormalData normData) {
	return normData.factor;
}*/

bool normDataHasNormal(NormalData normData) {
	return normData.normal.x != 0 || normData.normal.y != 0
		|| normData.normal.z != 0;
}

vec3 calcAmbient(AmbientLight ambientL) {
	return ambientL.factor*ambientL.color;
}

vec3 calcPointLight(PointLight pointl, vec2 position, NormalData normData) {
	vec3 vec3Pos = vec3(position, 0.);
	vec3 toLight = pointl.position-vec3Pos;
	
	float dist = length(toLight);
	Attenuation att = pointl.att;
	float attenuationInv = att.exponent*dist*dist+att.linear*dist+att.constant;
	
	//float normalFactor = normDataGetFactor(normData);
	float normalFactor = 1;
	if (normDataHasNormal(normData)) {
		vec3 toLightNorm = normalize(toLight);
		vec3 normal = normDataGetNormal(normData);
		normalFactor *= dot(normal, toLightNorm);
	}
	if (normalFactor < 0) normalFactor = 0;
	if (normalFactor > 1) normalFactor = 1;
	return (pointl.color*pointl.intensity/attenuationInv)*normalFactor;
}

void main() {
	vec4 textColor = texture(txtSampler, outTextCoord);
	vec3 normalColor = vec3(0.5, 0.5, 0.5);
	if (hasNormalMap > 0) {
		normalColor = texture(normalSampler, outTextCoord).xyz;
	}
	NormalData normData = normDataInit(normalColor);
	
	vec3 light = calcAmbient(ambientLight);
	for (int i = 0; i < MAX_POINT_LIGHTS; ++i) {
		light += calcPointLight(pointLights[i], outPosition, normData);
	}
	if (light.x > 1) light.x = 1+log(light.x);
	if (light.y > 1) light.y = 1+log(light.y);
	if (light.z > 1) light.z = 1+log(light.z);
	/*if (light.x > 2) light.x = 2;
	if (light.y > 2) light.y = 2;
	if (light.z > 2) light.z = 2;*/
	
	fragColor = textColor*vec4(light, 1);
}
