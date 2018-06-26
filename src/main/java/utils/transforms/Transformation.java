package utils.transforms;

import camera.Camera;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import renderEngineObject.gameobjects.GameObject;


/**
 *
 * Helper class use to calculate Transformation matrix.
 *
 */
public class Transformation {


    private final Matrix4f modelMatrix;

    public Transformation() {
        modelMatrix = new Matrix4f();
    }

    public Matrix4f getModelMatrix(GameObject gameItem) {
        Vector3f rotation = gameItem.getRotation();
        modelMatrix.identity().translate(gameItem.getPosition()).
            rotateX((float)Math.toRadians(-rotation.x)).
            rotateY((float)Math.toRadians(-rotation.y)).
            rotateZ((float)Math.toRadians(-rotation.z)).
            scale(gameItem.getScale());
        return modelMatrix;
    }
}
