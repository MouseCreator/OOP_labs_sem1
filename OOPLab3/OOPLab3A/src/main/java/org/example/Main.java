package org.example;

import org.example.game.GameLoop;
import org.example.game.GamePanel;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(true);
        window.setTitle("Ninja game");
        GameLoop gameLoop = new GameLoop();
        GamePanel gamePanel =  gameLoop.getGamePanel();
        window.add(gamePanel);

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);
        gameLoop.startGameThread();
    }
}