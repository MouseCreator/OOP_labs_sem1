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
import org.example.vector.Vector2I;
import org.example.vector.Vector3D;

import java.awt.*;

public class Game implements DrawUpdatable {
    private Enemies enemies;
    private Background background;
    private SpriteBuffer spriteBuffer;
    private ShurikenManager shurikenManager;
    private SimpleMessageProcessor messageProcessor;
    private CollisionDetector collisionDetector;
    private SwordManager swordManager;
    private Ninja ninja;
    private Symbol symbol;
    private GameMode gameMode;
    private NinjaManager ninjaManager;

    private void processMessage(MobileDTO mobileDTO) {
        if (mobileDTO.getMessageType() == 2) {
            switchToMode(GameState.CALIBRATING);
        }
        gameMode.processMessage(mobileDTO);
    }
    private void switchToMode(int mode) {
        gameMode.onExit();
        gameMode = switch (mode) {
            case 0 -> new ShurikenGameMode();
            case 1 -> new SwordGameMode();
            case 3 -> new CalibrationGameMode();
            default -> throw new IllegalStateException("Unknown game mode");
        };
        gameMode.onStart();
    }
    public void init() {
        createSpriteBuffer();
        enemies = Enemies.create(spriteBuffer);
        shurikenManager = ShurikenManager.create();
        background = Background.getBG();
        initCollisions();
        initSprites();
        initNinja();
        initGameMode();
    }

    private void initNinja() {
        ninja = Ninja.create(SpriteImpl.get(spriteBuffer.getNinja()));
        symbol = Symbol.createSymbol(Vector3D.get(640, 360, 0), AnimatedSpriteImpl.get(spriteBuffer.getSymbols(), 5));
        swordManager = new SwordManager();
        ninjaManager = new NinjaManager();
    }
    private void initGameMode() {
        gameMode = new CalibrationGameMode();
    }
    private void initCollisions() {
        collisionDetector = new CollisionDetector();
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
