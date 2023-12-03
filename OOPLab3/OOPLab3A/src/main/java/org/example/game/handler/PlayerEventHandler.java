package org.example.game.handler;

import org.example.game.event.Event;
import org.example.game.event.EventType;
import org.example.game.event.PlayerEvent;
import org.example.game.helper.GameUtils;
import org.example.game.player.PlayerManager;

public class PlayerEventHandler extends AbstractHandler<PlayerEvent> {

    private final PlayerManager playerManager;

    public PlayerEventHandler(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    @Override
    protected void handleEvent(PlayerEvent event) {
        switch (event.getActionType()) {
            case DAMAGE -> {
                playerManager.onDamage();
                if (playerManager.isPlayerDead()) {
                    //handle death
                }
            }
            case RESET -> playerManager.onReset();
            case SCORE -> playerManager.onScore(event.getAmount());
        }
    }

    @Override
    protected PlayerEvent cast(Event event) {
        return (PlayerEvent) event;
    }

    @Override
    public boolean canHandle(Event event) {
        return event.getType() == EventType.PLAYER_EVENT;
    }
}
