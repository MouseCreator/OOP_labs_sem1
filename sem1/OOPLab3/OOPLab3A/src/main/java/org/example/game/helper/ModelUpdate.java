package org.example.game.helper;

import org.example.game.model.GameModel;
import org.example.model.Updatable;
import java.util.ArrayList;
import java.util.List;

public class ModelUpdate implements GameModelSubscriber, GameService<Void, Void> {
    private final List<GameModel> models = new ArrayList<>();
    @Override
    public void onAdd(GameModel entity) {
        models.add(entity);
    }
    @Override
    public void onRemove(GameModel entity) {
        models.remove(entity);
    }
    public void update() {
        models.forEach(Updatable::update);
    }
    @Override
    public Void callService(Void value) {
        update();
        return null;
    }
}
