package org.example.game.drawable;

import org.example.vector.Vector2I;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SpriteImpl implements Sprite {
    private BufferedImage image;
    private boolean visible = true;
    @Override
    public void draw(Graphics2D g2d, Vector2I position, Vector2I size) {
        if (visible) {
            g2d.drawImage(image, position.x(), position.y(), size.x(), size.y(), null);
        }
    }

    private SpriteImpl() {
    }

    public static SpriteImpl get(BufferedImage image) {
        SpriteImpl sprite = new SpriteImpl();
        sprite.image = image;
        return sprite;
    }
    public boolean isVisible() {
        return visible;
    }
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public void setImage(BufferedImage image) {

    }
}
