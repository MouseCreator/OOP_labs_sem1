package org.example.game.event;

import org.example.game.model.GameModel;

public class CreationEvent implements Event{
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

    private GameModel model;

    public GameModel getModel() {
        return model;
    }

    public void setModel(GameModel model) {
        this.model = model;
    }
}
