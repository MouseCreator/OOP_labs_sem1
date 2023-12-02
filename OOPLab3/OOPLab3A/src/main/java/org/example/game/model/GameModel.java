package org.example.game.model;

import org.example.game.drawable.Drawable;
import org.example.game.entity.Entity;
import org.example.model.Updatable;

public interface GameModel extends Updatable {
    Entity getEntity();
    Drawable getDrawable();
}
