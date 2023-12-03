package org.example.game.modes;

import org.example.dto.DesktopDTO;
import org.example.dto.MessageType;
import org.example.dto.MobileDTO;
import org.example.game.drawable.AnimatedSpriteImpl;
import org.example.game.drawable.SpriteImpl;
import org.example.game.event.*;
import org.example.game.helper.GameUtils;
import org.example.game.timer.Timer;
import org.example.gamestate.GameState;
import org.example.model.Ninja;
import org.example.model.Symbol;
import org.example.ninja.SwordManager;
import org.example.vector.Vector3D;

public class SwordGameMode implements GameMode{
    private Ninja ninja;
    private Symbol symbol;
    private SwordManager swordManager;
    private final Timer timer = new Timer();
    @Override
    public void update() {
        if (ninja.isCentralized() && !timer.started()) {
            GameUtils.newEvent(new SendMessageEvent(new DesktopDTO(GameState.WAITS_DATA)));
            symbol.show();
            symbol.changeToRandom();
            timer.runFor(2000);
        }
        if (timer.finished()) {
            GameUtils.newEvent(new SendMessageEvent(new DesktopDTO(GameState.COLLECTS_SWORD_DATA)));
        }
    }

    @Override
    public void onStart() {
        initSelf();
        ninja.show();
        ninja.resetPosition();
        GameUtils.newEvent(new SendMessageEvent(new DesktopDTO(GameState.FIGHTING)));
    }

    private void initSelf() {
        ninja = Ninja.create(SpriteImpl.get(GameUtils.get().getSpriteBuffer().getNinja()));
        symbol = Symbol.createSymbol(Vector3D.get(400, 200, 0), AnimatedSpriteImpl.get(
                GameUtils.get().getSpriteBuffer().getSymbols(), 5));
        swordManager = new SwordManager();
        GameUtils.newEvent(new CreationEvent(ninja));
        GameUtils.newEvent(new CreationEvent(symbol));
    }

    @Override
    public void onExit() {
        ninja.hide();
        symbol.hide();
    }

    @Override
    public void handleEvent(Event event) {
        Handler.forType(EventType.MESSAGE_RECEIVED, event).run(()->{
            ReceiveMessageEvent messageEvent = (ReceiveMessageEvent) event;
            MobileDTO mobileDTO = messageEvent.getMessage();
            if (mobileDTO.getMessageType() == MessageType.SWORD_DATA) {
                boolean isRecognized = swordManager.process(symbol.getTag(), mobileDTO.getVectorData());
                updateSymbol(isRecognized);
                event.handle();
            }
        });

    }

    private void updateSymbol(boolean isRecognized) {
        symbol.setStatus(isRecognized);
    }
}
