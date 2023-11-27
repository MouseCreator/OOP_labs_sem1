package org.example.model;

import org.example.engine.ConstUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ShurikenManager implements DrawUpdatable {
    private final List<Shuriken> shurikenList = new ArrayList<>();

    public static ShurikenManager create() {
        return new ShurikenManager();
    }

    public void each(Consumer<Shuriken> consumer) {
        shurikenList.forEach(consumer);
    }

    @Override
    public void draw(Graphics2D g2d) {
        each(s -> s.draw(g2d));
    }

    @Override
    public void update() {
        each(Shuriken::update);
        removeDestroyed();
    }

    private void removeDestroyed() {
        shurikenList.removeIf(Shuriken::isDestroyed);
    }

    public void spawn(SpriteBuffer spriteBuffer, MovementParams movementParams) {
        ScalableSprite sprite = ScalableSprite.get(spriteBuffer.getShuriken());
        Vector2I origin = Vector2I.get(ConstUtils.worldWidth/2, ConstUtils.worldHeight);
        Shuriken shuriken = Shuriken.withOrigin(origin);
        shuriken.initFromMovement(movementParams);
        shuriken.initSprite(sprite);
        shurikenList.add(shuriken);
    }

    public void spawn(SpriteBuffer spriteBuffer, MovementParams movementParams, MoveableBody moveableBody) {
        ScalableSprite sprite = ScalableSprite.get(spriteBuffer.getShuriken());
        Vector2I origin = Vector2I.get(ConstUtils.worldWidth/2, ConstUtils.worldHeight);
        Shuriken shuriken = Shuriken.withOrigin(origin);
        shuriken.initFromMovement(movementParams);
        shuriken.initSprite(sprite);
        shuriken.setMoveableBody(moveableBody);
        shurikenList.add(shuriken);
    }
}
