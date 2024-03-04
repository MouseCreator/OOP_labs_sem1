package org.example.game.helper;

import org.example.game.drawable.SpriteBuffer;
import org.example.game.event.Event;
import org.example.game.event.EventList;
import org.example.game.event.EventProducer;
import org.example.game.ui.UserInterface;

public class GameUtils {
    private SpriteBuffer spriteBuffer;
    private EventList eventList;
    private EventProducer eventProducer;
    private static GameUtils gameUtils = null;
    private UserInterface userInterface = null;
    public static GameUtils get() {
        if (gameUtils == null) {
            throw new IllegalStateException("Game utils are not initialized");
        }
        return gameUtils;
    }

    public static void newEvent(Event event) {
        gameUtils.eventList.add(event);
    }
    private GameUtils() {

    }
    public SpriteBuffer getSpriteBuffer() {
        return spriteBuffer;
    }
    public EventList getEventList() {
        return eventList;
    }

    public EventProducer getEventProducer() {
        return eventProducer;
    }
    public static void create() {
        gameUtils = new GameUtils();
        gameUtils.spriteBuffer = new SpriteBuffer();
        gameUtils.spriteBuffer.init();
        gameUtils.eventList = new EventList();
        gameUtils.eventProducer = new EventProducer(gameUtils.eventList);
        gameUtils.userInterface = new UserInterface();
    }

    public UserInterface userInterface() {
        return userInterface;
    }
}
