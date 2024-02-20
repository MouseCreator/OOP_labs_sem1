package univ.lab.ninjagame1.client.mode;

import android.util.Log;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import univ.lab.ninjagame1.client.AdvancedCommunicator;
import univ.lab.ninjagame1.client.MovementParams;
import univ.lab.ninjagame1.controller.GameState;
import univ.lab.ninjagame1.controller.UIManager;
import univ.lab.ninjagame1.controller.timer.TimedAction;
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
        onLeave();
        switch (desktopDTO.getGameState()) {
            case GameState.CALIBRATING:
                currentMode = GameState.CALIBRATING;
                uiManager.onPause();
                break;
            case GameState.RECORDING:
                currentMode = GameState.RECORDING;
                uiManager.changeActivity(true);
                break;
            case GameState.START_RECORDING:
                movementManager.startAccelerometerRecording();
                break;
            case GameState.STOP_RECORDING:
                List<Vector3> v3 = movementManager.stopAccelerometerRecording();
                advancedCommunicator.sendRecordedData(v3);
                break;
            case GameState.SHOOTING:
                currentMode = GameState.SHOOTING;
                uiManager.onContinue();
                break;
            case GameState.FIGHTING:
                currentMode = GameState.FIGHTING;
                break;
            default:
                Log.d("MODE", "Unknown mode " + desktopDTO.getGameState());
        }
    }

    private void onLeave() {
        if (currentMode == GameState.CALIBRATING) {
            movementManager.resetOrientation();
        }
    }

    public void onPauseEvent() {
        if (currentMode != GameState.CALIBRATING) {
            currentMode = GameState.CALIBRATING;
            uiManager.onPause();
        } else {
            currentMode = GameState.SHOOTING;
            uiManager.onContinue();
        }
        advancedCommunicator.sendModeSwitch(currentMode);
    }
    private final AtomicBoolean recording = new AtomicBoolean(false);
    public void onRecordEvent() {
        if (!isRecordingMode())
            return;
        if (recording.get()) {
            return;
        }
        uiManager.startRecording();
        movementManager.startAccelerometerRecording();
        recording.set(true);
        TimedAction.executeAfter(2000, ()->{
            uiManager.stopRecording();
            List<Vector3> v3 = movementManager.stopAccelerometerRecording();
            advancedCommunicator.sendRecordedData(v3);
            recording.set(false);
        });
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
