package org.example.game.modes;

import org.example.collision.CollisionDetector;
import org.example.dto.DesktopDTO;
import org.example.dto.MessageType;
import org.example.dto.MobileDTO;
import org.example.game.connection.DataTransformer;
import org.example.game.event.*;
import org.example.game.event.Event;
import org.example.game.helper.GameUtils;
import org.example.gamestate.GameState;
import org.example.model.Enemies;
import org.example.model.ShurikenManager;


public class ShurikenGameMode implements GameMode {
    private Enemies enemies;
    private ShurikenManager shurikenManager;
    private CollisionDetector collisionDetector;
    @Override
    public void update() {
        collisionDetector.processDummies(shurikenManager, enemies);
        shurikenManager.update();
        enemies.update();
        if (enemies.destroyedAll()) {
            GameUtils.get().getEventProducer().createEvent(new ModeSwitchEvent(GameState.FIGHTING));
        }
    }

    @Override
    public void onStart() {
        initSelf();
        GameUtils.newEvent(new SendMessageEvent(new DesktopDTO(GameState.SHOOTING)));
    }

    private void initSelf() {
        enemies = Enemies.create();
        shurikenManager = ShurikenManager.create();
        collisionDetector = new CollisionDetector();
    }

    @Override
    public void onExit() {
        shurikenManager.reset();
        enemies.reset();
    }


    @Override
    public void handleEvent(Event event) {
        Handler.forType(EventType.MESSAGE_RECEIVED, event).run(()->{
            ReceiveMessageEvent messageEvent = (ReceiveMessageEvent) event;
            MobileDTO mobileDTO = messageEvent.getMessage();
            if (mobileDTO.getMessageType() == MessageType.SHURIKEN_DATA) {
                shurikenManager.spawn(GameUtils.get().getSpriteBuffer(),
                        DataTransformer.toMovement(mobileDTO.getVectorData()));
                event.handle();
            }
        });

    }
}
