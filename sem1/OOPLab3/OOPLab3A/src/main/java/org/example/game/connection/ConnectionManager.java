package org.example.game.connection;

import org.example.dto.DesktopDTO;
import org.example.game.event.ReceiveMessageEvent;
import org.example.game.helper.GameUtils;
import org.example.server.SimpleMessageProcessor;

public class ConnectionManager {
    private final SimpleMessageProcessor simpleMessageProcessor;
    public ConnectionManager(SimpleMessageProcessor simpleMessageProcessor) {
        this.simpleMessageProcessor = simpleMessageProcessor;
    }

    public void send(DesktopDTO message) {
        simpleMessageProcessor.send(message);
    }

    public void connectionEvents() {
        simpleMessageProcessor.ifAny(dto -> GameUtils.get().getEventList().add(new ReceiveMessageEvent(dto)));
    }
}
