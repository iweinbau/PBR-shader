package utils.transforms;


import org.joml.Vector3f;

/**
 *
 * Wrapper class for handling positions
 *
 */
public class Position extends Vector3f {

    public Position() {
    }

    public Position(float x, float y, float z) {
        super(x, y, z);
    }

    public float getX(){
        return this.x;
    }

    public float getY(){
        return this.y;
    }


    public float getZ(){
        return this.z;
    }

}
