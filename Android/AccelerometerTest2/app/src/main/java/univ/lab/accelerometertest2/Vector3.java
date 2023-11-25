package univ.lab.accelerometertest2;

public class Vector3 {
    private double x;
    private double y;
    private double z;

    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3(Vector3 other) {
        this.x = other.x;
        this.y = other.y;
        this.z = other.z;
    }

    public static Vector3 zero() {
        return new Vector3(0,0,0);
    }

    public Vector3 add(Vector3 addend) {
        return new Vector3(x + addend.x, y + addend.y, z + addend.z);
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

    public Vector3 multiply(double value) {
        return new Vector3(x * value, y * value, z * value);
    }

    public Vector3 subtract(Vector3 accel) {
        return new Vector3(x - accel.x, y - accel.y, z - accel.z);
    }

    public double magnitude() {
        return Math.sqrt(x*x + y*y + z*z);
    }

    public Vector3 divide(Vector3 byElements) {
        return new Vector3(x / byElements.x, y / byElements.y, z / byElements.z);
    }
}
