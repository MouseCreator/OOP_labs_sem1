package univ.lab.ninjagame1.filtered;

import java.util.concurrent.atomic.AtomicReference;

public class SensorWrapper {
    private final AtomicReference<Vector3> lastEvent;
    public SensorWrapper() {
        lastEvent = new AtomicReference<>(null);
    }
    public void update(Vector3 event) {
        this.lastEvent.set(event);
    }
    public Vector3 get() {
        return lastEvent.get();
    }
    public void reset() {
        lastEvent.set(null);
    }
}
