package renderEngineObject.material;

import org.joml.Vector3f;

public class Material {

  private static final Vector3f DEFAULT_COLOUR = new Vector3f(1.0f, 1.0f, 1.0f);

  private Vector3f albedo;

  private float metallic;

  private float roughness;

  private Texture texture;

  private Texture normalMap;

  private Texture roughnessMap;

  private Texture metallicMap;

  public Material(Texture texture) {
    this.albedo = new Vector3f(0, 0f, 0.f);
    this.metallic = 0;
    this.roughness = 0;
    this.texture = texture;
  }

  public Material(Texture texture, Texture normalMap) {
    this.albedo = new Vector3f(1, 0.5f, 0.31f);
    this.metallic = 0;
    this.roughness = 0;
    this.normalMap = normalMap;
    this.texture = texture;
  }

  public Material() {
    this.albedo = new Vector3f(0.5f, 0f, 0f);
    this.metallic = 0f;
    this.roughness = 0f;
  }

  public Texture getMetallicMap() {
    return metallicMap;
  }

  public void setMetallicMap(Texture metallicMap) {
    this.metallicMap = metallicMap;
  }

  public Texture getNormalMap() {
    return normalMap;
  }

  public void setNormalMap(Texture normalMap) {
    this.normalMap = normalMap;
  }

  public Texture getRoughnessMap(){
    return this.roughnessMap;
  }

  public void setRoughnessMap(Texture roughnessMap) {
    this.roughnessMap = roughnessMap;
  }

  public Texture getTexture() {
    return texture;
  }

  public Vector3f getAlbedo() {
    return albedo;
  }

  public float getMetallic() {
    return metallic;
  }

  public float getRoughness() {
    return roughness;
  }

  public boolean isTextured() {
    return texture != null;
  }

  public boolean hasNormalMap() {
    return normalMap != null;
  }

  public boolean hasRoughnesMap() {
    return roughnessMap != null;
  }

  public boolean hasMetallicMap() {
    return metallicMap != null;
  }
}
