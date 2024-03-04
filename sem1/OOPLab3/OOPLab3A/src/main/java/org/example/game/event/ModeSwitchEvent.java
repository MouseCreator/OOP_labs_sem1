package org.example.game.event;

public class ModeSwitchEvent implements Event{

    private final int toMode;
    @Override
    public EventType getType() {
        return EventType.MODE_SWITCH;
    }

    @Override
    public String getName() {
        return "Switch to mode";
    }
    private boolean handled = false;
    @Override
    public boolean isHandled() {
        return handled;
    }

    @Override
    public void handle() {
        handled = true;
    }

    public ModeSwitchEvent(int toMode) {
        this.toMode = toMode;
    }

    public int getToMode() {
        return toMode;
    }
}
