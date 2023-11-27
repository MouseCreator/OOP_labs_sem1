package univ.lab.ninjagame1.client;

public class MovementParams {
    private final int speed;
    private final double xAngle;
    private final double yAngle;
    private final double zAngle;

    public MovementParams(int speed, double xAngle, double yAngle, double zAngle) {
        this.speed = speed;
        this.xAngle = xAngle;
        this.yAngle = yAngle;
        this.zAngle = zAngle;
    }

    public int getSpeed() {
        return speed;
    }

    public double getxAngle() {
        return xAngle;
    }

    public double getyAngle() {
        return yAngle;
    }

    public double getzAngle() {
        return zAngle;
    }
}
