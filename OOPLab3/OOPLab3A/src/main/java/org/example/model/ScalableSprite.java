package org.example.model;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ScalableSprite implements DrawableSprite {
    private double scale = 1.0;
    public double getScale() {
        return scale;
    }
    public void setScale(double scale) {
        this.scale = scale;
    }
    private Vector2I size;
    private BufferedImage image;
    private boolean visible;
    public void draw(Graphics2D g2d, Vector2I position) {
        if (visible) {
            g2d.drawImage(image, position.x(), position.y(), (int) (size.x() * scale), (int) (size.y() * scale), null);
        }
    }

    public static ScalableSprite get(BufferedImage image) {
        ScalableSprite sprite = new ScalableSprite();
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
