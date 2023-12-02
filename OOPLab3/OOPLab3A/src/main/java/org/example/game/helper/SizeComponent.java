package org.example.game.helper;

import org.example.vector.Vector2I;

public class SizeComponent {
    private Vector2I initialSize;
    private double scale;
    private Vector2I currentSize;

    public SizeComponent(Vector2I initialSize) {
        this.initialSize = initialSize;
        scale = 1.0;
        currentSize = initialSize;
    }

    public SizeComponent() {
        this.initialSize = Vector2I.zero();
        scale = 1.0;
        currentSize = initialSize;
    }

    public void setSize(Vector2I initialSize) {
        this.initialSize = initialSize;
        setScale(this.scale);
    }

    public void setScale(double scale) {
        this.scale = scale;
        currentSize = initialSize.multiply(scale);
    }
    public Vector2I getCurrentSize() {
        return currentSize;
    }

    public double getScale() {
        return scale;
    }
}
