package org.example.model;

import org.example.engine.ConstUtils;
import org.example.game.drawable.SpriteImpl;
import org.example.vector.Vector2I;

import java.awt.*;

public class Background {
    private static final Vector2I bgSize = Vector2I.get(ConstUtils.worldWidth, ConstUtils.worldHeight);
    private SpriteImpl sprite = null;
    public static Background getBG() {
        return new Background();
    }
    private Background() {
    }
    public void initSprite(SpriteImpl sprite) {
        this.sprite = sprite;
    }
    public void draw(Graphics2D g2d) {
        sprite.draw(g2d, Vector2I.zero(), bgSize);
    }
}
