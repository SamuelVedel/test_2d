#version 330

layout (location=0) in vec2 position;
layout (location=1) in vec2 texCoord;

out vec2 outPosition;
out vec2 outTextCoord;

/*struct TransformMatrix {
	mat3 translate;
	mat3 scale;
	mat3 rotateAngle;
	mat3 rotatePos;
};*/
//typedef mat3 TransformMatrix;

uniform mat3 viewTm;
uniform mat3 modelTm;
uniform float depth;

vec2 applieTranformMatrix(mat3 tm, vec2 vec) {
	/*vec3 newVec = tm.translate*inverse(tm.rotatePos)*tm.rotateAngle*tm.scale*vec3(vec, 1);
	return vec3.xy;*/
	vec3 newVec = tm*vec3(vec, 1);
	return newVec.xy;
}

void main() {
	vec2 modelPosition = applieTranformMatrix(modelTm, position);
	vec2 worldPosition = applieTranformMatrix(viewTm, modelPosition);
	gl_Position = vec4(worldPosition+vec2(-1.0, 1.0), depth, 1.0);
	outPosition = modelPosition.xy;
	outTextCoord = texCoord;
}
