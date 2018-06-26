package renderEngine;

import utils.transforms.Position;
import utils.transforms.Rotation;
import utils.transforms.Scale;

public interface ITransform extends  IRotation, IScale, IPosition {
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

}
