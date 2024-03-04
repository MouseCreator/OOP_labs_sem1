package org.example.game.handler;

import org.example.game.Game;
import org.example.game.event.Event;
import org.example.game.event.EventType;
import org.example.game.event.ModeSwitchEvent;
import org.example.game.modes.*;
import org.example.game.player.PlayerManager;
import org.example.gamestate.GameState;

public class ModeSwitchHandler extends AbstractHandler<ModeSwitchEvent> {
    private final Game game;
    private final PlayerManager playerManager;
    public ModeSwitchHandler(Game game, PlayerManager playerManager) {
        this.game = game;
        this.playerManager = playerManager;
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
            case GameState.CONNECTING -> new ConnectingMode();
            case GameState.GAME_OVER -> new ScoreViewMode(playerManager);
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
