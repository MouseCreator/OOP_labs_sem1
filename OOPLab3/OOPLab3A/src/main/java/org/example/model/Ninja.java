package org.example.model;


import org.example.engine.ConstUtils;
import org.example.sprite.Sprite;
import org.example.vector.Vector2I;

import java.awt.*;

public class Ninja extends Entity implements DrawUpdatable {
    private Sprite sprite;
    private static final Vector2I ninjaSize = Vector2I.get(256, 256);
    private final Vector2I speed = Vector2I.get(-5, 0);
    public static Ninja create(Vector2I pos, Sprite sprite) {
        Ninja ninja = new Ninja(pos, ninjaSize);
        ninja.sprite = sprite;
        return ninja;
    }

    public Ninja(Vector2I pos, Vector2I size) {
        super(pos, size);
    }

    @Override
    public void draw(Graphics2D g2d) {
        sprite.draw(g2d, position);
    }

    @Override
    public void update() {
        if (!isCentralized()) {
            position = position.add(speed);
        }
    }

    public void show() {
        sprite.setVisible(true);
    }
    public void hide() {
        sprite.setVisible(false);
    }

    public boolean isCentralized() {
        return Math.abs(DimTranslator.get().toCenter(position, size).x() - ConstUtils.worldWidth/2) > 5;
    }
}
