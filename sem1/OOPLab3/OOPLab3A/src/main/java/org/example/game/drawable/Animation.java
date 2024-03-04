package org.example.game.drawable;

import java.awt.image.BufferedImage;

public interface Animation extends Drawable {
    int getFrame();
    void setFrame(int frame);
    void setSpeed(int speed);
    void nextTick();
    void setImage(BufferedImage image);
}
