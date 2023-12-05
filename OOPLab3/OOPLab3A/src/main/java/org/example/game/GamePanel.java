package org.example.game;

import org.example.utils.ConstUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class GamePanel extends JPanel {
    private double scaleX = 1.0;
    private double scaleY = 1.0;
    private final GameLoop gameLoop;
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.scale(scaleX, scaleY);
        gameLoop.draw(g2d);
        g2d.dispose();
    }

    public GamePanel(GameLoop gameLoop) {
        super();
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
        this.gameLoop = gameLoop;
    }

}
