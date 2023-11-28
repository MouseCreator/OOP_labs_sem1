package org.example.server;

public class ServerHandler {
    private GameServer gameServer;
    private Thread listenerThread;
    private Thread writerThread;
    public SimpleMessageProcessor start() {
        gameServer = new GameServer();
        listenerThread = new Thread(()->gameServer.start(6666));
        listenerThread.start();
        writerThread = new Thread(()-> gameServer.startWriter());
        writerThread.start();
        return new SimpleMessageProcessor(gameServer.getMessageQueue(), gameServer.getSendQueue());
    }

    public void stop() {
        listenerThread.interrupt();
        writerThread.interrupt();
        gameServer.stop();
    }
}
