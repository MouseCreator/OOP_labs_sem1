package org.example.game.helper;

import org.example.game.drawable.SpriteBuffer;
import org.example.game.event.EventList;
import org.example.game.event.EventProducer;

public class GameUtils {
    private SpriteBuffer buffer;
    private EventList eventList;
    private EventProducer eventProducer;

    public SpriteBuffer getSpriteBuffer() {
        return buffer;
    }

    public void setSpriteBuffer(SpriteBuffer buffer) {
        this.buffer = buffer;
    }

    public EventList getEventList() {
        return eventList;
    }

    public void setEventList(EventList eventList) {
        this.eventList = eventList;
    }

    public EventProducer getEventProducer() {
        return eventProducer;
    }

    public void setEventProducer(EventProducer eventProducer) {
        this.eventProducer = eventProducer;
    }
}
