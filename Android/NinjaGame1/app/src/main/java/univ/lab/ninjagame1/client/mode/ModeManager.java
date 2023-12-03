package univ.lab.ninjagame1.client.mode;

import android.util.Log;


import java.util.List;

import univ.lab.ninjagame1.client.AdvancedCommunicator;
import univ.lab.ninjagame1.client.MovementParams;
import univ.lab.ninjagame1.controller.GameState;
import univ.lab.ninjagame1.controller.UIManager;
import univ.lab.ninjagame1.controller.processor.EventProcessor;
import univ.lab.ninjagame1.controller.processor.EventProcessorImpl;
import univ.lab.ninjagame1.dto.DesktopDTO;
import univ.lab.ninjagame1.filtered.Vector3;
import univ.lab.ninjagame1.movement.MovementManager;

public class ModeManager {
    private int currentMode = GameState.CALIBRATING;
    private final MovementManager movementManager;
    private AdvancedCommunicator advancedCommunicator;
    private final UIManager uiManager;
    public ModeManager(MovementManager movementManager, UIManager uiManager) {
        this.movementManager = movementManager;
        this.uiManager = uiManager;
    }
    public void bind(AdvancedCommunicator communicator) {
        advancedCommunicator = communicator;
        advancedCommunicator.setOnReceive(this::onReceiveMessage);
    }
    public void onReceiveMessage(DesktopDTO desktopDTO) {
        switch (desktopDTO.getGameState()) {
            case GameState.CALIBRATING:
                currentMode = GameState.CALIBRATING;
                break;
            case GameState.RECORDING:
                currentMode = GameState.RECORDING;
                break;
            case GameState.START_RECORDING:
                movementManager.startAccelerometerRecording();
                break;
            case GameState.STOP_RECORDING:
                List<Vector3> v3 = movementManager.stopAccelerometerRecording();
                advancedCommunicator.sendRecordedData(v3);
                break;
            default:
                Log.d("MODE", "Unknown mode " + desktopDTO.getGameState());
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
