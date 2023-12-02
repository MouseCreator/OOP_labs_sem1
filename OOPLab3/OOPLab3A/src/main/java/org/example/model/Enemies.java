package org.example.model;


import org.example.game.drawable.SpriteBuffer;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

public class Enemies implements Updatable {
    private final List<Enemy> enemiesList = new ArrayList<>();
    private final EnemyFactory enemyFactory;
    private final int spawnFrequency = 2000;
    private int spawned = 0;
    private int destroyed = 0;
    private final int limit = 15;
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
    public void spawnNew() {
        enemiesList.add(enemyFactory.createEnemy());
    }
    public void update() {
        trySpawn();
        each(Enemy::update);
        removeDestroyed();
    }

    private void removeDestroyed() {
        int sizeBefore = enemiesList.size();
        enemiesList.removeIf(Enemy::isDestroyed);
        int sizeAfter = enemiesList.size();
        destroyed += (sizeBefore - sizeAfter);
    }
    public boolean destroyedAll() {
        return destroyed == limit;
    }
    private void trySpawn() {
        long currentTime = System.currentTimeMillis();
        if (spawned < limit && currentTime - lastSpawn > spawnFrequency) {
            spawnNew();
            spawned++;
            lastSpawn = currentTime;
        }
    }

    public void reset() {
        lastSpawn = System.currentTimeMillis();
        spawned = 0;
        destroyed = 0;
        enemiesList.clear();
    }
}
