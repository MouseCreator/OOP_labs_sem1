package org.example.game.movement;

import org.example.vector.Vector3D;

public class StaticMovement implements Movement{
    @Override
    public Vector3D nextPosition(Vector3D currentPosition) {
        return currentPosition;
    }
}
