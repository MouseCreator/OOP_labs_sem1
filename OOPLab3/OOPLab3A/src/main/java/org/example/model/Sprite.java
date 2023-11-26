package org.example.model;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Sprite implements DrawableSprite {
    private Vector2I size;
    private BufferedImage image;
    public void draw(Graphics2D g2d, Vector2I position) {
        g2d.drawImage(image, position.x(), position.y(), size.x(), size.y(), null);
    }

    private Sprite() {
    }

    public Sprite get(BufferedImage image) {
        Sprite sprite = new Sprite();
        sprite.image = image;
        sprite.size = new Vector2I(image.getWidth(), image.getHeight());
        return sprite;
    }


}
