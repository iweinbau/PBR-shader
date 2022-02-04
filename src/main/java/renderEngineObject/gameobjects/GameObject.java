package renderEngineObject.gameobjects;

import renderEngineObject.material.Material;
import utils.transforms.Position;
import utils.transforms.Rotation;
import utils.transforms.Scale;

import java.util.LinkedList;
import java.util.UUID;

public abstract class GameObject implements IGameObject {

    Position position;
    Rotation rotation;
    Scale scale;
    String name;
    final String ID;

    Mesh mesh;
    GameObject parent;
    Material material;

    LinkedList<GameObject> children = new LinkedList<>();

    public GameObject(String name,Position position, Rotation rotation, Scale scale, Material material) {
        this.name = name;
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
        this.material = material;
        this.ID = createUniqueID();
    }

    private String createUniqueID(){
        return UUID.randomUUID().toString();
    }


    public GameObject(GameObject parent,String name,Position position, Rotation rotation, Scale scale, Material material) {
        this.parent = parent;
        this.name = name;
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
        this.material = material;
        this.ID = createUniqueID();
    }

    @Override
    public LinkedList<GameObject> getChildren() {
        return children;
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

    @Override
    public Scale getScale() {
        return scale;
    }

    @Override
    public void setScale(Scale scale) {

    }

    @Override
    public void addChild( GameObject gameObject){
        gameObject.parent = this;
        children.add(gameObject);
    }

    @Override
    public GameObject getParent(){
        return this.parent;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Material getMaterial() {
        return this.material;
    }

    @Override
    public Mesh getMesh() {
        return this.mesh;
    }

    @Override
    public void onDestroy() {
        System.out.println("Destroyed: "+ getName());
    }

    @Override
    public String getID() {
        return this.ID;
    }

    public abstract void createMesh();
}
