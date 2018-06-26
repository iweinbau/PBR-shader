package objects;

import org.joml.Vector3f;

public class DirectionalLight extends Light {

  private Vector3f direction;

  public DirectionalLight(Vector3f color, Vector3f direction) {
    super(color);
    this.direction = direction;
  }

  public Vector3f getDirection() {
    return direction;
  }

  public void setDirection(Vector3f direction) {
    this.direction = direction;
  }
}
