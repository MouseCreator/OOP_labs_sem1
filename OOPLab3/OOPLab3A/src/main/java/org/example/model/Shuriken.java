package org.example.model;

import org.example.engine.ConstUtils;

import java.awt.*;

public class Shuriken extends Entity implements DrawUpdatable{
    private static final Vector2I shurikenSize =Vector2I.get(320, 128);
    private ScalableSprite sprite = null;
    private Vector3D position3D;
    private Vector3D speed3D;
    private boolean destroyed = false;
    private Vector3D acceleration3d;
    public static Shuriken withOrigin(Vector2I originPosition) {
        Vector2I newPosition = originPosition.subtract(shurikenSize.multiply(0.5));
        return new Shuriken(newPosition);
    }

    public void initFromMovement(MovementParams movementParams) {
        this.position3D = Vector3D.get(position.x(), position.y(), 0);
        double speed = movementParams.getSpeed() * 0.002;
        double x = -movementParams.getZAngle() * speed * 1.7;
        double y =-movementParams.getXAngle()*speed;
        double z = 0.4 * speed;
        this.speed3D = Vector3D.get(x, y, z);
        this.acceleration3d = Vector3D.get(0,0.5, 0);
    }

    public Shuriken(Vector2I pos) {
        super(pos, shurikenSize);
    }
    public void initSprite(ScalableSprite sprite) {
        this.sprite = sprite;
        modifyScaleAndPosition();
    }

    @Override
    public void draw(Graphics2D g2d) {
        sprite.draw(g2d, position);
    }

    @Override
    public void update() {
        speed3D = speed3D.add(acceleration3d);
        position3D = position3D.add(speed3D);
        modifyScaleAndPosition();
        if (position3D.y() > ConstUtils.worldHeight - position3D.z()) {
            destroyed = true;
        }
    }

    private void modifyScaleAndPosition() {
        double scale = 0.2 + 1.2 * Math.max(0, (ConstUtils.depth - position3D.z()) / ConstUtils.depth);
        Vector2I beforeSize = Vector2I.from(sprite.getCurrentSize());
        position = position.add(Vector2I.get((int) speed3D.x(), (int) speed3D.y()));
        sprite.setScale(scale);
        Vector2I afterSize = Vector2I.from(sprite.getCurrentSize());
        position = position.add(beforeSize.subtract(afterSize).multiply(0.5));
    }

    public boolean isDestroyed() {
        return destroyed;
    }
}
