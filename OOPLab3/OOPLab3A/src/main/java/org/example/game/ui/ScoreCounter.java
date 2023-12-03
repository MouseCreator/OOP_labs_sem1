package org.example.game.ui;

import org.example.game.drawable.Drawable;
import org.example.game.model.StaticUIObject;

public class ScoreCounter implements UIObject {
    private StaticUIObject root;
    @Override
    public StaticUIObject root() {
        return root;
    }

    @Override
    public Drawable getGraphic() {
        return null;
    }

    @Override
    public int zOrder() {
        return 1;
    }
}
