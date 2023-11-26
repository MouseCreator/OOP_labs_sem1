package org.example.model;

import java.awt.*;
import java.awt.image.BufferedImage;

public class AnimatedSprite {
    private BufferedImage image;
    private int spriteCount;
    private int currentFrame;
    private Vector2I currentPosition = Vector2I.zero();
    private Vector2I tileSize = Vector2I.zero();
    private int step;
    private AnimatedSprite() {
    }

    public static AnimatedSprite get(BufferedImage bufferedImage, int spriteCount) {
        AnimatedSprite animatedSprite = new AnimatedSprite();
        animatedSprite.image = bufferedImage;
        animatedSprite.spriteCount = spriteCount;
        animatedSprite.step = bufferedImage.getHeight() / spriteCount;
        animatedSprite.tileSize = new Vector2I(bufferedImage.getWidth(), animatedSprite.step);
        animatedSprite.setFrame(0);
        return animatedSprite;
    }

    private void imageFromFrame() {
        currentPosition = new Vector2I(0, step * currentFrame);
    }

    public void setFrame(int frame) {
        this.currentFrame = Math.min(frame, spriteCount - 1);
        imageFromFrame();
    }
    public void draw(Graphics2D g2d, Vector2I position) {
        g2d.drawImage(image, position.x(), position.y(), position.x() + tileSize.x(),  position.y() + tileSize.y(),
                currentPosition.x(), currentPosition.y(), currentPosition.x() + tileSize.x(),
                currentPosition.y() + tileSize.y(), null);
    }
}
