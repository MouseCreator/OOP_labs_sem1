package org.example.model;

import org.example.vector.Vector2I;

import java.awt.*;

public interface DrawableSprite {
    void draw(Graphics2D g2d, Vector2I position);
}
