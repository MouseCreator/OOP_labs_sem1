package org.example.game.entity;

import org.example.game.helper.SizeComponent;
import org.example.vector.Vector2I;
import org.example.vector.Vector3D;

public class EntityImpl implements Entity {

    protected Vector3D position;
    protected SizeComponent sizeComponent;
    public EntityImpl(Vector3D pos) {
        position = new Vector3D(pos);
        sizeComponent = new SizeComponent();
    }
    public EntityImpl(Vector3D pos, Vector2I size) {
        position = new Vector3D(pos);
        this.sizeComponent = new SizeComponent(size);
    }
    public EntityImpl() {
        position = Vector3D.zero();
        this.sizeComponent = new SizeComponent();
    }
    public Vector3D getPosition() {
        return position;
    }
    public void setPosition(Vector3D position) {
        this.position = position;
    }
    public SizeComponent getSize() {
        return sizeComponent;
    }
    public void setSize(Vector2I size) {
        this.sizeComponent.setSize(size);
    }
}
