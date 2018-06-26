package camera;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import renderEngine.IPosition;
import renderEngine.IRotation;
import utils.transforms.Position;
import utils.transforms.Rotation;

public abstract class Camera implements IPosition, IRotation {

    Position position;
    Rotation rotation;

    float NEAR_PLANE;
    float FAR_PLANE;

    public Camera(Position position, Rotation rotation, float NEAR_PLANE, float FAR_PLANE){
        this.position = position;
        this.rotation = rotation;
        this.NEAR_PLANE = NEAR_PLANE;
        this.FAR_PLANE = FAR_PLANE;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public Rotation getRotation() {
        return rotation;
    }

    @Override
    public void setRotation(Rotation rotation) {
        this.rotation = rotation;
    }

    public float getNearPlane() {
        return NEAR_PLANE;
    }

    public void setNearPlane(float NEAR_PLANE) {
        this.NEAR_PLANE = NEAR_PLANE;
    }

    public float getFarPlane() {
        return FAR_PLANE;
    }

    public void setFarPlane(float FAR_PLANE) {
        this.FAR_PLANE = FAR_PLANE;
    }

    public Matrix4f getViewMatrix(){
        Vector3f cameraPos = this.getPosition();
        Vector3f rotation = this.getRotation();
        Matrix4f viewMatrix = new Matrix4f();
        viewMatrix.identity();
        // First do the rotation so camera rotates over its position
        viewMatrix.rotate((float)Math.toRadians(rotation.x), new Vector3f(1, 0, 0))
            .rotate((float)Math.toRadians(rotation.y), new Vector3f(0, 1, 0));
        // Then do the translation
        viewMatrix.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);
        return viewMatrix;
    }

    public abstract Matrix4f getProjectionMatrix();

    public void move(){};
}
