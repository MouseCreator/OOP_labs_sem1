package org.example.ninja;

import org.example.dtw.recorder.Recorder;
import org.example.dtw.recorder.Recording;

import java.util.Optional;

public class SwordManager {

    private final NinjaGestures gestures;

    public SwordManager() {
        gestures = new NinjaGestures();
        gestures.load();
    }

    public boolean process(String expected, String input) {
        Recorder recorder = new Recorder();
        Optional<Recording> record = recorder.record(input);
        if (record.isEmpty()) {
            return false;
        }
        System.out.println(record.get());
        return record.filter(recording -> gestures.isClosestTo(expected, recording)).isPresent();
    }
}
