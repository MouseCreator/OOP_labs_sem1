package org.example.model;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class SpriteBuffer {
    private BufferedImage background = null;
    private BufferedImage drams = null;
    private BufferedImage bolt = null;
    private BufferedImage shuriken = null;
    public void init() {
        try {
            background = loadImage("gfx/SimpleBackground2.png");
            drams = loadImage("gfx/Drams.png");
            bolt = loadImage("gfx/SimpleBolt.png");
            shuriken = loadImage("gfx/ShurikenSmall.png");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static BufferedImage loadImage(String path) throws IOException {
        InputStream inputStream = SpriteBuffer.class.getClassLoader().getResourceAsStream(path);
        if (inputStream != null) {
            return ImageIO.read(inputStream);
        } else {
            throw new IOException("Could not find resource: " + path);
        }
    }

    public BufferedImage getBackground() {
        return background;
    }
    public BufferedImage getShuriken() {
        return shuriken;
    }

    public BufferedImage getDrams() {
        return drams;
    }

    public BufferedImage getBolt() {
        return bolt;
    }
}
