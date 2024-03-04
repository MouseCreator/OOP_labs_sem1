package org.example.model;

import org.example.collision.Collision;
import org.example.collision.Sizes;
import org.example.utils.ConstUtils;
import org.example.game.drawable.Drawable;
import org.example.game.drawable.Sprite;
import org.example.game.entity.Entity;
import org.example.game.entity.MovingEntity;
import org.example.game.entity.MovingEntityImpl;
import org.example.game.movement.Movement;
import org.example.vector.Vector2I;
import org.example.vector.Vector3D;

public class DummyEnemy implements Enemy{
    public static final Vector2I enemySize = Vector2I.get(256, 256);
    private Sprite sprite = null;
    private Collision collision;
    private boolean destroyed = false;
    private MovingEntity entity;
    public static DummyEnemy withOrigin(Vector3D position3D, Sprite sprite) {
        DummyEnemy enemy = new DummyEnemy();
        enemy.entity = new MovingEntityImpl(position3D, enemySize);
        enemy.initSprite(sprite);
        enemy.moveTo(position3D);
        enemy.initCollision();
        return enemy;
    }

    private void initCollision() {
        collision = new Collision(entity.getPosition(), Sizes.dummySize());
    }
    private void moveTo(Vector3D position3D) {
        this.entity.setPosition(position3D);
    }
    private DummyEnemy() {
    }
    @Override
    public Entity getEntity() {
        return entity;
    }

    @Override
    public Drawable getDrawable() {
        return sprite;
    }

    public void initSprite(Sprite sprite) {
        this.sprite = sprite;
    }
    public void update() {
        if (entity.getPosition().z() > ConstUtils.PLAYER_Z) {
            entity.updatePosition();
            collision.moveTo(entity.getPosition());
        }
    }
    @Override
    public Collision getCollision() {
        return collision;
    }

    @Override
    public Movement getMovement() {
        return entity.getMovement();
    }

    public void toDestroy() {
        destroyed = true;
    }
    public boolean isDestroyed() {
        return destroyed;
    }
    public void setMovement(Movement movement) {
        this.entity.setMovement(movement);
    }
}
