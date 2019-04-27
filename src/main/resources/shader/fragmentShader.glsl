#version 410

in vec2 pass_textureCoordinates;
in vec3 pass_normalVector;
in vec3 pass_position;
in mat3 TBNMatrix;

struct PointLight{
    vec3 position;
    vec3 color;
};

struct Material {
    vec3 albedo;
    float roughness;
    float metallic;

    sampler2D normalMap;
    sampler2D albedoMap;
    sampler2D metallicMap;
    sampler2D roughnessMap;
};

out vec4 fragColor;

uniform PointLight light;
uniform Material material;
uniform vec3 viewPosition;

const float PI = 3.14159265359;

// handy value clamping to 0 - 1 range
float saturate(in float value)
{
    return clamp(value, 0.0, 1.0);
}


// phong (lambertian) diffuse term
float phong_diffuse()
{
    return (1.0 / PI);
}


// compute fresnel specular factor for given base specular and product
// product could be NdV or VdH depending on used technique
vec3 fresnel_factor(in vec3 f0, in float product)
{
    return mix(vec3(0), vec3(1.0), pow(1.01 - product, 5.0));
}


// following functions are copies of UE4
// for computing cook-torrance specular lighting terms

float D_blinn(in float roughness, in float NdH)
{
    float m = roughness * roughness;
    float m2 = m * m;
    float n = 2.0 / m2 - 2.0;
    return (n + 2.0) / (2.0 * PI) * pow(NdH, n);
}

float D_beckmann(in float roughness, in float NdH)
{
    float m = roughness * roughness;
    float m2 = m * m;
    float NdH2 = NdH * NdH;
    return exp((NdH2 - 1.0) / (m2 * NdH2)) / (PI * m2 * NdH2 * NdH2);
}

float D_GGX(in float roughness, in float NdH)
{
    float m = roughness * roughness;
    float m2 = m * m;
    float d = (NdH * m2 - NdH) * NdH + 1.0;
    return m2 / (PI * d * d);
}

float G_schlick(in float roughness, in float NdV, in float NdL)
{
    float k = roughness * roughness * 0.5;
    float V = NdV * (1.0 - k) + k;
    float L = NdL * (1.0 - k) + k;
    return 0.25 / (V * L);
}


// simple phong specular calculation with normalization
vec3 phong_specular(in vec3 V, in vec3 L, in vec3 N, in vec3 specular, in float roughness)
{
    vec3 R = reflect(-L, N);
    float spec = max(0.0, dot(V, R));

    float k = 1.999 / (roughness * roughness);

    return min(1.0, 3.0 * 0.0398 * k) * pow(spec, min(10000.0, k)) * specular;
}

// simple blinn specular calculation with normalization
vec3 blinn_specular(in float NdH, in vec3 specular, in float roughness)
{
    float k = 1.999 / (roughness * roughness);

    return min(1.0, 3.0 * 0.0398 * k) * pow(NdH, min(10000.0, k)) * specular;
}

// cook-torrance specular calculation
vec3 cooktorrance_specular(in float NdL, in float NdV, in float NdH, in vec3 specular, in float roughness)
{
    float D = D_blinn(roughness, NdH);
    //float D = D_beckmann(roughness, NdH);
    //float D = D_GGX(roughness, NdH);

    float G = G_schlick(roughness, NdV, NdL);

    float rim = mix(1.0 - roughness * 0.9, 1.0, NdV);

    return (1.0 / rim) * specular * G * D;
}


void main()
{
    vec3 albedo = pow(texture(material.albedoMap, pass_textureCoordinates).rgb, vec3(2.2));
    //getting normal vector from sampler2D
    vec3 normal = texture(material.normalMap, pass_textureCoordinates).rgb;
    normal = normalize(normal * 2.0 - 1.0);
    normal = normalize(TBNMatrix * normal);
    vec3 N = normal;
    float metallic = texture(material.metallicMap, pass_textureCoordinates).r;
    float roughness = texture(material.roughnessMap, pass_textureCoordinates).r;


    // L,V,H vectors
    vec3 L = normalize(light.position - pass_position);
    vec3 V = normalize(viewPosition - pass_position);
    vec3 H = normalize(L + V);

    float distance    = length(light.position - pass_position);
    float attenuation = 1.0 / (distance * distance);
    vec3 radiance     = light.color * attenuation;
    vec3 viewDir    = normalize(viewPosition - pass_position);


// compute material reflectance

    float NdL = max(0.0, dot(N, L));
    float NdV = max(0.001, dot(N, V));
    float NdH = max(0.001, dot(N, H));
    float HdV = max(0.001, dot(H, V));
    float LdV = max(0.001, dot(L, V));

    //set constant F0;
    vec3 F0 = vec3(0.04);
    F0 = mix(F0, albedo, metallic);

    float D = D_beckmann(roughness,NdH);
    vec3 F = fresnel_factor(F0,HdV);
    float G = G_schlick(roughness,NdV,NdL);
    vec3 kS = F;
    vec3 kD = vec3(1.0) - kS;
    kD *= 1.0 - metallic;

    vec3 numerator    = D * G * F;
    float denominator = 4.0 * NdV * NdL;
    vec3 specular     = numerator / max(denominator, 0.001);

    //result color
    vec3 Lo = (kD * albedo / PI + specular) * NdL * radiance * 1;

    vec3 ambient = vec3(0.03) * albedo;
    vec3 color = ambient + Lo;

    color = color / (color + vec3(1.0));
    color = pow(color, vec3(1.0/2.2));

    fragColor = vec4(color, 1.0);
}