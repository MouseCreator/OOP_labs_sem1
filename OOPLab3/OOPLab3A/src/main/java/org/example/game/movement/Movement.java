package org.example.game.movement;

import org.example.vector.Vector3D;

public interface Movement {
    Vector3D nextPosition(Vector3D currentPosition);
    Vector3D estimate(Vector3D currentPosition);
}
