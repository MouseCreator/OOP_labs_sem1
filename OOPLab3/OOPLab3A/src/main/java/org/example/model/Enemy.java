package org.example.model;

import org.example.collision.Collidable;
import org.example.collision.Collision;
import org.example.collision.Sizes;

import java.awt.*;

public class Enemy extends Entity implements DrawUpdatable, Collidable {
    public static final Vector2I enemySize = Vector2I.get(128, 128);
    private ScalableSprite sprite = null;
    private Vector3D position3D = Vector3D.zero();
    private Collision collision;
    private boolean destroyed = false;
    public static Enemy withOrigin(Vector3D position3D, ScalableSprite sprite) {
        Enemy enemy = new Enemy();
        enemy.initSprite(sprite);
        enemy.moveTo(position3D);
        enemy.initCollision();
        return enemy;
    }

    private void initCollision() {
        collision = new Collision(position3D, Sizes.dummySize());
    }

    private void moveTo(Vector3D position3D) {
        this.position3D = position3D;
        updatePosition();
    }

    private void updatePosition() {
        position = DimTranslator.get().translate(sprite, position3D);
    }

    private Enemy() {
        position = Vector2I.zero();
        size = Vector2I.from(enemySize);
    }
    public void initSprite(ScalableSprite sprite) {
        this.sprite = sprite;
        this.size = sprite.getCurrentSize();
    }
    public void update() {
    }
    public void draw(Graphics2D g2d) {
        sprite.draw(g2d, position);
    }
    public Vector3D position3d() {
        return position3D;
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
