package org.example.game.entity;

import org.example.game.movement.Movement;

public interface MovingEntity extends Entity {
    void updatePosition();
    void setMovement(Movement movement);
    Movement getMovement();
}
