package renderEngineObject.gameobjects;

import loader.Loader;
import objConverter.ModelData;
import objConverter.OBJFileLoader;
import renderEngineObject.material.Material;
import utils.transforms.Position;
import utils.transforms.Rotation;
import utils.transforms.Scale;

import java.util.LinkedList;

public abstract class GameObject3D extends GameObject {

    public abstract String getFileLocation();

    public GameObject3D(String name, Position position, Rotation rotation, Scale scale, Material material) {
        super(name, position, rotation, scale, material);
    }

    public GameObject3D(GameObject parent, String name, Position position, Rotation rotation, Scale scale, Material material) {
        super(parent, name, position, rotation, scale, material);
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
    public String getName() {
        return super.getName();
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

    public void createMesh(){
        ModelData mesh = OBJFileLoader.loadOBJ(this.getFileLocation());
        this.mesh = Loader.loadToVAO(mesh.getVertices(),mesh.getTextureCoords(),mesh.getNormals(),mesh.getTangents(),mesh.getIndices());
    }
}
