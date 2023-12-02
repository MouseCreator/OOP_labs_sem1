package org.example.model;

import org.example.game.drawable.AnimatedSprite;
import org.example.game.drawable.Drawable;
import org.example.game.entity.Entity;
import org.example.game.entity.EntityImpl;
import org.example.game.model.GameStaticObject;
import org.example.vector.Vector2I;
import org.example.vector.Vector3D;

import java.util.Random;

public class Symbol implements GameStaticObject, Updatable {
    private String tag;
    private final Random random = new Random();
    private static final Vector2I symbolSize = Vector2I.get(256, 256);
    private AnimatedSprite animatedSprite;
    private final Entity entity;
    public Symbol(Vector3D position, Vector2I symbolSize) {
        entity = new EntityImpl(position, symbolSize);
    }

    @Override
    public Drawable getDrawable() {
        return animatedSprite;
    }

    public void changeToRandom() {
        int frame = random.nextInt(3);
        animatedSprite.getAnimation().setFrame(frame);
        tag = switch (frame) {
            case 0 -> "CIRCLE";
            case 1 -> "CROSS";
            case 2 -> "M_LETTER";
            default -> throw new IllegalStateException("Frame out of bounds " + frame);
        };
    }

    public void show() {
        this.animatedSprite.setVisible(true);
    }
    public void hide() {
        this.animatedSprite.setVisible(false);
    }
    public static Symbol createSymbol(Vector3D position, AnimatedSprite sprite) {
        Symbol symbol = new Symbol(position, symbolSize);
        symbol.animatedSprite = sprite;
        symbol.hide();
        return symbol;
    }

    public String getTag() {
        return tag;
    }

    @Override
    public void update() {

    }
    public boolean isHidden() {
        return !animatedSprite.isVisible();
    }

    public void setStatus(boolean isRecognized) {
        if (isRecognized) {
            animatedSprite.getAnimation().setFrame(3);
        } else {
            animatedSprite.getAnimation().setFrame(4);
        }
    }
}
