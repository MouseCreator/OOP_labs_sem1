package org.example.game.handler;

import org.example.game.event.DeletionEvent;
import org.example.game.helper.GameModelPublisher;

public class DeletionHandler implements EventHandler<DeletionEvent> {
    private final GameModelPublisher publisher;

    public DeletionHandler(GameModelPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void handle(DeletionEvent event) {
        publisher.removeModel(event.getModel());
        event.handle();
    }
}
