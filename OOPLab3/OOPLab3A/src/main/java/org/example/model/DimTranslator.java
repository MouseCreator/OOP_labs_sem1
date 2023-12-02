package org.example.model;

import org.example.engine.ConstUtils;
import org.example.game.helper.SizeComponent;
import org.example.vector.Vector2I;
import org.example.vector.Vector3D;

public class DimTranslator {
    private static DimTranslator translator = null;
    public static DimTranslator get() {
        if (translator == null)
            translator = new DimTranslator();
        return translator;
    }

    private DimTranslator() {
        define();
    }
    private double middle;
    private double alphaSide;
    private double depthXCoefficient;
    private double depthYCoefficient;
    private int skyline;
    private double zRatio;
    private double maxScale;
    private double minScale;
    private double scaleM;

    public Vector2I toCenter(Vector2I originPosition, Vector2I size) {
        return originPosition.subtract(size.multiply(0.5));
    }

    private void define() {
        depthXCoefficient = 0.6;
        depthYCoefficient = 0.4;
        middle = ConstUtils.worldWidth / 2.0;
        alphaSide = ConstUtils.depth / (1.0 / depthXCoefficient - 1);
        skyline = 292;
        zRatio = (skyline - ConstUtils.worldHeight) / (double) ConstUtils.depth;
        maxScale = 2.0;
        minScale = 0.2;
        scaleM = (maxScale - minScale) / (double) ConstUtils.depth;
    }
    public Vector2I translate(SizeComponent sizeComponent, Vector3D position3D) {
        return translate2D(sizeComponent, position3D);
    }

    private Vector2I translate2D(SizeComponent sizeComponent, Vector3D position3D) {
        double extraLen = depthXCoefficient * (alphaSide + ConstUtils.depth - position3D.z()) / alphaSide;
        double x = middle + extraLen * (position3D.x() - middle);
        double y = skyline + zRatio * (position3D.z() - ConstUtils.depth);
        double scale = minScale + scaleM * (ConstUtils.depth - position3D.z());
        double height = depthYCoefficient * position3D.y() * (1 - zRatio);

        y -= height;
        sizeComponent.setScale(scale);
        Vector2I position = Vector2I.get((int) x, (int) y);
        return centralize(sizeComponent, position);
    }

    private Vector2I centralize(SizeComponent sizeComponent, Vector2I position) {
        return position.subtract(sizeComponent.getCurrentSize().multiply(0.5));
    }

    public Vector2I fromCenter(Vector2I origin, Vector2I size) {
        return origin.add(size.multiply(0.5));
    }
}
