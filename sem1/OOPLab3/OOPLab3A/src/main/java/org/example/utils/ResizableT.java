package org.example.utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.IOException;

public class ResizableT extends JFrame {
    private final JPanel gamePanel;
    private final int worldWidth = 1280;
    private final int worldHeight = 720;

    public ResizableT() {
        setTitle("Resizable Game Window");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(worldWidth, worldHeight));
        gamePanel = new BackgroundPanel();
        pack();
        setLocationRelativeTo(null);
        add(gamePanel);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                gamePanel.repaint(); // Redraw the game when the window is resized
            }
        });
    }

    public class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel() {
            try {
                backgroundImage = ImageIO.read(new File("background.jpg"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }

            double scaleX = (double) getWidth() / worldWidth;
            double scaleY = (double) getHeight() / worldHeight;

            Graphics2D g2d = (Graphics2D) g;
            g2d.scale(scaleX, scaleY);

            g2d.setColor(Color.RED);
            g2d.fillRect(100, 100, 200, 100);
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ResizableT window = new ResizableT();
            window.setVisible(true);
        });
    }
}
