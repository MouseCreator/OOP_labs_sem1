package org.example.model;


import org.example.engine.ConstUtils;
import org.example.game.drawable.SpriteBuffer;
import org.example.game.event.CreationEvent;
import org.example.game.event.DeletionEvent;
import org.example.game.event.PlayerEvent;
import org.example.game.helper.GameUtils;
import java.util.ArrayList;
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
        Enemy enemy = enemyFactory.createEnemy();
        GameUtils.newEvent(new CreationEvent(enemy));
        enemiesList.add(enemy);
    }
    public void update() {
        trySpawn();
        handlePlayerCollision();
        removeDestroyed();
    }

    private void handlePlayerCollision() {
        each(enemy->{
            if (enemy.getEntity().getPosition().z() < ConstUtils.PLAYER_Z) {
                enemy.toDestroy();
                GameUtils.newEvent(new PlayerEvent(PlayerEvent.Type.DAMAGE));
            }
        });
    }

    private void removeDestroyed() {
        int sizeBefore = enemiesList.size();
        enemiesList.stream().filter(Enemy::isDestroyed).forEach(enemy -> GameUtils.newEvent(new DeletionEvent(enemy)));
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
