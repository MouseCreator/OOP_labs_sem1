package org.example.game.player;

public class Player {
    private int score;
    private int health;

    public Player() {
        reset();
    }

    public void reset() {
        score = 0;
        health = 3;
    }

    public void addScore(int value) {
        score += value;
    }

    public int getScore() {
        return score;
    }

    public void damage() {
        health--;
    }
    public int getHealth() {
        return health;
    }

    public boolean isDead() {
        return health <= 0;
    }
}
