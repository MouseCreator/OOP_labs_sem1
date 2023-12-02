package org.example.game.helper;

import org.example.game.model.GameModel;

public interface GameModelPublisher {
    void createModel(GameModel gameModel);
    void removeModel(GameModel gameModel);
    void subscribe(GameModelSubscriber subscriber);
    void unsubscribe(GameModelSubscriber subscriber);
}
