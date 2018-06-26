package objects;

import renderEngineObject.gameobjects.GameObject3D;
import renderEngineObject.material.Material;
import utils.transforms.Position;
import utils.transforms.Rotation;
import utils.transforms.Scale;

public class Entity extends GameObject3D {

    private Material material;

    public Entity(String objName, Material material) {
        super(objName,new Position(0,0,0),new Rotation(-20,45,25),new Scale(1,1,1),material);
        this.material = material;
    }

    @Override
    public String getFileLocation() {
        return "cube.obj";
    }

    @Override
    public Material getMaterial() {
        return material;
    }

    @Override
    public Position getPosition() {
        return super.getPosition();
    }

    @Override
    public Rotation getRotation() {
        return super.getRotation();
    }

    @Override
    public Scale getScale() {
        return super.getScale();
    }

    @Override
    public void setPosition(Position position) {
        super.setPosition(position);
    }

    @Override
    public void setRotation(Rotation rotation) {

    }

    @Override
    public void setScale(Scale scale) {

    }
}
