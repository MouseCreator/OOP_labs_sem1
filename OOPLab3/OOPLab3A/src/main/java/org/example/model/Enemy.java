package org.example.model;

import org.example.collision.Collidable;
import org.example.collision.Collision;
import org.example.collision.Sizes;
import org.example.game.drawable.Drawable;
import org.example.game.drawable.Sprite;
import org.example.game.entity.Entity;
import org.example.game.entity.EntityImpl;
import org.example.game.model.GameModel;
import org.example.vector.Vector2I;
import org.example.vector.Vector3D;

public class Enemy implements GameModel, Collidable {
    public static final Vector2I enemySize = Vector2I.get(128, 128);
    private Sprite sprite = null;
    private Collision collision;
    private boolean destroyed = false;
    private Entity entity;
    public static Enemy withOrigin(Vector3D position3D, Sprite sprite) {
        Enemy enemy = new Enemy();
        enemy.entity = new EntityImpl(position3D, enemySize);
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
    private Enemy() {
        entity = new EntityImpl(Vector3D.zero(), enemySize);
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
    }
    @Override
    public Collision getCollision() {
        return collision;
    }
    public void toDestroy() {
        destroyed = true;
    }
    public boolean isDestroyed() {
        return destroyed;
    }
}
