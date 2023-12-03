package org.example.game.event;

import org.example.game.model.GameModel;

public class DeletionEvent implements Event {

    public DeletionEvent(GameModel model) {
        this.model = model;
    }

    @Override
    public EventType getType() {
        return EventType.MODEL_DELETION;
    }
    @Override
    public String getName() {
        return "Deletion event";
    }
    private boolean handled = false;
    @Override
    public boolean isHandled() {
        return handled;
    }

    @Override
    public void handle() {
        handled = true;
    }

    private final GameModel model;

    public GameModel getModel() {
        return model;
    }
}
