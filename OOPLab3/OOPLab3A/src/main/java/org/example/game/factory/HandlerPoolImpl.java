package org.example.game.factory;

import org.example.game.Game;
import org.example.game.connection.ConnectionManager;
import org.example.game.event.*;
import org.example.game.handler.*;
import org.example.game.helper.GameModelPublisher;


public class HandlerPoolImpl implements HandlerPool {
    private CreationHandler creationHandler;
    private DeletionHandler deletionHandler;
    private ModeSwitchHandler modeSwitchHandler;
    private SendMessageHandler sendMessageHandler;

    public void initCreationHandler(GameModelPublisher publisher) {
        this.creationHandler = new CreationHandler(publisher);
    }
    public void initDeletionHandler(GameModelPublisher publisher) {
        this.deletionHandler = new DeletionHandler(publisher);
    }
    public void initModeSwitchHandler(Game game) {
        this.modeSwitchHandler = new ModeSwitchHandler(game);
    }
    public void initSendMessageHandler(ConnectionManager connectionManager) {
        this.sendMessageHandler = new SendMessageHandler(connectionManager);
    }

    public CreationHandler getCreationHandler() {
        return creationHandler;
    }

    public DeletionHandler getDeletionHandler() {
        return deletionHandler;
    }

    public ModeSwitchHandler getModeSwitchHandler() {
        return modeSwitchHandler;
    }

    public SendMessageHandler getSendMessageHandler() {
        return sendMessageHandler;
    }

    @Override
    public void handle(Event event) {
        switch (event.getType()) {
            case MODEL_CREATION -> creationHandler.handle((CreationEvent) event);
            case MODEL_DELETION -> deletionHandler.handle((DeletionEvent) event);
            case SEND_MESSAGE -> sendMessageHandler.handle((SendMessageEvent) event);
            case MODE_SWITCH -> modeSwitchHandler.handle((ModeSwitchEvent) event);
            default -> {

            }
        }
    }
}
