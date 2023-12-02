package org.example.game.event;

public interface Event {
    EventType getType();
    String getName();
    boolean isHandled();
    void handle();
}
