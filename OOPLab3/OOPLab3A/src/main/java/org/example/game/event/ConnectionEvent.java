package org.example.game.event;

public class ConnectionEvent implements Event {
    public static final ConnectionState CONNECTED = ConnectionState.CONNECTION_RECEIVED;
    public static final ConnectionState DISCONNECTED = ConnectionState.CONNECTION_LOST;

    @Override
    public EventType getType() {
        return EventType.CONNECTION;
    }
    @Override
    public String getName() {
        return "Connection event";
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

    public enum ConnectionState {
        CONNECTION_RECEIVED, CONNECTION_LOST
    }

    private final ConnectionState state;
    public ConnectionEvent(ConnectionState state) {
        this.state = state;
    }

    public boolean isConnected() {
        return state == ConnectionState.CONNECTION_RECEIVED;
    }

    public boolean isLostConnection() {
        return state == ConnectionState.CONNECTION_LOST;
    }
}
