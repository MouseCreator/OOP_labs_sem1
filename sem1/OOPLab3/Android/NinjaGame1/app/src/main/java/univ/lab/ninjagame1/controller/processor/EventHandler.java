package univ.lab.ninjagame1.controller.processor;

import univ.lab.ninjagame1.event.Event;

public interface EventHandler {
    void handle(Event event);
    boolean canHandle(Event event);
}
