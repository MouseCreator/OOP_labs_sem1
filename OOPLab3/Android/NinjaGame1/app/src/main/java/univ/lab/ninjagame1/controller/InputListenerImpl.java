package univ.lab.ninjagame1.controller;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.function.Consumer;

import univ.lab.ninjagame1.event.Event;

public class InputListenerImpl implements InputListener {
    private final BlockingDeque<Event> queue;
    public InputListenerImpl() {
        queue = new LinkedBlockingDeque<>();
    }

    public void putEvent(Event event) {
        queue.add(event);
    }

    @Override
    public Event get() throws InterruptedException {
        return queue.take();
    }
}
