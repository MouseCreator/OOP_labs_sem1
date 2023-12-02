package org.example.game.modes;

import org.example.collision.CollisionDetector;
import org.example.dto.DesktopDTO;
import org.example.dto.MobileDTO;
import org.example.game.event.*;
import org.example.game.event.Event;
import org.example.game.helper.GameUtils;
import org.example.gamestate.GameState;
import org.example.model.Enemies;
import org.example.model.ShurikenManager;
import org.example.server.SimpleMessageProcessor;

import java.awt.*;

public class ShurikenGameMode implements GameMode {
    private Enemies enemies;
    private ShurikenManager shurikenManager;
    private CollisionDetector collisionDetector;
    private SimpleMessageProcessor messageProcessor;
    private GameUtils gameUtils;

    @Override
    public void draw(Graphics2D g2d) {

    }

    @Override
    public void update() {
        collisionDetector.processDummies(shurikenManager, enemies);
        shurikenManager.update();
        enemies.update();
        if (enemies.destroyedAll()) {
            gameUtils.getEventProducer().createEvent(new ModeSwitchEvent(GameState.FIGHTING));
        }
    }

    @Override
    public void onStart() {
        gameUtils.getEventProducer().createEvent(new SendMessageEvent(new DesktopDTO(GameState.SHOOTING)));
        shurikenManager.reset();
        enemies.reset();
    }

    @Override
    public void onExit() {
        shurikenManager.reset();
        enemies.reset();
    }

    @Override
    public void onPause() {

    }

    @Override
    public void handleEvent(Event event) {
        Handler.forType(EventType.MESSAGE_RECEIVED, event).run(()->{
            ReceiveMessageEvent messageEvent = (ReceiveMessageEvent) event;
            MobileDTO mobileDTO = messageEvent.getMessage();
            if (mobileDTO.getMessageType() == 0) {
                shurikenManager.spawn(gameUtils.getSpriteBuffer(), SimpleMessageProcessor.toMovement(mobileDTO.getVectorData()));
                event.handle();
            }
        });

    }
}
