package org.example.game.drawable;

import org.example.vector.Vector2I;

import java.awt.*;

public class TextGraphic implements Drawable{
    private final Color color;
    private String text;
    private final Font font;
    private boolean centralized = false;
    public TextGraphic(Color color, int fontSize) {
        font = new Font("Arial", Font.PLAIN, fontSize);
        this.color = color;
        text = "";
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void draw(Graphics2D g2d, Vector2I position, Vector2I size) {
        g2d.setColor(color);
        g2d.setFont(font);
        Vector2I drawPosition = position;
        if (centralized) {
            FontMetrics fm = g2d.getFontMetrics();
            Vector2I offset = Vector2I.get(fm.stringWidth(text)/2, fm.getHeight()/2 - fm.getAscent());
            drawPosition = position.subtract(offset);
        }
        g2d.drawString(text, drawPosition.x(), drawPosition.y());
    }

    public void setCentralized(boolean b) {
        centralized = b;
    }
}
