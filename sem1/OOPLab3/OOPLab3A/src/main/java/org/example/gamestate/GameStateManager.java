package org.example.gamestate;

public class GameStateManager {
    private final int currentState = GameState.CALIBRATING;
    public void update() {

    }

    public int getCurrentState() {
        return currentState;
    }
}
