package org.example.game.entity;

import org.example.game.helper.ModelDraw;
import org.example.game.helper.ModelUpdate;

public class ModelManager {
    private ModelDraw modelDraw;
    private ModelUpdate modelUpdate;
    public ModelDraw getModelDraw() {
        return modelDraw;
    }

    public void setModelDraw(ModelDraw modelDraw) {
        this.modelDraw = modelDraw;
    }

    public ModelUpdate getModelUpdate() {
        return modelUpdate;
    }

    public void setModelUpdate(ModelUpdate modelUpdate) {
        this.modelUpdate = modelUpdate;
    }
}
