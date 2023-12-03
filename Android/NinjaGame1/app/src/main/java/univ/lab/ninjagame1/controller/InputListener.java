package univ.lab.ninjagame1.controller;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

import univ.lab.ninjagame1.event.Event;

public class InputListener {
    private final ConcurrentLinkedQueue<Event> queue;
    public InputListener() {
        queue = new ConcurrentLinkedQueue<>();
    }

    public void putEvent(Event event) {
        queue.add(event);
    }

    public void ifAny(Consumer<Event> consumer) {
        Event e;
        while ((e = queue.poll()) != null) {
            consumer.accept(e);
        }
    }
}
