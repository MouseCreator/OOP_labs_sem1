package univ.lab.ninjagame1.game;

public class Vector2 {
    private double x;
    private double y;
    public Vector2(double positionX, double positionY) {
        this.x = positionX;
        this.y = positionY;
    }

    public Vector2(Vector2 position) {
        x = position.x;
        y = position.y;
    }

    public static Vector2 get(double x, double y) {
        return new Vector2(x, y);
    }

    public void from(Vector2 vector) {
        this.x = vector.x;
        this.y = vector.y;
    }

    public double x() {
        return x;
    }
    public double y() {
        return y;
    }
    public float xf() {
        return (float) x;
    }
    public float yf() {
        return (float) y;
    }
}
