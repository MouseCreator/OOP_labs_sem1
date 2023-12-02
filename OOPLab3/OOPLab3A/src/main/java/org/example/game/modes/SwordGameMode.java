package org.example.game.modes;

import org.example.dto.MobileDTO;
import org.example.game.event.Event;
import org.example.game.event.EventType;
import org.example.game.event.Handler;
import org.example.game.event.MessageEvent;
import org.example.gamestate.GameState;
import org.example.model.Ninja;
import org.example.model.Symbol;
import org.example.ninja.NinjaManager;
import org.example.ninja.SwordManager;
import org.example.server.SimpleMessageProcessor;

import java.awt.*;

public class SwordGameMode implements GameMode{

    private Ninja ninja;
    private Symbol symbol;
    private NinjaManager ninjaManager;
    private SimpleMessageProcessor messageProcessor;
    private SwordManager swordManager;
    @Override
    public void onPause() {

    }

    private boolean timedStarted = false;

    @Override
    public void draw(Graphics2D g2d) {
    }

    @Override
    public void update() {
        ninjaManager.update(ninja, symbol);
        ninja.update();
        if (ninja.isCentralized() && !timedStarted) {
            messageProcessor.send(7);
            timedStarted = true;
        }

        symbol.update();

    }

    @Override
    public void onStart() {
        ninja.show();
        ninja.resetPosition();
        messageProcessor.send(GameState.FIGHTING);
    }

    @Override
    public void onExit() {
        ninja.hide();
        symbol.hide();
    }

    @Override
    public void handleEvent(Event event) {

    }
}
