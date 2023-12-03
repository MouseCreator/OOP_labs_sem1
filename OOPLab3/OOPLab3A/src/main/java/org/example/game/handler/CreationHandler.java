package org.example.game.handler;

import org.example.game.event.CreationEvent;
import org.example.game.event.Event;
import org.example.game.event.EventType;
import org.example.game.helper.GameModelPublisher;

public class CreationHandler extends AbstractHandler<CreationEvent> {
    private final GameModelPublisher publisher;
    public CreationHandler(GameModelPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    protected void handleEvent(CreationEvent event) {
        publisher.createModel(event.getModel());
        event.handle();
    }

    @Override
    protected CreationEvent cast(Event event) {
        return (CreationEvent) event;
    }

    @Override
    public boolean canHandle(Event event) {
        return event.getType() == EventType.MODEL_CREATION;
    }
}
