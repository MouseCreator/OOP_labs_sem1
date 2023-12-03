package org.example.collision;

import org.example.game.event.DeletionEvent;
import org.example.game.helper.GameUtils;
import org.example.game.model.GameModel;
import org.example.model.Enemies;
import org.example.model.Shuriken;
import org.example.model.ShurikenManager;

public class CollisionDetector {
    private final CollisionManager collisionManager = new CollisionManager();
    public void processDummies(ShurikenManager shurikenManager, Enemies enemies) {
        enemies.each(enemy -> {
            for (Shuriken shuriken : shurikenManager) {
                if (collisionManager.interacts(enemy, shuriken)) {
                    enemy.toDestroy();
                    shuriken.markToDestroy();
                    destroyEntity(enemy);
                    destroyEntity(shuriken);
                    return;
                }
            }
        }
        );
    }

    private void destroyEntity(GameModel gameModel) {
        GameUtils.get().getEventList().add(new DeletionEvent(gameModel));
    }
}
