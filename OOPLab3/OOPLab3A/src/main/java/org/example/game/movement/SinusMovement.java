package org.example.game.movement;

import org.example.vector.Vector3D;

public class SinusMovement implements Movement{
    private final double xSpeed;
    private final double amplitude;
    private final double frequency;
    private final double step;
    private final double phase;

    public SinusMovement(double amplitude, double frequency, double step, double phase, double xSpeed) {
        this.amplitude = amplitude;
        this.frequency = frequency;
        this.step = step;
        this.xSpeed = xSpeed;
        this.phase = phase;
    }

    @Override
    public Vector3D nextPosition(Vector3D cp) {
        double currentTime = cp.z() * frequency / amplitude + phase;
        double newZ = amplitude * Math.sin(2 * Math.PI * frequency * (currentTime + step));
        return new Vector3D(cp.x() + xSpeed, cp.y(), newZ);
    }
}
