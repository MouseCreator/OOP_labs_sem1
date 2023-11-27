package org.example.model;


import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

public class Enemies implements DrawUpdatable {
    private final List<Enemy> enemiesList = new ArrayList<>();
    public static Enemies create() {
        return new Enemies();
    }
    public void each(Consumer<Enemy> consumer) {
        enemiesList.forEach(consumer);
    }

    public void add(Enemy enemy) {
        enemiesList.add(enemy);
    }
    public void draw(Graphics2D g2d) {
        enemiesList.stream().sorted(Comparator.comparingInt(e -> (int) e.position3d().z())).forEach(b -> b.draw(g2d));
    }
    public void update() {
        each(Enemy::update);
    }
}
