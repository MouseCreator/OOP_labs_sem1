package org.example.game.ui;

import org.example.game.drawable.Drawable;
import org.example.game.drawable.TextGraphic;
import org.example.game.model.StaticUIObject;
import org.example.vector.Vector2I;

import java.awt.*;

public class ScoreCounter implements UIObject {
    private final StaticUIObject root;
    private final TextGraphic textGraphic;
    public ScoreCounter() {
        root = StaticUIObject.on(Vector2I.get(10, 100), Vector2I.zero());
        textGraphic = new TextGraphic(Color.white, 32);
        update(0);
    }

    public void update(int count) {
        textGraphic.setText("Score: " + count);
    }

    @Override
    public StaticUIObject root() {
        return root;
    }

    @Override
    public Drawable getGraphic() {
        return textGraphic;
    }

    @Override
    public int zOrder() {
        return 1;
    }

    @Override
    public String tag() {
        return "SCORE";
    }
}
