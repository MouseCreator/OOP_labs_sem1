package org.example.game.event;

public class PlayerEvent implements Event{

    public PlayerEvent(Type type) {
        this.type = type;
        amount = 1;
    }

    public PlayerEvent(int amount) {
        this.type = Type.SCORE;
        this.amount = amount;
    }

    public enum Type {
        DAMAGE, SCORE, RESET
    }

    private final Type type;
    private final int amount;
    private boolean handled = false;

    @Override
    public EventType getType() {
        return EventType.PLAYER_EVENT;
    }

    @Override
    public String getName() {
        return "Player event";
    }

    @Override
    public boolean isHandled() {
        return handled;
    }

    @Override
    public void handle() {
        handled = true;
    }

    public Type getActionType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }
}
