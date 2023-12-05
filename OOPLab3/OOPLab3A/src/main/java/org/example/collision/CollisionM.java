package org.example.collision;


public interface CollisionM {
    boolean interacts(Collidable collidable, Collidable collidable2);
    boolean interacts(Collision c1, Collision c2);
}
