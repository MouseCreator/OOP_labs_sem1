package univ.lab.ninjagame1.controller.processor;

import univ.lab.ninjagame1.event.Event;

public abstract class AbstractEventHandler<T extends Event> implements EventHandler {
    @Override
    public void handle(Event event) {
        if (canHandle(event)) {
            handleEvent(cast(event));
        }
    }
    protected abstract void handleEvent(T event);
    protected abstract T cast(Event event);
}
