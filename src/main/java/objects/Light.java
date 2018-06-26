package objects;

import Input.Input;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import utils.time.Time;
import utils.transforms.Position;

public class Light {

  private Vector3f color;

  public Light(Vector3f color) {
    this.color = color;
  }

  public Vector3f getColor() {
    return color;
  }

  public void setColor(Vector3f color) {
    this.color = color;
  }
}