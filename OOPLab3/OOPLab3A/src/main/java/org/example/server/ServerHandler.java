package org.example.server;

public class ServerHandler {
    private GameServer gameServer;
    private Thread listenerThread;
    public SimpleMessageProcessor start() {
        gameServer = new GameServer();
        listenerThread = new Thread(()->gameServer.start(7777));
        listenerThread.start();
        return new SimpleMessageProcessor(gameServer.getMessageQueue());
    }

    public void stop() {
        gameServer.stop();
    }
}
