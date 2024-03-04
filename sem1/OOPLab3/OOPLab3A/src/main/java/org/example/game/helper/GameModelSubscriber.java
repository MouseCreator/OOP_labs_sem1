package org.example.game.helper;

import org.example.game.model.GameModel;

public interface GameModelSubscriber {
    void onAdd(GameModel entity);
    void onRemove(GameModel entity);
}
