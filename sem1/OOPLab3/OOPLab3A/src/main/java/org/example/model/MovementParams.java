package org.example.model;

public class MovementParams {
    private double xAngle;
    private double yAngle;
    private double zAngle;
    private int speed;
    public MovementParams() {
        xAngle = 0;
        yAngle = 0;
        zAngle = 0;
        speed = 0;
    }
    public MovementParams(double x, double y, double z, int speed) {
        this.xAngle = x;
        this.yAngle = y;
        this.zAngle = z;
        this.speed = speed;
    }

    public double getXAngle() {
        return xAngle;
    }

    public void setXAngle(double xAngle) {
        this.xAngle = xAngle;
    }

    public double getYAngle() {
        return yAngle;
    }

    public void setYAngle(double yAngle) {
        this.yAngle = yAngle;
    }

    public double getZAngle() {
        return zAngle;
    }

    public void setZAngle(double zAngle) {
        this.zAngle = zAngle;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
