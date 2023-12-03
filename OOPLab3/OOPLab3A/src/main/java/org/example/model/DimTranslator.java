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
    public Vector2I toCenter(Vector2I originPosition, Vector2I size) {
        return originPosition.add(size.multiply(0.5));
    }
    private static Vector3D cameraPos = Vector3D.zero();
    private static Vector2I windowOffset = Vector2I.zero();
    private void define() {
        double middle = ConstUtils.worldWidth / 2.0;
        int midHeight = ConstUtils.worldHeight / 2;
        int skyline = 292;
        cameraPos = Vector3D.get(middle, midHeight, -100);

        double cameraHeight = Math.abs(cameraPos.y());
        double cameraDepth = Math.abs(cameraPos.z());
        double yAdd = skyline - ConstUtils.worldWidth + (ConstUtils.depth * cameraHeight) / (cameraDepth + ConstUtils.depth);
        int ySubs = ConstUtils.worldWidth + (int) yAdd;
        windowOffset = Vector2I.get(0, ySubs);

    }
    public Vector2I translate(SizeComponent sizeComponent, Vector3D position3D) {
        return translate2D(sizeComponent, position3D);
    }

    private Vector2I translate2D(SizeComponent sizeComponent, Vector3D position3D) {
        double x = calculateX(position3D, cameraPos);
        double y = calculateY(position3D, cameraPos);
        double scale = -cameraPos.z() / (-cameraPos.z() + position3D.z());
        sizeComponent.setScale(scale);
        Vector2I position = Vector2I.get((int) x, (int) y);
        return toCamera(position, sizeComponent);
    }

    private Vector2I toCamera(Vector2I position, SizeComponent sizeComponent) {
        Vector2I v = Vector2I.get(position.x(), -position.y());
        Vector2I r = v.add(windowOffset);
        return fromCenter(r, sizeComponent.getCurrentSize());
    }

    private double calculateX(Vector3D a, Vector3D c) {
        return a.x() - (a.z() / (c.z() - a.z())) * (c.x() - a.x());
    }
    private double calculateY(Vector3D a, Vector3D c) {
        return a.y() - (a.z() / (c.z() - a.z())) * (c.y() - a.y());
    }

    public Vector2I fromCenter(Vector2I origin, Vector2I size) {
        return origin.subtract(size.multiply(0.5));
    }
}
