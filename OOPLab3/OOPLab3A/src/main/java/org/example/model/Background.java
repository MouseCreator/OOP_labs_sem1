package org.example.model;

import org.example.engine.ConstUtils;

import java.awt.*;

public class Background extends Entity implements DrawUpdatable{
    private static final Vector2I bgSize = Vector2I.get(ConstUtils.worldWidth, ConstUtils.worldHeight);
    private Sprite sprite = null;
    public static Background getBG() {
        Vector2I newPosition = Vector2I.zero();
        return new Background(newPosition);
    }
    private Background(Vector2I pos) {
        super(pos, bgSize);
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
