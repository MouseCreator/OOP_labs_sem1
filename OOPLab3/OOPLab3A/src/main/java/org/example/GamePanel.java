package org.example;

import org.example.engine.ConstUtils;
import org.example.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class GamePanel extends JPanel implements Runnable{
    private Thread gameThread;
    private double scaleX = 1.0;
    private double scaleY = 1.0;
    private final int FPS = 60;
    private Drams drams;
    private Bolts bolts;
    private SpriteBuffer spriteBuffer;
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.scale(scaleX, scaleY);

        bolts.draw(g2d);
        drams.draw(g2d);

        g2d.dispose();
    }

    public void update() {
        bolts.update();
        drams.update();
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
        bolts = Bolts.create();
        drams = Drams.create(bolts);
        createSpriteBuffer();
        initSprites();
    }

    private void initSprites() {
        bolts.each(b -> b.initSprite(Sprite.get(spriteBuffer.getBolt())));
        drams.each(d -> d.initSprite(AnimatedSprite.get(spriteBuffer.getDrams(), 4)));
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

//    public void run() {
//        double drawInterval = 1000.0 / FPS;
//        double delta = 0;
//        long lastTime = System.currentTimeMillis();
//        long currentTime;
//        while (gameThread != null) {
//            currentTime = System.currentTimeMillis();
//            delta += (currentTime - lastTime) / drawInterval;
//            lastTime = currentTime;
//            if (delta >= 1) {
//                update();
//                repaint();
//                delta -= 1;
//            }
//        }
//    }

}
