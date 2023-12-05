package org.example.utils;

import org.example.vector.Vector2I;

public class ConstUtils {
    public static final int worldWidth = 1280;
    public static final int worldHeight = 720;
    public static final int worldDepth = 356;
    public static final double X_MULTIPLIER = 4;
    public static final double Y_MULTIPLIER = 2.5;
    public static final double Z_MULTIPLIER = 0.7;
    public static final double DISTANCE_BAND = 5;
    public static final int FPS = 60;
    public static final int ENEMY_LIMIT = 10;
    public static double PLAYER_Z = -30;
    public static Vector2I center = Vector2I.get(worldWidth/2, worldHeight/2);
}
