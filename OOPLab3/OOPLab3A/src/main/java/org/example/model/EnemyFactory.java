package org.example.model;

import org.example.engine.ConstUtils;
import org.example.game.drawable.SpriteImpl;
import org.example.vector.Vector2I;
import org.example.vector.Vector3D;

import java.awt.image.BufferedImage;
import java.util.Random;

public class EnemyFactory {
    private final BufferedImage enemySprite;
    private final Random random;
    private double minX;
    private double maxX;
    private double minZ;
    private double maxZ;
    public EnemyFactory(BufferedImage enemySprite) {
        this.enemySprite = enemySprite;
        this.random = new Random();
        init();
    }

    private void init() {
        maxX = ConstUtils.worldWidth - 90;
        minX = 90;
        minZ = 100;
        maxZ = ConstUtils.depth - 30;
    }

    public Enemy createEnemy() {
        Vector2I enemySize = Enemy.enemySize;
        double x = random.nextDouble(minX, maxX);
        double y = enemySize.y() / 2.0;
        double z = random.nextDouble(minZ, maxZ);
        return Enemy.withOrigin(Vector3D.get(x,y,z), SpriteImpl.get(enemySprite));
    }
}
