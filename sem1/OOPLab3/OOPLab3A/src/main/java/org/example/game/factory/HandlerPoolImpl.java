package org.example.game.factory;

import org.example.game.Game;
import org.example.game.connection.ConnectionManager;
import org.example.game.event.*;
import org.example.game.handler.*;
import org.example.game.helper.GameModelPublisher;
import org.example.game.player.PlayerManager;

import java.util.ArrayList;
import java.util.List;


public class HandlerPoolImpl implements HandlerPool {
    private final List<EventHandler> handlers = new ArrayList<>();

    public void initCreationHandler(GameModelPublisher publisher) {
        EventHandler creationHandler = new CreationHandler(publisher);
        handlers.add(creationHandler);
    }
    public void initDeletionHandler(GameModelPublisher publisher) {
        EventHandler deletionHandler = new DeletionHandler(publisher);
        handlers.add(deletionHandler);
    }
    public void initReceiveMessageHandler() {
        EventHandler receiveMessageHandler = new ReceiveMessageHandler();
        handlers.add(receiveMessageHandler);
    }
    public void initModeSwitchHandler(Game game, PlayerManager playerManager) {
        EventHandler modeSwitchHandler = new ModeSwitchHandler(game, playerManager);
        handlers.add(modeSwitchHandler);
    }
    public void initSendMessageHandler(ConnectionManager connectionManager) {
        EventHandler sendMessageHandler = new SendMessageHandler(connectionManager);
        handlers.add(sendMessageHandler);
    }

    public void initPlayerEventHandler(PlayerManager playerManager) {
        EventHandler playerEventHandler = new PlayerEventHandler(playerManager);
        handlers.add(playerEventHandler);
    }
    public void initConnectionEventHandler() {
        ConnectionEventHandler playerEventHandler = new ConnectionEventHandler();
        handlers.add(playerEventHandler);
    }


    @Override
    public void handle(Event event) {
        for (EventHandler eventHandler : handlers) {
            if (eventHandler.canHandle(event)) {
                eventHandler.handle(event);
            }
        }
    }


}
