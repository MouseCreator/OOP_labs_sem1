package org.example.game;

import org.example.game.connection.ConnectionManager;
import org.example.game.draw.GameDraw;
import org.example.game.event.Event;
import org.example.game.factory.GameInitializer;
import org.example.game.factory.HandlerPool;
import org.example.game.helper.GameUtils;
import org.example.game.modes.CalibrationGameMode;
import org.example.game.modes.GameMode;
import org.example.game.update.GameUpdate;

import java.awt.*;

public class Game {
    private GameMode gameMode;
    private GameUpdate gameUpdate;
    private GameDraw gameDraw;
    private HandlerPool handlerPool;
    private ConnectionManager connectionManager;
    public void init() {
        GameUtils.create();
        initGameMode();
        initConnection();
        initGame();
    }

    private void initGame() {
        GameInitializer initializer = new GameInitializer();
        gameUpdate = initializer.generateGameUpdate();
        gameDraw = initializer.generateGameDraw();
        handlerPool = initializer.generateHandlerPool(this, connectionManager);
    }

    private void initConnection() {
        connectionManager = new ConnectionManager();
    }

    private void initGameMode() {
        gameMode = new CalibrationGameMode();
    }
    public void draw(Graphics2D g2d) {
        gameDraw.draw(g2d);
    }
    public void update() {
        connectionManager.connectionEvents();
        handleEvents();
        gameUpdate.update();
        gameMode.update();
    }

    private void handleEvents() {
        GameUtils.get().getEventList().read(this::handleEvent);
    }

    private void handleEvent(Event event) {
        handlerPool.handle(event);
        gameMode.handleEvent(event);
    }

    public GameMode getGameMode() {
        return gameMode;
    }
    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }
}
