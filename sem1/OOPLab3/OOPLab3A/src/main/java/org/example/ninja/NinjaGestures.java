package org.example.ninja;

import org.example.dtw.AdvancedDTW;
import org.example.dtw.DTW;
import org.example.dtw.distance.EuclideanDistance;
import org.example.dtw.recorder.GestureManager;
import org.example.dtw.recorder.Recording;
import org.example.utils.ConstUtils;
import org.example.log.Log;
import org.example.vector.Vector3D;

import java.util.List;
public class NinjaGestures {

    private List<Recording> recordings;
    public void load() {
        GestureManager gestureManager = new GestureManager();
        recordings = gestureManager.readRecordings();
    }
    public boolean isClosestTo(String tag, Recording recording) {
        return tag.equalsIgnoreCase(recognize(recording));
    }
    public String recognize(Recording recording) {
        DTW<Vector3D> dtw = new AdvancedDTW<>(new EuclideanDistance());
        String tag = "";
        double min = ConstUtils.DISTANCE_BAND;
        for (Recording r : recordings) {
            Log.write(r);
            double distance = dtw.dtwNormalizedDistance(recording.getValues(), r.getValues());
            Log.write("Distance", distance);
            if (distance < min) {
                min = distance;
                tag = r.getTag();
            }
        }
        Log.write(tag);
        Log.write(min);
        return tag;
    }
}
