package org.example.model;

import org.example.collision.Collision;
import org.example.collision.Sizes;
import org.example.game.drawable.Drawable;
import org.example.game.drawable.Sprite;
import org.example.game.entity.Entity;
import org.example.game.entity.MovingEntity;
import org.example.game.entity.MovingEntityImpl;
import org.example.game.movement.Movement;
import org.example.vector.Vector2I;
import org.example.vector.Vector3D;

public class BatEnemy implements Enemy{
    private MovingEntity entity;
    private Collision collision;
    private Sprite sprite;

    public static Vector2I size = new Vector2I(160, 100);
    public static BatEnemy withOrigin(Vector3D position, Sprite image) {
        BatEnemy enemy = new BatEnemy();
        enemy.sprite = image;
        enemy.entity = new MovingEntityImpl(position, size);
        enemy.collision = new Collision(position, Sizes.batSize());
        return enemy;
    }
    @Override
    public Collision getCollision() {
        return collision;
    }

    @Override
    public Entity getEntity() {
        return entity;
    }

    @Override
    public Drawable getDrawable() {
        return sprite;
    }

    @Override
    public void initSprite(Sprite sprite) {
        this.sprite = sprite;
    }
    private boolean destroyed = false;
    @Override
    public void toDestroy() {
        destroyed = true;
    }

    @Override
    public boolean isDestroyed() {
        return destroyed;
    }

    @Override
    public void setMovement(Movement movement) {
        entity.setMovement(movement);
    }

    @Override
    public void update() {
        entity.updatePosition();
        collision.moveTo(entity.getPosition());
    }
}
