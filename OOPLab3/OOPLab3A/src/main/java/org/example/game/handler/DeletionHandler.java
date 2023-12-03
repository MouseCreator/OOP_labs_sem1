package org.example.game.handler;

import org.example.game.event.DeletionEvent;
import org.example.game.event.Event;
import org.example.game.event.EventType;
import org.example.game.helper.GameModelPublisher;

public class DeletionHandler extends AbstractHandler<DeletionEvent> {
    private final GameModelPublisher publisher;

    public DeletionHandler(GameModelPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    protected void handleEvent(DeletionEvent event) {
        publisher.removeModel(event.getModel());
    }

    @Override
    protected DeletionEvent cast(Event event) {
        return (DeletionEvent) event;
    }

    @Override
    public boolean canHandle(Event event) {
        return event.getType() == EventType.MODEL_DELETION;
    }
}
