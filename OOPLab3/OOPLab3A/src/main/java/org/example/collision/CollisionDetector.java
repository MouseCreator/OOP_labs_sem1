package org.example.collision;

import org.example.model.Enemies;
import org.example.model.Shuriken;
import org.example.model.ShurikenManager;

public class CollisionDetector {
    private final CollisionManager collisionManager = new CollisionManager();
    public void processDummies(ShurikenManager shurikenManager, Enemies enemies) {
        enemies.each(e -> {
            for (Shuriken shuriken : shurikenManager) {
                if (collisionManager.interacts(e, shuriken)) {
                    e.toDestroy();
                    shuriken.markToDestroy();
                    return;
                }
            }
        }
        );
    }
}
