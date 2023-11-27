package org.example.model;

import java.awt.*;

public class Bolt extends Entity implements DrawUpdatable {

    private static final Vector2I boltSize = Vector2I.get(170, 170);
    private Sprite sprite = null;
    public static Bolt withOrigin(Vector2I originPosition) {
        Vector2I newPosition = originPosition.subtract(boltSize.multiply(0.5));
        return new Bolt(newPosition);
    }
    private Bolt(Vector2I pos) {
        super(pos, boltSize);
    }

    public void initSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void update() {
    }
    public void draw(Graphics2D g2d) {
        sprite.draw(g2d, position);
    }
}
