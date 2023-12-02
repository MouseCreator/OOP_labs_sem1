package org.example.game.drawable;

import org.example.vector.Vector2I;

import java.awt.*;

public interface Drawable {
    void draw(Graphics2D g2d, Vector2I position, Vector2I size);
}
