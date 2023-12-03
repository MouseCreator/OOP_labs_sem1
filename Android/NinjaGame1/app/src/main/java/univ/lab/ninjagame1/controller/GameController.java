package univ.lab.ninjagame1.controller;

import android.content.Context;
import android.hardware.SensorManager;
import android.view.GestureDetector;

import univ.lab.ninjagame1.client.Communicator;
import univ.lab.ninjagame1.client.mode.ModeManager;
import univ.lab.ninjagame1.client.recording.RecordingManager;
import univ.lab.ninjagame1.filtered.OrientationManager;

public class GameController {
    private GestureDetector gestureDetector;
    private Communicator communicator;
    private OrientationManager orientationManager;
    private ModeManager modeManager;
    private RecordingManager recordingManager;
    private SensorManager sensorManager;
    private InputListener inputListener;
    private Context context;
    private UIManager uiManager;
    private GameController() {

    }
    public static GameController create(Context context, UIManager uiManager) {
        GameController gameController = new GameController();
        gameController.context = context;
        gameController.uiManager = uiManager;
        gameController.initialize();
        return gameController;
    }

    private void initialize() {
    }

    public InputListener getInputListener() {
        return inputListener;
    }

    public void run() {

    }
}
