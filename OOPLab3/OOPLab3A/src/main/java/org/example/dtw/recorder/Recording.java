package org.example.dtw.recorder;

import org.example.vector.Vector3D;

import java.util.List;

public class Recording {
    private final List<Vector3D> values;
    private final String tag;

    public List<Vector3D> getValues() {
        return values;
    }


    public String getTag() {
        return tag;
    }

    public Recording(String tag, List<Vector3D> vectors) {
        this.tag = tag;
        this.values = vectors;
    }
}
