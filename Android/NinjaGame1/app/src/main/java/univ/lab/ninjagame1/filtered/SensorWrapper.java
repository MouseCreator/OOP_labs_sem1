package univ.lab.ninjagame1.filtered;

import android.hardware.SensorEvent;

public class SensorWrapper {
    private SensorEvent lastEvent;
    public SensorWrapper(SensorEvent lastEvent) {
        this.lastEvent = lastEvent;
    }
    public void update(SensorEvent event) {
        this.lastEvent = event;
    }
    public SensorEvent get() {
        return lastEvent;
    }
}
