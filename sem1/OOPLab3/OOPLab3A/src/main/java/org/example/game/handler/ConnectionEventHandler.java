package org.example.game.handler;

import org.example.game.event.ConnectionEvent;
import org.example.game.event.Event;
import org.example.game.event.EventType;
import org.example.game.event.ModeSwitchEvent;
import org.example.game.helper.GameUtils;
import org.example.gamestate.GameState;

public class ConnectionEventHandler extends AbstractHandler<ConnectionEvent> {
    @Override
    protected void handleEvent(ConnectionEvent event) {
        if (event.isConnected()) {
            GameUtils.newEvent(new ModeSwitchEvent(GameState.CALIBRATING));
        } else if (event.isLostConnection()) {
            GameUtils.newEvent(new ModeSwitchEvent(GameState.CONNECTING));
        }
    }

    @Override
    protected ConnectionEvent cast(Event event) {
        return (ConnectionEvent) event;
    }

    @Override
    public boolean canHandle(Event event) {
        return event.getType() == EventType.CONNECTION;
    }
}
