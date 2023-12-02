package org.example.game.helper;

import org.example.game.model.GameModel;
import org.example.model.DimTranslator;
import org.example.vector.Vector2I;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GameDraw implements GameModelSubscriber, GameService<Void, Graphics2D> {
    private final List<GameModel> models = new ArrayList<>();
    private final Comparator<GameModel> zOrderComparator = (a, b) -> {
        double az = a.getEntity().getPosition().z();
        double bz = b.getEntity().getPosition().z();
        return (int) Math.signum(az - bz);
    };

    @Override
    public void onAdd(GameModel entity) {
        models.add(entity);
    }
    @Override
    public void onRemove(GameModel entity) {
        models.remove(entity);
    }
    public void draw(Graphics2D g2d) {
        models.stream().sorted(zOrderComparator).forEach(e -> drawModel(e, g2d));
    }
    private void drawModel(GameModel model, Graphics2D g2d) {
        Vector2I position = DimTranslator.get().translate(model.getEntity().getSize(), model.getEntity().getPosition());
        model.getDrawable().draw(g2d, position, model.getEntity().getSize().getCurrentSize());
    }

    @Override
    public Void callService(Graphics2D value) {
        draw(value);
        return null;
    }
}
