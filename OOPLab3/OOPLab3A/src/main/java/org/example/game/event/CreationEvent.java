package org.example.game.event;

import org.example.game.model.GameModel;

public class CreationEvent implements Event{

    public CreationEvent(GameModel model) {
        this.model = model;
    }

    @Override
    public EventType getType() {
        return EventType.MODEL_CREATION;
    }
    @Override
    public String getName() {
        return "Creation event";
    }
    private boolean handled =false;
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
