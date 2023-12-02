package org.example.game.event;

import org.example.dto.DesktopDTO;

public class SendMessageEvent implements Event{
    public SendMessageEvent(DesktopDTO message) {
        this.message = message;
    }

    @Override
    public EventType getType() {
        return EventType.SEND_MESSAGE;
    }
    @Override
    public String getName() {
        return "Send message event";
    }
    private boolean isHandled = false;
    @Override
    public boolean isHandled() {
        return isHandled;
    }
    @Override
    public void handle() {
        isHandled = true;
    }
    private final DesktopDTO message;
    public void setHandled(boolean handled) {
        isHandled = handled;
    }
    public DesktopDTO getMessage() {
        return message;
    }
}
