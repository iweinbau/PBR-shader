package camera;

import org.joml.Matrix4f;
import utils.transforms.Position;
import utils.transforms.Rotation;

public abstract class OrthoCamera extends Camera {
    
    float left;
    float right;
    float bottom;
    float top;

    public OrthoCamera(Position position, Rotation rotation,float right, float left, float bottom, float top, float NEAR_PLANE, float FAR_PLANE) {
        super(position, rotation, NEAR_PLANE, FAR_PLANE);
        this.right =right;
        this.left =left;
        this.top =top;
        this.bottom =bottom;
    }

    @Override
    public Position getPosition() {
        return super.getPosition();
    }

    @Override
    public void setPosition(Position position) {
        super.setPosition(position);
    }

    @Override
    public Rotation getRotation() {
        return super.getRotation();
    }

    @Override
    public void setRotation(Rotation rotation) {
        super.setRotation(rotation);
    }

    @Override
    public float getNearPlane() {
        return super.getNearPlane();
    }

    @Override
    public void setNearPlane(float NEAR_PLANE) {
        super.setNearPlane(NEAR_PLANE);
    }

    @Override
    public float getFarPlane() {
        return super.getFarPlane();
    }

    @Override
    public void setFarPlane(float FAR_PLANE) {
        super.setFarPlane(FAR_PLANE);
    }

    @Override
    public Matrix4f getViewMatrix() {
        return super.getViewMatrix();
    }

    @Override
    public Matrix4f getProjectionMatrix() {
        Matrix4f orthoMatrix = new Matrix4f();
        orthoMatrix.identity();
        orthoMatrix.setOrtho2D(left, right, bottom, top);
        return orthoMatrix;
    }


    public float getRight() {
        return right;
    }
    
    public float getLeft() {
        return left;
    }

    public float getTop() {
        return top;
    }

    public float getBottom() {
        return bottom;
    }
}
