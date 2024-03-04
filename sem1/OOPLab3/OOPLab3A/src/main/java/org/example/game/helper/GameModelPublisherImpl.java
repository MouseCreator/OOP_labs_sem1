package org.example.game.helper;

import org.example.game.model.GameModel;

import java.util.ArrayList;
import java.util.List;

public class GameModelPublisherImpl implements GameModelPublisher {
    private final List<GameModelSubscriber> subscribers = new ArrayList<>();
    @Override
    public void createModel(GameModel gameModel) {
        for (GameModelSubscriber subscriber : subscribers) {
            subscriber.onAdd(gameModel);
        }
    }

    @Override
    public void removeModel(GameModel gameModel) {
        for (GameModelSubscriber subscriber : subscribers) {
            subscriber.onRemove(gameModel);
        }
    }

    public void subscribe(GameModelSubscriber subscriber) {
        this.subscribers.add(subscriber);
    }

    public void unsubscribe(GameModelSubscriber subscriber) {
        this.subscribers.remove(subscriber);
    }
}
