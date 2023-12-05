package org.example.collision;

import org.example.game.movement.Movement;
import org.example.vector.Vector3D;

public class AdvancedCollisionManager implements CollisionM{
    private final CollisionManager collisionManager = new CollisionManager();
    @Override
    public boolean interacts(Collidable collidable, Collidable collidable2) {
        Movement movement1 = collidable.getMovement();
        Movement movement2 = collidable.getMovement();
        Vector3D c1s = collidable.getCollision().getCollisionStart();
        Vector3D c1e = collidable.getCollision().getCollisionEnd();
        Vector3D c2s = collidable2.getCollision().getCollisionStart();
        Vector3D c2e = collidable2.getCollision().getCollisionEnd();

        Vector3D v1D = movement1.estimate(c1s).subtract(c1s);
        Vector3D v2D = movement2.estimate(c1s).subtract(c1s);
        double c1Speed = v1D.magnitude();
        double c2Speed = v2D.magnitude();

        double c1Size = c1e.subtract(c1s).magnitude();
        double c2Size = c2e.subtract(c2s).magnitude();

        double iterations = Math.min(c1Speed / c1Size, c2Speed / c2Size);
        if (iterations == 0 || Double.isInfinite(iterations)) {
            return collisionManager.interacts(collidable, collidable2);
        }
        Vector3D d1 = v1D.multiply(1.0 / iterations);
        Vector3D d2 = v1D.multiply(1.0 / iterations);
        for (int i = 0; i < iterations; i++) {
            if (interacts(c1s, c1e, c2s, c2e)) {
                return true;
            }
            c1s = c1s.add(d1);
            c1e = c1e.add(d1);
            c2s = c2s.add(d2);
            c2e = c2e.add(d2);

        }
        return false;
    }

    @Override
    public boolean interacts(Collision c1, Collision c2) {
        return collisionManager.interacts(c1, c2);
    }

    public boolean interacts(Vector3D c1, Vector3D c2, Vector3D c3, Vector3D c4) {
        boolean xOverlap = (c1.x() < c4.x()) &&
                (c2.x() > c3.x());
        boolean yOverlap = (c1.y() < c4.y()) &&
                (c2.y() > c3.y());
        boolean zOverlap = (c1.z() < c4.z()) &&
                (c2.z() > c3.z());

        return xOverlap && yOverlap && zOverlap;
    }
}
