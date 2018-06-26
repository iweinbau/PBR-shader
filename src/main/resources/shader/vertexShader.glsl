#version 430

in vec3 position;
in vec2 texCoord;
in vec3 vertexNormal;
in vec3 tangent;

out vec2 pass_textureCoordinates;
out vec3 pass_normalVector;
out vec3 pass_position;
out mat3 TBNMatrix;
out mat4 view_matrix;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

void main()
{
    gl_Position = projectionMatrix * viewMatrix * transformationMatrix * vec4(position,1.0);
    pass_textureCoordinates = texCoord;
    pass_normalVector = mat3( transformationMatrix) * vertexNormal;
    pass_position = vec3( transformationMatrix * vec4(position,1));

    vec3 N = normalize(vec3(transformationMatrix * vec4(vertexNormal,0)));
    vec3 T = normalize(vec3(transformationMatrix * vec4(tangent,   0.0)));
    vec3 B = normalize(cross(N,T));

    TBNMatrix = mat3(T,B,N);
    view_matrix = viewMatrix;

}