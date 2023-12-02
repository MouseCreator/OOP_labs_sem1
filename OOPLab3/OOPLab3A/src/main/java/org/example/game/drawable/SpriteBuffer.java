package org.example.game.drawable;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class SpriteBuffer {
    private BufferedImage background = null;
    private BufferedImage shuriken = null;
    private BufferedImage dummy = null;
    private BufferedImage ninja = null;
    private BufferedImage symbols = null;
    public void init() {
        try {
            background = loadImage("gfx/GameBackground.png");
            shuriken = loadImage("gfx/ShurikenSmall.png");
            dummy = loadImage("gfx/Dummy.png");
            ninja = loadImage("gfx/Ninja.png");
            symbols = loadImage("gfx/Symbols.png");
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
    public BufferedImage getDummy() {
        return dummy;
    }
    public BufferedImage getNinja() {
        return ninja;
    }
    public BufferedImage getSymbols() {
        return symbols;
    }
}
