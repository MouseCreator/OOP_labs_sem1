package org.example.game.movement;

import org.example.vector.Vector3D;

public class LinearMovement implements Movement{

    private final Vector3D speed;
    public LinearMovement(Vector3D speed) {
        this.speed = speed;
    }
    @Override
    public Vector3D nextPosition(Vector3D currentPosition) {
        return currentPosition.add(speed);
    }

    @Override
    public Vector3D estimate(Vector3D currentPosition) {
        return nextPosition(currentPosition);
    }
}
