package org.example.game.handler;

import org.example.dto.MessageType;
import org.example.game.event.Event;
import org.example.game.event.EventType;
import org.example.game.event.ModeSwitchEvent;
import org.example.game.event.ReceiveMessageEvent;
import org.example.game.helper.GameUtils;

public class ReceiveMessageHandler extends AbstractHandler<ReceiveMessageEvent> {
    @Override
    protected void handleEvent(ReceiveMessageEvent event) {
        if (event.getMessage().getMessageType() == MessageType.MODE_SWITCH) {
            GameUtils.newEvent(new ModeSwitchEvent(event.getMessage().getGameMode()));
        }
    }

    @Override
    protected ReceiveMessageEvent cast(Event event) {
        return (ReceiveMessageEvent) event;
    }

    @Override
    public boolean canHandle(Event event) {
        return event.getType() == EventType.MESSAGE_RECEIVED;
    }
}
