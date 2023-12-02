package org.example.game.handler;

import org.example.game.event.SendMessageEvent;
import org.example.server.SimpleMessageProcessor;

public class SendMessageHandler implements EventHandler<SendMessageEvent> {
    private final SimpleMessageProcessor messageProcessor;
    public SendMessageHandler(SimpleMessageProcessor messageProcessor) {
        this.messageProcessor = messageProcessor;
    }

    @Override
    public void handle(SendMessageEvent event) {
        messageProcessor.send(event.getMessage());
    }
}
