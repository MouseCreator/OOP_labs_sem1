package org.example.game.handler;

import org.example.game.event.CreationEvent;
import org.example.game.helper.GameModelPublisher;

public class CreationHandler implements EventHandler<CreationEvent> {
    private final GameModelPublisher publisher;
    public CreationHandler(GameModelPublisher publisher) {
        this.publisher = publisher;
    }
    @Override
    public void handle(CreationEvent event) {
        publisher.createModel(event.getModel());
        event.handle();
    }
}
