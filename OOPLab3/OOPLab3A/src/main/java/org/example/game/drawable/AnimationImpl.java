package org.example.game.drawable;

import org.example.engine.ConstUtils;
import org.example.vector.Vector2I;

import java.awt.*;
import java.awt.image.BufferedImage;

public class AnimationImpl implements Animation {
    private final Vector2I tileSize;
    private final int spriteCount;
    private BufferedImage image;
    private int currentFrame;
    private final int step;
    private int speed = 0;
    private int updateFrequency;
    private int currentTime;
    private Vector2I currentPosition;
    @Override
    public int getFrame() {
        return currentFrame;
    }

    public AnimationImpl(BufferedImage image, int spriteCount) {
        this.image = image;
        this.spriteCount = spriteCount;
        this.step = spriteCount == 0 ? 0 : image.getHeight() / spriteCount;
        this.tileSize = new Vector2I(image.getWidth(), step);
        setFrame(0);
        currentTime = 0;
    }
    private void imageFromFrame() {
        currentPosition = new Vector2I(0, step * currentFrame);
    }

    @Override
    public void setFrame(int frame) {
        currentFrame = frame;
        currentTime = 0;
        imageFromFrame();
    }

    @Override
    public void setSpeed(int speed) {
        this.speed = speed;
        if (speed != 0) {
            updateFrequency = ConstUtils.FPS / speed;
        } else {
            currentTime = 0;
            updateFrequency = 0;
        }
    }


    @Override
    public void nextTick() {
        if (speed == 0) {
            return;
        }
        currentTime++;
        if (currentTime == updateFrequency) {
            currentTime = 0;
            setFrame((currentFrame + 1) % spriteCount );
        }

    }

    @Override
    public void setImage(BufferedImage image) {
        this.image = image;
    }

    @Override
    public void draw(Graphics2D g2d, Vector2I position, Vector2I size) {
        g2d.drawImage(image, position.x(), position.y(), position.x() + tileSize.x(), position.y() + tileSize.y(),
                currentPosition.x(), currentPosition.y(), currentPosition.x() + tileSize.x(),
                currentPosition.y() + tileSize.y(), null);
    }
}
