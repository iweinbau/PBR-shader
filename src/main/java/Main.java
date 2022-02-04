
import static org.lwjgl.opengl.GL11.glEnable;

import Input.MouseCursor;
import camera.Camera;
import camera.FPSCamera;
import display.Display;
import java.util.Locale;
import loader.Loader;
import objects.Entity;
import objects.PointLight;
import org.joml.Vector3f;
import renderEngine.ObjectStore;
import renderEngine.Renderer;
import renderEngineObject.material.Material;
import renderEngineObject.material.Texture;
import utils.time.Time;
import utils.transforms.Position;
import utils.transforms.Rotation;

public class Main {

  static Camera camera;
  static PointLight light;
  static Renderer renderer;


  public static void main(String[] args) {

    initRenderEngine();

    loop();

    closeEngine();
  }

  private static Entity entity;

  /**
   * init the render Engine
   */
  public static void initRenderEngine() {
    initDisplay();

    renderer = new Renderer();
    MouseCursor.setMouseIsLocked(true);
    camera = new FPSCamera(new Position(0, 0, 5), new Rotation(0, 0, 0), 70, 0.01f, 1000f);
    light = new PointLight(new Vector3f(10), new  Vector3f(0,  0, 0f));

    //Material newMaterial = new Material();
    Material newMaterial = new Material(new Texture(Loader.loadTexture("rustediron2_basecolor.png")));
    newMaterial.setNormalMap(new Texture(Loader.loadTexture("rustediron2_normal.png")));
    newMaterial.setMetallicMap(new Texture(Loader.loadTexture("rustediron2_metallic.png")));
    newMaterial.setRoughnessMap(new Texture(Loader.loadTexture("rustediron2_roughness.png")));
    entity = new Entity("Name", newMaterial);

    //Don't forget to add add objects to object store.
    ObjectStore.addGameObject(entity);

  }

  /**
   * Main loop of the engine
   */
  private static void loop() {
    while (!Display.isCloseRequest()) {
      Time.updateTime();

      entity.setRotation(new Rotation(entity.getRotation().getX(),entity.getRotation().getY(),entity.getRotation().getZ()+0.5f));
      light.move();
      //render all gameObjects
      renderer.render(camera, light);

      //updated display
      Display.updateDisplay();

    }
  }

  /**
   * handler for closing the engine.
   */
  private static void closeEngine() {
    Display.closeDisplay();
    Loader.cleanUp();
  }

  private static void initDisplay() {
    Display.createDisplay("Game");
  }

}
