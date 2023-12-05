package org.example.model;

import org.example.collision.Collidable;
import org.example.game.drawable.Sprite;
import org.example.game.model.GameModel;
import org.example.game.movement.Movement;

public interface Enemy extends GameModel, Collidable {
    void initSprite(Sprite sprite);
    void toDestroy();
    boolean isDestroyed();
    void setMovement(Movement movement);
}
