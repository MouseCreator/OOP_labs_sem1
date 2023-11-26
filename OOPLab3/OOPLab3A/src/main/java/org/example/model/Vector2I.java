package org.example.model;

public class Vector2I {
    private final int x;
    private final int y;

    public static Vector2I zero() {
        return new Vector2I(0,0);
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    public Vector2I(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
