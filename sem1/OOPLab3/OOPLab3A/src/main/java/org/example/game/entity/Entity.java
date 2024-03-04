package org.example.game.entity;

import org.example.game.helper.SizeComponent;
import org.example.vector.Vector2I;
import org.example.vector.Vector3D;

public interface Entity {
    Vector3D getPosition();
    void setPosition(Vector3D position);
    SizeComponent getSize();
    void setSize(Vector2I size);
}
