package univ.lab.ninjagame1.movement;

import java.util.List;

import univ.lab.ninjagame1.filtered.Vector3;

public interface MovementManager {
    Vector3 getCurrentOrientation();
    void resetOrientation();
    void startAccelerometerRecording() ;
    List<Vector3> stopAccelerometerRecording();
    void begin();
    void stop();

}
