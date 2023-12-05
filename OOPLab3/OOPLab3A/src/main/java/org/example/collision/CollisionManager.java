package org.example.collision;

public class CollisionManager implements CollisionM {
    public boolean interacts(Collidable collidable, Collidable collidable2) {
        return interacts(collidable.getCollision(), collidable2.getCollision());
    }
    public boolean interacts(Collision c1, Collision c2) {
        boolean xOverlap = (c1.getCollisionStart().x() < c2.getCollisionEnd().x()) &&
                (c1.getCollisionEnd().x() > c2.getCollisionStart().x());
        boolean yOverlap = (c1.getCollisionStart().y() < c2.getCollisionEnd().y()) &&
                (c1.getCollisionEnd().y() > c2.getCollisionStart().y());
        boolean zOverlap = (c1.getCollisionStart().z() < c2.getCollisionEnd().z()) &&
                (c1.getCollisionEnd().z() > c2.getCollisionStart().z());

        return xOverlap && yOverlap && zOverlap;
    }


}
