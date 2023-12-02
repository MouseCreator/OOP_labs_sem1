package org.example.game.draw;

import org.example.game.drawable.DrawManager;
import org.example.game.helper.ModelDraw;
import org.example.model.Background;

import java.awt.*;

public class GameDraw implements DrawManager {
    private Background background;
    private ModelDraw modelDraw;
    private UIDraw uiDraw;
    @Override
    public void draw(Graphics2D g2d) {
        background.draw(g2d);
        modelDraw.draw(g2d);
        uiDraw.draw(g2d);
    }

    public void setBackground(Background background) {
        this.background = background;
    }

    public void setModelDraw(ModelDraw modelDraw) {
        this.modelDraw = modelDraw;
    }

    public void setUiDraw(UIDraw uiDraw) {
        this.uiDraw = uiDraw;
    }
}
