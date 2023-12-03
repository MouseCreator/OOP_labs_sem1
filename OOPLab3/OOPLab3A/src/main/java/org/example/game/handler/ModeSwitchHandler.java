package org.example.game.handler;

import org.example.game.Game;
import org.example.game.event.Event;
import org.example.game.event.EventType;
import org.example.game.event.ModeSwitchEvent;
import org.example.game.modes.CalibrationGameMode;
import org.example.game.modes.GameMode;
import org.example.game.modes.ShurikenGameMode;
import org.example.game.modes.SwordGameMode;
import org.example.gamestate.GameState;

public class ModeSwitchHandler extends AbstractHandler<ModeSwitchEvent> {
    private final Game game;
    public ModeSwitchHandler(Game game) {
        this.game = game;
    }


    @Override
    protected void handleEvent(ModeSwitchEvent event) {
        if (game.getGameMode() != null) {
            game.getGameMode().onExit();
        }
        GameMode gameMode = switch (event.getToMode()) {
            case GameState.SHOOTING -> new ShurikenGameMode();
            case GameState.FIGHTING -> new SwordGameMode();
            case GameState.CALIBRATING -> new CalibrationGameMode();
            default -> throw new IllegalStateException("Unknown game mode");
        };
        game.setGameMode(gameMode);
        gameMode.onStart();
    }

    @Override
    protected ModeSwitchEvent cast(Event event) {
        return (ModeSwitchEvent) event;
    }

    @Override
    public boolean canHandle(Event event) {
        return event.getType()== EventType.MODE_SWITCH;
    }
}
