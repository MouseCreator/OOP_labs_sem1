package org.example.model;


import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

public class Enemies implements DrawUpdatable {
    private final List<Enemy> enemiesList = new ArrayList<>();
    private final EnemyFactory enemyFactory;
    private final int spawnFrequency = 1000;
    private long lastSpawn;
    public Enemies(SpriteBuffer spriteBuffer) {
        this.enemyFactory = new EnemyFactory(spriteBuffer.getDummy());
        lastSpawn = System.currentTimeMillis();
    }
    public static Enemies create(SpriteBuffer spriteBuffer) {
        return new Enemies(spriteBuffer);
    }
    public void each(Consumer<Enemy> consumer) {
        enemiesList.forEach(consumer);
    }

    public void add(Enemy enemy) {
        enemiesList.add(enemy);
    }
    public void draw(Graphics2D g2d) {
        enemiesList.stream().sorted(Comparator.comparingInt(e -> (int) -e.position3d().z())).forEach(b -> b.draw(g2d));
    }
    public void spawnNew() {
        enemiesList.add(enemyFactory.createEnemy());
    }
    public void update() {
        trySpawn();
        each(Enemy::update);
    }

    private void trySpawn() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastSpawn > spawnFrequency) {
            spawnNew();
            lastSpawn = currentTime;
        }
    }
}
