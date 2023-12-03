package org.example.game.model;

import org.example.model.DimTranslator;
import org.example.vector.Vector2I;

public class StaticUIObject {
    protected Vector2I position;
    protected Vector2I size;
    public StaticUIObject() {

    }

    public static StaticUIObject on(Vector2I vector2I, Vector2I size) {
        StaticUIObject staticUIObject = new StaticUIObject();
        staticUIObject.position = vector2I;
        staticUIObject.size = size;
        return staticUIObject;
    }

    public Vector2I getPosition() {
        return position;
    }
    public void setPosition(Vector2I position) {
        this.position = position;
    }
    public Vector2I getSize() {
        return size;
    }
    public void setSize(Vector2I size) {
        this.size = size;
    }
    public Vector2I origin() {
        return DimTranslator.get().toCenter(position, size);
    }
}
