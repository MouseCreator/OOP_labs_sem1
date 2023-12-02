package org.example.model;


import org.example.engine.ConstUtils;
import org.example.game.drawable.Drawable;
import org.example.game.drawable.Sprite;
import org.example.game.drawable.SpriteImpl;
import org.example.game.entity.Entity;
import org.example.game.entity.MovingEntity;
import org.example.game.entity.MovingEntityImpl;
import org.example.game.model.GameModel;
import org.example.game.movement.LinearMovement;
import org.example.vector.Vector2I;
import org.example.vector.Vector3D;

import java.awt.*;

public class Ninja implements GameModel, Updatable {
    private Sprite sprite;
    private static final Vector2I ninjaSize = Vector2I.get(256, 256);
    private static final Vector3D originalPosition = Vector3D.get(1300, 500, 5);
    private final MovingEntity entity;
    public static Ninja create(SpriteImpl sprite) {
        Ninja ninja = new Ninja(ninjaSize);
        ninja.sprite = sprite;
        return ninja;
    }

    public Ninja(Vector2I size) {
        entity = new MovingEntityImpl(Vector3D.from(originalPosition), size);
        entity.setMovement(new LinearMovement(Vector3D.get(-5, 0, 0)));
    }


    @Override
    public void update() {
        if (!isCentralized()) {
            entity.updatePosition();
        }
    }

    public void show() {
        sprite.setVisible(true);
    }
    public void hide() {
        sprite.setVisible(false);
    }

    public boolean isCentralized() {
        return Math.abs(entity.getPosition().x() - ConstUtils.worldWidth / 2.0) < 5;
    }

    public void resetPosition() {
        entity.setPosition(Vector3D.from(originalPosition));
    }
    @Override
    public Entity getEntity() {
        return entity;
    }
    @Override
    public Drawable getDrawable() {
        return sprite;
    }
}
