package org.example.game.event;

import org.example.dto.MobileDTO;

public class ReceiveMessageEvent implements Event {
    private final EventType type = EventType.MESSAGE_RECEIVED;
    private boolean handled = false;
    public ReceiveMessageEvent(MobileDTO dto) {
        this.message = dto;
    }
    @Override
    public EventType getType() {
        return type;
    }
    @Override
    public String getName() {
        return "Message Event";
    }
    @Override
    public boolean isHandled() {
        return handled;
    }
    @Override
    public void handle() {
        handled = true;
    }
    private final MobileDTO message;
    public MobileDTO getMessage() {
        return message;
    }
}
