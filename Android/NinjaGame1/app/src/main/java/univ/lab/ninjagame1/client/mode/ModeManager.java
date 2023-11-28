package univ.lab.ninjagame1.client.mode;

import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import univ.lab.ninjagame1.client.Communicator;
import univ.lab.ninjagame1.client.recording.RecordingManager;
import univ.lab.ninjagame1.filtered.OrientationManager;
import univ.lab.ninjagame1.filtered.Vector3;

public class ModeManager {
    private int currentMode = 3; //calibrating
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private OrientationManager orientationManager;
    private RecordingManager recordingManager;
    private Communicator communicator;
    public int getCurrentMode() {
        try {
            readWriteLock.readLock().lock();
            return currentMode;
        } finally {
            readWriteLock.readLock().unlock();
        }
    }
    public void postConstruct(Communicator communicator, RecordingManager recordingManager, OrientationManager orientationManager) {
        this.communicator = communicator;
        this.recordingManager = recordingManager;
        this.orientationManager = orientationManager;
    }
    public boolean isRecordMode() {
        try {
            readWriteLock.readLock().lock();
            return currentMode == 4;
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    public void switchToMode(int mode) {
        int prev = this.currentMode;
        try {
            readWriteLock.writeLock().lock();
            this.currentMode = mode;
            if (currentMode == prev) {
                return;
            }
            exitMode(prev);
            updateMode();
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    private void exitMode(int prev) {
        if (prev == 0) {
            orientationManager.stop();
        } else if (prev == 1) {
            List<Vector3> values = recordingManager.summarize(orientationManager.sensorManager());
            communicator.sendMessageType(values);
        }
    }

    private void updateMode() {
        switch (currentMode) {
            case 0:
                orientationManager.start();
                break;
            case 1:
                recordingManager.start(orientationManager.sensorManager());
                break;
        }
    }
}
