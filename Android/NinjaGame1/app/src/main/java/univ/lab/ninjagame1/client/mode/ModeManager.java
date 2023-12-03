package univ.lab.ninjagame1.client.mode;

import android.hardware.SensorManager;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import univ.lab.ninjagame1.client.Communicator;
import univ.lab.ninjagame1.controller.GameState;
import univ.lab.ninjagame1.filtered.OrientationManager;
import univ.lab.ninjagame1.movement.MovementManager;

public class ModeManager {
    private int currentMode = GameState.CALIBRATING; //calibrating
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private OrientationManager orientationManager;
    private MovementManager movementManager;
    private Communicator communicator;
    private SensorManager sensorManager;
    public int getCurrentMode() {
        try {
            readWriteLock.readLock().lock();
            return currentMode;
        } finally {
            readWriteLock.readLock().unlock();
        }
    }
    public void postConstruct(SensorManager sensorManager, Communicator communicator, MovementManager movementManager, OrientationManager orientationManager) {
        this.communicator = communicator;
        this.sensorManager = sensorManager;
        this.movementManager = movementManager;
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
            updateMode();
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    private void updateMode() {

    }

    public void onPauseEvent() {

    }

    public void onRecordEvent() {

    }
}
