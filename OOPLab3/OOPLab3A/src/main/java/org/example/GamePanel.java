package org.example;

import org.example.collision.CollisionDetector;
import org.example.dto.MobileDTO;
import org.example.engine.ConstUtils;
import org.example.gamestate.GameState;
import org.example.model.*;
import org.example.ninja.NinjaManager;
import org.example.ninja.SwordManager;
import org.example.server.ServerHandler;
import org.example.server.SimpleMessageProcessor;
import org.example.sprite.AnimatedSprite;
import org.example.sprite.Sprite;
import org.example.sprite.SpriteBuffer;
import org.example.vector.Vector2I;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class GamePanel extends JPanel implements Runnable{
    private Thread gameThread;
    private double scaleX = 1.0;
    private double scaleY = 1.0;
    private final int FPS = 60;
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
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.scale(scaleX, scaleY);
        draw(g2d);
        g2d.dispose();
    }

    private void draw(Graphics2D g2d) {
        background.draw(g2d);
        gameMode.draw(g2d);
    }

    public void update() {
        messageProcessor.ifAny(this::processMessage);
        gameMode.update();
    }

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

    public GamePanel () {
        this.setPreferredSize(new Dimension(ConstUtils.worldWidth, ConstUtils.worldHeight));
        this.setBackground(Color.blue);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                scaleX = (double) getWidth() / ConstUtils.worldWidth;
                scaleY = (double) getHeight() / ConstUtils.worldHeight;
            }
        });
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
        ninja = Ninja.create(Vector2I.get(1280, 500), Sprite.get(spriteBuffer.getNinja()));
        symbol = Symbol.createSymbol(Vector2I.get(640, 360), AnimatedSprite.get(spriteBuffer.getSymbols(), 5));
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
        background.initSprite(Sprite.get(spriteBuffer.getBackground()));
    }
    private void createSpriteBuffer() {
        spriteBuffer = new SpriteBuffer();
        spriteBuffer.init();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void run() {
        double drawInterval = 1000.0 / FPS;
        double nextDrawTime = System.currentTimeMillis() + drawInterval;
        while (gameThread != null) {

            update();
            repaint();

            try {
                double remainingTime = nextDrawTime - System.currentTimeMillis();
                if (remainingTime > 0) {
                    Thread.sleep((long) remainingTime);
                }
                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private abstract static class GameMode implements DrawUpdatable {
        public abstract void onStart();
        public abstract void onExit();
        public abstract void processMessage(MobileDTO mobileDTO);
    }

    private class ShurikenGameMode extends GameMode {

        @Override
        public void draw(Graphics2D g2d) {
            enemies.draw(g2d);
            shurikenManager.draw(g2d);
        }

        @Override
        public void update() {
            collisionDetector.processDummies(shurikenManager, enemies);
            shurikenManager.update();
            enemies.update();
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
        public void processMessage(MobileDTO mobileDTO) {
            if (mobileDTO.getMessageType() == 0) { //shooting
                shurikenManager.spawn(spriteBuffer, SimpleMessageProcessor.toMovement(mobileDTO.getVectorData()));
            }
        }
    }

    private class CalibrationGameMode extends GameMode {

        @Override
        public void draw(Graphics2D g2d) {

        }

        @Override
        public void update() {

        }

        @Override
        public void onStart() {
            messageProcessor.send(GameState.CALIBRATING);
        }

        @Override
        public void onExit() {

        }
        @Override
        public void processMessage(MobileDTO mobileDTO) {
            if (mobileDTO.getMessageType() == 4) {
                switchToMode(GameState.SHOOTING);
            }
        }
    }

    private class SwordGameMode extends GameMode {

        @Override
        public void draw(Graphics2D g2d) {
            ninja.draw(g2d);
            symbol.draw(g2d);
        }

        @Override
        public void update() {
            ninja.update();
            ninjaManager.update(ninja, symbol);
            symbol.update();
        }

        @Override
        public void onStart() {
            ninja.show();
            messageProcessor.send(GameState.FIGHTING);
        }

        @Override
        public void onExit() {
            ninja.hide();
            symbol.hide();
        }

        @Override
        public void processMessage(MobileDTO mobileDTO) {
            if (mobileDTO.getMessageType() == 1) {
                swordManager.process(symbol.getTag(), mobileDTO.getVectorData());
            }
        }
    }

}
