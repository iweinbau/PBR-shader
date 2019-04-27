#version 410

in vec2 pass_textureCoordinates;
in vec3 pass_normalVector;
in vec3 pass_position;
in mat3 TBNMatrix;

struct PointLight{
    vec3 position;
    vec3 color;
};
out vec4 fragColor;

uniform PointLight light;
uniform vec3 viewPosition;
vec4 colorNode_rgb;
float metallic;
float roughness;
const float PI = 3.14159265359;

float spacularD(float cosThetaH, float roughness){
    float a      = roughness*roughness;
    float a2     = a*a;
    float NdotH  = cosThetaH;
    float NdotH2 = NdotH*NdotH;

    float num   = a2;
    float denom = (NdotH2 * (a2 - 1.0) + 1.0);
    denom = PI * denom * denom;

    return num / denom;
}

vec3 spacularF(float cosThetaD,vec3 F0){
    return F0 + (1.0 - F0) * pow(1.0 - cosThetaD, 5.0);
}

float GeometrySchlickGGX(float NdotV, float roughness)
{
    float r = (roughness + 1.0);
    float k = (r*r) / 8.0;

    float num   = NdotV;
    float denom = NdotV * (1.0 - k) + k;

    return num / denom;
}

float specularG(float cosThetaV, float cosThetaL, float roughness){
  float G1 = GeometrySchlickGGX(cosThetaV, roughness);
  float G2 = GeometrySchlickGGX(cosThetaL, roughness);
  return G1 * G2;
}
void main()
{
    //calculate albedo
    colorNode_rgb = vec4(1.000000,1.000000,1.000000,1.000000);
    vec3 albedo = colorNode_rgb.xyz;
    //getting normal vector from sampler2D
    vec3 N = pass_normalVector;
    // calculate metallic
    metallic = 0.000000;
    float metallic = metallic;
    // calculate roughness
    roughness = 0.000000;
    float roughness = metallic;


    // L,V,H vectors
    vec3 L = normalize(light.position - pass_position);
    vec3 V = normalize(viewPosition - pass_position);
    vec3 H = normalize(L + V);

    float distance    = length(light.position - pass_position);
    float attenuation = 1.0 / (distance * distance);
    vec3 radiance     = light.color * attenuation;
    vec3 viewDir    = normalize(viewPosition - pass_position);


    // cosuinus of the angel bewteen vector and normal.
    float costhetaL = max(dot(L,N),0);
    float costhetaV = max(dot(V,N),0);
    float costhetaH = max(dot(H,N),0);
    float costhetaD = max(dot(H,V),0);

    //set constant F0;
    vec3 F0 = vec3(0.04);
    F0 = mix(F0, albedo, metallic);

    float D = spacularD(costhetaH,roughness);
    vec3 F = spacularF(costhetaD,F0);
    float G = specularG(costhetaL,costhetaV,roughness);

    vec3 kD = mix(vec3(1.0) - F, vec3(0.0), metallic);

    vec3 numerator    = D * G * F;
    float denominator = 4.0 * costhetaV * costhetaL;
    vec3 specular     = numerator / max(denominator, 0.001);

    //result diffuse
    vec3 diffuse = (kD * albedo / PI + specular) * costhetaL * radiance * 1.0;

    //calculate the abmient color.
    vec3 ambient = vec3(0.03) * albedo;

    // adding ambient and diffuse for final result.
    vec3 color = ambient + diffuse;

    //gamma correct
    color = pow(color, vec3(1.0/2.2));

    fragColor = vec4(color, 1.0);
}
