package org.example.model;

import org.example.GamePanel;

import java.awt.*;

public class Dram extends Entity{
    private GamePanel gamePanel;
    public enum State {
        ALIVE, DEAD, WAITING
    }
    private State state = State.WAITING;
    public Dram(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void draw(Graphics2D g2d) {

    }
    public void update() {

    }
}
