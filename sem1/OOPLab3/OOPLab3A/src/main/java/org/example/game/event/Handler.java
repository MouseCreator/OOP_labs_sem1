package org.example.game.event;

public class Handler {
    public static EventRunner forType(EventType type, Event event) {
        if (event.isHandled())
            return new EventConsumer();
        if (event.getType() == type) {
            return new EventRunnerImpl();
        }
        return new EventConsumer();
    }
    public interface EventRunner {
        void run(Runnable r);
    }
    private static class EventRunnerImpl implements EventRunner{

        public void run(Runnable r) {
            r.run();
        }
    }

    private static class EventConsumer implements EventRunner{
        public void run(Runnable r) {

        }
    }
}
