package utils.colour;


import org.joml.Vector3f;

/**
 *
 * Wrapper class for handling colour
 *
 */
public class Colour extends Vector3f {

    private float r;
    private float g;
    private float b;

    public Colour(float r, float g, float b) {
        super(r,g,b);
    }

    public float getR() {
        return r;
    }

    public void setR(float r) {
        this.r = r;
    }

    public float getG() {
        return g;
    }

    public void setG(float g) {
        this.g = g;
    }

    public float getB() {
        return b;
    }

    public void setB(float b) {
        this.b = b;
    }
}
