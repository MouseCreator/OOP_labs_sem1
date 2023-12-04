package org.example.model;

import org.example.collision.Collidable;
import org.example.collision.Collision;
import org.example.collision.Sizes;
import org.example.engine.ConstUtils;
import org.example.game.drawable.Drawable;
import org.example.game.drawable.Sprite;
import org.example.game.entity.Entity;
import org.example.game.entity.MovingEntity;
import org.example.game.entity.MovingEntityImpl;
import org.example.game.model.GameModel;
import org.example.game.movement.Movement;
import org.example.game.movement.ParabolaMovement;
import org.example.vector.Vector2I;
import org.example.vector.Vector3D;

public class Shuriken implements GameModel, Collidable {
    private static final Vector2I shurikenSize =Vector2I.get(320, 128);
    private Sprite sprite = null;
    private boolean destroyed = false;
    private final MovingEntity entity;
    public static Shuriken withOrigin(Vector3D origin) {
        return new Shuriken(origin);
    }
    public void initFromMovement(MovementParams movementParams) {
        entity.setPosition(Vector3D.get(ConstUtils.worldWidth / 2.0, 120, -10));
        initSpeed(movementParams);
        collision = new Collision(entity.getPosition(), Sizes.shurikenSize());
    }

    private void initSpeed(MovementParams movementParams) {
        double speed = movementParams.getSpeed() * 0.001;
        double x = -movementParams.getZAngle() * speed * ConstUtils.X_MULTIPLIER;
        double y = movementParams.getXAngle() * speed * ConstUtils.Y_MULTIPLIER;
        double z = speed * ConstUtils.Z_MULTIPLIER;
        Vector3D speed3D = Vector3D.get(x, y, z);
        Vector3D acceleration3d = Vector3D.get(0,-0.4, 0);
        Movement movement = new ParabolaMovement(acceleration3d, speed3D);
        entity.setMovement(movement);
    }

    public Shuriken(Vector3D pos) {
        entity = new MovingEntityImpl(pos, shurikenSize);
    }
    public void initSprite(Sprite sprite) {
        this.sprite = sprite;
    }
    @Override
    public void update() {
        entity.updatePosition();
        collision.moveTo(entity.getPosition());
        if (isOutOfBounds()) {
            destroyed = true;
        }
    }
    public void markToDestroy() {
        destroyed = true;
    }
    private boolean isOutOfBounds() {
        return entity.getPosition().z() > ConstUtils.depth || entity.getPosition().y() < 0
                || entity.getPosition().z() < -40;
    }
    public boolean isDestroyed() {
        return destroyed;
    }
    private Collision collision;
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
}
