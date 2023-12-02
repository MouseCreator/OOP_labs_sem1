package org.example.game.modes;

import org.example.game.drawable.DrawManager;
import org.example.game.event.Event;
import org.example.model.Updatable;

public interface GameMode extends Updatable, DrawManager {
    void onStart();
    void onExit();
    void onPause();
    void handleEvent(Event event);
}
