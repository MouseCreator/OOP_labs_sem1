package org.example.collision;

import org.example.game.movement.Movement;

public interface Collidable  {
    Collision getCollision();
    Movement getMovement();
}
