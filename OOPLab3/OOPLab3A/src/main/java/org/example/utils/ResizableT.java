package org.example.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class ResizableT extends JFrame {
    private final JPanel gamePanel;
    private final int worldWidth = 1280;
    private final int worldHeight = 720;

    public ResizableT() {
        setTitle("Resizable Game Window");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(worldWidth, worldHeight));

        gamePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                double scaleX = (double) getWidth() / worldWidth;
                double scaleY = (double) getHeight() / worldHeight;

                Graphics2D g2d = (Graphics2D) g;
                g2d.scale(scaleX, scaleY);

                // Render your game elements here using world coordinates
                // For example, draw a rectangle at (100, 100) in world coordinates
                g2d.setColor(Color.RED);
                g2d.fillRect(100, 100, 200, 100);
            }
        };

        add(gamePanel);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                gamePanel.repaint(); // Redraw the game when the window is resized
            }
        });

        pack();
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ResizableT window = new ResizableT();
            window.setVisible(true);
        });
    }
}
