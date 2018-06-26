package camera;

import Input.Input;
import Input.MouseCursor;
import org.lwjgl.glfw.GLFW;
import utils.time.Time;
import utils.transforms.Position;
import utils.transforms.Rotation;

public class FPSCamera extends PerspectiveCamera {

    float sensitivity =5;

    float speed =5f;

    public FPSCamera(Position position, Rotation rotation, float FOV, float NEAR_PLANE, float FAR_PLANE) {
        super(position, rotation, FOV, NEAR_PLANE, FAR_PLANE);
    }

    @Override
    public void move() {
        rotateCamera();
        if(Input.isKeyDown(GLFW.GLFW_KEY_UP)){
            setPosition(new Position(this.getPosition().getX(),this.getPosition().getY(),this.getPosition().getZ() + Time.getDeltaTime() * -speed));
        }
        if (Input.isKeyDown(GLFW.GLFW_KEY_RIGHT)){
            setPosition(new Position(this.getPosition().getX() + Time.getDeltaTime() *speed,this.getPosition().getY(),this.getPosition().getZ()));
        }
        if (Input.isKeyDown(GLFW.GLFW_KEY_LEFT)){
            setPosition(new Position(this.getPosition().getX() + Time.getDeltaTime() *-speed,this.getPosition().getY(),this.getPosition().getZ()));
        }
        if (Input.isKeyDown(GLFW.GLFW_KEY_DOWN)){
            setPosition(new Position(this.getPosition().getX(),this.getPosition().getY(),this.getPosition().getZ() + Time.getDeltaTime() * speed));
        }
    }
    private void rotateCamera(){
        if(MouseCursor.isRotY()) {
            setRotation(new Rotation( this.getRotation().getX() + (float) MouseCursor.getDeltaY() * Time.getDeltaTime() * sensitivity, this.getRotation().getY(),0));

        }
        if(MouseCursor.isRotX()) {
            setRotation(new Rotation( this.getRotation().getX(),this.getRotation().getY()+(float) MouseCursor.getDeltaX() * Time.getDeltaTime() * sensitivity,0));

        }

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
}
