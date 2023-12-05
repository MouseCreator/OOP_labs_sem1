package org.example.model;

import org.example.engine.ConstUtils;
import org.example.game.drawable.SpriteImpl;
import org.example.game.helper.GameUtils;
import org.example.game.movement.LinearMovement;
import org.example.game.movement.Movement;
import org.example.game.movement.SinusMovement;
import org.example.vector.Vector3D;

import java.awt.image.BufferedImage;
import java.util.Random;

public class EnemyFactory {
    private final BufferedImage dummySprite;
    private final BufferedImage batSprite;
    private final Random random;
    private double minX;
    private double maxX;

    public EnemyFactory() {
        this.dummySprite = GameUtils.get().getSpriteBuffer().getDummy();
        this.batSprite = GameUtils.get().getSpriteBuffer().getBat();
        this.random = new Random();
        init();
    }

    private void init() {
        maxX = ConstUtils.worldWidth + 256;
        minX = -256;
    }

    private static final double dummyChance = 0.5;
    public Enemy randomEnemy() {
        double r = random.nextDouble();
        return (r < dummyChance) ?
             createDummy() : createBat();
    }
    public DummyEnemy createDummy() {
        double x = random.nextDouble(minX, maxX);
        double y = 0;
        double z = ConstUtils.depth;
        DummyEnemy enemy = DummyEnemy.withOrigin(Vector3D.get(x,y,z), SpriteImpl.get(dummySprite));
        enemy.setMovement(createDummyMovement(enemy.getEntity().getPosition()));
        return enemy;
    }

    public BatEnemy createBat() {
        double x = random.nextDouble(minX, maxX);
        double y = 300;
        double z = ConstUtils.depth;
        BatEnemy enemy = BatEnemy.withOrigin(Vector3D.get(x,y,z), SpriteImpl.get(batSprite));
        enemy.setMovement(createBatMovement(enemy.getEntity().getPosition()));
        return enemy;
    }

    private Movement createDummyMovement(Vector3D position) {
        double speed = 4;
        double destX = ConstUtils.worldWidth / 2.0 + random.nextDouble(-100, 100);
        Vector3D destination = Vector3D.get(destX, 0,ConstUtils.PLAYER_Z);
        Vector3D direction = destination.subtract(position);
        return new LinearMovement(direction.normalize().multiply(speed));
    }

    private Movement createBatMovement(Vector3D position) {
        double xSpeed = 3;
        double phase = Math.asin((maxX - position.z()) / (maxX - minX));
        double ampl = (maxX - minX) / 5;
        double freq = 1;
        double step = 0.1;
        return new SinusMovement(ampl, freq, step, phase, xSpeed, position.z());
    }
}
