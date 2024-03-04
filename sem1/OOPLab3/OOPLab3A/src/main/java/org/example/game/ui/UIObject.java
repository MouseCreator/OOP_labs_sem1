package org.example.game.ui;

import org.example.game.drawable.Drawable;
import org.example.game.model.StaticUIObject;

public interface UIObject {
    StaticUIObject root();
    Drawable getGraphic();
    int zOrder();
    String tag();
    void hide();
    void show();
    boolean isVisible();
}
