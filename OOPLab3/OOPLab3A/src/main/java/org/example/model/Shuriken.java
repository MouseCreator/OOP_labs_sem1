package org.example.model;

import org.example.collision.Collidable;
import org.example.collision.Collision;
import org.example.collision.Sizes;
import org.example.utils.ConstUtils;
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
    private static final Vector2I shurikenSize =Vector2I.get(160, 64);
    private Sprite sprite = null;
    private boolean destroyed = false;
    private final MovingEntity entity;

    public static Shuriken newInstance() {
        Vector3D spawnPos = Vector3D.get(ConstUtils.worldWidth / 2.0, 120, -10);
        return new Shuriken(spawnPos);
    }

    public void initFromMovement(MovementParams movementParams) {

        initSpeed(movementParams);
        collision = new Collision(entity.getPosition(), Sizes.shurikenSize());
    }

    private void initSpeed(MovementParams movementParams) {
        double speed = movementParams.getSpeed() * 0.00075;
        double x = -movementParams.getZAngle() * speed * ConstUtils.X_MULTIPLIER;
        double y = movementParams.getXAngle() * speed * ConstUtils.Y_MULTIPLIER;
        double z = speed * ConstUtils.Z_MULTIPLIER;
        Vector3D speed3D = Vector3D.get(x, y, z);
        Vector3D acceleration3d = Vector3D.get(0,-0.2, 0);
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
        System.out.println(collision.getCollisionEnd());
    }
    public void markToDestroy() {
        destroyed = true;
    }
    private boolean isOutOfBounds() {
        return entity.getPosition().z() > ConstUtils.worldDepth || entity.getPosition().y() < 0
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
