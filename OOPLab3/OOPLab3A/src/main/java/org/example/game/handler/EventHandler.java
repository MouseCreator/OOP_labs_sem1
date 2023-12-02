package org.example.game.handler;

import org.example.game.event.Event;

public interface EventHandler<T extends Event> {
    void handle(T event);
}
