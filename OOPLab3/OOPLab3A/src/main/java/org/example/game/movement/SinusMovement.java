package org.example.game.movement;

import org.example.vector.Vector3D;

public class SinusMovement implements Movement{
    private final double zSpeed;
    private final double amplitude;
    private final double frequency;
    private final double step;
    private double phase;
    private final double originX;

    public SinusMovement(double amplitude, double frequency, double step, double phase, double zSpeed, double originX) {
        this.amplitude = amplitude;
        this.frequency = frequency;
        this.step = step;
        this.zSpeed = zSpeed;
        this.phase = phase;
        this.originX = originX;
    }

    @Override
    public Vector3D nextPosition(Vector3D cp) {
        Vector3D pos = estimate(cp);
        phase += step;
        return pos;
    }

    @Override
    public Vector3D estimate(Vector3D currentPosition) {
        double p = phase + step;
        double newX = originX + amplitude * Math.sin(frequency * p);
        return new Vector3D(newX, currentPosition.y(), currentPosition.z() - zSpeed);
    }
}
