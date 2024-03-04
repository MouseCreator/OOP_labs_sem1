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

    public Vector3D(Vector3D vector3D) {
        this.x = vector3D.x;
        this.y = vector3D.y;
        this.z = vector3D.z;
    }

    public static Vector3D get(double x, double y, double z) {
        return new Vector3D(x,y,z);
    }

    public static Vector3D zero() {
        return new Vector3D(0,0,0);
    }

    public static Vector3D from(Vector3D originalPosition) {
        return new Vector3D(originalPosition.x, originalPosition.y, originalPosition.z);
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

    public double magnitude() {
        return Math.sqrt(x*x+y*y+z*z);
    }

    public Vector3D subtract(Vector3D v2) {
        return new Vector3D(x - v2.x, y - v2.y, z - v2.z);
    }

    public Vector3D normalize() {
        double m = magnitude();
        if (m == 0) {
            return Vector3D.zero();
        }
        return get(x / m, y / m, z / m);
    }

    public Vector3D multiply(double scalar) {
        return get(x * scalar, y * scalar, z * scalar);
    }
}
