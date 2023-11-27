package org.example.model;

public class Entity {
    protected Vector2I position;

    protected Vector2I size;
    public Entity(Vector2I pos) {
        position = new Vector2I(pos);
        size = Vector2I.zero();
    }
    public Entity(Vector2I pos, Vector2I size) {
        position = new Vector2I(pos);
        this.size = new Vector2I(size);
    }
    public Entity() {
        position = Vector2I.zero();
        size = Vector2I.zero();
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

    public Vector2I originPosition() {
        return position.add(size.multiply(0.5));
    }
}
