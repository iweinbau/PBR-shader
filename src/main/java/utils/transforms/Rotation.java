package utils.transforms;


import org.joml.Vector3f;

/**
 *
 * Wrapper class for rotation.
 *
 */
public class Rotation extends Vector3f {

    public Rotation() {

    }
    public Rotation(float x, float y, float z) {
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
