package org.example.model;

import org.example.engine.ConstUtils;

import java.awt.*;

public class Shuriken extends Entity implements DrawUpdatable{
    private static final Vector2I shurikenSize =Vector2I.get(320, 128);
    private ScalableSprite sprite = null;
    private Vector3D position3D;
    private Vector3D speed3D;
    private Vector3D acceleration3d;
    public static Shuriken withOrigin(Vector2I originPosition) {
        Vector2I newPosition = originPosition.subtract(shurikenSize.multiply(0.5));
        return new Shuriken(newPosition);
    }

    public void initFromMovement(MovementParams movementParams) {
        this.position3D = Vector3D.get(position.x(), position.y(), 0);
        double speed = movementParams.getSpeed() * 0.2;
        this.speed3D = Vector3D.get(movementParams.getZAngle()*speed, movementParams.getXAngle()*speed, 0.5 * speed);
        this.acceleration3d = Vector3D.get(0,-1, 0);
    }

    public Shuriken(Vector2I pos) {
        super(pos, shurikenSize);
    }
    public void initSprite(ScalableSprite sprite) {
        this.sprite = sprite;
    }

    @Override
    public void draw(Graphics2D g2d) {
        sprite.draw(g2d, position);
    }

    @Override
    public void update() {
        speed3D = speed3D.add(acceleration3d);
        position3D = position3D.add(speed3D);
        position = Vector2I.get((int) position3D.x(), (int) position3D.y());
        sprite.setScale(0.5 + Math.max (0, (ConstUtils.depth - position3D.z()) / ConstUtils.depth));
    }
}
