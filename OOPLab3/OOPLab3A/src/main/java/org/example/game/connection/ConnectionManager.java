package org.example.game.connection;

import org.example.dto.DesktopDTO;
import org.example.game.event.ReceiveMessageEvent;
import org.example.game.helper.GameUtils;
import org.example.server.SimpleMessageProcessor;

public class ConnectionManager {
    private SimpleMessageProcessor simpleMessageProcessor;
    private boolean isConnected;
    public void send(DesktopDTO message) {
        if (isConnected) {
            simpleMessageProcessor.send(message);
        }
    }

    public void connectionEvents() {
        simpleMessageProcessor.ifAny(dto -> GameUtils.get().getEventList().add(new ReceiveMessageEvent(dto)));
    }
}
