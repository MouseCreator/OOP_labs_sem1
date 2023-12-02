package org.example.game.event;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class EventList {
    private final List<Event> list = new ArrayList<>();
    public void add(Event event) {
        list.add(event);
    }
    public List<Event> readAll() {
        ArrayList<Event> events = new ArrayList<>(list);
        list.clear();
        return events;
    }

    public void read(Consumer<Event> consumer) {
        readAll().forEach(consumer);
    }
}
