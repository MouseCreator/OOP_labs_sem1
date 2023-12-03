package org.example.game.ui;

import org.example.game.drawable.AnimatedSprite;
import org.example.game.drawable.AnimatedSpriteImpl;
import org.example.game.drawable.Drawable;
import org.example.game.model.StaticUIObject;
import org.example.vector.Vector2I;

import java.awt.image.BufferedImage;

public class HealthBar implements UIObject {
    private final StaticUIObject root;
    public static final Vector2I size = Vector2I.get(192, 64);
    private final AnimatedSprite animatedSprite;
    public HealthBar(BufferedImage hearts) {
        root = StaticUIObject.on(Vector2I.get(10, 10), size);
        animatedSprite = AnimatedSpriteImpl.get(hearts, 4);
    }
    @Override
    public StaticUIObject root() {
        return root;
    }

    @Override
    public Drawable getGraphic() {
        return animatedSprite;
    }

    @Override
    public int zOrder() {
        return 0;
    }

    @Override
    public String tag() {
        return "HEALTH";
    }

    public void updateView(int healthPoints) {
        if (healthPoints < 1) {
            animatedSprite.getAnimation().setFrame(4);
        }
        if (healthPoints > 3) {
            animatedSprite.getAnimation().setFrame(0);
        }
        animatedSprite.getAnimation().setFrame(3 - healthPoints);
    }
}
