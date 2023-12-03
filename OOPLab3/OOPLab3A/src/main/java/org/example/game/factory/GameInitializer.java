package org.example.game.factory;

import org.example.game.Game;
import org.example.game.connection.ConnectionManager;
import org.example.game.draw.GameDraw;
import org.example.game.draw.UIDraw;
import org.example.game.drawable.SpriteImpl;
import org.example.game.entity.ModelManager;
import org.example.game.helper.*;
import org.example.game.player.PlayerManager;
import org.example.game.update.GameUpdate;
import org.example.model.Background;

public class GameInitializer {
    private ModelManager modelManager;
    private GameModelPublisher modelPublisher;
    public GameInitializer() {
        initSelf();
    }
    private void initSelf() {
        modelManager = new ModelManager();
        modelPublisher = new GameModelPublisherImpl();

        ModelDraw modelDraw = new ModelDraw();
        modelPublisher.subscribe(modelDraw);
        modelManager.setModelDraw(modelDraw);

        ModelUpdate modelUpdate = new ModelUpdate();
        modelPublisher.subscribe(modelUpdate);
        modelManager.setModelUpdate(modelUpdate);
    }
    public GameDraw generateGameDraw() {
        GameDraw gameDraw = new GameDraw();
        Background background = Background.getBG();
        background.initSprite(SpriteImpl.get(GameUtils.get().getSpriteBuffer().getBackground()));
        gameDraw.setBackground(background);
        gameDraw.setModelDraw(modelManager.getModelDraw());
        gameDraw.setUiDraw(new UIDraw(GameUtils.get().userInterface()));
        return gameDraw;
    }

    public GameUpdate generateGameUpdate() {
        GameUpdate gameUpdate = new GameUpdate();
        gameUpdate.setModelUpdate(modelManager.getModelUpdate());
        return gameUpdate;
    }

    public HandlerPool generateHandlerPool(Game game, ConnectionManager connectionManager, PlayerManager playerManager) {
        HandlerPoolImpl handlerPool = new HandlerPoolImpl();
        handlerPool.initCreationHandler(modelPublisher);
        handlerPool.initDeletionHandler(modelPublisher);
        handlerPool.initModeSwitchHandler(game);
        handlerPool.initSendMessageHandler(connectionManager);
        handlerPool.initPlayerEventHandler(playerManager);
        handlerPool.initReceiveMessageHandler();
        return handlerPool;
    }
}
