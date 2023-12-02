package org.example.game.entity;

import org.example.game.helper.ModelDraw;
import org.example.game.helper.ModelUpdate;

public class ModelManager {
    private ModelDraw modelDraw;
    private ModelUpdate modelUpdate;
    public ModelDraw getGameDraw() {
        return modelDraw;
    }

    public void setGameDraw(ModelDraw modelDraw) {
        this.modelDraw = modelDraw;
    }

    public ModelUpdate getGameUpdate() {
        return modelUpdate;
    }

    public void setGameUpdate(ModelUpdate modelUpdate) {
        this.modelUpdate = modelUpdate;
    }
}
