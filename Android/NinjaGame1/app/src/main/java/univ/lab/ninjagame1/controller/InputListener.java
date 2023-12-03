package univ.lab.ninjagame1.controller;


import java.util.function.Consumer;

import univ.lab.ninjagame1.event.Event;

public interface InputListener {
    void putEvent(Event event);
    void ifAny(Consumer<Event> consumer);
}
