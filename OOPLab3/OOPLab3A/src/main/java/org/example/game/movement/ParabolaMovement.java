package org.example.game.movement;

import org.example.vector.Vector3D;

public class ParabolaMovement implements Movement{
    private final Vector3D acceleration;
    private Vector3D speed;
    public ParabolaMovement(Vector3D acceleration, Vector3D speed) {
        this.acceleration = acceleration;
        this.speed = speed;
    }

    @Override
    public Vector3D nextPosition(Vector3D currentPosition) {
        speed = speed.add(acceleration);
        return currentPosition.add(speed);
    }
}
