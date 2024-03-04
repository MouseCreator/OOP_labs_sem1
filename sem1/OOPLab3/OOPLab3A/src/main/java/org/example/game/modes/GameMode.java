package org.example.game.modes;

import org.example.game.event.Event;
import org.example.model.Updatable;

public interface GameMode extends Updatable {
    void onStart();
    void onExit();
    void handleEvent(Event event);
}
