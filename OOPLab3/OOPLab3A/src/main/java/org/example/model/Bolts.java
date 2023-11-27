package org.example.model;

import org.example.engine.ConstUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Bolts implements DrawUpdatable {
    List<Bolt> boltList = new ArrayList<>();

    public static Bolts create() {
        int width = 3;
        int height = 2;
        int gWidth = ConstUtils.worldWidth / width;
        int gHeight = ConstUtils.worldHeight / height;
        Bolts bolts = new Bolts();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int x = gWidth / 2 + gWidth * i;
                int y = gHeight / 2 + gHeight * j;
                bolts.boltList.add(Bolt.withOrigin(Vector2I.get(x,y)));
            }
        }
        return bolts;
    }

    public void each(Consumer<Bolt> consumer) {
        boltList.forEach(consumer);
    }

    public void draw(Graphics2D g2d) {
        each(b -> b.draw(g2d));
    }
    public void update() {
        each(Bolt::update);
    }
}
