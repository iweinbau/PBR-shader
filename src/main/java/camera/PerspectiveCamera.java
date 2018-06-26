package camera;

import display.Display;
import org.joml.Matrix4f;
import utils.transforms.Position;
import utils.transforms.Rotation;

public class PerspectiveCamera extends Camera {

    float FOV;

    public PerspectiveCamera(Position position, Rotation rotation,float FOV , float NEAR_PLANE, float FAR_PLANE){
        super(position,rotation,NEAR_PLANE,FAR_PLANE);
        this.FOV = FOV;
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

    public float getFOV() {
        return (float)Math.toRadians(FOV);
    }

    public void setFOV(float FOV) {
        this.FOV = FOV;
    }

    public Matrix4f getProjectionMatrix(){
        Matrix4f projectionMatrix = new Matrix4f();
        projectionMatrix.identity();
        float aspectRatio = (float)Display.getWidth() / (float)Display.getHeight();
        projectionMatrix.identity();
        projectionMatrix.perspective(this.getFOV(), aspectRatio, this.getNearPlane(), this.getFarPlane());
        return projectionMatrix;
    }
}
