package org.example.game.drawable;

import org.example.vector.Vector2I;

import java.awt.*;
import java.awt.image.BufferedImage;

public class AnimatedSpriteImpl implements AnimatedSprite {
    private boolean visible = true;
    private Animation animation;
    public boolean isVisible() {
        return visible;
    }
    @Override
    public void setImage(BufferedImage image) {
        animation.setImage(image);
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    private AnimatedSpriteImpl() {
    }
    public static AnimatedSpriteImpl get(BufferedImage bufferedImage, int spriteCount) {
        AnimatedSpriteImpl animatedSprite = new AnimatedSpriteImpl();
        animatedSprite.animation = new AnimationImpl(bufferedImage, spriteCount);
        return animatedSprite;
    }

    @Override
    public void draw(Graphics2D g2d, Vector2I position, Vector2I size) {
        if (visible) {
            animation.draw(g2d, position, size);
        }
        animation.nextTick();
    }

    @Override
    public Animation getAnimation() {
        return animation;
    }
}
