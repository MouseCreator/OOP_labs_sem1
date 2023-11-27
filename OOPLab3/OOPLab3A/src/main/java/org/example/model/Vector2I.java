package org.example.model;

public class Vector2I {
    private final int x;
    private final int y;

    public Vector2I(Vector2I pos) {
        this.x = pos.x;
        this.y = pos.y;
    }

    public static Vector2I zero() {
        return new Vector2I(0,0);
    }

    public static Vector2I get(int x, int y) {
        return new Vector2I(x, y);
    }

    public static Vector2I from(Vector2I other) {
        return new Vector2I(other.x, other.y);
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

    public Vector2I multiply(double v) {
        return new Vector2I((int) (x * v), (int) (y * v));
    }

    public Vector2I add(Vector2I other) {
        return new Vector2I(x+other.x, y + other.y);
    }

    public Vector2I subtract(Vector2I other) {
        return new Vector2I(x - other.x, y - other.y);
    }

    @Override
    public String toString() {
        return "{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
