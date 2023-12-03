package univ.lab.ninjagame1.client.mode;

import android.hardware.SensorManager;


import java.util.List;

import univ.lab.ninjagame1.client.AdvancedCommunicator;
import univ.lab.ninjagame1.client.Communicator;
import univ.lab.ninjagame1.client.MovementParams;
import univ.lab.ninjagame1.controller.GameState;
import univ.lab.ninjagame1.controller.UIManager;
import univ.lab.ninjagame1.dto.DesktopDTO;
import univ.lab.ninjagame1.filtered.OrientationManager;
import univ.lab.ninjagame1.filtered.Vector3;
import univ.lab.ninjagame1.movement.MovementManager;

public class ModeManager {
    private int currentMode = GameState.CALIBRATING; //calibrating

    private OrientationManager orientationManager;
    private MovementManager movementManager;
    private AdvancedCommunicator advancedCommunicator;
    private UIManager uiManager;
    public int getCurrentMode() {
        return currentMode;
    }

    public void setCurrentMode(int mode) {
        this.currentMode = mode;
    }
    public void onReceiveMessage(DesktopDTO desktopDTO) {
        if (desktopDTO.getGameState() == GameState.CALIBRATING) {
            currentMode = GameState.CALIBRATING;
        }
        if (desktopDTO.getGameState() == GameState.RECORDING) {
            currentMode = GameState.RECORDING;
        }
    }
    public void onPauseEvent() {
        if (currentMode != GameState.CALIBRATING) {
            currentMode = GameState.CALIBRATING;
        } else {
            currentMode = GameState.SHOOTING;
        }
        advancedCommunicator.sendModeSwitch(currentMode);
    }
    private boolean recording = false;
    public void onRecordEvent() {
        if (!isRecordingMode())
            return;
        if (recording) {
            uiManager.startRecording();
            movementManager.startAccelerometerRecording();
        } else {
            uiManager.stopRecording();
            List<Vector3> v3 = movementManager.stopAccelerometerRecording();
            advancedCommunicator.sendRecordedData(v3);
        }
        recording = !recording;
    }

    private boolean isRecordingMode() {
        return currentMode == GameState.RECORDING;
    }

    public void handleFling(MovementParams movementParams) {
        if (currentMode != GameState.SHOOTING) {
            return;
        }
        advancedCommunicator.sendMovementParams(movementParams);
    }
}
