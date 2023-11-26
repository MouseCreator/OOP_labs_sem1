package org.example.model;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Sprite {
    private Vector2I size;
    private BufferedImage image;
    public void draw(Graphics2D g2d, Vector2I position) {
        g2d.drawImage(image, position.x(), position.y(), size.x(), size.y(), null);
    }


}
