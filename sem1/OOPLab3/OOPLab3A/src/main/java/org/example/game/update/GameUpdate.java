package org.example.game.update;

import org.example.game.helper.ModelUpdate;
import org.example.model.Updatable;

public class GameUpdate implements Updatable {
    private ModelUpdate modelUpdate;
    @Override
    public void update() {
        modelUpdate.update();
    }

    public void setModelUpdate(ModelUpdate modelUpdate) {
        this.modelUpdate = modelUpdate;
    }
}
