package org.example.game.drawable;

import java.awt.image.BufferedImage;

public interface Sprite extends Drawable{
    void setImage(BufferedImage image);
    void setVisible(boolean visible);
    boolean isVisible();
}
