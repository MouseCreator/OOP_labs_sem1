package org.example.game.helper;

import org.example.game.drawable.SpriteBuffer;
import org.example.game.event.EventList;
import org.example.game.event.EventProducer;

public class GameUtils {
    private SpriteBuffer spriteBuffer;
    private EventList eventList;
    private EventProducer eventProducer;
    private static GameUtils gameUtils = null;
    public static GameUtils get() {
        if (gameUtils == null) {
            throw new IllegalStateException("Game utils are not initialized");
        }
        return gameUtils;
    }

    public SpriteBuffer getSpriteBuffer() {
        return spriteBuffer;
    }

    public void setSpriteBuffer(SpriteBuffer buffer) {
        this.spriteBuffer = buffer;
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
    public static void create() {
        gameUtils = new GameUtils();
        gameUtils.spriteBuffer = new SpriteBuffer();
        gameUtils.spriteBuffer.init();
        gameUtils.eventList = new EventList();
        gameUtils.eventProducer = new EventProducer(gameUtils.eventList);
    }
}
