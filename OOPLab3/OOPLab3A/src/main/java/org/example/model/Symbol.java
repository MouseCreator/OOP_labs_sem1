package org.example.model;

import org.example.sprite.AnimatedSprite;
import org.example.vector.Vector2I;

import java.awt.*;
import java.util.Random;

public class Symbol extends Entity implements DrawUpdatable {
    private String tag;
    private final Random random = new Random();
    private static final Vector2I symbolSize = Vector2I.get(256, 256);
    private AnimatedSprite animatedSprite;
    public Symbol(Vector2I position, Vector2I symbolSize) {
        super(position, symbolSize);
    }

    public void changeToRandom() {
        int frame = random.nextInt(3);
        animatedSprite.setFrame(frame);
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
    public static Symbol createSymbol(Vector2I position, AnimatedSprite sprite) {
        Symbol symbol = new Symbol(position, symbolSize);
        symbol.animatedSprite = sprite;
        symbol.position = DimTranslator.get().toCenter(position, symbolSize);
        symbol.hide();
        return symbol;
    }

    public String getTag() {
        return tag;
    }

    @Override
    public void draw(Graphics2D g2d) {
        animatedSprite.draw(g2d, position);
    }

    @Override
    public void update() {

    }
}
