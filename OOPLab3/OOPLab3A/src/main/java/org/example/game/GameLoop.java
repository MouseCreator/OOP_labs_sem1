package org.example.game;

import org.example.engine.ConstUtils;

import java.awt.*;

public class GameLoop implements Runnable {
    private Thread gameThread;
    private GamePanel gamePanel = null;
    private final Game game;
    public GameLoop() {
        game = new Game();
    }

    public GamePanel getGamePanel() {
        if (gamePanel == null) {
            throw new IllegalStateException("Game loop is not initialized!");
        }
        return gamePanel;
    }

    public void init() {
        initGamePanel();
    }

    private void initGamePanel() {
        gamePanel = new GamePanel(this);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    public void run() {
        double drawInterval = 1000.0 / ConstUtils.FPS;
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

    private void repaint() {
        gamePanel.repaint();
    }

    public void update() {
        game.update();
    }

    public void draw(Graphics2D g2d) {
        game.draw(g2d);
    }
}
