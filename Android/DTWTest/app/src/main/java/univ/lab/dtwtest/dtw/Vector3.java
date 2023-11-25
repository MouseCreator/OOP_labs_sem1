package univ.lab.dtwtest.dtw;

public class Vector3 {
    private final double x;
    private final double y;
    private final double z;
    public double x() {
        return x;
    }
    public double y() {
        return y;
    }
    public double z() {
        return z;
    }

    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public Vector3 subtract(Vector3 v2) {
        return new Vector3(x - v2.x, y - v2.y, z - v2.z);
    }
    public double magnitude() {
        return Math.sqrt(x*x + y*y + z*z);
    }
    public Vector3 multiply(double value) {
        return new Vector3(x*value, y*value, z*value);
    }
    public Vector3 add(Vector3 v2) {
        return new Vector3(x + v2.x, y + v2.y, z + v2.z);
    }
}
