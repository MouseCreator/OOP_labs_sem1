package org.example.game.handler;

import org.example.game.event.Event;

public abstract class AbstractHandler<T extends Event> implements EventHandler{
    @Override
    public void handle(Event event) {
        handleEvent(cast(event));
    }
    protected abstract void handleEvent(T event);
    protected abstract T cast(Event event);
}
