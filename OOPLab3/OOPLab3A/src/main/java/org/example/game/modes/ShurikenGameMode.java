package org.example.game.modes;

import org.example.collision.CollisionDetector;
import org.example.dto.MobileDTO;
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

    @Override
    public void draw(Graphics2D g2d) {

    }

    @Override
    public void update() {
        collisionDetector.processDummies(shurikenManager, enemies);
        shurikenManager.update();
        enemies.update();
        if (enemies.destroyedAll()) {
          //  switchToMode(1);
        }
    }

    @Override
    public void onStart() {
        messageProcessor.send(GameState.SHOOTING);
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
    public void processMessage(MobileDTO mobileDTO) {
        if (mobileDTO.getMessageType() == 0) { //shooting
            //shurikenManager.spawn(spriteBuffer, SimpleMessageProcessor.toMovement(mobileDTO.getVectorData()));
        }
    }
}
