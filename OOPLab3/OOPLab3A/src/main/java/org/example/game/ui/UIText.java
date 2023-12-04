package org.example.game.ui;

import org.example.game.drawable.Drawable;
import org.example.game.drawable.TextGraphic;
import org.example.game.model.StaticUIObject;
import org.example.vector.Vector2I;

import java.awt.*;

public class UIText implements UIObject{
    private int zOrder;
    private final String tag;
    private final TextGraphic textGraphic;
    private final StaticUIObject staticUIObject;
    public UIText(String tag, String content, Vector2I position, boolean centralized) {
        this.tag = tag;
        textGraphic = new TextGraphic(Color.white, 64);
        textGraphic.setCentralized(centralized);
        setContent(content);
        setZOrder(2);
        staticUIObject = StaticUIObject.on(position, Vector2I.zero());
    }

    @Override
    public StaticUIObject root() {
        return staticUIObject;
    }

    @Override
    public Drawable getGraphic() {
        return textGraphic;
    }

    @Override
    public int zOrder() {
        return zOrder;
    }

    @Override
    public String tag() {
        return tag;
    }
    private boolean visible = true;
    @Override
    public void hide() {
        visible = false;
    }

    @Override
    public void show() {
        visible = false;
    }

    @Override
    public boolean isVisible() {
        return visible;
    }

    public void setContent(String content) {
        textGraphic.setText(content);
    }

    public void setZOrder(int val) {
        this.zOrder = val;
    }
}
