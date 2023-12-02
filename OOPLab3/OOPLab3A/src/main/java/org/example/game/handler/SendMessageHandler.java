package org.example.game.handler;

import org.example.game.connection.ConnectionManager;
import org.example.game.event.SendMessageEvent;

public class SendMessageHandler implements EventHandler<SendMessageEvent> {
    private final ConnectionManager connectionManager;
    public SendMessageHandler(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }
    @Override
    public void handle(SendMessageEvent event) {
        connectionManager.send(event.getMessage());
        event.handle();
    }
}
