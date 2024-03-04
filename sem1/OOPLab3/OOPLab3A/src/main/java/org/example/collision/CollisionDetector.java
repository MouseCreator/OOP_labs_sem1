package org.example.collision;

import org.example.game.event.DeletionEvent;
import org.example.game.event.PlayerEvent;
import org.example.game.helper.GameUtils;
import org.example.game.model.GameModel;
import org.example.log.Log;
import org.example.model.Enemies;
import org.example.model.Shuriken;
import org.example.model.ShurikenManager;

public class CollisionDetector {
    private final CollisionM collisionManager = new AdvancedCollisionManager();
    public void processDummies(ShurikenManager shurikenManager, Enemies enemies) {
        enemies.each(enemy -> {
            for (Shuriken shuriken : shurikenManager) {
                if (collisionManager.interacts(enemy, shuriken)) {
                    enemy.toDestroy();
                    Log.write("COLLISION detected");
                    shuriken.markToDestroy();
                    destroyEntity(enemy);
                    destroyEntity(shuriken);
                    GameUtils.newEvent(new PlayerEvent(PlayerEvent.Type.SCORE));
                    return;
                }
            }
        }
        );
    }

    private void destroyEntity(GameModel gameModel) {
        GameUtils.newEvent(new DeletionEvent(gameModel));
    }
}
