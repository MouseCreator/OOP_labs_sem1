package org.example.model;

import java.awt.*;

public class Dram extends Entity implements DrawUpdatable {
    private AnimatedSprite animatedSprite = null;
    private int lives = 0;
    private final static Vector2I dramSize = Vector2I.get(256, 256);
    public static Dram withOrigin(Vector2I originPosition) {
        Vector2I newPosition = originPosition.subtract(dramSize.multiply(0.5));
        return new Dram(newPosition);
    }
    public enum State {
        ALIVE, DEAD, WAITING
    }
    private State state = State.WAITING;
    public Dram(Vector2I pos) {
        super(pos, dramSize);
    }

    public void draw(Graphics2D g2d) {
        animatedSprite.draw(g2d, position);
    }
    public void update() {

    }

    public void initSprite(AnimatedSprite animatedSprite) {
        this.animatedSprite = animatedSprite;
    }
}
