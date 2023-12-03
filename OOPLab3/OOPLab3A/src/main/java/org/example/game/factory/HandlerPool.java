package org.example.game.factory;

import org.example.game.event.Event;
import org.example.game.handler.CreationHandler;
import org.example.game.handler.DeletionHandler;
import org.example.game.handler.ModeSwitchHandler;
import org.example.game.handler.SendMessageHandler;


public interface HandlerPool {
    void handle(Event event);
}
