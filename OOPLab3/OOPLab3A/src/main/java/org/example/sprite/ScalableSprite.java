package org.example.sprite;

import org.example.model.DrawableSprite;
import org.example.vector.Vector2I;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ScalableSprite implements DrawableSprite {
    private double scale = 1.0;
    public double getScale() {
        return scale;
    }
    public void setScale(double scale) {
        this.scale = scale;
        currentSize = size.multiply(scale);
    }
    private Vector2I size;
    private Vector2I currentSize;
    private BufferedImage image;
    private boolean visible = true;
    public void draw(Graphics2D g2d, Vector2I position) {
        if (visible) {
            g2d.drawImage(image, position.x(), position.y(), currentSize.x(), currentSize.y(), null);
        }
    }

    public static ScalableSprite get(BufferedImage image) {
        ScalableSprite sprite = new ScalableSprite();
        sprite.image = image;
        sprite.size = new Vector2I(image.getWidth(), image.getHeight());
        sprite.setScale(1.0);
        return sprite;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public Vector2I getCurrentSize() {
        return currentSize;
    }
}
