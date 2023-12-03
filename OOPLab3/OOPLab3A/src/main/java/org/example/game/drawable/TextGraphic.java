package org.example.game.drawable;

import org.example.vector.Vector2I;

import java.awt.*;

public class TextGraphic implements Drawable{
    private final Color color;
    private String text;
    private final Font font;
    public TextGraphic(Color color, int fontSize) {
        font = new Font("Arial", Font.PLAIN, fontSize);
        this.color = color;
        text = "";
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void draw(Graphics2D g2d, Vector2I position, Vector2I size) {
        g2d.setColor(color);
        g2d.setFont(font);
        g2d.drawString(text, position.x(), position.y());
    }
}
