package org.example.engine;

import org.example.vector.Vector2I;

public class ConstUtils {
    public static final int worldWidth = 1280;
    public static final int worldHeight = 720;
    public static final int depth = 356;
    public static final double X_MULTIPLIER = 3.7;
    public static final double Y_MULTIPLIER = 0.8;
    public static final double Z_MULTIPLIER = 0.8;
    public static final double DISTANCE_BAND = 5;
    public static final int FPS = 60;
    public static double PLAYER_Z = -30;

    public static Vector2I center = Vector2I.get(worldWidth/2, worldHeight/2);
}
