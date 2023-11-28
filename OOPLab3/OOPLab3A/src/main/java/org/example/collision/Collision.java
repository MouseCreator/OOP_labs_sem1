package org.example.collision;

import org.example.vector.Vector3D;

public class Collision {
    private Vector3D collisionStart;
    private Vector3D collisionEnd;
    private Vector3D size;
    private Vector3D position;
    public Vector3D getCollisionStart() {
        return collisionStart;
    }

    public Vector3D getCollisionEnd() {
        return collisionEnd;
    }

    public Collision(Vector3D position, Vector3D size) {
        this.size = size;
        this.position = position;
        calculatePosition();
    }

    private void calculatePosition() {
        double xD = size.x() / 2;
        double yD = size.y() / 2;
        double zD = size.z() / 2;
        collisionStart = Vector3D.get(position.x() - xD, position.y() - yD, position.z() - zD);
        collisionEnd = Vector3D.get(position.x() + xD, position.y() + yD, position.z() + zD);
    }

    public void moveTo(Vector3D position) {
        this.position = position;
        calculatePosition();
    }
    public void changeSize(Vector3D size) {
        this.size = size;
        calculatePosition();
    }
}
