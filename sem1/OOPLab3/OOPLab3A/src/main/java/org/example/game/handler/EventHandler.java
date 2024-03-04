package org.example.game.handler;

import org.example.game.event.Event;

public interface EventHandler {
    void handle(Event event);
    boolean canHandle(Event event);
}
