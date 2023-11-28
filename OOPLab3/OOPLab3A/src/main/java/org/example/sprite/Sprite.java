package org.example.sprite;

import org.example.model.DrawableSprite;
import org.example.vector.Vector2I;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Sprite implements DrawableSprite {
    private Vector2I size;
    private BufferedImage image;
    private boolean visible = true;
    public void draw(Graphics2D g2d, Vector2I position) {
        if (visible) {
            g2d.drawImage(image, position.x(), position.y(), size.x(), size.y(), null);
        }
    }

    private Sprite() {
    }

    public static Sprite get(BufferedImage image) {
        Sprite sprite = new Sprite();
        sprite.image = image;
        sprite.size = new Vector2I(image.getWidth(), image.getHeight());
        return sprite;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
