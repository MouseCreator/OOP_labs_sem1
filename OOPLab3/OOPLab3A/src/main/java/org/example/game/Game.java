package org.example.game;

import org.example.collision.CollisionDetector;
import org.example.dto.MobileDTO;
import org.example.game.drawable.*;
import org.example.game.modes.CalibrationGameMode;
import org.example.game.modes.GameMode;
import org.example.game.modes.ShurikenGameMode;
import org.example.game.modes.SwordGameMode;
import org.example.gamestate.GameState;
import org.example.model.*;
import org.example.ninja.NinjaManager;
import org.example.ninja.SwordManager;
import org.example.server.ServerHandler;
import org.example.server.SimpleMessageProcessor;
import org.example.vector.Vector3D;

import java.awt.*;

public class Game implements DrawUpdatable {
    private Background background;
    private SpriteBuffer spriteBuffer;
    private SimpleMessageProcessor messageProcessor;
    private GameMode gameMode;
    private void processMessage(MobileDTO mobileDTO) {
        if (mobileDTO.getMessageType() == 2) {
            switchToMode(GameState.CALIBRATING);
        }
        //gameMode.processMessage(mobileDTO);
    }
    private void switchToMode(int mode) {
        gameMode.onExit();
        gameMode = switch (mode) {
            case GameState.SHOOTING -> new ShurikenGameMode();
            case GameState.FIGHTING -> new SwordGameMode();
            case GameState.CALIBRATING -> new CalibrationGameMode();
            default -> throw new IllegalStateException("Unknown game mode");
        };
        gameMode.onStart();
    }
    public void init() {
        createSpriteBuffer();
        background = Background.getBG();
        initSprites();
        initGameMode();
    }


    private void initGameMode() {
        gameMode = new CalibrationGameMode();
    }
    public void connectAndStart(ServerHandler handler) {
        messageProcessor = handler.start();
    }
    private void initSprites() {
        background.initSprite(SpriteImpl.get(spriteBuffer.getBackground()));
    }
    private void createSpriteBuffer() {
        spriteBuffer = new SpriteBuffer();
        spriteBuffer.init();
    }


    @Override
    public void draw(Graphics2D g2d) {

    }

    @Override
    public void update() {

    }
}
