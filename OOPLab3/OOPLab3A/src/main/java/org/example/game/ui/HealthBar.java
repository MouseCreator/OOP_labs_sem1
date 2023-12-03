package org.example.game.ui;

import org.example.game.drawable.Drawable;
import org.example.game.model.StaticUIObject;
import org.example.vector.Vector2I;

public class HealthBar implements UIObject {
    private final StaticUIObject root;
    public static final Vector2I size = Vector2I.zero();
    public HealthBar() {
        root = new StaticUIObject(Vector2I.get(10, 10), size);
    }
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
        return 0;
    }
}
