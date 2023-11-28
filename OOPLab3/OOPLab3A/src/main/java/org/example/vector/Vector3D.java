package org.example.vector;

public class Vector3D {
    private final double x;
    private final double y;
    private final double z;

    public Vector3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static Vector3D get(double x, double y, double z) {
        return new Vector3D(x,y,z);
    }

    public static Vector3D zero() {
        return new Vector3D(0,0,0);
    }

    public double x() {
        return x;
    }
    public double y() {
        return y;
    }
    public double z() {
        return z;
    }

    public Vector3D add(Vector3D other) {
        return new Vector3D(x + other.x, y + other.y, z + other.z);
    }

    @Override
    public String toString() {
        return String.format("%.3f; %.3f; %.3f", x, y, z);
    }
}
