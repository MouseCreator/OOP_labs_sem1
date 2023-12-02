package org.example.game.entity;

import org.example.game.helper.SizeComponent;
import org.example.game.movement.Movement;
import org.example.game.movement.StaticMovement;
import org.example.vector.Vector2I;
import org.example.vector.Vector3D;

public class MovingEntityImpl implements MovingEntity {

    private final Entity entity;
    private Movement movement;
    public MovingEntityImpl(Vector3D position, Vector2I size) {
        entity = new EntityImpl(position, size);
        movement = new StaticMovement();
    }

    public MovingEntityImpl(Entity entity, Movement movement) {
        this.entity = entity;
        this.movement = movement;
    }

    @Override
    public Vector3D getPosition() {
        return entity.getPosition();
    }

    @Override
    public void setPosition(Vector3D position) {
        entity.setPosition(position);
    }

    @Override
    public SizeComponent getSize() {
        return entity.getSize();
    }

    @Override
    public void setSize(Vector2I size) {
        entity.setSize(size);
    }

    @Override
    public void updatePosition() {
        entity.setPosition(movement.nextPosition(getPosition()));
    }

    @Override
    public void setMovement(Movement movement) {
        this.movement = movement;
    }

    @Override
    public Movement getMovement() {
        return movement;
    }
}
