package org.example.game;

import org.example.game.drawable.*;
import org.example.game.modes.CalibrationGameMode;
import org.example.game.modes.GameMode;
import org.example.model.*;
import java.awt.*;

public class Game {
    private Background background;
    private SpriteBuffer spriteBuffer;
    private GameMode gameMode;

    public void init() {
        createSpriteBuffer();
        background = Background.getBG();
        initSprites();
        initGameMode();
    }


    private void initGameMode() {
        gameMode = new CalibrationGameMode();
    }
    private void initSprites() {
        background.initSprite(SpriteImpl.get(spriteBuffer.getBackground()));
    }
    private void createSpriteBuffer() {
        spriteBuffer = new SpriteBuffer();
        spriteBuffer.init();
    }


    public void draw(Graphics2D g2d) {

    }

    public void update() {

    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }
}
