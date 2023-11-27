package org.example.model;

import org.example.engine.ConstUtils;

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
    public Vector2I translate(ScalableSprite sprite, Vector3D position3D) {
        return translate2D(sprite, position3D);
    }

    private Vector2I translate2D(ScalableSprite sprite, Vector3D position3D) {
        double extraLen = depthXCoefficient * (alphaSide + ConstUtils.depth - position3D.z()) / alphaSide;
        double x = middle + extraLen * (position3D.x() - middle);
        double y = skyline + zRatio * (position3D.z() - ConstUtils.depth);
        double scale = minScale + scaleM * (ConstUtils.depth - position3D.z());
        double height = depthYCoefficient * position3D.y() * (1 - zRatio);

        y -= height;
        sprite.setScale(scale);
        Vector2I position = Vector2I.get((int) x, (int) y);
        return centralize(sprite, position);
    }

    private Vector2I centralize(ScalableSprite sprite, Vector2I position) {
        return position.subtract(sprite.getCurrentSize().multiply(0.5));
    }

}
