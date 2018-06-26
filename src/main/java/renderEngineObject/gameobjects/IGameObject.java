package renderEngineObject.gameobjects;

import renderEngine.ITransform;
import renderEngineObject.material.Material;
import utils.transforms.Position;
import utils.transforms.Rotation;
import utils.transforms.Scale;

import java.util.LinkedList;

public interface IGameObject extends IObject, ITransform {
    @Override
    String getName();

    @Override
    Material getMaterial();

    @Override
    Position getPosition();

    @Override
    void setPosition(Position position);

    @Override
    Rotation getRotation();

    @Override
    void setRotation(Rotation rotation);

    @Override
    Scale getScale();

    @Override
    void setScale(Scale scale);

    Mesh getMesh();

    String getID();

    LinkedList<GameObject> getChildren();

    void addChild( GameObject gameObject);

    GameObject getParent();

    /**
     * Method used when destroying obj.
     */
    void onDestroy();
}
