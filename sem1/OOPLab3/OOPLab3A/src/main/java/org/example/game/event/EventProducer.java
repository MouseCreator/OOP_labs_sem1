package org.example.game.event;



public class EventProducer {
    private final EventList eventList;
    public EventProducer(EventList eventList) {
        this.eventList = eventList;
    }

    public void createEvent(Event event) {
        eventList.add(event);
    }

}
