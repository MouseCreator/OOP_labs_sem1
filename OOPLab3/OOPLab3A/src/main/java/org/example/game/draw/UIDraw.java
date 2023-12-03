package org.example.game.draw;

import org.example.game.drawable.DrawManager;
import org.example.game.drawable.Drawable;
import org.example.game.ui.UIObject;
import org.example.game.ui.UserInterface;

import java.awt.*;
import java.util.Comparator;

public class UIDraw implements DrawManager {
    private final UserInterface userInterface;

    public UIDraw(UserInterface userInterface) {
        this.userInterface = userInterface;
    }

    @Override
    public void draw(Graphics2D g2d) {
        userInterface.get().stream().sorted(Comparator.comparingInt(UIObject::zOrder)).forEach(e -> draw(g2d, e));
    }
    private void draw(Graphics2D g2d, UIObject e) {
        Drawable graphic = e.getGraphic();
        graphic.draw(g2d, e.root().getPosition(), e.root().getSize());
    }

}
