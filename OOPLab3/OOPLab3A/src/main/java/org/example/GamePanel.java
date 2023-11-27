package org.example;

import org.example.engine.ConstUtils;
import org.example.model.*;
import org.example.server.ServerHandler;
import org.example.server.SimpleMessageProcessor;

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
    private Background background;
    private SpriteBuffer spriteBuffer;
    private ShurikenManager shurikenManager;
    private SimpleMessageProcessor messageProcessor;
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
        //bolts.draw(g2d);
        //drams.draw(g2d);
        shurikenManager.draw(g2d);
    }
    private int count = 0;
    public void update() {
        //bolts.update();
        //drams.update();
        messageProcessor.ifAny(m -> shurikenManager.spawn(spriteBuffer, m));
        shurikenManager.update();
        if (count < 31) {
            count++;
        }
        if (count==30) {
            //shurikenManager.spawn(spriteBuffer, new MovementParams(-0.2,0,0.4,12000));
        }
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
        shurikenManager = ShurikenManager.create();
        background = Background.getBG();
        drams = Drams.create(bolts);
        createSpriteBuffer();
        initSprites();
    }

    public void connectAndStart(ServerHandler handler) {
        messageProcessor = handler.start();
    }

    private void initSprites() {
        bolts.each(b -> b.initSprite(Sprite.get(spriteBuffer.getBolt())));
        drams.each(d -> d.initSprite(AnimatedSprite.get(spriteBuffer.getDrams(), 4)));
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
