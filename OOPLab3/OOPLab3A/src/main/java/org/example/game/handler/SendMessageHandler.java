package org.example.game.handler;

import org.example.game.connection.ConnectionManager;
import org.example.game.event.Event;
import org.example.game.event.EventType;
import org.example.game.event.SendMessageEvent;

public class SendMessageHandler extends AbstractHandler<SendMessageEvent> {
    private final ConnectionManager connectionManager;
    public SendMessageHandler(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    protected void handleEvent(SendMessageEvent event) {
        connectionManager.send(event.getMessage());
    }

    @Override
    protected SendMessageEvent cast(Event event) {
        return (SendMessageEvent) event;
    }

    @Override
    public boolean canHandle(Event event) {
        return event.getType() == EventType.SEND_MESSAGE;
    }
}
