package renderEngineObject.gameobjects;

import loader.Loader;
import renderEngineObject.material.Material;
import utils.transforms.Position;
import utils.transforms.Rotation;
import utils.transforms.Scale;

import java.util.LinkedList;

public abstract class GameObject2D extends GameObject implements ISprite {

    private float width;
    private float height;

    public GameObject2D(String name, Position position, Rotation rotation, Scale scale, Material material,
                        float width, float height) {
        super(name, position, rotation, scale, material);
        this.height = height;
        this.width =width;
    }

    public GameObject2D(GameObject parent, String name, Position position, Rotation rotation, Scale scale, Material material,
                        float width, float height) {
        super(parent, name, position, rotation, scale, material);
        this.height = height;
        this.width = width;
    }

    @Override
    public LinkedList<GameObject> getChildren() {
        return super.getChildren();
    }

    @Override
    public void addChild(GameObject gameObject) {
        super.addChild(gameObject);
    }

    @Override
    public GameObject getParent() {
        return super.getParent();
    }

    @Override
    public Mesh getMesh() {
        return super.getMesh();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public String getID() {
        return super.getID();
    }

    @Override
    public float getWidth() {
        return this.width;
    }

    @Override
    public float getHeight() {
        return this.height;
    }

    @Override
    public void createMesh() {
        Position position = this.getPosition();
        float width = this.getWidth();
        float height = this.getHeight();

        float[] vertices = {
                position.getX()- width/2, position.getY()+ height/2, 0,
                position.getX()- width/2, position.getY()- height/2, 0,
                position.getX()+ width/2, position.getY()- height/2, 0,
                position.getX()+ width/2, position.getY()+ height/2, 0f
        };

        int[] indices = {
                0,1,3,
                3,1,2
        };

        float[] textureCord = {
                0,0,
                0,1,
                1,1,
                1,0
        };

        this.mesh = Loader.loadToVAO(vertices,textureCord,indices);
    }

}
