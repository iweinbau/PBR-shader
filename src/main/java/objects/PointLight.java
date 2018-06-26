package objects;

import Input.Input;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import utils.time.Time;
import utils.transforms.Position;

public class PointLight extends Light {


  private Vector3f position;

  public PointLight(Vector3f color, Vector3f position) {
    super(color);
    this.position = position;
  }

  public Vector3f getPosition() {
    return position;
  }

  public void setPosition(Vector3f position) {
    this.position = position;
  }

  public void move(){
    if(Input.isKeyDown(GLFW.GLFW_KEY_UP)){
      setPosition(new Position(this.getPosition().x,this.getPosition().y,this.getPosition().z + Time
          .getDeltaTime() * -5));
    }
    if (Input.isKeyDown(GLFW.GLFW_KEY_RIGHT)){
      setPosition(new Position(this.getPosition().x + Time.getDeltaTime() *5,this.getPosition().y,this.getPosition().z));
    }
    if (Input.isKeyDown(GLFW.GLFW_KEY_LEFT)){
      setPosition(new Position(this.getPosition().x + Time.getDeltaTime() *-5,this.getPosition().y,this.getPosition().z));
    }
    if (Input.isKeyDown(GLFW.GLFW_KEY_DOWN)){
      setPosition(new Position(this.getPosition().x,this.getPosition().y,this.getPosition().z + Time.getDeltaTime() * 5));
    }
  }
}
