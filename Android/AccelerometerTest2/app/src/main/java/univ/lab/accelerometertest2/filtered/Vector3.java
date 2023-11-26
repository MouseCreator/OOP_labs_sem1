package univ.lab.accelerometertest2.filtered;

public class Vector3 {
    private final double x;
    private final double y;
    private final double z;

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

    public Vector3(float[] arr) {
        this.x = arr.length < 1 ? 0 : arr[0];
        this.y = arr.length < 2 ? 0 : arr[1];
        this.z = arr.length < 3 ? 0 : arr[2];
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
